package models;

/**
 * @author Gavin Orme
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * comment class. Comment on projects, tasks, and comments
 */
public class Comment {

    public UUID id;
    public User user;
    public ArrayList<Comment> comments;
    public Date date;
    public String content;

    /**
     * construtor for new comment
     * 
     * @param content
     * @param user
     */
    public Comment(String content, User user) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.content = content;
        this.date = new Date();
        this.comments = new ArrayList<Comment>();
    }

    /**
     * constuctor for existing comment
     * 
     * @param id
     * @param content
     * @param user
     * @param comments
     * @param date
     */
    public Comment(UUID id, String content, User user, ArrayList<Comment> comments, Date date) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.date = date;
        this.comments = comments;
    }

    /**
     * creates comment
     * 
     * @param comment
     * @return if comment is created
     */
    public boolean createComment(Comment comment) {
        this.comments.add(comment);
        return true;
    }

    /**
     * deletes comment
     * 
     * @param comment
     * @return if comment is deleted
     */
    public boolean deleteComment(Comment comment) {
        this.comments.remove(comment);
        return true;
    }
}