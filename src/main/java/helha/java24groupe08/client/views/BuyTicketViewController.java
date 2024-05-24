package helha.java24groupe08.client.views;

import helha.java24groupe08.client.controllers.BuyTicketController;
import helha.java24groupe08.client.controllers.AlertUtils;
import helha.java24groupe08.client.models.*;
import helha.java24groupe08.client.models.exceptions.MovieNotFoundException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
/**
 * This class is the controller for the Buy Ticket view.
 * It handles the actions of the Buy Ticket view.
 */
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

    @FXML
    private Tab tabSeat;
    @FXML
    private ListView<String> recapListView;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private TabPane tabPane;

    @FXML
    public Button addToCartButton;
    @FXML
    private GridPane seatsGrid;

    private Session selectedSession;

    private int TicketsSelected = 0;

    private final Set<String> selectedSeats = new CopyOnWriteArraySet<>();

    @FXML
    private Button payButton;

    /**
     * This method sets the controller for the BuyTicketViewController.
     * It sets up the event listeners for the quantity spinners, the session table, the seat grid, and the tab pane.
     *
     * @param controller The BuyTicketController to set.
     */
    public void setController(BuyTicketController controller) {

        quantitySpinnerRegular.valueProperty().addListener((obs, oldSelection, newSelection) -> {
            controller.updateTotal(new TicketsRegular(), newSelection);
            clearSeat();
        });

        quantitySpinnerChild.valueProperty().addListener((obs, oldSelection, newSelection) -> {
            controller.updateTotal(new TicketsChild(), newSelection);
            clearSeat();
        });

        quantitySpinnerStudent.valueProperty().addListener((obs, oldSelection, newSelection) -> {
            controller.updateTotal(new TicketsStudent(), newSelection);
            clearSeat();
        });

        quantitySpinnerSenior.valueProperty().addListener((obs, oldSelection, newSelection) -> {
            controller.updateTotal(new TicketsSenior(), newSelection);
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


    /**
     * This method is called when the user clicks on the "Add to cart" button.
     * It adds the selected seats to the cart.
     */
    @FXML
    private void handleAddToCart() {
        try {
            Session selectedSession = sessionTableView.getSelectionModel().getSelectedItem();
            if (selectedSession == null) {
                System.out.println("No session selected.");
                return;
            }

            if(quantitySpinnerChild.getValue() > 0 && GetTicketsSelected() == 1){
                AlertUtils.showErrorAlert("You must select at least one adult ticket for each child ticket.");
                return;
            }

            ResultSession resultSession = getSession(selectedSession);

            String dateString = (resultSession.date() != null) ? resultSession.date().toString() : "";
            String timeString = (resultSession.time() != null) ? resultSession.time().toString() : "";

            for (String seatNumber : resultSession.selectedSeatNumbers()) {
                if (Buffer.getInstance().isSeatInCart(dateString, timeString, resultSession.room(), seatNumber)) {
                    AlertUtils.showErrorAlert("Seat " + seatNumber + " is already in the cart.");
                    return;
                }
            }

            for (String seatNumber : resultSession.selectedSeatNumbers()) {
                TicketInfo ticketInfo = new TicketInfo(dateString, timeString, resultSession.room(), resultSession.movie(), resultSession.duration(), seatNumber);
                Buffer.getInstance().addToCart(ticketInfo);
            }

            markSeatsAsReserved(resultSession.selectedSeatNumbers());
        } catch (Exception e) {
            AlertUtils.showErrorAlert("An error occurred : "+ e.getMessage());
        }
    }

    /**
     * Creates and returns an instance of ResultSession from a selected session.
     * @param selectedSession The session selected by the user.
     * @return An instance of ResultSession containing the details of the selected session.
     * @throws MovieNotFoundException If the movie associated with the session is not found.
     */
    private ResultSession getSession(Session selectedSession) throws MovieNotFoundException {
        String movie = movieTitleLabel.getText();
        Date date = selectedSession.getDate();
        Time time = selectedSession.getStartTime();
        String room = "Room " + selectedSession.getRoomNumber();
        MovieDBController movieDBController = new MovieDBController();
        String duration = movieDBController.getMovieDuration(movieTitleLabel.getText());
        List<String> selectedSeatNumbers = new ArrayList<>(selectedSeats);
        ResultSession resultSession = new ResultSession(movie, date, time, room, duration, selectedSeatNumbers);
        return resultSession;
    }

    /**
     * An immutable data class representing a movie session selected by the user.
     * Contains details such as movie title, session date and time, theater number, movie duration and selected seat numbers.
     */
    private record ResultSession(String movie, Date date, Time time, String room, String duration, List<String> selectedSeatNumbers) {
    }

    /**
     * Marks the selected seats as reserved in the seat grid.
     * @param seatNumbers
     */
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


    private void clearSeat() {
        seatsGrid.getChildren().clear();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                seatsGrid.add(createButton(row, col), col, row);
            }
        }
        selectedSeats.clear();
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
                AlertUtils.showErrorAlert("You cannot select more seats than tickets.");
            } else if ("-fx-background-color: red;".equals(seatButton.getStyle()) || "-fx-background-color: yellow;".equals(seatButton.getStyle())) {
                AlertUtils.showErrorAlert("This seat is already reserved or taken.");
            } else {
                seatButton.setStyle("-fx-background-color: blue;");
                selectedSeats.add(seatButton.getText());
            }
        });
        return seatButton;
    }



    public void setMovieDetails(String[] movieDetails) {
        if (movieDetails != null && movieDetails.length > 14) {
            movieTitleLabel.setText(movieDetails[0]);
            moviePlotTextArea.setText(movieDetails[9]);
            BuyTicketController.loadSessions(Integer.parseInt(movieDetails[14]));
        }
    }

    public void updateSessionTable(List<Session> sessions) {
        sessionTableView.setItems(FXCollections.observableArrayList(sessions));
    }

    public void updateSeatGrid(Session session) {
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

}
