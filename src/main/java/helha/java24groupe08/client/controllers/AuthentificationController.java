package helha.java24groupe08.client.controllers;

import helha.java24groupe08.client.models.User;
import helha.java24groupe08.client.models.UserSession;

/**
 * Controller used to check whether the user is authenticated or not (or admin) and sends an info to display the right view
 */
public class AuthentificationController {
    private static boolean isLoggedIn = false;
    private static boolean isAdminLoggedIn = false;

    /**
     * Method to login as an admin
     */
    public static void loginAdmin() {
        isLoggedIn = true;
        isAdminLoggedIn = true;
    }

    /**
     * Method to login as a user
     * @param user The user to login
     */
    public static void loginUser(User user) {
        isLoggedIn = true;
        isAdminLoggedIn = false;
        UserSession.getInstance().setUser(user);
    }

    /**
     * Method to logout
     */
    public static void logout() {
        isLoggedIn = false;
        isAdminLoggedIn = false;
        UserSession.getInstance().setUser(null);
    }


    /**
     * Method to check if the user is logged in
     * @return True if the user is logged in, false otherwise
     */
    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    /**
     * Method to check if the admin is logged in
     * @return True if the admin is logged in, false otherwise
     */
    public static boolean isAdminLoggedIn() {
        return isAdminLoggedIn;
    }
}
