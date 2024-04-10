
package helha.java24groupe08.models;
import com.google.gson.annotations.SerializedName;
/**
 * Représente un film dans l'application de cinéma.
 * Contient des informations sur le film qui peuvent être récupérées et stockées dans une base de données,
 * ainsi que sérialisées et désérialisées à partir du format JSON.
 */
public class Movie {
    // Annotations @SerializedName indiquent le nom des champs dans le JSON source
    @SerializedName("Title") private String title;
    @SerializedName("Year") private String year;
    @SerializedName("Rated") private String rated;
    @SerializedName("Released") private String released;
    @SerializedName("Runtime") private String runtime;
    @SerializedName("Genre") private String genre;
    @SerializedName("Director") private String director;
    @SerializedName("Writer") private String writer;
    @SerializedName("Actors") private String actors;
    @SerializedName("Plot") private String plot;
    @SerializedName("Language") private String language;
    @SerializedName("Country") private String country;
    @SerializedName("Awards") private String awards;
    @SerializedName("Poster") private String poster;
    // Getters
    public String getTitle() { return title; }
    public String getYear() { return year; }
    public String getRated() { return rated; }
    public String getReleased() { return released; }
    public String getRuntime() { return runtime; }
    public String getGenre() { return genre; }
    public String getDirector() { return director; }
    public String getWriter() { return writer; }
    public String getActors() { return actors; }
    public String getPlot() { return plot; }
    public String getLanguage() { return language; }
    public String getCountry() { return country; }
    public String getAwards() { return awards; }
    public String getPoster() { return poster; }
    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setYear(String year) { this.year = year; }
    public void setRated(String rated) { this.rated = rated; }
    public void setReleased(String released) { this.released = released; }
    public void setRuntime(String runtime) { this.runtime = runtime; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setDirector(String director) { this.director = director; }
    public void setWriter(String writer) { this.writer = writer; }
    public void setActors(String actors) { this.actors = actors; }
    public void setPlot(String plot) { this.plot = plot; }
    public void setLanguage(String language) { this.language = language; }
    public void setCountry(String country) { this.country = country; }
    public void setAwards(String awards) { this.awards = awards; }
    public void setPoster(String poster) { this.poster = poster; }
    /**
     * Retourne une représentation en chaîne de caractères de l'objet Movie.
     * Utilisé principalement à des fins de débogage pour afficher les informations du film dans la console.
     * @return Une chaîne de caractères représentant les informations du film.
     */
    @Override
    public String toString() {     // toString pour l'affichage
        return "Movie{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", rated='" + rated + '\'' +
                ", released='" + released + '\'' +
                ", runtime='" + runtime + '\'' +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                ", writer='" + writer + '\'' +
                ", actors='" + actors + '\'' +
                ", plot='" + plot + '\'' +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                ", awards='" + awards + '\'' +
                ", poster='" + poster + '\'' +
                '}';
    }
}
