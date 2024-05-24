package helha.java24groupe08.client.views;

import helha.java24groupe08.client.controllers.AlertUtils;
import helha.java24groupe08.client.controllers.SideWindowController;
import helha.java24groupe08.client.models.Session;
import helha.java24groupe08.client.models.SessionDBController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SideWindowViewController {
    public GridPane modifyMoviePane;
    private Listener listener;

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
    public String[] movieDetails;
    public Consumer<String> deleteMovieCallback;
    public Consumer<String[]> updateMovieCallback;

    SideWindowController sideWindowController;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

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

        sideWindowController = new SideWindowController();
        sideWindowController.setViewController(this);
        setListener(sideWindowController);
    }

    public void initData(String[] movieDetails, Consumer<String> deleteMovieCallback, Consumer<String[]> updateMovieCallback) {
        this.movieDetails = movieDetails;
        this.deleteMovieCallback = deleteMovieCallback;
        this.updateMovieCallback = updateMovieCallback;

        sideWindowController.initData(movieDetails, deleteMovieCallback, updateMovieCallback);

        titleField.setText(movieDetails[0]);
        plotArea.setText(movieDetails[9]);

        initSessionTable();
    }

    @FXML
    private void saveChangesOnAction(ActionEvent event) {
        if(listener != null) {
            String newTitle = titleField.getText();
            String newPlot = plotArea.getText();
            listener.saveChangesOnAction(event, newTitle, newPlot);
        }
    }

    @FXML
    private void deleteMovieAction(ActionEvent event) {
        if(listener != null) {
            listener.deleteMovieAction(event);
        }
    }

    public void initSessionTable() {
        try {
            List<Session> sessions = SessionDBController.getSessionsByMovieId(Integer.parseInt(movieDetails[14]));
            sessionTable.setItems(FXCollections.observableArrayList(sessions));
        } catch (Exception e) {
            AlertUtils.showErrorAlert("Invalid MovieID format: " + e.getMessage());
        }
    }

    @FXML
    private void addSessionAction(ActionEvent event) {
        if(listener != null) {
            Integer roomNumber = roomNumberComboBox.getValue();
            Time startTime = parseStartTime();
            Date date = parseDate();
            listener.addSessionAction(event, roomNumber, startTime, date);
        }
    }

    @FXML
    private void deleteSessionAction(ActionEvent event) {
        if(listener != null) {
            Session selectedSession = sessionTable.getSelectionModel().getSelectedItem();
            listener.deleteSessionAction(event, selectedSession);
        }
    }

    public Time parseStartTime() {
        String hour = hourComboBox.getValue();
        String minute = minuteComboBox.getValue();
        String timeString = hour + ":" + minute + ":00";
        return Time.valueOf(timeString);
    }

    public Date parseDate() {
        java.time.LocalDate localDate = dateField.getValue();
        if (localDate == null) {
            AlertUtils.showErrorAlert("Date is required.");
            return null;
        }
        return Date.valueOf(localDate);
    }

    public interface Listener {
        void deleteMovieAction(ActionEvent event);
        void deleteSessionAction(ActionEvent event, Session session);

        void saveChangesOnAction(ActionEvent event, String newTitle, String newPlot);

        void addSessionAction(ActionEvent event, Integer roomNumber, Time startTime, Date date);
    }
}
