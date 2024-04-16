package helha.java24groupe08.controllers;

import helha.java24groupe08.models.MovieDBController;

import java.io.InputStreamReader;

import java.util.Arrays;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.stream.Collectors;

/**
 * This class is responsible for managing the API.
 * It includes methods for retrieving movie data from an external API,
 * storing movie data in a SQLite database, and loading movie data from a JSON file.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class MovieAPIController {

    private static final String API_URL = "http://www.omdbapi.com/";
    private static final String API_KEY = "3703372b";
    private static final String QUERY_PARAM_TITLE = "t";

    /**
     * Retrieves movie data from the API.
     * @param title The title of the movie to search for.
     * @return The movie data as a JSON string.
     */
    public static String getMovieFromApi(String title) { // title comes from the user input
        try {
            String encodedTitle = URLEncoder.encode(title, "UTF-8"); // Encode the title to handle special characters
            String apiUrl = API_URL + "?" + QUERY_PARAM_TITLE + "=" + encodedTitle + "&apikey=" + API_KEY; // Construct the API URL
            HttpURLConnection con = (HttpURLConnection) new URL(apiUrl).openConnection(); // Open a connection to the API
            con.setRequestMethod("GET"); // Set the request method to GET

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { // Check if the request was successful
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) { // Read the response from the API
                    String response = in.lines().collect(Collectors.joining()); // Convert the response to a string
                    if (response.contains("\"Response\":\"False\"")) { // Check if the movie was not found
                        System.out.println("The movie : " + title + " has not been found or is not accessible via the API."); // Print an error message
                        return null; // Return null
                    }
                    return response; // Or return the movie data
                }
            } else { // If the request was not successful
                System.err.println("GET request failed with response code: " + con.getResponseCode()); // Print an error message
            }
        } catch (Exception e) { // Catch any exceptions
            System.err.println("Error fetching movie data from API: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves movie data from the API and inserts it into the database.
     * @param title The title of the movie to search for.
     */
    public static void getMovieFromApiAndInsertIntoDB(String title) {
        String movieData = getMovieFromApi(title); // Get the movie data from the API
        if (movieData != null) { // If the movie data is not null
            MovieDBController.insertMovie(movieData); // Insert the movie data into the database
        } else {
            System.out.println("The movie : " + title + " has not been found or is not accessible via the API."); // Print an error message
        }
    }


    /**
     * Retrieves movie data from the API and inserts it into the database.
     * @param titles The titles of the movies to search for.
     */
    public static void getAndStoreMoviesFromApi(String[] titles) {
        Arrays.stream(titles).forEach(MovieAPIController::getMovieFromApiAndInsertIntoDB); // For each title, get the movie data from the API and insert it into the database
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
        } catch (Exception e) {
            return false;
        }
    }
}
