package helha.java24groupe08.client.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a group of tickets. It can contain other TicketGroup objects or Tickets.
 */
public class TicketGroup implements TicketComponent{
    private final List<TicketComponent> tickets = new ArrayList<>();

    /**
     * Adds a ticket to the group.
     * @param ticket The ticket to add.
     */
    public void addTicket(TicketComponent ticket) {
        tickets.add(ticket);//use to add a TicketComponent object (Tickets or TicketGroup) to the list
    }

    /**
     * Design pattern: Composite
     * @return The total price of all tickets in the group.
     */
    @Override
    public double getPrice() {
        double total = tickets.stream().mapToDouble(TicketComponent::getPrice).sum();
        if (tickets.size() >= 10) {
            total *= 0.9; // Apply 10% discount
        }
        return total;
    }

    /**
     * Get the details of all tickets in the group.
     */
    @Override
    public void getDetails() {
        tickets.forEach(TicketComponent::getDetails);
    }

    /**
     * Get the tickets in the group.
     * @return The list of tickets.
     */
    public List<TicketComponent> getTickets() {
        return tickets;
    }

    /**
     * @return The type of the object.
     */
    @Override
    public String getType() {
        return "TicketGroup";
    }
}
