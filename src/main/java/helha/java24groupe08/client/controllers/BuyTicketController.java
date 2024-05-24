package helha.java24groupe08.client.controllers;

import helha.java24groupe08.client.models.*;
import helha.java24groupe08.client.models.SessionDBController;
import helha.java24groupe08.client.views.BuyTicketViewController;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is the controller for the BuyTicketView.
 * It handles the logic for buying tickets.
 */
public class BuyTicketController {

    private static BuyTicketViewController viewController;
    private final TicketGroup group;

    /**
     * Constructs a new BuyTicketController with the given view controller.
     * @param viewController the view controller to interact with
     */
    public BuyTicketController(BuyTicketViewController viewController) {
        BuyTicketController.viewController = viewController;
        this.group = new TicketGroup();
    }


    /**
     * Updates the total price based on the given ticket and quantity.
     * @param ticket the type of ticket
     * @param quantity the quantity of the ticket
     */
    public void updateTotal(TicketComponent ticket, int quantity) {
        try{
            group.getTickets().removeIf(t -> t.getType().equals(ticket.getType()));
            for (int i = 0; i < quantity; i++) {
                group.addTicket(ticket);
            }
            String totalText = String.format("%.2f €", group.getPrice());
            viewController.updateTotal(totalText);
            updateRecap();
        } catch (Exception e) {
            ErrorUtils.showErrorAlert(e.getMessage());
        }
    }

    /**
     * Updates the recap list based on the current tickets in the group.
     */
    private void updateRecap() {
        try{
            Map<String, Long> ticketCounts = group.getTickets().stream()
                    .collect(Collectors.groupingBy(TicketComponent::getType, Collectors.counting()));
            viewController.updateRecap(ticketCounts);
        } catch (Exception e) {
            ErrorUtils.showErrorAlert(e.getMessage());
        }
    }

    public static void loadSessions(int movieId) {
        try {
            List<Session> sessions = SessionDBController.getSessionsByMovieId(movieId);
            viewController.updateSessionTable(sessions);
            if (!sessions.isEmpty()) {
                Session selectedSession = sessions.get(0);
                viewController.updateSeatGrid(selectedSession);
            }
        } catch (Exception e) {
            System.err.println("Error loading sessions: " + e.getMessage());
        }
    }

}