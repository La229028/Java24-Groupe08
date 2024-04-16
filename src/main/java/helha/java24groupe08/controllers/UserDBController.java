package helha.java24groupe08.controllers;

import helha.java24groupe08.models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDBController {
        private static final String CONNECTION_STRING = "jdbc:sqlite:src/main/DB/DB.db";

        public static void addUser(User user) {
            String sql = "INSERT INTO user(lastname, firstname, numberPhone, email, age, status, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, user.getLastname());
                pstmt.setString(2, user.getFirstname());
                pstmt.setString(3, user.getNumberPhone());
                pstmt.setString(4, user.getEmail());
                pstmt.setInt(5, user.getAge());
                pstmt.setString(6, user.getStatus());
                pstmt.setString(7, user.getPassword());

                pstmt.executeUpdate();
                System.out.println("User inserted successfully.");
            } catch (SQLException e) {
                System.err.println("Error inserting user into database: " + e.getMessage());
            }
        }
}
