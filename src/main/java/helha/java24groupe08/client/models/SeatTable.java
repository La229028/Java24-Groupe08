package helha.java24groupe08.client.models;

public class SeatTable {
    private String[][] seats;

    public SeatTable(int rows, int cols) {
        seats = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Convert row number to seat row label (1-based index)
                String rowLabel = Integer.toString(i + 1);
                // Convert column number to seat column label (a, b, c, ...)
                char colLabel = (char) ('a' + j);
                seats[i][j] = rowLabel + colLabel;
            }
        }
    }

    public String getSeat(int row, int col) {
        return seats[row][col];
    }
}