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

    public static String getMovieFromApi(String title) {
        try {
            String encodedTitle = URLEncoder.encode(title, "UTF-8");
            String apiUrl = API_URL + "?" + QUERY_PARAM_TITLE + "=" + encodedTitle + "&apikey=" + API_KEY;
            HttpURLConnection con = (HttpURLConnection) new URL(apiUrl).openConnection();
            con.setRequestMethod("GET");

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String response = in.lines().collect(Collectors.joining());
                    if (response.contains("\"Response\":\"False\"")) {
                        System.out.println("The movie : " + title + " has not been found or is not accessible via the API.");
                        return null;
                    }
                    return response;
                }
            } else {
                System.err.println("GET request failed with response code: " + con.getResponseCode());
            }
        } catch (Exception e) {
            System.err.println("Error fetching movie data from API: " + e.getMessage());
        }
        return null;
    }

    public static void getMovieFromApiAndInsertIntoDB(String title) {
        String movieData = getMovieFromApi(title);
        if (movieData != null) {
            MovieDBController.insertMovie(movieData);
        } else {
            System.out.println("The movie : " + title + " has not been found or is not accessible via the API.");
        }
    }

    public static void getAndStoreMoviesFromApi(String[] titles) {
        Arrays.stream(titles).forEach(MovieAPIController::getMovieFromApiAndInsertIntoDB);
    }

    public boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
