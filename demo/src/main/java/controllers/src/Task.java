package controllers.src;

/**
 * @author Gavin Orme
 */
import java.util.ArrayList;
import java.util.UUID;

public class Task {

    public UUID id;
    public String description;
    public String title;
    public String type;
    public ArrayList<User> assignedUsers;
    public boolean completion;
    public int priority;
    public ArrayList<Comment> comments;
    public ArrayList<Change> changeLog;

    /**
     * creates getters and setters for the class Task
     * 
     * @param title       name of the task
     * @param description overview of the task
     * @param priority    a 1-3 priority of the task. 1 being the highest and 3
     *                    being the lowest
     * @param type        type of task which include bug, tester, or new feature
     */
    public Task(String title, String description, int priority, String type) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.type = type;
        this.completion = false;
        this.comments = new ArrayList<Comment>();
        this.changeLog = new ArrayList<Change>();
        this.assignedUsers = new ArrayList<User>();
    }

    /**
     * creates unique id's for the class Task along with the other strings,
     * booleans, integers, and array lists
     * 
     * @param id            universal unique identifer id
     * @param title         name of the task
     * @param description   overview of the task
     * @param type          type of task which include bug, tester, or new feature
     * @param assignedUsers users that can work on that specific task
     * @param completion    completion boolean of the task (true or false)
     * @param priority      a 1-3 priority of the task. 1 being the highest and 3
     *                      being the lowest
     * @param comments      comments under the task
     * @param changeLog     history of changes that have been made to the task
     */
    public Task(UUID id, String title, String description, String type, ArrayList<User> assignedUsers,
            boolean completion, int priority, ArrayList<Comment> comments,
            ArrayList<Change> changeLog) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.assignedUsers = assignedUsers;
        this.completion = completion;
        this.priority = priority;
        this.comments = comments;
        this.changeLog = changeLog;
    }

    /**
     * Allows a user to create a comment for a task
     * 
     * @param comment comment for the task
     * @return returns a completed comment specified for the task
     */
    public boolean createComment(Comment comment) {
        this.comments.add(comment);
        return true;
    }

    /**
     * Deletes a comment for the task
     * 
     * @param comment comment for the task
     * @return returns the deleted comment as successful
     */
    public boolean deleteComment(Comment comment) {
        this.comments.remove(comment);
        return true;
    }

    /**
     * Adds a user to a task
     * 
     * @param user someone who is being assigned to a task
     * @return returns true because a user is being added
     */
    public boolean addAssignedUser(User user) {
        assignedUsers.add(user);
        return true;
    }

    /**
     * Removes a user from the task
     * 
     * @param user someone who has been assigned to a task
     * @return returns true because the user has been removed from the task
     */
    public boolean removeAssignedUser(User user) {
        assignedUsers.remove(user);
        return true;
    }
}