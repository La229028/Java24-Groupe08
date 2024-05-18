package helha.java24groupe08.client.models;

// TicketInfo.java
public class TicketInfo {
    private String date;
    private String time;
    private String room;
    private String movie;
    private String duration;
    private String seatNumber;

    // Constructor, getters and setters
    public TicketInfo(String date, String time, String room, String movie, String duration, String seatNumber) {
        this.date = date;
        this.time = time;
        this.room = room;
        this.movie = movie;
        this.duration = duration;
        this.seatNumber = seatNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getMovie() {
        return movie;
    }

    public String getRoom() {
        return room;
    }

    public String getSeatNumber() {
        return seatNumber;
    }
}
