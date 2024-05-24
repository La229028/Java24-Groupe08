package helha.java24groupe08.client.views;

import helha.java24groupe08.client.models.Buffer;
import helha.java24groupe08.client.models.TicketInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;

public class CartViewController {

    @FXML
    private ListView<TicketInfo> cartListView;

    private GridPane seatsGrid;
    private BuyTicketViewController buyTicketViewController; // Reference to BuyTicketViewController
    private Stage buyTicketViewStage; // Reference to the BuyTicketView stage

    public void setSeatsGrid(GridPane seatsGrid) {
        this.seatsGrid = seatsGrid;
    }

    public void setBuyTicketViewController(BuyTicketViewController buyTicketViewController) {
        this.buyTicketViewController = buyTicketViewController;
    }

    public void setBuyTicketViewStage(Stage stage) {
        this.buyTicketViewStage = stage;
    }

    @FXML
    private void initialize() {
        cartListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<TicketInfo> call(ListView<TicketInfo> listView) {
                return new CartItemCell();
            }
        });
        loadCartItems();
    }

    private void loadCartItems() {
        cartListView.getItems().clear();
        cartListView.getItems().addAll(Buffer.getInstance().getCart());
    }

    @FXML
    private void handleCheckout() {
        System.out.println("handleCheckout triggered");

        if (Buffer.getInstance().getCart().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cart");
            alert.setHeaderText(null);
            alert.setContentText("Your cart is empty!");
            alert.showAndWait();
        } else {
            System.out.println("Processing checkout");

            List<TicketInfo> cartItems = new ArrayList<>(Buffer.getInstance().getCart());

            for (TicketInfo item : cartItems) {
                markSeatAsTaken(item);
                Buffer.getInstance().addTakenSeat(item);
                System.out.println("Seat " + item.getSeatNumber() + " marked as taken.");
            }

            Buffer.getInstance().clearCart();
            loadCartItems();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Checkout");
            alert.setHeaderText(null);
            alert.setContentText("Checkout successful!");
            alert.showAndWait();

            if (buyTicketViewController != null) {
                buyTicketViewController.refreshSeatGrid(); // Notify BuyTicketViewController to refresh seat grid
            }

            // Close the BuyTicketView stage if it's open
            if (buyTicketViewStage != null && buyTicketViewStage.isShowing()) {
                buyTicketViewStage.close();
            }
        }
    }

    private void markSeatAsTaken(TicketInfo item) {
        if (seatsGrid == null) {
            System.out.println("seatsGrid is null");
            return;
        }

        for (Node node : seatsGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (button.getText().equals(item.getSeatNumber())) {
                    button.setStyle("-fx-background-color: blue;");
                    button.setDisable(true);
                    System.out.println("Seat " + button.getText() + " marked as taken.");
                }
            }
        }
    }

    private class CartItemCell extends ListCell<TicketInfo> {
        private HBox hbox = new HBox();
        private Text detailsText = new Text();
        private Button removeButton = new Button("❌");

        public CartItemCell() {
            super();
            hbox.setSpacing(10);
            hbox.getChildren().addAll(detailsText, removeButton);
            removeButton.setOnAction(event -> {
                TicketInfo item = getItem();
                if (item != null) {
                    Buffer.getInstance().removeFromCart(item);
                    loadCartItems();
                    markSeatAsAvailable(item);
                }
            });
        }

        @Override
        protected void updateItem(TicketInfo item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                String itemText = "Movie: " + item.getMovie() +
                        ", Date: " + item.getDate() +
                        ", Time: " + item.getTime() +
                        ", Room: " + item.getRoom() +
                        ", Seat: " + item.getSeatNumber();
                detailsText.setText(itemText);
                setGraphic(hbox);
            }
        }

        private void markSeatAsAvailable(TicketInfo item) {
            if (seatsGrid == null) {
                return;
            }

            for (Node node : seatsGrid.getChildren()) {
                if (node instanceof Button) {
                    Button button = (Button) node;
                    if (button.getText().equals(item.getSeatNumber())) {
                        button.setStyle("-fx-background-color: white;");
                        button.setDisable(false);
                        System.out.println("Seat " + button.getText() + " marked as available.");
                    }
                }
            }
        }
    }
}
