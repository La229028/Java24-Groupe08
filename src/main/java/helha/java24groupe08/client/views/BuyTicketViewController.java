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
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javafx.application.Platform;
import java.util.concurrent.Executors;
import java.util.concurrent.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

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
    private TableView<Session> sessionTableView;
    @FXML
    private TableColumn<Session, Date> dateColumn;
    @FXML
    private TableColumn<Session, Time> startTimeColumn;
    @FXML
    private TableColumn<Session, Integer> seatsAvailableColumn;

    @FXML
    private Label movieTitleLabel;
    @FXML
    private TextArea moviePlotTextArea;

    private BuyTicketController controller;

    @FXML
    private Tab tabSeat;
    @FXML
    private ListView<String> recapListView;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private TabPane tabPane;

    @FXML
    private Button addToCartButton;
    @FXML
    private GridPane seatsGrid;

    private Session selectedSession;

    private int TicketsSelected = 0;

    private final Set<String> selectedSeats = new CopyOnWriteArraySet<>();

    @FXML
    private Button payButton;

    @FXML
    public void initialize() {
        controller = new BuyTicketController(this);

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


        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        seatsAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("seatsAvailable"));

        sessionTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedSession = newSelection;
                updateSeatGrid(newSelection);
            }
        });

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab == tabSeat && selectedSession != null) {
                updateSeatGrid(selectedSession);
            }
        });
    }

    private void clearSeat() {
        seatsGrid.getChildren().clear();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                seatsGrid.add(createButton(row, col), col, row);
            }
        }
        selectedSeats.clear();
    }

    @FXML
    private void handleAddToCart() {
        try {
            Session selectedSession = sessionTableView.getSelectionModel().getSelectedItem();
            if (selectedSession == null) {
                System.out.println("No session selected.");
                return;
            }

            if(quantitySpinnerChild.getValue() > 0 && GetTicketsSelected() == 1){
                displayError("You must select at least one adult ticket for each child ticket.");
                return;
            }

            String movie = movieTitleLabel.getText();
            Date date = selectedSession.getDate();
            Time time = selectedSession.getStartTime();
            String room = "Room " + selectedSession.getRoomNumber();
            String duration = "120 mins";
            List<String> selectedSeatNumbers = new ArrayList<>(selectedSeats);

            String dateString = (date != null) ? date.toString() : "";
            String timeString = (time != null) ? time.toString() : "";

            for (String seatNumber : selectedSeatNumbers) {
                if (Buffer.getInstance().isSeatInCart(dateString, timeString, room, seatNumber)) {
                    displayError("Seat " + seatNumber + " is already in the cart.");
                    return;
                }
            }

            for (String seatNumber : selectedSeatNumbers) {
                TicketInfo ticketInfo = new TicketInfo(dateString, timeString, room, movie, duration, seatNumber);
                Buffer.getInstance().addToCart(ticketInfo);
            }

            markSeatsAsReserved(selectedSeatNumbers);
        } catch (Exception e) {
            displayError("An error occurred : "+ e.getMessage());
        }
    }

    private void markSeatsAsReserved(List<String> seatNumbers) {
        for (Node node : seatsGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (seatNumbers.contains(button.getText())) {
                    button.setStyle("-fx-background-color: red;");
                    button.setDisable(true);
                    System.out.println("Seat " + button.getText() + " marked as reserved.");
                }
            }
        }
    }

    private Button createButton(int row, int col) {
        Button seatButton = new Button();
        seatButton.setPrefWidth(30);
        seatButton.setPrefHeight(30);
        seatButton.setText(String.format("%s%d", (char) ('A' + row), col + 1));
        seatButton.setStyle("-fx-background-color: white;");
        seatButton.setOnAction(event -> {
            GetTicketsSelected();
            int totalSeatsSelected = selectedSeats.size();

            if ("-fx-background-color: blue;".equals(seatButton.getStyle())) {
                seatButton.setStyle("-fx-background-color: white;");
                selectedSeats.remove(seatButton.getText());
            } else if (totalSeatsSelected >= TicketsSelected) {
                displayError("Vous ne pouvez pas sélectionner plus de sièges que de tickets.");
            } else if ("-fx-background-color: red;".equals(seatButton.getStyle()) || "-fx-background-color: yellow;".equals(seatButton.getStyle())) {
                displayError("Ce siège est déjà réservé ou pris.");
            } else {
                seatButton.setStyle("-fx-background-color: blue;");
                selectedSeats.add(seatButton.getText());
            }
        });
        return seatButton;
    }

    public void loadSessions(int movieId) {
        try {
            List<Session> sessions = MovieDBController.getSessionsByMovieId(movieId);
            sessionTableView.setItems(FXCollections.observableArrayList(sessions));
            if (!sessions.isEmpty()) {
                selectedSession = sessions.get(0);
                updateSeatGrid(selectedSession);
            }
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

    private void updateSeatGrid(Session session) {
        clearSeat();
        List<TicketInfo> tickets = Buffer.getInstance().getTicketsForSession(session);
        for (TicketInfo ticket : tickets) {
            for (Node node : seatsGrid.getChildren()) {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    if (button.getText().equals(ticket.getSeatNumber())) {
                        button.setStyle("-fx-background-color: red;");
                        button.setDisable(true);
                    }
                }
            }
        }
    }

    public int GetTicketsSelected() {
        TicketsSelected = quantitySpinnerRegular.getValue() + quantitySpinnerChild.getValue() +
                quantitySpinnerStudent.getValue() + quantitySpinnerSenior.getValue();
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
}
