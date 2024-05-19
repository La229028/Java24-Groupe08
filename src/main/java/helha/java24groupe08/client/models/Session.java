package helha.java24groupe08.client.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.sql.Time;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a session in the cinema system.
 * A session links to a specific movie showing at a specific time in a specific room.
 */
public class Session {
    private int sessionId;
    private int roomNumber;
    private Time startTime;
    private Date date;
    private int movieId;

    private IntegerProperty seatsAvailable;
    private List<String> reservedSeats; // List to store reserved seats

    public Session(int sessionId, int roomNumber, Time startTime, Date date, int movieId) {
        this.sessionId = sessionId;
        this.roomNumber = roomNumber;
        this.startTime = startTime;
        this.date = date;
        this.movieId = movieId;
        this.seatsAvailable = new SimpleIntegerProperty(100);
        this.reservedSeats = new ArrayList<>();
    }

    // Overloaded constructor for new session creation without sessionId

    public Session(int roomNumber, Time startTime, Date date, int movieId) {
        this(0, roomNumber, startTime, date, movieId);  // Calling the main constructor with 0 or a dummy sessionId
    }



    /**
     * Gets the session ID.
     * @return the session ID.
     */
    public int getSessionId() {
        return sessionId;
    }

    /**
     * Sets the session ID.
     * @param sessionId the session ID to set.
     */
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Gets the room number where the session will take place.
     * @return the room number.
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Sets the room number for the session.
     * @param roomNumber the room number to set.
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Gets the start time of the session.
     * @return the start time.
     */
    public Time getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time for the session.
     * @param startTime the start time to set.
     */
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the date of the session.
     * @return the date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of the session.
     * @param date the date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the movie ID associated with this session.
     * @return the movie ID.
     */
    public int getMovieId() {
        return movieId;
    }

    /**
     * Sets the movie ID for this session.
     * @param movieId the movie ID to set.
     */
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getSeatsAvailable() {
        return seatsAvailable.get();
    }

    public IntegerProperty seatsAvailableProperty() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable.set(seatsAvailable);
    }

    public List<String> getReservedSeats() {
        return reservedSeats;
    }

    public synchronized void addReservedSeat(String seat) {
        reservedSeats.add(seat);
    }

    public synchronized void removeReservedSeat(String seat) {
        reservedSeats.remove(seat);
    }
}
