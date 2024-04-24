package helha.java24groupe08.models;

import java.util.ArrayList;
import java.util.List;

public class TicketGroup implements TicketComponent{
    private List<TicketComponent> tickets = new ArrayList<>();

    public void addTicket(TicketComponent ticket) {
        tickets.add(ticket);//use to add a TicketComponent object (Tickets or TicketGroup) to the list
    }

    @Override
    public double getPrice() {
        double total = tickets.stream().mapToDouble(TicketComponent::getPrice).sum();
        if (tickets.size() >= 10) {
            total *= 0.9; // Apply 10% discount
        }
        return total;
    }

    @Override
    public void getDetails() {
        tickets.forEach(TicketComponent::getDetails);
    }
}
