package helha.java24groupe08.client.models;


public class Seats {
    private int seatID;
    private char row;
    private int number;

    public Seats(char row, int number) {
        this.row = row;
        this.number = number;
    }



    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
