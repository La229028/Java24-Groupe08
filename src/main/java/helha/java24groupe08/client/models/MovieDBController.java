/**
 * The MovieDBController class provides methods to interact with the movie database.
 * It includes functionality for retrieving, inserting, updating, and deleting movie data.
 */
package helha.java24groupe08.client.models;

import helha.java24groupe08.client.models.exceptions.MovieNotFoundException;
import javafx.scene.control.Alert;

import helha.java24groupe08.client.models.exceptions.MovieNotFoundException;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDBController {

    private static final String CONNECTION_STRING = "jdbc:sqlite:src/main/DB/DB.db";
    private static final int MOVIE_DETAILS_LENGTH = 14;

    /**
     * Searches for movies in the database that match the given partial or complete title.
     * It will return a list of movie details where each movie's title contains the search term.
     *
     * @param searchTitle The partial or complete title of the movie to search for.
     * @return A list of String arrays, each containing details of a matching movie.
     */

    public static List<String[]> searchMoviesByTitle(String searchTitle) {
        List<String[]> foundMovies = new ArrayList<>();
        String sql = "SELECT * FROM movies WHERE Title LIKE ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + searchTitle + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                foundMovies.add(getMovieDetailsFromResultSet(rs));
            }
        } catch (SQLException e) {
            showErrorAlert("Error searching movies in database: " + e.getMessage());
        }
        return foundMovies;
    }


    /**
     * Inserts a movie into the database.
     *
     * @param movieData The data of the movie to be inserted.
     */
    public static void insertMovie(String movieData) {
        String sql = "INSERT INTO movies(data) VALUES(?)";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, movieData);
            pstmt.executeUpdate();
            System.out.println("Movie inserted successfully.");
        } catch (SQLException e) {
            showErrorAlert("Error inserting movie into database: " + e.getMessage());
        }
    }

    /**
     * Retrieves all movies from the database.
     *
     * @return A list containing details of all movies.
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
            showErrorAlert("Error getting all movies from database: " + e.getMessage());
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

    /**
     * Inserts a movie into the database with specified details.
     *
     * @param title    The title of the movie.
     * @param year     The release year of the movie.
     * @param rated    The rating of the movie.
     * @param released The release date of the movie.
     * @param runtime  The runtime of the movie.
     * @param genre    The genre of the movie.
     * @param director The director of the movie.
     * @param writer   The writer of the movie.
     * @param actors   The actors of the movie.
     * @param plot     The plot of the movie.
     * @param language The language of the movie.
     * @param country  The country of the movie.
     * @param awards   The awards of the movie.
     * @param poster   The poster URL of the movie.
     */
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
            showErrorAlert("Error setting movie in database: " + e.getMessage());
        }
    }

    /**
     * Retrieves details of a movie from the database based on its title.
     *
     * @param title The title of the movie.
     * @return An array containing details of the movie if found, empty otherwise.
     */
    public String[] getMovie(String title) throws MovieNotFoundException {
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
            showErrorAlert("Error getting movie from database: " + e.getMessage());
        }

        return movieDetails;
    }

    /**
     * Checks if a movie with the specified title exists in the database.
     *
     * @param title The title of the movie.
     * @return True if the movie exists, false otherwise.
     */
    public boolean movieExists(String title) {
        String sql = "SELECT * FROM movies WHERE Title = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            showErrorAlert("Error checking movie existence in database: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a movie from the database based on its title.
     *
     * @param title The title of the movie to be deleted.
     */
    public static void deleteMovie(String title) {
        String sql = "DELETE FROM movies WHERE Title = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.executeUpdate();
            System.out.println("Movie deleted successfully.");
        } catch (SQLException e) {
            showErrorAlert("Error deleting movie from database: " + e.getMessage());
        }
    }



    public static void updateMovieDetails(String oldTitle, String[] movieDetails){
        String sql = "UPDATE movies SET Title = ?, Plot = ? WHERE Title = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, movieDetails[0]); // new title
            pstmt.setString(2, movieDetails[9]); // new plot
            pstmt.setString(3, oldTitle); // old title
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Movie updated successfully.");
            } else {
                showErrorAlert("No movie found with the given title.");
            }
        } catch (SQLException e) {
            showErrorAlert("Error updating movie in database: " + e.getMessage());
        }
    }


    private static void showErrorAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database Error");
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

}
