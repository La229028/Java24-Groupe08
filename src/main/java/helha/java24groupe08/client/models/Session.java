package helha.java24groupe08.client.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.sql.Time;
import java.sql.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Session {
    private final int sessionId;
    private final int roomNumber;
    private final Time startTime;
    private final Date date;
    private final int movieId;
    private final IntegerProperty seatsAvailable;
    private final Map<String, SessionSeat> seats;

    public Session(int sessionId, int roomNumber, Time startTime, Date date, int movieId) {
        this.sessionId = sessionId;
        this.roomNumber = roomNumber;
        this.startTime = startTime;
        this.date = date;
        this.movieId = movieId;
        this.seatsAvailable = new SimpleIntegerProperty(100);
        this.seats = new ConcurrentHashMap<>();
        initializeSeats();
    }

    public Session(int roomNumber, Time startTime, Date date, int movieId) {
        this(0, roomNumber, startTime, date, movieId); // Calling the main constructor with 0 or a dummy sessionId
    }


    private void initializeSeats() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                String seatNumber = String.format("%s%d", (char) ('A' + row), col + 1);
                seats.put(seatNumber, new SessionSeat(seatNumber));
            }
        }
    }

    public int getSessionId() {
        return sessionId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Date getDate() {
        return date;
    }

    public int getMovieId() {
        return movieId;
    }



    public Map<String, SessionSeat> getSeats() {
        return seats;
    }





    public synchronized void reserveSeat(String seatNumber) {
        SessionSeat seat = seats.get(seatNumber);
        if (seat != null && seat.getStatus().equals("free")) {
            seat.setStatus("reserved");
        }
    }

    public synchronized void takeSeat(String seatNumber) {
        SessionSeat seat = seats.get(seatNumber);
        if (seat != null && seat.getStatus().equals("reserved")) {
            seat.setStatus("taken");
        }
    }
}
