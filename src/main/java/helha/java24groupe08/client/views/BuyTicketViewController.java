package helha.java24groupe08.client.views;

import helha.java24groupe08.client.controllers.BuyTicketController;
import helha.java24groupe08.client.models.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private ListView<String> recapListView;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Button nextButton;
    @FXML
    private TabPane tabPane;


    @FXML
    private GridPane seatsGrid;

    private Session selectedSession;



    /**
     * Initializes the controller and sets up listeners for the spinners.
     * Each spinner represents a different type of ticket and updates the total price when its value changes.
     * Also initializes a 10x10 grid of buttons to represent seats in a theater.
     * Each button changes color from white to blue and vice versa when clicked, indicating seat selection.
     */
    @FXML
    public void initialize() {
        controller = new BuyTicketController(this);

        // Setup listeners for the spinners
        quantitySpinnerRegular.valueProperty().addListener((obs, oldSelection, newSelection) -> {
            controller.updateTotal(new TicketsRegular("movie"), newSelection);
        });

        quantitySpinnerChild.valueProperty().addListener((obs, oldSelection, newSelection) -> {
            controller.updateTotal(new TicketsChild("movie", 10), newSelection);//à changer avec la db par la suite
        });

        quantitySpinnerStudent.valueProperty().addListener((obs, oldSelection, newSelection) -> {
            controller.updateTotal(new TicketsStudent("movie", "Some School Name"), newSelection);//à récup de l'utilisateur ou db ??
        });

        quantitySpinnerSenior.valueProperty().addListener((obs, oldSelection, newSelection) -> {
            controller.updateTotal(new TicketsSenior("movie", 65), newSelection);//à changer avec la db par la suite
        });

        quantitySpinnerVIP.valueProperty().addListener((obs, oldSelection, newSelection) -> {
            controller.updateTotal(new TicketsVIP("movie"), newSelection);
        });



        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Button seatButton = new Button();
                seatButton.setPrefWidth(30);
                seatButton.setPrefHeight(30);
                seatButton.setStyle("-fx-background-color: white;");

                seatButton.setOnAction(event -> {
                    int totalTickets = quantitySpinnerRegular.getValue() + quantitySpinnerChild.getValue() +
                            quantitySpinnerStudent.getValue() + quantitySpinnerSenior.getValue() +
                            quantitySpinnerVIP.getValue();

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
                    } else if (totalSeatsSelected >= totalTickets) {
                        displayError("Vous ne pouvez pas sélectionner plus de sièges que de tickets.");
                    } else {
                        seatButton.setStyle("-fx-background-color: blue;");
                    }
                });

                seatsGrid.add(seatButton, j, i);
            }
        }

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        seatsAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("seatsAvailable"));

    }

    /**
     * Loads the sessions into the TableView based on the movie ID.
     *
     * @param movieId The ID of the movie.
     */
    void loadSessions(int movieId) {
        try {
            // Fetch sessions from the controller or directly from a model if appropriate
            List<Session> sessions = MovieDBController.getSessionsByMovieId(movieId);
            sessionTableView.setItems(FXCollections.observableArrayList(sessions));
        } catch (Exception e) {
            System.err.println("Error loading sessions: " + e.getMessage());
            // Handle error
        }
    }

    /**
     * Sets the details of the movie to be displayed in the buy ticket view.
     *
     * @param movieDetails The details of the movie.
     */
    public void setMovieDetails(String[] movieDetails) {
        if (movieDetails != null && movieDetails.length > 14) {
            movieTitleLabel.setText(movieDetails[0]);  // title
            moviePlotTextArea.setText(movieDetails[9]);  //  plot
            loadSessions(Integer.parseInt(movieDetails[14])); // movie ID
        }
    }



    /**
     * Updates the total price label in the view.
     * @param totalText the new total price
     */
    public void updateTotal(String totalText) {
        totalLabel.setText(totalText);
        totalPriceLabel.setText(totalText);
    }

    /**
     * Updates the recap list in the view.
     * @param ticketCounts a map of ticket types to their counts
     */
    public void updateRecap(Map<String, Long> ticketCounts) {
        recapListView.getItems().clear();
        ticketCounts.forEach((type, count) -> {
            recapListView.getItems().add(count + " " + type);
        });
    }

    /**
     * Displays an error message to the user.
     * @param message the error message to display
     */
    public void displayError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initModality(Modality.APPLICATION_MODAL);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true); // Always show on top of other windows
        alert.setTitle("Error Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    /**
     * Displays a success message to the user.
     * @param message the success message to display
     */
    public void displaySuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true); // Always show on top of other windows
        alert.setTitle("Success Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    @FXML
    private TableView<Session> sessionTable;

    public void updateSessionsForMovie(int movieId) {
        List<Session> sessions = MovieDBController.getSessionsByMovieId(movieId);
        sessionTable.setItems(FXCollections.observableArrayList(sessions));
    }

    public void setSelectedSession(Session session) {
        this.selectedSession = session;
        // Update the UI elements here based on the selected session
    }

    @FXML
    public void onNextButtonClicked() {
        int totalTickets = quantitySpinnerRegular.getValue() + quantitySpinnerChild.getValue() +
                quantitySpinnerStudent.getValue() + quantitySpinnerSenior.getValue() +
                quantitySpinnerVIP.getValue();

        if (totalTickets <= 0) {
            displayError("Veuillez sélectionner au moins un ticket.");
            return;
        }

        if (selectedSession != null && totalTickets > selectedSession.getSeatsAvailable()) {
            displayError("Vous ne pouvez pas sélectionner plus de tickets que de sièges disponibles.");
            return;
        }

        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.selectNext();
    }

    public void onSeatClicked(int sessionId, int seatId, String username) {
        controller.reserveSeat(sessionId, seatId, username);
    }
}