package models;

import java.util.UUID;

/**
 * Creates a new user
 */
public class User {

    public static void main(String[] args) {
        User user1 = new User("jim", "jones", "jj@gmail.com");
        System.out.println(user1.email);
    }

    public UUID id;
    public String username;
    private String password;
    public String email;

    /**
     * Creates a new user with a random uuid
     * 
     * @param username holds the username of the user in a string
     * @param password holds the password of the user in a string
     * @param email    holds the email of the user in a string
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = UUID.randomUUID();
    }

    /**
     * Creates a new user
     * 
     * @param id       holds the uuid of the user
     * @param username holds the username of the user in a string
     * @param password holds the password of the user in a string
     * @param email    holds the email of the user in a string
     */
    public User(UUID id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Allows a user to login with their username and password
     * 
     * @param username holds the username of the user in a string
     * @param password holds the password of the user in a string
     * @return returns the users username and password for login
     */
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    /**
     * Gets the user password
     * 
     * @return returns the password of a user
     */
    public String getPassword() {
        return password;
    }
}
