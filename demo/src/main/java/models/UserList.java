package models;

import java.util.ArrayList;

import utils.DataLoader;
import utils.DataWriter;

/**
 * Creates an array list of users
 */
public class UserList {

    private static UserList list;
    public ArrayList<User> users;
    public User user;

    private UserList() {
        users = DataLoader.loadUsers();
        user = null;
    }

    /**
     * Returns the array list of users
     * 
     * @return returns the list of users it if is not null
     */
    public static UserList getUserList() {
        if (list == null)
            return new UserList();
        return list;
    }

    /**
     * Regesters a new user by a username, email, and password
     * 
     * @param email    holds the email of the user in a string
     * @param username holds the username of the user in a string
     * @param password holds the password of the user in a string
     * @return returns a different status depending on information entered
     */
    public boolean register(String email, String username, String password) {
        // all empty
        if ((username == null || username.equals("")) && (password == null || password.equals(""))
                && (email == null || email.equals("")))
            return false;

        // empty username and password
        if ((username == null || username.equals("")) && (password == null || password.equals("")))
            return false;

        // empty username and email
        if ((username == null || username.equals(""))
                && (email == null || email.equals("")))
            return false;

        // empty email and password
        if ((password == null || password.equals(""))
                && (email == null || email.equals("")))
            return false;

        // empty username
        if (username == null || username.equals(""))
            return false;

        // empty password
        if (password == null || password.equals(""))
            return false;

        // empty email
        if (email == null || email.equals(""))
            return false;

        // username taken
        for (User user : users) {
            if (user.username.equals(username))
                return false;
        }

        // short password
        if (password.length() < 8)
            return false;

        User newUser = new User(username, password, email);
        users.add(newUser);
        user = newUser;
        DataWriter.saveUsers(users);
        return true;
    }

    /**
     * Logs in a user when they use the correct credentials
     * 
     * @param username holds the username of the user in a string
     * @param password holds the password of the user in a string
     * @return returns a different status based on information entered
     */
    public boolean login(String username, String password) {
        if ((username == null || username.equals("")) && (password == null || password.equals("")))
            return false;

        if (username == null || username.equals(""))
            return false;

        if (username == null || username.equals(""))
            return false;

        for (User userElement : users) {
            if (userElement.login(username, password)) {
                user = userElement;
                return true;
            }
        }
        return false;
    }

    /**
     * Logs out a user
     * 
     * @return returns successesfull on logout
     */
    public boolean logout() {
        user = null;
        return true;
    }
}