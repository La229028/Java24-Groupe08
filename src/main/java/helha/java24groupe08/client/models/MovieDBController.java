/**
 * The MovieDBController class provides methods to interact with the movie database.
 * It includes functionality for retrieving, inserting, updating, and deleting movie data.
 */
package helha.java24groupe08.client.models;

import helha.java24groupe08.client.models.exceptions.MovieNotFoundException;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDBController {

private static final String CONNECTION_STRING = "jdbc:sqlite:src/main/DB/DB.db";
private static final int MOVIE_DETAILS_LENGTH = 16;

    /**
     * Constructs a MovieDBController object and initializes the sessions table if it doesn't exist.
     */
public MovieDBController() {
    createSessionsTable();
}

    /**
     * Searches for movies by title, now also retrieves movie_id.
     *
     * @param searchTitle The title to search for.
     * @return A list of movies matching the search criteria.
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
public static void insertMovieWithTitle(String movieData) {
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

    // Updated 'getMovieDetailsFromResultSet' to place 'id' at the end
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
        movie[14] = String.valueOf(rs.getInt("id"));  // 'id' at the end
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
                     String language, String country, String awards, String poster, int id) {
    String sql = "INSERT INTO movies(Title, Year, Rated, Released, Runtime, Genre, Director, Writer, Actors, Plot, Language, Country, Awards, Poster, Id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
        pstmt.setInt(15, id);

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
                showErrorAlert("Error deleting session from database: " + e.getMessage());
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
            showErrorAlert("Error retrieving sessions from database: " + e.getMessage());
        }
        return sessions;
    }

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
            showErrorAlert("Error creating sessions table: " + e.getMessage());
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
            showErrorAlert("Error inserting session into database: " + e.getMessage());
        }
    }



    private static void showErrorAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database Error");
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
     * Filters and sorts movies based on genre, language, and sort criteria.
     *
     * @param genre The genre to filter by.
     * @param language The language to filter by.
     * @param sort The sorting criteria.
     * @return A list of movies matching the filter and sort criteria.
     */
    public static List<String[]> filterAndSortMovies(String genre, String language, String sort) {
        List<String[]> movies = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM movies WHERE 1=1");

        if (genre != null && !genre.equals("All")) {
            queryBuilder.append(" AND genre LIKE ?");
        }
        if (language != null && !language.equals("All")) {
            queryBuilder.append(" AND language LIKE ?");
        }
        if (sort != null) {
            if (sort.equals("Title")) {
                queryBuilder.append(" ORDER BY title");
            } else if (sort.equals("Release Date")) {
                queryBuilder.append(" ORDER BY released");
            }
        }

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString())) {

            int paramIndex = 1;
            if (genre != null && !genre.equals("All")) {
                pstmt.setString(paramIndex++, "%" + genre + "%");
            }
            if (language != null && !language.equals("All")) {
                pstmt.setString(paramIndex++, "%" + language + "%");
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                movies.add(getMovieDetailsFromResultSet(rs));
            }
        } catch (SQLException e) {
            showErrorAlert("Error filtering and sorting movies from database: " + e.getMessage());
        }

        return movies;
    }


}
