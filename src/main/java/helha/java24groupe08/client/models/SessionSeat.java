package helha.java24groupe08.client.models;

public class SessionSeat {
    private int sessionID;
    private int seatID;

    // Constructeur
    public SessionSeat(int sessionID, int seatID) {
        this.sessionID = sessionID;
        this.seatID = seatID;
    }

    // Getters et setters
    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public int getSeatID() {
        return seatID;
    }

    public void setSeatID(int seatID) {
        this.seatID = seatID;
    }
}
