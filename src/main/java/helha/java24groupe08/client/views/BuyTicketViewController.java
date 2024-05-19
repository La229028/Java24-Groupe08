package helha.java24groupe08.client.views;

import helha.java24groupe08.client.controllers.BuyTicketController;
import helha.java24groupe08.client.models.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BuyTicketViewController {

    @FXML
    private Label totalLabel;
    @FXML
    private Spinner<Integer> quantitySpinnerRegular;
    @FXML
    private Spinner<Integer> quantitySpinnerChild;
    @FXML
    private Spinner<Integer> quantitySpinnerStudent;
    @FXML
    private Spinner<Integer> quantitySpinnerSenior;
    @FXML
    private Spinner<Integer> quantitySpinnerVIP;

    @FXML
    private TableView<Session> sessionTableView;
    @FXML
    private TableColumn<Session, String> dateColumn;
    @FXML
    private TableColumn<Session, String> startTimeColumn;
    @FXML
    private TableColumn<Session, Integer> seatsAvailableColumn;

    @FXML
    private Label movieTitleLabel;
    @FXML
    private TextArea moviePlotTextArea;
    @FXML
    private ImageView moviePosterImageView;

    private BuyTicketController controller;

    @FXML
    private Tab tabSeat;
    @FXML
    private ListView<String> recapListView;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Button nextButton;
    @FXML
    private TabPane tabPane;

    @FXML
    private Button addToCartButton;
    @FXML
    private GridPane seatsGrid;

    private Session selectedSession;

    private int TicketsSelected = 0;
    private List<String> selectedSeats = new ArrayList<>(); // Track selected seats

    @FXML
    private Button payButton;

    @FXML
    public void initialize() {
        controller = new BuyTicketController(this);

        // Setup listeners for the spinners
        quantitySpinnerRegular.valueProperty().addListener((obs, oldSelection, newSelection) -> {
            controller.updateTotal(new TicketsRegular("movie"), newSelection);
            clearSeat();
        });

        quantitySpinnerChild.valueProperty().addListener((obs, oldSelection, newSelection) -> {
            controller.updateTotal(new TicketsChild("movie", 10), newSelection);
            clearSeat();
        });

        quantitySpinnerStudent.valueProperty().addListener((obs, oldSelection, newSelection) -> {
            controller.updateTotal(new TicketsStudent("movie", "Some School Name"), newSelection);
            clearSeat();
        });

        quantitySpinnerSenior.valueProperty().addListener((obs, oldSelection, newSelection) -> {
            controller.updateTotal(new TicketsSenior("movie", 65), newSelection);
            clearSeat();
        });

        quantitySpinnerVIP.valueProperty().addListener((obs, oldSelection, newSelection) -> {
            controller.updateTotal(new TicketsVIP("movie"), newSelection);
        });

        // Setup the grid of seats
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                seatsGrid.add(createButton(row, col), col, row);
            }
        }

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        seatsAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("seatsAvailable"));
    }

    private void clearSeat() {
        seatsGrid.getChildren().clear();
        selectedSeats.clear(); // Clear selected seats list
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                seatsGrid.add(createButton(row, col), col, row);
            }
        }
    }

    @FXML
    private void handleAddToCart() {
        try {
            // Get the selected session
            Session selectedSession = sessionTableView.getSelectionModel().getSelectedItem();
            if (selectedSession == null) {
                System.out.println("No session selected.");
                return;
            }

            // Collect ticket information from the selected session
            String movie = movieTitleLabel.getText();
            Date date = selectedSession.getDate();
            Time time = selectedSession.getStartTime();
            String room = "Room " + selectedSession.getRoomNumber();
            String duration = "120 mins"; // This can be fetched from your data model if available

            // Convert date and time to strings
            String dateString = (date != null) ? date.toString() : "";
            String timeString = (time != null) ? time.toString() : "";

            // Create a TicketInfo object for each selected seat
            for (String seatNumber : selectedSeats) {
                TicketInfo ticketInfo = new TicketInfo(dateString, timeString, room, movie, duration, seatNumber);

                // Add to buffer
                Buffer.getInstance().addToCart(ticketInfo);
            }

            // Mark the selected seats as reserved
            markSeatsAsReserved(selectedSeats);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void markSeatsAsReserved(List<String> seatNumbers) {
        for (Node node : seatsGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (seatNumbers.contains(button.getText())) {
                    button.setStyle("-fx-background-color: red;");
                    System.out.println("Seat " + button.getText() + " marked as reserved.");
                }
            }
        }
    }

    public Button createButton(int row, int col) {
        Button seatButton = new Button();
        seatButton.setPrefWidth(30);
        seatButton.setPrefHeight(30);
        seatButton.setText(String.format("%s%d", (char) ('A' + row), col + 1)); // Example seat label
        seatButton.setStyle("-fx-background-color: white;");
        seatButton.setOnAction(event -> {
            GetTicketsSelected();
            int totalSeatsSelected = 0;
            for (Node node : seatsGrid.getChildren()) {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    if ("-fx-background-color: blue;".equals(button.getStyle())) {
                        totalSeatsSelected++;
                    }
                }
            }

            if ("-fx-background-color: blue;".equals(seatButton.getStyle())) {
                seatButton.setStyle("-fx-background-color: white;");
                selectedSeats.remove(seatButton.getText());
            } else if (totalSeatsSelected >= TicketsSelected) {
                displayError("Vous ne pouvez pas sélectionner plus de sièges que de tickets.");
            } else {
                seatButton.setStyle("-fx-background-color: blue;");
                selectedSeats.add(seatButton.getText());
            }
        });
        return seatButton;
    }

    void loadSessions(int movieId) {
        try {
            List<Session> sessions = MovieDBController.getSessionsByMovieId(movieId);
            sessionTableView.setItems(FXCollections.observableArrayList(sessions));
        } catch (Exception e) {
            System.err.println("Error loading sessions: " + e.getMessage());
        }
    }

    public void setMovieDetails(String[] movieDetails) {
        if (movieDetails != null && movieDetails.length > 14) {
            movieTitleLabel.setText(movieDetails[0]);
            moviePlotTextArea.setText(movieDetails[9]);
            loadSessions(Integer.parseInt(movieDetails[14]));
        }
    }

    public int GetTicketsSelected() {
        TicketsSelected = quantitySpinnerRegular.getValue() + quantitySpinnerChild.getValue() +
                quantitySpinnerStudent.getValue() + quantitySpinnerSenior.getValue() +
                quantitySpinnerVIP.getValue();
        return TicketsSelected;
    }

    public void updateTotal(String totalText) {
        totalLabel.setText(totalText);
        totalPriceLabel.setText(totalText);
    }

    public void updateRecap(Map<String, Long> ticketCounts) {
        recapListView.getItems().clear();
        ticketCounts.forEach((type, count) -> {
            recapListView.getItems().add(count + " " + type);
        });
    }

    public void displayError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public void displaySuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        alert.setTitle("Success Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public void updateSessionsForMovie(int movieId) {
        List<Session> sessions = MovieDBController.getSessionsByMovieId(movieId);
        sessionTableView.setItems(FXCollections.observableArrayList(sessions));
    }

    public void setSelectedSession(Session session) {
        this.selectedSession = session;
    }
}