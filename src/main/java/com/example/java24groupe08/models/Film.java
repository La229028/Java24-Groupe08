package com.example.java24groupe08.models;

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
    private String plot;
    private String language;
    private String country;
    private String awards;
    private String poster;

    // Constructeur par défaut
    public Film() {
        // Initialisation des attributs
    }

    //récuperer le titre
    public String getTitle() {
        return title;
    }

    // Getters et setters pour chaque attribut
    // Méthodes supplémentaires selon les besoins
}