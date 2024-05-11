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

import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

public class SideWindowController {

    private String[] movieDetails;
    private Consumer<String> deleteMovieCallback;
    private Consumer<String[]> updateMovieCallback;
    private IndexViewController indexViewController;

    // Reference to fxml fields
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
        roomNumberComboBox.getSelectionModel().selectFirst(); // Select the first room by default

        // Initialiser les heures
        List<String> hours = IntStream.range(0, 24)
                .mapToObj(h -> String.format("%02d", h))
                .collect(Collectors.toList());
        hourComboBox.getItems().setAll(hours);
        hourComboBox.getSelectionModel().selectFirst(); // Sélectionnez la première heure par défaut

        // Initialiser les minutes
        List<String> minutes = IntStream.range(0, 60)
                .mapToObj(m -> String.format("%02d", m))
                .collect(Collectors.toList());
        minuteComboBox.getItems().setAll(minutes);
        minuteComboBox.getSelectionModel().selectFirst(); // Sélectionnez la première minute par défaut

    }




    /**
     * This method initializes the side window with the movie details and the delete movie callback.
     *
     * @param movieDetails        The details of the movie to be displayed in the side window.
     * @param deleteMovieCallback The callback function to be called when the delete button is clicked.
     */
    public void initData(String[] movieDetails, Consumer<String> deleteMovieCallback, Consumer<String[]> updateMovieCallback) {
        this.movieDetails = movieDetails;
        this.deleteMovieCallback = deleteMovieCallback;
        this.updateMovieCallback = updateMovieCallback;

        titleField.setText(movieDetails[0]); // title is at index 0
        plotArea.setText(movieDetails[9]); // plot is at index 9

        initSessionTable(); // Initialize the session table
    }

    /**
     * This method sets the index view controller.
     *
     * @param indexViewController The index view controller.
     */
    public void setIndexViewController(IndexViewController indexViewController) {
        this.indexViewController = indexViewController;
    }

    @FXML
    private void saveChangesOnAction(ActionEvent event) {
        try{
            // Get the new title and plot from the text fields
            String oldTitle = movieDetails[0];
            String newTitle = titleField.getText();
            String newPlot = plotArea.getText();

            // Update the movie details
            movieDetails[0] = newTitle;
            movieDetails[9] = newPlot;

            // Update the movie in the database
            MovieDBController.updateMovieDetails(oldTitle, movieDetails);

            // Call the update callback
            if (updateMovieCallback != null) {
                updateMovieCallback.accept(movieDetails);
            }

            // Close the side window
            ((Button) event.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
            showErrorAlert("Error updating movie details: " + e.getMessage());
        }
    }

    /**
     * This method is called when the delete button is clicked.
     * It calls the deleteMovieCallback function with the movie title as the argument.
     * It also closes the side window.
     *
     * @param event The action event triggered by the delete button click.
     */
    @FXML
    private void deleteMovieAction(ActionEvent event) {
        try{
            String movieTitle = movieDetails[0];
            if (deleteMovieCallback != null) {
                deleteMovieCallback.accept(movieTitle);
            }
            // Close the side window
            ((Button) event.getSource()).getScene().getWindow().hide();
        } catch (NullPointerException e) {
            showErrorAlert("The delete callback function is not set : " + e.getMessage());
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
        LocalDate localDate = dateField.getValue();
        if (localDate == null) {
            showErrorAlert("Date is required.");
            return null;
        }
        return Date.valueOf(localDate);  // Converts LocalDate to java.sql.Date
    }


    @FXML
    private void addSessionAction(ActionEvent event) {
        Integer roomNumber = roomNumberComboBox.getValue();
        Time startTime = parseStartTime();
        Date date = parseDate();

        if (roomNumber == null || startTime == null || date == null) {
            return;  // Exit if any critical error in parsing
        }

        try {
            Session session = new Session(roomNumber, startTime, date, Integer.parseInt(movieDetails[14]));
            MovieDBController.insertSession(session);
            initSessionTable(); // Refresh the table or update UI
        } catch (Exception e) {
            showErrorAlert("Error adding session: " + e.getMessage());
        }
    }



    @FXML
    private void deleteSessionAction(ActionEvent event) {
        Session selectedSession = sessionTable.getSelectionModel().getSelectedItem();
        if (selectedSession != null) {
            MovieDBController.deleteSession(selectedSession.getSessionId());
            initSessionTable(); // Refresh the table
        } else {
            showErrorAlert("No session selected for deletion.");
        }
    }

}