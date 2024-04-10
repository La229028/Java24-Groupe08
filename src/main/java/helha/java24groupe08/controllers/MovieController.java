package helha.java24groupe08.controllers;

import helha.java24groupe08.models.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.GsonBuilder;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class is responsible for managing movies.
 * It includes methods for retrieving movie data from an external API,
 * storing movie data in a SQLite database, and loading movie data from a JSON file.
 */
public class MovieController {

    private static final String CONNECTION_STRING = "jdbc:sqlite:src/main/DB/DB.db";
    private static final String JSON_FILE_PATH = "src/main/resources/movie.json";

    /**
     * Retrieves a movie from an external API using the movie's title.
     *
     * @param title The title of the movie to search for.
     * @return A Movie object containing the retrieved movie data, or null if the movie is not found.
     */
    public static Movie getMovieFromApi(String title) {
        String apiUrl = "http://www.omdbapi.com/?t=" + title.replace(" ", "%20") + "&apikey=3703372b";
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    Gson gson = new Gson();
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    String response = content.toString();

                    // Check if the film is not found
                    if(response.contains("\"Response\":\"False\"")) {
                        System.out.println("The movie : " + title + " has not been found or is not accessible via the API.");
                        return null;
                    }

                    return gson.fromJson(response, Movie.class);
                }
            } else {
                System.out.println("GET request not worked");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves and stores a list of movies in the database and in a JSON file from their titles.
     *
     * @param titles An array containing the titles of the movies to retrieve.
     */
    public static void getAndStoreMoviesFromApi(String[] titles) {
        List<Movie> moviesFromApi = new ArrayList<>();
        List<Movie> existingMovies = loadMovieData(); // Load movies already in JSON

        for (String title : titles) {
            Movie movie = getMovieFromApi(title);
            if (movie != null && !existingMovies.contains(movie) && isValidURL(movie.getPoster())) { // Check if the film is already present
                moviesFromApi.add(movie);
            }
        }

        // Add new movies to JSON file and database
        existingMovies.addAll(moviesFromApi);
        writeMoviesToJson(existingMovies); // Update the JSON file with the combined list
        for (Movie movie : moviesFromApi) { // Insert only new films into the database
            insertMoviesIntoDb(movie);
        }
    }

    /**
     * Checks if a URL is valid.
     *
     * @param url The URL to check.
     * @return true if the URL is valid, false otherwise.
     */
    public static boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Writes a list of movies to a JSON file.
     *
     * @param movies The list of movies to write to the file.
     */
    public static void writeMoviesToJson(List<Movie> movies) {
        if (movies != null) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (Writer writer = new FileWriter(JSON_FILE_PATH)) {
                gson.toJson(movies, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("La liste des films est nulle. Impossible d'écrire dans le fichier JSON.");
        }
    }

    /**
     * Loads movie data from a JSON file.
     *
     * @return A list of movies loaded from the JSON file.
     */
    public static List<Movie> loadMovieData() {
        File jsonFile = new File(JSON_FILE_PATH);
        if (!jsonFile.exists()) {
            setupDefaultJsonFile();
        }
        try (Reader reader = new FileReader(JSON_FILE_PATH)) {
            Gson gson = new Gson();
            Type movieListType = new TypeToken<List<Movie>>(){}.getType();
            return gson.fromJson(reader, movieListType);
        } catch (IOException e) {
            System.out.println("Error loading JSON file: " + e.getMessage());
            return null; // Returns null on error
        }
    }

    private static void setupDefaultJsonFile() {
        try {
            Files.write(Paths.get(JSON_FILE_PATH), "[]".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Inserts a movie into the SQLite database.
     *
     * @param movie The Movie object containing the movie data to insert.
     */
    public static void insertMoviesIntoDb(Movie movie) {
        if (movieExistsInDb(movie)) {
            return;
        }
        System.out.println("The Movie: " + movie.getTitle() + " does not exist in the JSON, i.e. in the database.");
        String sql = "INSERT INTO movies(title, year, rated, released, runtime, genre, director, writer, actors, plot, language, country, awards, poster) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING); // Connexion à la db sqlite
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getYear());
            pstmt.setString(3, movie.getRated());
            pstmt.setString(4, movie.getReleased());
            pstmt.setString(5, movie.getRuntime());
            pstmt.setString(6, movie.getGenre());
            pstmt.setString(7, movie.getDirector());
            pstmt.setString(8, movie.getWriter());
            pstmt.setString(9, movie.getActors());
            pstmt.setString(10, movie.getPlot());
            pstmt.setString(11, movie.getLanguage());
            pstmt.setString(12, movie.getCountry());
            pstmt.setString(13, movie.getAwards());
            pstmt.setString(14, movie.getPoster());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error inserting film into database : " + e.getMessage());
        }
    }

    /**
     * Checks if a movie already exists in the database using its title and release year.
     *
     * @param movie The Movie object to check.
     * @return true if the movie already exists, false otherwise.
     */
    public static boolean movieExistsInDb(Movie movie) {
        String url = "jdbc:sqlite:src/main/DB/DB.db/";
        String sql = "SELECT * FROM movies WHERE title = ? AND year = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getYear());
            ResultSet rs = pstmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public static void deleteMovie(String posterURL) {
        String sql = "DELETE FROM movies WHERE poster = ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, posterURL);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du film dans la base de données : " + e.getMessage());
        }

        // Load all movies from the JSON file
        List<Movie> movies = loadMovieData();

        // Remove the movie with the matching poster URL
        movies.removeIf(movie -> movie.getPoster().equals(posterURL));

        // Write the updated list back to the JSON file
        writeMoviesToJson(movies);
    }
}

