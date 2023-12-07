package utils;

import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import models.Change;
import models.Comment;
import models.Project;
import models.Section;
import models.Task;
import models.User;

import java.util.ArrayList;

/**
 * @author Alexis Hill and Matt Linder
 */
public class DataWriter extends DataConstants {

    public static void TextFileWriter(Project project) throws IOException {
        String output = "";
        output += project.title + "\n\n";
        for (Section section : project.sections) {
            output += section.title + "\n\n";
            for (Task task : section.tasks) {
                output += "\t" + task.title + "\n";
                for (Comment comment : task.comments) {
                    output += "\t\t" + comment.content + "\n";
                    for (Comment innerComment : comment.comments) {
                        output += "\t\t\t" + innerComment.content + "\n";
                    }
                }
            }
            output += "\n\n";
        }

        FileWriter fileWriter = new FileWriter(new File(BASE_URI + "output.txt"));
        fileWriter.write(output);
        fileWriter.close();
    }

    /**
     * 
     * @param users the method saveUsers takes in an arraylist of users
     *              and converts each user in the arraylist into a json object and
     *              then stores the object into the json array
     * @return true if the file that the json array will be written into
     *         is available and false if there is no file to write into or nothing
     *         being written into the file
     */
    public static boolean saveUsers(ArrayList<User> users) {
        JSONArray jsonUsers = new JSONArray();
        for (User user : users) {
            jsonUsers.add(getUserJson(user));
        }
        try (FileWriter file = new FileWriter(BASE_URI + USERS_PATH)) {
            file.write(jsonUsers.toJSONString());
            file.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 
     * @param projects the method saveProjects takes in an arraylist of
     *                 projects and converts each project in the arraylist into a
     *                 json
     *                 object and then stores the object into the json array
     * @return true if the file that the json array will be written into
     *         is available and false if there is no file to write into or nothing
     *         being written into the file
     */
    public static boolean saveProjects(ArrayList<Project> projects) {
        JSONArray jsonProject = new JSONArray();
        for (Project project : projects) {
            jsonProject.add(getProjectJson(project));
        }
        try (FileWriter file = new FileWriter(BASE_URI + PROJECTS_PATH)) {
            file.write(jsonProject.toJSONString());
            file.flush();
            return true;
        } catch (IOException o) {
            o.printStackTrace();
            return false;
        }
    }

    /**
     * 
     * @param user the method getUserJson takes in a user object and takes in
     *             the arguments from the user and makes them into json attributes
     *             in an
     *             effort to convert the object into a json object
     * @return the json object is returned after the user data is obtained
     */
    public static JSONObject getUserJson(User user) {
        JSONObject userJson = new JSONObject();
        userJson.put(USER_ID, user.id.toString());
        userJson.put(USER_USERNAME, user.username);
        userJson.put(USER_EMAIL, user.email);
        userJson.put(USER_PASSWORD, user.getPassword());
        return userJson;
    }

    /**
     * 
     * @param project the method getProjectJson takes in a project object and
     *                extracts
     *                the arguments from the project and makes them into json
     *                attributes in an
     *                effort to convert the object into a json object
     * @return the json object is returned after the project data is obtained
     */
    public static JSONObject getProjectJson(Project project) {
        JSONObject projectJson = new JSONObject();
        projectJson.put(PROJECT_ID, project.id.toString());
        projectJson.put(PROJECT_OWNER, getUserJson(project.owner));
        projectJson.put(PROJECT_TITLE, project.title);

        JSONArray commentsJson = new JSONArray();
        for (Comment comment : project.comments) {
            commentsJson.add(getCommentJson(comment));
        }
        projectJson.put(PROJECT_COMMENTS, commentsJson);

        JSONArray usersJson = new JSONArray();
        for (User user : project.users) {
            usersJson.add(getUserJson(user));
        }
        projectJson.put(PROJECT_USERS, usersJson);

        JSONArray sectionsJson = new JSONArray();
        for (Section section : project.sections) {
            sectionsJson.add(getSectionJson(section));
        }
        projectJson.put(PROJECT_SECTIONS, sectionsJson);

        return projectJson;
    }

    /**
     * 
     * @param section the method getSectionJson takes in a section object and
     *                extracts
     *                the arguments from the section and makes them into json
     *                attributes in an
     *                effort to convert the object into a json object
     * @return the json object is returned after the section data is obtained
     */
    public static JSONObject getSectionJson(Section section) {
        JSONObject sectionJson = new JSONObject();
        sectionJson.put(PROJECT_SECTION_TITLE, section.title);

        JSONArray tasks = new JSONArray();
        for (Task task : section.tasks) {
            tasks.add(getTaskJson(task));
        }
        sectionJson.put(PROJECT_SECTION_TASKS, tasks);
        return sectionJson;
    }

    /**
     * 
     * @param task the method getTaskJson takes in a task object and extracts
     *             the arguments from the task and makes them into json attributes
     *             in an
     *             effort to convert the object into a json object
     * @return the json object is returned after the task data is obtained
     */
    public static JSONObject getTaskJson(Task task) {
        JSONObject taskJson = new JSONObject();
        taskJson.put(PROJECT_TASK_ID, task.id.toString());
        taskJson.put(PROJECT_TASK_TITLE, task.title);
        taskJson.put(PROJECT_TASK_DESCRIPTION, task.description);
        taskJson.put(PROJECT_TASK_TYPE, task.type);
        taskJson.put(PROJECT_TASK_COMPLETION, task.completion);
        taskJson.put(PROJECT_TASK_PRIORITY, task.priority);

        JSONArray commentsJson = new JSONArray();
        for (Comment comment : task.comments) {
            commentsJson.add(getCommentJson(comment));
        }
        taskJson.put(PROJECT_TASK_COMMENTS, commentsJson);

        JSONArray assignedUsersJson = new JSONArray();
        for (User assignedUser : task.assignedUsers) {
            assignedUsersJson.add(getUserJson(assignedUser));
        }
        taskJson.put(PROJECT_TASK_ASSIGNED_USERS, assignedUsersJson);

        JSONArray changesJson = new JSONArray();
        for (Change change : task.changeLog) {
            changesJson.add(getChangeJson(change));
        }
        taskJson.put(PROJECT_TASK_CHANGELOG, changesJson);
        return taskJson;
    }

    /**
     * 
     * @param comment the method getCommentJson takes in a section object and
     *                extracts
     *                the arguments from the comment and makes them into json
     *                attributes in an
     *                effort to convert the object into a json object
     * @return the json object is returned after the comment data is obtained
     */
    public static JSONObject getCommentJson(Comment comment) {
        JSONObject commentJson = new JSONObject();
        commentJson.put(PROJECT_COMMENTS_ID, comment.id.toString());
        commentJson.put(PROJECT_COMMENTS_USER, getUserJson(comment.user));
        commentJson.put(PROJECT_COMMENTS_DATE, comment.date.toString());
        commentJson.put(PROJECT_COMMENTS_CONTENT, comment.content);

        JSONArray commentsJson = new JSONArray();
        for (Comment commentRecursive : comment.comments) {
            commentsJson.add(getCommentJson(commentRecursive));
        }
        commentJson.put(PROJECT_COMMENTS_COMMENTS, commentsJson);
        return commentJson;
    }

    /**
     * 
     * @param change the method getChangeJson takes in a section object and extracts
     *               the arguments from the change and makes them into json
     *               attributes in an
     *               effort to convert the object into a json object
     * @return the json object is returned after the change data is obtained
     */
    public static JSONObject getChangeJson(Change change) {
        JSONObject changeJson = new JSONObject();
        changeJson.put(PROJECT_CHANGE_ID, change.id.toString());
        changeJson.put(PROJECT_CHANGE_PREVIOUS, change.previousSection);
        changeJson.put(PROJECT_CHANGE_NEXT, change.nextSection);
        changeJson.put(PROJECT_CHANGE_DATE, change.date.toString());
        changeJson.put(PROJECT_CHANGE_USER, getUserJson(change.userEdited));
        return changeJson;
    }
}
