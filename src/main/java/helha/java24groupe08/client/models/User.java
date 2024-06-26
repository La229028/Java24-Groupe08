package helha.java24groupe08.client.models;

/**
 * User class represents a user in the system.
 * It contains information about the user such as name, firstname, numberPhone, email, age, status, username, and password.
 */
public class User {
    private String name;
    private String firstname;
    private int numberPhone;
    private String email;
    private int age;
    private String status;
    private String username;
    private String password;

    /**
     * Constructor for the User class.
     * @param name The name of the user.
     * @param firstname The firstname of the user.
     * @param numberPhone The phone number of the user.
     * @param email The email of the user.
     * @param age The age of the user.
     * @param status The status of the user.
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public User(String name, String firstname, int numberPhone, String email, int age, String status, String username, String password) {
        this.name = name;
        this.firstname = firstname;
        this.numberPhone = numberPhone;
        this.email = email;
        this.age = age;
        this.status = status;
        this.username = username;
        this.password = password;
    }

    //GETTERS

    public String getName() {
        return name;
    }

    public String getFirstname() {
        return firstname;
    }

    public int getNumberPhone() {
        return numberPhone;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}