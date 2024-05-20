package helha.java24groupe08.client.controllers;

import helha.java24groupe08.client.models.MovieDBController;
import helha.java24groupe08.client.models.Session;
import helha.java24groupe08.client.views.IndexViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.function.Consumer;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;


public class SideWindowController {

    private String[] movieDetails;
    private Consumer<String> deleteMovieCallback;
    private Consumer<String[]> updateMovieCallback;
    private IndexViewController indexViewController;

    @FXML
    private TextField titleField;

    @FXML
    private TextArea plotArea;

    @FXML
    private TableView<Session> sessionTable;

    @FXML
    private ComboBox<Integer> roomNumberComboBox;

    @FXML
    private ComboBox<String> hourComboBox;
    @FXML
    private ComboBox<String> minuteComboBox;

    @FXML
    private DatePicker dateField;

    @FXML
    private TableColumn<Session, Integer> roomNumberColumn;
    @FXML
    private TableColumn<Session, Time> startTimeColumn;
    @FXML
    private TableColumn<Session, Date> dateColumn;

    @FXML
    private void initialize() {
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        List<Integer> roomNumbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        roomNumberComboBox.getItems().setAll(roomNumbers);
        roomNumberComboBox.getSelectionModel().selectFirst();

        List<String> hours = IntStream.range(0, 24)
                .mapToObj(h -> String.format("%02d", h))
                .collect(Collectors.toList());
        hourComboBox.getItems().setAll(hours);
        hourComboBox.getSelectionModel().selectFirst();

        List<String> minutes = IntStream.range(0, 60)
                .mapToObj(m -> String.format("%02d", m))
                .collect(Collectors.toList());
        minuteComboBox.getItems().setAll(minutes);
        minuteComboBox.getSelectionModel().selectFirst();

        dateField.setValue(java.time.LocalDate.now());
    }

    public void initData(String[] movieDetails, Consumer<String> deleteMovieCallback, Consumer<String[]> updateMovieCallback) {
        this.movieDetails = movieDetails;
        this.deleteMovieCallback = deleteMovieCallback;
        this.updateMovieCallback = updateMovieCallback;

        titleField.setText(movieDetails[0]);
        plotArea.setText(movieDetails[9]);

        initSessionTable();
    }

    public void setIndexViewController(IndexViewController indexViewController) {
        this.indexViewController = indexViewController;
    }

    @FXML
    private void saveChangesOnAction(ActionEvent event) {
        try {
            String oldTitle = movieDetails[0];
            String newTitle = titleField.getText();
            String newPlot = plotArea.getText();

            movieDetails[0] = newTitle;
            movieDetails[9] = newPlot;

            MovieDBController.updateMovieDetails(oldTitle, movieDetails);

            if (updateMovieCallback != null) {
                updateMovieCallback.accept(movieDetails);
            }

            ((Button) event.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
            showErrorAlert("Error updating movie details: " + e.getMessage());
        }
    }

    @FXML
    private void deleteMovieAction(ActionEvent event) {
        try {
            String movieTitle = movieDetails[0];
            if (deleteMovieCallback != null) {
                deleteMovieCallback.accept(movieTitle);
            }
            ((Button) event.getSource()).getScene().getWindow().hide();
        } catch (NullPointerException e) {
            showErrorAlert("The delete callback function is not set: " + e.getMessage());
        }
    }

    private void showErrorAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void initSessionTable() {
        try {
            List<Session> sessions = MovieDBController.getSessionsByMovieId(Integer.parseInt(movieDetails[14]));
            sessionTable.setItems(FXCollections.observableArrayList(sessions));
        } catch (Exception e) {
            showErrorAlert("Invalid MovieID format: " + e.getMessage());
        }
    }

    private Time parseStartTime() {
        String hour = hourComboBox.getValue();
        String minute = minuteComboBox.getValue();
        String timeString = hour + ":" + minute + ":00";
        return Time.valueOf(timeString);
    }

    private Date parseDate() {
        java.time.LocalDate localDate = dateField.getValue();
        if (localDate == null) {
            showErrorAlert("Date is required.");
            return null;
        }
        return Date.valueOf(localDate);
    }

    @FXML
    private void addSessionAction(ActionEvent event) {
        Integer roomNumber = roomNumberComboBox.getValue();
        Time startTime = parseStartTime();
        Date date = parseDate();

        if (roomNumber == null || startTime == null || date == null) {
            return;
        }

        try {
            // Create a new session without the sessionId (use the overloaded constructor)
            Session session = new Session(roomNumber.intValue(), startTime, date, Integer.parseInt(movieDetails[14]));
            MovieDBController.insertSession(session);
            initSessionTable();
        } catch (Exception e) {
            showErrorAlert("Error adding session: " + e.getMessage());
        }
    }


    @FXML
    private void deleteSessionAction(ActionEvent event) {
        Session selectedSession = sessionTable.getSelectionModel().getSelectedItem();
        if (selectedSession != null) {
            MovieDBController.deleteSession(selectedSession.getSessionId());
            initSessionTable();
        } else {
            showErrorAlert("No session selected for deletion.");
        }
    }
}
