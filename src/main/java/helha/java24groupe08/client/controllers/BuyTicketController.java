package helha.java24groupe08.client.controllers;

import helha.java24groupe08.client.models.*;
import helha.java24groupe08.client.views.BuyTicketViewController;

import java.util.Map;
import java.util.stream.Collectors;

public class BuyTicketController {

    private BuyTicketViewController viewController;
    private TicketGroup group;

    /**
     * Constructs a new BuyTicketController with the given view controller.
     * @param viewController the view controller to interact with
     */
    public BuyTicketController(BuyTicketViewController viewController) {
        this.viewController = viewController;
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
            viewController.displayError(e.getMessage());
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
            viewController.displayError(e.getMessage());
        }
    }

    //méthode utilisé dans
    public void reserveSeat(int sessionId, int seatId, String username){
        try{
            SeatReservationRequest request = new SeatReservationRequest(sessionId, seatId, username);
//            boolean success = MovieDBController.reserveSeat(request);
//            if (success) {
//                viewController.displaySuccess("Seat reserved successfully!");
//            } else {
//                viewController.displayError("Failed to reserve seat.");
//            }
        } catch (Exception e) {
            viewController.displayError(e.getMessage());
        }
    }
}