package helha.java24groupe08.client.controllers;

import helha.java24groupe08.client.models.MovieDBController;
import javafx.scene.control.Alert;

import java.io.InputStreamReader;

import java.net.*;

import java.io.*;

import java.util.stream.Collectors;

/**
 * This class is responsible for managing the API.
 * It includes methods for retrieving movie data from an external API,
 * storing movie data in a SQLite database, and loading movie data from a JSON file.
 */
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MovieAPIController {

    private static final String API_URL = "http://www.omdbapi.com/";
    private static final String API_KEY = "3703372b";
    private static final String QUERY_PARAM_TITLE = "t";

    /**
     * Retrieves movie data from the API.
     * @param title The title of the movie to search for.
     * @return The movie data as a JSON string.
     */
    public String getMovieFromApi(String title) { // title comes from the user input
        try {
            String encodedTitle = URLEncoder.encode(title, "UTF-8"); // Encode the title to handle special characters
            String apiUrl = API_URL + "?" + QUERY_PARAM_TITLE + "=" + encodedTitle + "&apikey=" + API_KEY; // Construct the API URL
            HttpURLConnection con = (HttpURLConnection) new URL(apiUrl).openConnection(); // Open a connection to the API
            con.setRequestMethod("GET"); // Set the request method to GET

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { // Check if the request was successful
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) { // Read the response from the API
                    String response = in.lines().collect(Collectors.joining()); // Convert the response to a string
                    if (response.contains("\"Response\":\"False\"")) { // Check if the movie was not found
                        showErrorAlert("The movie : " + title + " has not been found or is not accessible via the API."); // Print an error message
                        return null; // Return null
                    }
                    return response; // Or return the movie data
                }
            } else { // If the request was not successful
                showErrorAlert("GET request failed with response code: " + con.getResponseCode()); // Print an error message
            }
        } catch (UnsupportedEncodingException e) {
            showErrorAlert("Error encoding the movie title: " + e.getMessage());
        } catch (MalformedURLException e) {
            showErrorAlert("Error with the API URL: " + e.getMessage());
        } catch (ProtocolException e) {
            showErrorAlert("Error setting the request method: " + e.getMessage());
        } catch (IOException e) {
            showErrorAlert("Error fetching movie data from API: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves movie data from the API and inserts it into the database.
     * @param title The title of the movie to search for.
     */
    public static void getMovieFromApiAndInsertIntoDB(String title) {
        MovieAPIController movieAPIController = new MovieAPIController(); // Create a new instance of the MovieAPIController
        try{
            String movieData = movieAPIController.getMovieFromApi(title); // Get the movie data from the API
            if (movieData != null) { // If the movie data is not null
                MovieDBController.insertMovie(movieData); // Insert the movie data into the database
            } else {
                movieAPIController.showErrorAlert("The movie : " + title + " has not been found or is not accessible via the API.");
            }
        } catch(Exception e){
            movieAPIController.showErrorAlert("Error getting movie data from API: " + e.getMessage());
        }
    }


    /**
     * Retrieves movie data from the API and inserts it into the database.
     * @param titles The titles of the movies to search for.
     */
    public static void getAndStoreMoviesFromApi(String[] titles) {
        MovieAPIController movieAPIController = new MovieAPIController();
        for (String title : titles) {
            try {
                movieAPIController.getMovieFromApiAndInsertIntoDB(title); // For each title, get the movie data from the API and insert it into the database
            } catch (Exception e) {
                movieAPIController.showErrorAlert("An error occurred while getting the movie '" + title + "' from the API and inserting it into the database: " + e.getMessage());
            }
        }
    }


    /**
     * Checks if a given string is a valid URL.
     * @param url The string to check.
     * @return True if the string is a valid URL, false otherwise.
     */
    public boolean isValidURL(String url) {
        try {
            new URL(url).toURI(); // Try to convert the string to a URL
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            showErrorAlert("Invalid URL: " + e.getMessage());
            return false;
        }
    }


    private void showErrorAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
