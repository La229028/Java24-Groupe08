package helha.java24groupe08.client.models;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.Set;

public class UserSeatDisplay implements SeatReservationObserver {
    private final String sessionKey;
    private final SeatTable seatTable;
    private final GridPane seatsGrid;

    public UserSeatDisplay(String sessionKey, SeatTable seatTable, GridPane seatsGrid) {
        this.sessionKey = sessionKey;
        this.seatTable = seatTable;
        this.seatsGrid = seatsGrid;
        SeatReservationManager.getInstance().addObserver(this);
    }

    @Override
    public void updateReservations() {
        Platform.runLater(() -> {
            Set<String> reservedSeats = SeatReservationManager.getInstance().getReservedSeats(sessionKey);
            for (int i = 0; i < seatTable.getRows(); i++) {
                for (int j = 0; j < seatTable.getCols(); j++) {
                    String seat = seatTable.getSeat(i, j);
                    for (Node node : seatsGrid.getChildren()) {
                        if (node instanceof Button) {
                            Button button = (Button) node;
                            if (button.getText().equals(seat)) {
                                if (reservedSeats.contains(seat)) {
                                    button.setStyle("-fx-background-color: red;");
                                } else {
                                    button.setStyle(""); // Default style for non-reserved seats
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
