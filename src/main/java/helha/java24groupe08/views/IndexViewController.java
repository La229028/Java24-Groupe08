package helha.java24groupe08.views;

import helha.java24groupe08.models.Movie;
import helha.java24groupe08.controllers.FilmController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static helha.java24groupe08.controllers.FilmController.isValidURL;

/**
 * Contrôleur pour la vue principale de l'application, responsable de l'affichage des films.
 * Utilise JavaFX pour générer dynamiquement des éléments d'interface utilisateur qui présentent les films.
 */
public class IndexViewController {
    @FXML
    public Label titleLabel;

    @FXML
    public Pane pane;

    @FXML
    public ScrollPane scrollPane;

    public void initialize() {
        titleLabel.setText("CINEMA"); // Mise à jour du titre avec celui du second code
        List<Movie> movies = FilmController.loadFilmData();
        createVBoxes(movies);
        scrollPane.setContent(pane);
    }

    private void createVBoxes(List<Movie> movies) {
        pane.getChildren().clear();
        for (Movie movie : movies) {
            VBox vbox = createVBox(movie);
            pane.getChildren().add(vbox);
            // La logique de positionnement est omise ici pour simplifier
        }
    }

    private VBox createVBox(Movie movie) {
        VBox vbox = new VBox();
        vbox.setPrefSize(150, 300); // Taille ajustée selon le second code
        vbox.setStyle("-fx-background-color: #F0F0F0; -fx-padding: 10px; -fx-spacing: 10px; -fx-border-color: black; -fx-border-width: 1px;");

        AnchorPane movieTitle = createMovieTitle(movie.getTitle());
        AnchorPane poster = createPoster(movie.getPoster());
        Button seeMoreButton = createSeeMoreButton(movie);

        vbox.getChildren().addAll(movieTitle, poster, seeMoreButton);
        vbox.setAlignment(Pos.CENTER);

        return vbox;
    }

    private AnchorPane createMovieTitle(String title) {
        AnchorPane movieTitle = new AnchorPane();
        movieTitle.setPrefSize(150, 50);
        movieTitle.setStyle("-fx-background-color: #6495ED; -fx-padding: 5px;");

        Label filmTitle = new Label(title);
        filmTitle.setWrapText(true);
        filmTitle.setPrefSize(140, 40);
        filmTitle.setAlignment(Pos.CENTER);
        filmTitle.setStyle("-fx-font-size: 14pt; -fx-text-fill: white;");
        movieTitle.getChildren().add(filmTitle);

        return movieTitle;
    }

    private AnchorPane createPoster(String posterURL) {
        AnchorPane poster = new AnchorPane();
        poster.setPrefSize(150, 200);
        poster.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 1px;");

        if (posterURL != null && !posterURL.isEmpty() && isValidURL(posterURL)) {
            Image image = new Image(posterURL, true);
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(130);
            poster.getChildren().add(imageView);
        }

        return poster;
    }

    private Button createSeeMoreButton(Movie movie) {
        Button seeMoreButton = new Button("Voir plus");
        seeMoreButton.setPrefSize(150, 30);
        seeMoreButton.setStyle("-fx-background-color: #6495ED; -fx-text-fill: white; -fx-font-size: 12pt; -fx-font-weight: bold;");

        seeMoreButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/java24groupe08/views/descrip.fxml"));
                Parent root = loader.load();
                // Mise à jour du controller avec les informations du film
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return seeMoreButton;
    }
}