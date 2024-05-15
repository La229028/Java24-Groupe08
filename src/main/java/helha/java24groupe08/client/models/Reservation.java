package helha.java24groupe08.client.models;

import java.security.Timestamp;

public class Reservation {
    private int reservationID;
    private int userID;
    private int seatID;
    private int sessionID;
    private String status;
    private Timestamp timestamp;

    // Constructeur
    public Reservation(int userID, int seatID, int sessionID, String status) {
        this.userID = userID;
        this.seatID = seatID;
        this.sessionID = sessionID;
        this.status = status;
    }

    // Getters et setters
    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getSeatID() {
        return seatID;
    }

    public void setSeatID(int seatID) {
        this.seatID = seatID;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}