package controllers.src;

import java.util.Date;
import java.util.UUID;

/**
 * Shows the changes a user makes
 */
public class Change {

    public UUID id;
    public String previousSection;
    public String nextSection;
    public Date date;
    public User userEdited;

    /**
     * Holds the information a user has changed
     * 
     * @param previousSection holds the previous section in a string
     * @param nextSection     holds the next section in a string
     * @param userEdited      holds the information of what the user edited in a
     *                        string
     */
    public Change(String previousSection, String nextSection, User userEdited) {
        this.id = UUID.randomUUID();
        this.previousSection = previousSection;
        this.nextSection = nextSection;
        this.userEdited = userEdited;
        this.date = new Date();
    }

    /**
     * Holds the information a user has changed
     * 
     * @param id              holds the UUID of the user
     * @param previousSection holds the previous section in a string
     * @param nextSection     holds the next section in a string
     * @param date            holds the date information
     * @param userEdited      holds the information of what the user edited in a
     *                        string
     */
    public Change(UUID id, String previousSection, String nextSection, Date date, User userEdited) {
        this.id = id;
        this.previousSection = previousSection;
        this.nextSection = nextSection;
        this.date = date;
        this.userEdited = userEdited;
    }
}
