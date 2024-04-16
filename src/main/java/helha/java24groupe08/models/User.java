package helha.java24groupe08.models;

public class User {
    private String lastname;
    private String firstname;
    private String numberPhone;
    private String email;
    private int age;
    private String status;
    private String password;

    public User(String lastname, String firstname, String numberPhone, String email, int age, String status, String password) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.numberPhone = numberPhone;
        this.email = email;
        this.age = age;
        this.status = status;
        this.password = password;
    }

    //GETTERS

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getNumberPhone() {
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

    public String getPassword() {
        return password;
    }

    //SETTERS

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}