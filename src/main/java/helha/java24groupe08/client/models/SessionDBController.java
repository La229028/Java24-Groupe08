package helha.java24groupe08.client.models;

import helha.java24groupe08.client.controllers.AlertUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for managing sessions in the database.
 */
public class SessionDBController {
    private static final String CONNECTION_STRING = "jdbc:sqlite:src/main/DB/DB.db";

    /**
     * Deletes a session from the database by its ID.
     * @param sessionId The ID of the session to delete.
     */
    public static void deleteSession(int sessionId) {
        String deleteSessionSeatsSql = "DELETE FROM SessionSeats WHERE SessionID = ?";
        String deleteSessionSql = "DELETE FROM Sessions WHERE session_id = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement deleteSessionSeatsStmt = conn.prepareStatement(deleteSessionSeatsSql);
             PreparedStatement deleteSessionStmt = conn.prepareStatement(deleteSessionSql)) {
            // Delete session seats
            deleteSessionSeatsStmt.setInt(1, sessionId);
            deleteSessionSeatsStmt.executeUpdate();
            // Delete session
            deleteSessionStmt.setInt(1, sessionId);
            deleteSessionStmt.executeUpdate();
        } catch (SQLException e) {
            AlertUtils.showErrorAlert("Error deleting session from database: " + e.getMessage());
        }
    }

    /**
     * Retrieves sessions associated with a specific movie by its ID.
     * @param movieId The ID of the movie for which to retrieve sessions.
     * @return A list of Session objects representing each session.
     */
    public static List<Session> getSessionsByMovieId(int movieId) {
        List<Session> sessions = new ArrayList<>();
        String sql = "SELECT * FROM sessions WHERE movie_id = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, movieId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                sessions.add(new Session(
                        rs.getInt("session_id"),
                        rs.getInt("room_number"),
                        rs.getTime("start_time"),
                        rs.getDate("date"),
                        movieId
                ));
            }
        } catch (SQLException e) {
            AlertUtils.showErrorAlert("Error retrieving sessions from database: " + e.getMessage());
        }
        return sessions;
    }

    /**
     * Retrieves all sessions from the database.
     */
    public static void createSessionsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS sessions (\n"
                + " session_id integer PRIMARY KEY,\n"
                + " room_number integer NOT NULL,\n"
                + " start_time time NOT NULL,\n"
                + " date date NOT NULL,\n"
                + " movie_id integer,\n"
                + " FOREIGN KEY(movie_id) REFERENCES movies(movie_id)\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Sessions table created successfully.");
        } catch (SQLException e) {
            AlertUtils.showErrorAlert("Error creating sessions table: " + e.getMessage());
        }
    }


    /**
     * Inserts a session into the database.
     * @param session The session object to insert.
     */
    public static void insertSession(Session session) {
        String sql = "INSERT INTO sessions (room_number, start_time, date, movie_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, session.getRoomNumber());
            pstmt.setTime(2, session.getStartTime());
            pstmt.setDate(3, session.getDate());
            pstmt.setInt(4, session.getMovieId());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int sessionId = generatedKeys.getInt(1);
                    SessionDatabaseHandler dbHandler = new SessionDatabaseHandler();
                    dbHandler.generateSeatsForSession(sessionId);
                }
            }
        } catch (SQLException e) {
            AlertUtils.showErrorAlert("Error inserting session into database: " + e.getMessage());
        }
    }
}
