package helha.java24groupe08.client.controllers;

import helha.java24groupe08.client.models.MovieDBController;
import helha.java24groupe08.client.models.Session;
import helha.java24groupe08.client.models.SessionDBController;
import helha.java24groupe08.client.views.IndexViewController;
import helha.java24groupe08.client.views.SideWindowViewController;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.sql.Date;
import java.sql.Time;
import java.util.function.Consumer;

/**
 * This class is the controller for the side window of the application.
 * It handles the actions of the side window.
 */
public class SideWindowController implements SideWindowViewController.Listener{

    public String[] movieDetails;
    public Consumer<String> deleteMovieCallback;
    public Consumer<String[]> updateMovieCallback;
    public SideWindowViewController viewController;


    public void setViewController(SideWindowViewController viewController) {
        this.viewController = viewController;
    }

    /**
     * This method is called when the user clicks on the "Save changes" button.
     * It saves the changes made to the movie details.
     *
     * @param event The event that triggered the action.
     * @param newTitle The new title of the movie.
     * @param newPlot The new plot of the movie.
     */
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
            AlertUtils.showErrorAlert("Error updating movie details: " + e.getMessage());
        }
    }

    /**
     * This method is called when the user clicks on the "Delete movie" button.
     * It deletes the movie from the database.
     *
     * @param event The event that triggered the action.
     */
    @Override
    public void deleteMovieAction(ActionEvent event) {
        try {
            String movieTitle = movieDetails[0];
            if (deleteMovieCallback != null) {
                deleteMovieCallback.accept(movieTitle);
            }
            ((Button) event.getSource()).getScene().getWindow().hide();
        } catch (NullPointerException e) {
            AlertUtils.showErrorAlert("The delete callback function is not set: " + e.getMessage());
        }
    }

    /**
     * This method is called when the user clicks on the "Add session" button.
     * It adds a new session to the database.
     *
     * @param event The event that triggered the action.
     * @param roomNumber The room number of the session.
     * @param startTime The start time of the session.
     * @param date The date of the session.
     */
    @Override
    public void addSessionAction(ActionEvent event, Integer roomNumber, Time startTime, Date date) {
        if (roomNumber == null || startTime == null || date == null) {
            AlertUtils.showErrorAlert("Please fill in all fields.");
            return;
        }

        try {
            // Create a new session without the sessionId (use the overloaded constructor)
            Session session = new Session(roomNumber.intValue(), startTime, date, Integer.parseInt(movieDetails[14]));
            SessionDBController.insertSession(session);
            viewController.initSessionTable();
        } catch (Exception e) {
            AlertUtils.showErrorAlert("Error adding session: " + e.getMessage());
        }
    }

    /**
     * This method is called when the user clicks on the "Delete session" button.
     * It deletes the selected session from the database.
     *
     * @param event The event that triggered the action.
     * @param selectedSession The session to delete.
     */
    @Override
    public void deleteSessionAction(ActionEvent event, Session selectedSession) {
        if (selectedSession != null) {
            SessionDBController.deleteSession(selectedSession.getSessionId());
            viewController.initSessionTable();
        } else {
            AlertUtils.showErrorAlert("No session selected for deletion.");
        }
    }

    /**
     * This method is called when the user clicks on the "Update session" button.
     * @param movieDetails
     * @param deleteMovieCallback
     * @param updateMovieCallback
     */
    public void initData(String[] movieDetails, Consumer<String> deleteMovieCallback, Consumer<String[]> updateMovieCallback) {
        this.movieDetails = movieDetails;
        this.deleteMovieCallback = deleteMovieCallback;
        this.updateMovieCallback = updateMovieCallback;
    }
}
