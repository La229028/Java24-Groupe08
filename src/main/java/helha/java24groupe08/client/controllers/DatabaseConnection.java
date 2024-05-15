package helha.java24groupe08.client.controllers;

import helha.java24groupe08.client.models.SessionDatabaseHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private final Connection connection;
    private static final String CONNECTION_STRING = "jdbc:sqlite:src/main/DB/DB.db";

    private DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection(CONNECTION_STRING);
            initializeDatabase();  // Ensure table is created on connection setup
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            throw new RuntimeException("Database connection error.", e);
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return this.connection;
    }

    private void initializeDatabase() {
        try (Statement statement = connection.createStatement()) {
            // Création de la table Seats si elle n'existe pas déjà
            statement.execute("CREATE TABLE IF NOT EXISTS Seats (" +
                    "SeatID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Row CHAR(1), " +
                    "Number INTEGER)");
            System.out.println("Table Seats present or created.");
            // Création de la table SessionSeats
            statement.execute("CREATE TABLE IF NOT EXISTS SessionSeats (" +
                    "SessionID INT, " +
                    "SeatID INT, " +
                    "PRIMARY KEY (SessionID, SeatID), " +
                    "FOREIGN KEY (SeatID) REFERENCES Seats(SeatID))");
            System.out.println("Table SessionSeats present or created.");
            // Création de la table Reservations
            statement.execute("CREATE TABLE IF NOT EXISTS Reservations (" +
                    "ReservationID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "UserID INT, " +
                    "SeatID INT, " +
                    "SessionID INT, " +
                    "Status VARCHAR(10) CHECK (Status IN ('reserved', 'purchased')), " +
                    "Timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "FOREIGN KEY (SeatID) REFERENCES Seats(SeatID), " +
                    "FOREIGN KEY (SessionID) REFERENCES SessionSeats(SessionID))");
            System.out.println("Table Reservations present or created.");
        } catch (SQLException e) {
            System.out.println("Error creating database tables: " + e.getMessage());
        }
    }


}
