package helha.java24groupe08.client.controllers;

import helha.java24groupe08.client.models.MovieDBController;
import helha.java24groupe08.client.models.Session;
import helha.java24groupe08.client.views.IndexViewController;
import helha.java24groupe08.client.views.SideWindowViewController;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.sql.Date;
import java.sql.Time;
import java.util.function.Consumer;


public class SideWindowController implements SideWindowViewController.Listener{

    public String[] movieDetails;
    public Consumer<String> deleteMovieCallback;
    public Consumer<String[]> updateMovieCallback;
    public IndexViewController indexViewController;
    public SideWindowViewController viewController;


    public void setViewController(SideWindowViewController viewController) {
        this.viewController = viewController;
    }

    @Override
    public void saveChangesOnAction(ActionEvent event, String newTitle, String newPlot) {
        try {
            String oldTitle = movieDetails[0];


            movieDetails[0] = newTitle;
            movieDetails[9] = newPlot;

            MovieDBController.updateMovieDetails(oldTitle, movieDetails);

            if (updateMovieCallback != null) {
                updateMovieCallback.accept(movieDetails);
            }


            ((Button) event.getSource()).getScene().getWindow().hide();
        } catch (Exception e) {
            ErrorUtils.showErrorAlert("Error updating movie details: " + e.getMessage());
        }
    }

    @Override
    public void deleteMovieAction(ActionEvent event) {
        try {
            String movieTitle = movieDetails[0];
            if (deleteMovieCallback != null) {
                deleteMovieCallback.accept(movieTitle);
            }
            ((Button) event.getSource()).getScene().getWindow().hide();
        } catch (NullPointerException e) {
            ErrorUtils.showErrorAlert("The delete callback function is not set: " + e.getMessage());
        }
    }


    @Override
    public void addSessionAction(ActionEvent event, Integer roomNumber, Time startTime, Date date) {
        if (roomNumber == null || startTime == null || date == null) {
            ErrorUtils.showErrorAlert("Please fill in all fields.");
            return;
        }

        try {
            // Create a new session without the sessionId (use the overloaded constructor)
            Session session = new Session(roomNumber.intValue(), startTime, date, Integer.parseInt(movieDetails[14]));
            MovieDBController.insertSession(session);
            viewController.initSessionTable();
        } catch (Exception e) {
            ErrorUtils.showErrorAlert("Error adding session: " + e.getMessage());
        }
    }


    @Override
    public void deleteSessionAction(ActionEvent event, Session selectedSession) {
        if (selectedSession != null) {
            MovieDBController.deleteSession(selectedSession.getSessionId());
            viewController.initSessionTable();
        } else {
            ErrorUtils.showErrorAlert("No session selected for deletion.");
        }
    }

    public void initData(String[] movieDetails, Consumer<String> deleteMovieCallback, Consumer<String[]> updateMovieCallback) {
        this.movieDetails = movieDetails;
        this.deleteMovieCallback = deleteMovieCallback;
        this.updateMovieCallback = updateMovieCallback;
    }
}
