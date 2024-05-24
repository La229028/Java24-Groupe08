package helha.java24groupe08.client.controllers;

import helha.java24groupe08.client.models.exceptions.DatabaseException;
import helha.java24groupe08.client.models.User;

import java.sql.*;

/**
 * UserDBController class is responsible for interacting with the SQLite database.
 * It provides methods to add a user to the database and to retrieve a user from the database by username.
 */
public class UserDBController {
        private static final String CONNECTION_STRING = "jdbc:sqlite:src/main/DB/DB.db";

    /**
     * Adds a user to the SQLite database.
     * @param user The user to be added to the database.
     */
        public static void addUser(User user) throws DatabaseException {
            String sql = "INSERT INTO user(name, firstname, numberPhone, email, age, status, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, user.getName());
                pstmt.setString(2, user.getFirstname());
                pstmt.setInt(3, user.getNumberPhone());
                pstmt.setString(4, user.getEmail());
                pstmt.setInt(5, user.getAge());
                pstmt.setString(6, user.getStatus());
                pstmt.setString(7, user.getUsername());
                pstmt.setString(8, user.getPassword());

                pstmt.executeUpdate();
                System.out.println("User inserted successfully.");
            } catch (SQLException e) {
                throw new DatabaseException("Error inserting user into database : " + e.getMessage(), e);
            }
        }


    /**
     * Retrieves a user from the SQLite database by username.
     * @param username The username of the user to be retrieved.
     * @return The user retrieved from the database, or null if no user was found with the given username.
     */
        public User getUserByUsername(String username) throws DatabaseException {
            String sql = "SELECT * FROM user WHERE username = ?";

            try(Connection conn = DriverManager.getConnection(CONNECTION_STRING);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, username);
                //ResultSet rs = pstmt.executeQuery();

//                if (rs.next()) {
//                    return new User(
//                            rs.getString("name"),
//                            rs.getString("firstname"),
//                            rs.getInt("numberPhone"),
//                            rs.getString("email"),
//                            rs.getInt("age"),
//                            rs.getString("status"),
//                            rs.getString("username"),
//                            rs.getString("password")
//                    );
//                }
            } catch (SQLException e) {
                throw new DatabaseException("Error getting user from database: " + e.getMessage(), e);
            }

            return null;
        }
}
