package helha.java24groupe08.models;

import com.google.gson.annotations.SerializedName;

public class Film {
    @SerializedName("Title")
    private String title;
    private String year;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String director;
    private String writer;
    private String actors;

    @SerializedName("Plot")
    private String plot;
    private String language;
    private String country;
    private String awards;

    @SerializedName("Poster")
    private String poster;

    // Constructeur par défaut
    public Film() {
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

    // Getters et setters pour chaque attribut
    // Méthodes supplémentaires selon les besoins
}