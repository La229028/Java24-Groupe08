package helha.java24groupe08.client.models;

import java.sql.*;

public class SessionDatabaseHandler {
    // this class is responsible for generating seats for a session
    // it generates 100 seats for a session, 10 rows and 10 columns
    // each seat is inserted into the Seats table and a corresponding entry is inserted into the SessionSeats table

    private static final String DB_URL = "jdbc:sqlite:src/main/DB/DB.db";

    public void generateSeatsForSession(int sessionId) {
        Connection conn = null;
        PreparedStatement insertSeatStmt = null;
        PreparedStatement insertSessionSeatStmt = null;
        ResultSet generatedKeys = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
            insertSeatStmt = conn.prepareStatement("INSERT INTO Seats (Row, Number) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertSessionSeatStmt = conn.prepareStatement("INSERT INTO SessionSeats (SessionID, SeatID) VALUES (?, ?)");
            for (int row = 1; row <= 10; row++) {
                for (int number = 1; number <= 10; number++) {
                    // Insert a new seat
                    insertSeatStmt.setInt(1, row);
                    insertSeatStmt.setInt(2, number);
                    insertSeatStmt.executeUpdate();
                    generatedKeys = insertSeatStmt.getGeneratedKeys();
                    // Get the ID of the seat that was just inserted
                        if (generatedKeys.next()) {
                            int seatId = generatedKeys.getInt(1);
                            // Insert a new session seat
                            insertSessionSeatStmt.setInt(1, sessionId);
                            insertSessionSeatStmt.setInt(2, seatId);
                            insertSessionSeatStmt.executeUpdate();
                        }
                        if(generatedKeys != null) {
                            generatedKeys.close();
                        }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (insertSeatStmt != null) {
                    insertSeatStmt.close();
                }
                if (insertSessionSeatStmt != null) {
                    insertSessionSeatStmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
