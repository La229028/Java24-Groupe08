package helha.java24groupe08.models;

import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("Title") private String title;
    @SerializedName("Released") private String released;
    @SerializedName("Runtime") private String runtime;
    @SerializedName("Genre") private String genre;
    @SerializedName("Director") private String director;
    @SerializedName("Actors") private String actors;
    @SerializedName("Plot") private String plot;
    @SerializedName("Poster") private String poster;

    // Constructeur par défaut
    public Movie() {
        // Initialisation des attributs
    }

    //récuperer le titre
    public String getTitle() {
        return title;
    }

    //récuperer l'image
    public String getPoster() {
        return poster;
    }

    //récuperer la description/résumé
    public String getPlot() {
        return plot;
    }

    public String getReleased() {
        return released;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }


    // Getters et setters pour chaque attribut
    // Méthodes supplémentaires selon les besoins
}