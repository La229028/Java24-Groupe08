package helha.java24groupe08.models;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDBController {

    private static final String CONNECTION_STRING = "jdbc:sqlite:src/main/DB/DB.db";
    private static final int MOVIE_DETAILS_LENGTH = 14;

    /**
     * Retrieves movie data from the database.
     * @param title The title of the movie to search for.
     * @return The movie data as a JSON string.
     */
    public static String getTitle(String title) {
        String sql = "SELECT Title FROM movies WHERE data LIKE ?"; // SQL query to get the movie title from the database
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING); // Open a connection to the database
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepare the SQL statement
            pstmt.setString(1, "%" + title + "%"); // Set the title parameter
            ResultSet rs = pstmt.executeQuery(); // Execute the query (search for the movie title) and get the result
            if (rs.next()) { // Check if a result was found
                return rs.getString("Title"); // Return the movie title
            }
        } catch (SQLException e) {
            System.err.println("Error getting movie title from database: " + e.getMessage());
        }
        return null;
    }


    /**
     * Retrieves movie data from the database.
     * @param movieData The movie data to search for.
     * @return The movie data as a JSON string.
     */
    public static void insertMovie(String movieData) {
        String sql = "INSERT INTO movies(data) VALUES(?)"; // SQL query to insert the movie data into the database
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING); // Open a connection to the database
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepare the SQL statement
            pstmt.setString(1, movieData); // Set the movie data parameter
            pstmt.executeUpdate(); // Execute the query (insert the movie data into the database)
            System.out.println("Movie inserted successfully."); // Print a success message
        } catch (SQLException e) {
            System.err.println("Error inserting movie into database: " + e.getMessage());
        }
    }

    /**
     * Retrieves all movie data from the database.
     * @return A list of movie data as JSON strings.
     */
    public static List<String[]> getAllMovies() {
        List<String[]> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                movies.add(getMovieDetailsFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all movies from database: " + e.getMessage());
        }
        return movies;
    }

    private static String[] getMovieDetailsFromResultSet(ResultSet rs) throws SQLException {
        String[] movie = new String[MOVIE_DETAILS_LENGTH];
        movie[0] = rs.getString("Title");
        movie[1] = rs.getString("Year");
        movie[2] = rs.getString("Rated");
        movie[3] = rs.getString("Released");
        movie[4] = rs.getString("Runtime");
        movie[5] = rs.getString("Genre");
        movie[6] = rs.getString("Director");
        movie[7] = rs.getString("Writer");
        movie[8] = rs.getString("Actors");
        movie[9] = rs.getString("Plot");
        movie[10] = rs.getString("Language");
        movie[11] = rs.getString("Country");
        movie[12] = rs.getString("Awards");
        movie[13] = rs.getString("Poster");
        return movie;
    }


    public void setMovie(String title, String year, String rated, String released, String runtime,
                         String genre, String director, String writer, String actors, String plot,
                         String language, String country, String awards, String poster) {
        String sql = "INSERT INTO movies(Title, Year, Rated, Released, Runtime, Genre, Director, Writer, Actors, Plot, Language, Country, Awards, Poster) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            pstmt.setString(2, year);
            pstmt.setString(3, rated);
            pstmt.setString(4, released);
            pstmt.setString(5, runtime);
            pstmt.setString(6, genre);
            pstmt.setString(7, director);
            pstmt.setString(8, writer);
            pstmt.setString(9, actors);
            pstmt.setString(10, plot);
            pstmt.setString(11, language);
            pstmt.setString(12, country);
            pstmt.setString(13, awards);
            pstmt.setString(14, poster);

            pstmt.executeUpdate();
            System.out.println("Movie inserted successfully.");
        } catch (SQLException e) {
            System.err.println("Error setting movie in database: " + e.getMessage());
        }
    }

    public String[] getMovie(String title) {
        String[] movieDetails = new String[MOVIE_DETAILS_LENGTH];
        String sql = "SELECT * FROM movies WHERE Title = ?";

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                movieDetails = getMovieDetailsFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting movie from database: " + e.getMessage());
        }

        return movieDetails;
    }

    public boolean movieExists(String title) {
        String sql = "SELECT * FROM movies WHERE Title = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Error checking movie existence in database: " + e.getMessage());
            return false;
        }
    }

    public static void deleteMovie(String title) {
        String sql = "DELETE FROM movies WHERE Title = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.executeUpdate();
            System.out.println("Movie deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Error deleting movie from database: " + e.getMessage());
        }
    }
}
