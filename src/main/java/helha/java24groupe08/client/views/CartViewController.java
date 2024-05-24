package helha.java24groupe08.client.views;// CartViewController.java
import helha.java24groupe08.client.models.Buffer;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CartViewController {

    @FXML
    private ListView<String> cartListView;

    @FXML
    private void initialize() {
        loadCartItems();
    }

    private void loadCartItems() {
        cartListView.getItems().clear();
        Buffer.getInstance().getCart().forEach(ticketInfo -> {
            String item = "Movie: " + ticketInfo.getMovie() +
                    ", Date: " + ticketInfo.getDate() +
                    ", Time: " + ticketInfo.getTime() +
                    ", Room: " + ticketInfo.getRoom() +
                    ", Seat: " + ticketInfo.getSeatNumber();
            cartListView.getItems().add(item);
        });
    }

    @FXML
    private void handleCheckout() {
        if (Buffer.getInstance().getCart().isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Cart");
            alert.setHeaderText(null);
            alert.setContentText("Your cart is empty!");
            alert.showAndWait();
        } else {
            // Implement checkout logic here
            Buffer.getInstance().clearCart();
            loadCartItems();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Checkout");
            alert.setHeaderText(null);
            alert.setContentText("Checkout successful!");
            alert.showAndWait();
        }
    }
}
