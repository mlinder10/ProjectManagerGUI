package models;

import java.util.ArrayList;
import java.util.UUID;

import utils.DataWriter;

/**
 * Creates a new project
 */
public class Project {
    public UUID id;
    public User owner;
    public String title;
    public ArrayList<Comment> comments;
    public ArrayList<User> users;
    public ArrayList<Section> sections;

    /**
     * Creates a new project and adds a user and different array list to the project
     * 
     * @param title holds the title of the project in a string
     * @param owner holds the owner of the project as a user
     */
    public Project(String title, User owner) {
        this.id = UUID.randomUUID();
        this.owner = owner;
        this.title = title;
        this.comments = new ArrayList<Comment>();
        this.users = new ArrayList<User>();
        this.sections = new ArrayList<Section>();
        this.sections.add(new Section("Todo"));
        this.sections.add(new Section("Doing"));
        this.sections.add(new Section("Done"));
    }

    /**
     * creates a new project
     * 
     * @param id       holds the users UUID
     * @param title    holds the title of the project in a string
     * @param owner    holds the owner of the project as a user
     * @param comments holds the comments to the project in an array list of
     *                 comments
     * @param users    holds the users on the project as an array list of users
     * @param sections holds the sections in the project as an array list of
     *                 sections
     */
    public Project(UUID id, String title, User owner, ArrayList<Comment> comments, ArrayList<User> users,
            ArrayList<Section> sections) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.comments = comments;
        this.users = users;
        this.sections = sections;
    }

    /**
     * Edits the title string that holds the projects title
     * 
     * @param title holds the project title in a string
     * @return returns true to change the project title
     */
    public boolean editProjectTitle(String title) {
        this.title = title;
        DataWriter.saveProjects(ProjectList.getProjectList().projects);
        return true;
    }

    public Section getSection(String title) {
        for (Section section : sections) {
            if (section.title.equals(title))
                return section;
        }
        return null;
    }

    public boolean createSection(Section section) {
        this.sections.add(section);
        return true;
    }

    /**
     * Deletes a section from the array list of sections
     * 
     * @param section holds sections in an array list of sections
     * @return returns the succes status when a section is removed
     */
    public boolean deleteSection(Section section) {
        this.sections.remove(section);
        DataWriter.saveProjects(ProjectList.getProjectList().projects);
        return true;
    }

    /**
     * Adds another user to the project
     * 
     * @param user holds the user in a user object
     * @return returns the success status when a new user is added
     */
    public boolean addUser(User user) {
        this.users.add(user);
        DataWriter.saveProjects(ProjectList.getProjectList().projects);
        return true;
    }

    /**
     * Creates a new comment and adds a status to it
     * 
     * @param comment holds comments in an array list of comments
     * @return returns the success status when a new comment is created
     */
    public boolean createComment(Comment comment) {
        this.comments.add(comment);
        DataWriter.saveProjects(ProjectList.getProjectList().projects);
        return true;
    }

    /**
     * Deletes a comment by removing a comment from the comment array list
     * 
     * @param comment holds comments in an array list of comments
     * @return returns the success status when a commment is removed
     */
    public boolean deleteComment(Comment comment) {
        this.comments.remove(comment);
        DataWriter.saveProjects(ProjectList.getProjectList().projects);
        return true;
    }

    public double getPercentage() {
        int totalTasks = 0;
        for (Section section : sections) {
            totalTasks += section.tasks.size();
        }
        int completedTasks = 0;
        for (Section section : sections) {
            if (section.title.equals("Done")) {
                completedTasks = section.tasks.size();
                break;
            }
        }
        if (totalTasks == 0)
            return 0;
        return (double) completedTasks / totalTasks;
    }
}
