package helha.java24groupe08.client.models;


public class Seats {
    private int seatID;
    private char row;
    private int number;

    public Seats(char row, int number) {
        this.row = row;
        this.number = number;
    }

    // Getters and setters
    public int getSeatID() {
        return seatID;
    }

    public void setSeatID(int seatID) {
        this.seatID = seatID;
    }

    public char getRow() {
        return row;
    }

    public void setRow(char row) {
        this.row = row;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
