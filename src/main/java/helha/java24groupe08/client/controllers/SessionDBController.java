package helha.java24groupe08.client.controllers;

import helha.java24groupe08.client.models.Session;
import helha.java24groupe08.client.models.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Handles database operations for movie sessions.
 */
public class SessionDBController {
    private static final String CONNECTION_STRING = "jdbc:sqlite:src/main/DB/DB.db";


    /**
     * Adds a session to the SQLite database.
     * @param session The session to be added to the database.
     * @throws DatabaseException if there is an error during the database insert operation.
     */
    public static void addSession(Session session) throws DatabaseException {
        String sql = "INSERT INTO sessions(room_number, start_time, date, movie_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, session.getRoomNumber());
            pstmt.setTime(2, session.getStartTime());
            pstmt.setDate(3, session.getDate());
            pstmt.setInt(4, session.getMovieId());

            pstmt.executeUpdate();
            System.out.println("Session inserted successfully.");
        } catch (SQLException e) {
            throw new DatabaseException("Error inserting session into database: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves a session from the SQLite database by session ID.
     * @param sessionId The ID of the session to be retrieved.
     * @return The session retrieved from the database, or null if no session was found with the given ID.
     * @throws DatabaseException if there is an error during the database query operation.
     */
    public static Session getSessionById(int sessionId) throws DatabaseException {
        String sql = "SELECT * FROM sessions WHERE session_id = ?";

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, sessionId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Session(
                        rs.getInt("session_id"),
                        rs.getInt("room_number"),
                        rs.getTime("start_time"),
                        rs.getDate("date"),
                        rs.getInt("movie_id")
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error getting session from database: " + e.getMessage(), e);
        }

        return null;
    }

    /**
     * Deletes a session from the database based on its ID.
     * @param sessionId The ID of the session to be deleted.
     * @throws DatabaseException if there is an error during the database delete operation.
     */
    public static void deleteSession(int sessionId) throws DatabaseException, SQLException {
        String sql = "DELETE FROM sessions WHERE session_id = ?";

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, sessionId);
            pstmt.executeUpdate();

        }
}
}