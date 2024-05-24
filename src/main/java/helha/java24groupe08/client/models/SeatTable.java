package helha.java24groupe08.client.models;

public class SeatTable {
    private String[][] seats;

    public SeatTable(int rows, int cols) {
        seats = new String[rows][cols];
        initializeSeats();
    }

    private void initializeSeats() {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                seats[i][j] = "Seat " + (i * seats[i].length + j + 1);
            }
        }
    }

    public int getRows() {
        return seats.length;
    }

    public int getCols() {
        return seats[0].length;
    }

    public String getSeat(int row, int col) {
        if (row >= 0 && row < seats.length && col >= 0 && col < seats[0].length) {
            return seats[row][col];
        } else {
            throw new IndexOutOfBoundsException("Invalid seat position.");
        }
    }


}
