package com.example.java24groupe08;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;
import com.google.gson.Gson;

public class OMDbApiService {
    private final String apiKey;

    public OMDbApiService(String apiKey) {
        this.apiKey = apiKey;
    }

    // Méthode pour récupérer les données d'un film par son titre
    public String fetchMovieData(String movieTitle) {
        HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2).build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://www.omdbapi.com/?apikey=" + this.apiKey + "&t=" + movieTitle.replace(" ", "+")))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // Méthode pour parser la réponse JSON en objet Java
    public Movie parseMovieData(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Movie.class);
    }

    // Tu peux ajouter d'autres méthodes utiles ici, par exemple, une méthode pour insérer les données dans ta base de données

    // Classe interne représentant les données d'un film
    public static class Movie {
        String Title;
        String Year;
        String Plot;
        String Poster;
        // Ajoute d'autres champs selon besoin
    }
}
