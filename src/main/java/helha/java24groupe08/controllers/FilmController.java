package helha.java24groupe08.controllers;

import helha.java24groupe08.models.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Contrôle les opérations liées aux films, notamment la récupération depuis une API externe,
 * le stockage dans une base de données SQLite, et la gestion des données de film.
 */
public class FilmController {
    private static final String CONNECTION_STRING = "jdbc:sqlite:src/main/DB/DB.db";
    private static final String JSON_PATH = "src/main/resources/movies.json";
    /**
     * Récupère un film depuis une API externe en utilisant le titre du film.
     *
     * @param title Le titre du film à rechercher.
     * @return Un objet Movie contenant les données du film récupéré, ou null si le film n'est pas trouvé.
     */
    public static Movie getFilmFromApi(String title) {
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

                    // Vérifier si le film n'est pas trouvé
                    if(response.contains("\"Response\":\"False\"")) {
                        System.out.println("Le film " + title + " n'a pas été trouvé ou n'est pas accessible via l'API.");
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
     * Récupère et stocke une liste de films dans la base de données et dans un fichier JSON à partir de leurs titres.
     *
     * @param titles Tableau contenant les titres des films à récupérer.
     */
    public static void getAndStoreFilmsFromApi(String[] titles) {
        createMoviesTable(); // Crée la table 'movies' si elle n'existe pas déjà

        List<Movie> moviesFromApi = new ArrayList<>();
        List<Movie> existingMovies = loadFilmData(); // Chargez les films déjà présents dans le JSON

        for (String title : titles) {
            Movie movie = getFilmFromApi(title);
            if (movie != null && isValidURL(movie.getPoster())) { // Vérifiez si le film est déjà présent
                moviesFromApi.add(movie);
            }
        }

        // Ajoutez les nouveaux films au fichier JSON et à la base de données
        existingMovies.addAll(moviesFromApi);
        writeMoviesToJson(existingMovies); // Mettez à jour le fichier JSON avec la liste combinée
        for (Movie movie : moviesFromApi) { // Insérez seulement les nouveaux films dans la base de données
            insertMoviesIntoDb(movie);
        }
    }

    /**
     * Vérifie si une URL est valide.
     *
     * @param url L'URL à vérifier.
     * @return true si l'URL est valide, false autrement.
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
     * Écrit une liste de films dans un fichier JSON.
     *
     * @param movies La liste des films à écrire dans le fichier.
     */

    // ERREUR ICI : json est vide
    // ici besoin au moins d'une fois le json pour setup la db, puis récupérer le data à partir de la DB
    public static void writeMoviesToJson(List<Movie> movies) {
        if (movies != null) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (Writer writer = new FileWriter(JSON_PATH)) {
                gson.toJson(movies, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("La liste des films est nulle. Impossible d'écrire dans le fichier JSON.");
        }
    }
    /**
     * Charge les données de film à partir d'un fichier JSON.
     *
     * @return Une liste de films chargée à partir du fichier JSON.
     */

    // ISSUE HERE : ou est le JSON ??
    public static List<Movie> loadFilmData() {
        File jsonFile = new File(JSON_PATH);
        if (!jsonFile.exists()) {
            try {
                jsonFile.createNewFile();
                writeMoviesToJson(new ArrayList<>()); // Écrivez une liste vide de films dans le fichier
            } catch (IOException e) {
                System.out.println("Erreur lors de la création du fichier JSON: " + e.getMessage());
                return null;
            }
        }

        try (Reader reader = new FileReader(JSON_PATH)) {
            Gson gson = new Gson();
            Type filmListType = new TypeToken<List<Movie>>(){}.getType();
            return gson.fromJson(reader, filmListType);
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement du fichier JSON: " + e.getMessage());
            return null;
        }
    }
    public static void createMoviesTable() {
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS movies ("
                    + "id INTEGER PRIMARY KEY,"
                    + "title TEXT,"
                    + "year TEXT,"
                    + "rated TEXT,"
                    + "released TEXT,"
                    + "runtime TEXT,"
                    + "genre TEXT,"
                    + "director TEXT,"
                    + "writer TEXT,"
                    + "actors TEXT,"
                    + "plot TEXT,"
                    + "language TEXT,"
                    + "country TEXT,"
                    + "awards TEXT,"
                    + "poster TEXT"
                    + ")");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de la table 'movies' : " + e.getMessage());
        }
    }

    /**
     * Insère un film dans la base de données SQLite.
     *
     * @param movie L'objet Movie contenant les données du film à insérer.
     */
    public static void insertMoviesIntoDb(Movie movie) {
        if (movieExistsInDb(movie)) {
            System.out.println("Le film " + movie.getTitle() + " existe déjà dans le JSON donc dans la base de données.");
            return;
        }

        String sql = "INSERT INTO movies(title, year, rated, released, runtime, genre, director, writer, actors, plot, language, country, awards, poster) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
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
            System.out.println("Erreur lors de l'insertion du film dans la base de données : " + e.getMessage());
        }
    }

    /**
     * Vérifie si un film existe déjà dans la base de données en utilisant son titre et son année de sortie.
     *
     * @param movie L'objet Movie à vérifier.
     * @return true si le film existe déjà, false autrement.
     */
    public static boolean movieExistsInDb(Movie movie) {
        String sql = "SELECT COUNT(*) FROM movies WHERE title = ? AND year = ?";

        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getYear());

            ResultSet rs = pstmt.executeQuery();

            // Si le nombre de films avec le même titre et la même année est supérieur à 0, alors le film existe déjà
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification de l'existence du film dans la base de données : " + e.getMessage());
        }

        return false;
    }


}
