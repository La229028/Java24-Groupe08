package helha.java24groupe08.client.models;

import java.io.Serializable;

// Represents a request to reserve a seat in a session.
public class SeatReservationRequest implements Serializable {
    private int sessionId;
    private int seatId;
    private String username;

    public SeatReservationRequest(int sessionId, int seatId, String username) {
        this.sessionId = sessionId;
        this.seatId = seatId;
        this.username = username;
    }

    public int getSessionId() {
        return sessionId;
    }

    public int getSeatId() {
        return seatId;
    }

    public String getUsername() {
        return username;
    }
}
