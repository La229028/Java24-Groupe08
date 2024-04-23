package helha.java24groupe08.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private final Connection connection;
    private static final String CONNECTION_STRING = "jdbc:sqlite:src/main/DB/DB.db";

    private DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection(CONNECTION_STRING);
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données: " + e.getMessage());
            throw new RuntimeException("Erreur de connexion à la base de données.", e);
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
}