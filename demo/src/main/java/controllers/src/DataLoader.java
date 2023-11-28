package controllers.src;

import java.util.UUID;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

/**
 * @author Matt Linder and Alexis Hill
 */
public class DataLoader extends DataConstants {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

    /**
     * 
     * @return the method loadUsers returns an arraylist of users that is read in
     *         from the
     *         jsonfile by taking in the data on file and parsing it into a json
     *         array. The json array
     *         is then iterated through with a for loop to put each individual user
     *         into
     *         an arraylist. If there is nothing on file, then null is returned
     */
    public static ArrayList<User> loadUsers() {
        try {
            ArrayList<User> users = new ArrayList<User>();
            FileReader reader = new FileReader("json/usersTest.json");
            JSONArray usersJson = (JSONArray) new JSONParser().parse(reader);

            for (int i = 0; i < usersJson.size(); i++) {
                JSONObject userJson = (JSONObject) usersJson.get(i);
                users.add(parseUser(userJson));
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * @return the method loadProjects returns an arraylist of projects that is read
     *         in from the
     *         jsonfile by taking in the data on file and parsing it into a json
     *         array. The json array
     *         is then iterated through with a for loop to put each individual
     *         project into
     *         an arraylist. If there is nothing on file, then null is returned
     */
    public static ArrayList<Project> loadProjects() {
        try {
            ArrayList<Project> projects = new ArrayList<Project>();
            FileReader reader = new FileReader("json/projectsTest.json");
            JSONArray projectsJson = (JSONArray) new JSONParser().parse(reader);

            for (int projectIndex = 0; projectIndex < projectsJson.size(); projectIndex++) {
                // root project parsing
                JSONObject projectJson = (JSONObject) projectsJson.get(projectIndex);
                UUID id = UUID.fromString((String) projectJson.get(PROJECT_ID));
                JSONObject projectOwnerJson = (JSONObject) projectJson.get(PROJECT_OWNER);
                User projectOwner = parseUser(projectOwnerJson);
                String title = (String) projectJson.get(PROJECT_TITLE);

                // sections parsing
                JSONArray sectionsJson = (JSONArray) projectJson.get(PROJECT_SECTIONS);
                ArrayList<Section> sections = new ArrayList<Section>();
                for (int sectionIndex = 0; sectionIndex < sectionsJson.size(); sectionIndex++) {
                    JSONObject sectionJson = (JSONObject) sectionsJson.get(sectionIndex);
                    String sectionTitle = (String) sectionJson.get(PROJECT_SECTION_TITLE);

                    // tasks parsing
                    JSONArray tasksJson = (JSONArray) sectionJson.get(PROJECT_SECTION_TASKS);
                    ArrayList<Task> tasks = new ArrayList<Task>();
                    for (int taskIndex = 0; taskIndex < tasksJson.size(); taskIndex++) {
                        JSONObject taskJson = (JSONObject) tasksJson.get(taskIndex);
                        UUID taskId = UUID.fromString((String) taskJson.get(PROJECT_TASK_ID));
                        String taskTitle = (String) taskJson.get(PROJECT_TASK_TITLE);
                        String taskDescription = (String) taskJson.get(PROJECT_TASK_DESCRIPTION);
                        String taskType = (String) taskJson.get(PROJECT_TASK_TYPE);
                        boolean completion = (boolean) taskJson.get(PROJECT_TASK_COMPLETION);
                        int priority = (int) (long) taskJson.get(PROJECT_TASK_PRIORITY);

                        // assigned user parsing
                        JSONArray assignedUsersJson = (JSONArray) taskJson.get(PROJECT_TASK_ASSIGNED_USERS);
                        ArrayList<User> assignedUsers = new ArrayList<User>();
                        for (int assignedUserIndex = 0; assignedUserIndex < assignedUsersJson
                                .size(); assignedUserIndex++) {
                            JSONObject assignedUserJson = (JSONObject) assignedUsersJson.get(assignedUserIndex);
                            assignedUsers.add(parseUser(assignedUserJson));
                        }

                        // comment parsing
                        JSONArray taskCommentsjson = (JSONArray) taskJson.get(PROJECT_TASK_COMMENTS);
                        ArrayList<Comment> taskComments = new ArrayList<Comment>();
                        for (int commentIndex = 0; commentIndex < taskCommentsjson.size(); commentIndex++) {
                            JSONObject commentJsonRecursive = (JSONObject) taskCommentsjson.get(commentIndex);
                            Comment parsedComment = (parseComment(commentJsonRecursive));
                            taskComments.add(parsedComment);
                        }

                        // changelog parsing
                        JSONArray changeLogJson = (JSONArray) taskJson.get(PROJECT_TASK_CHANGELOG);
                        ArrayList<Change> changeLog = new ArrayList<Change>();
                        for (int changeIndex = 0; changeIndex < changeLogJson.size(); changeIndex++) {
                            JSONObject changeJson = (JSONObject) changeLogJson.get(changeIndex);
                            UUID changeId = UUID.fromString((String) changeJson.get(PROJECT_CHANGE_ID));
                            String previousSection = (String) changeJson.get(PROJECT_CHANGE_PREVIOUS);
                            String nextSection = (String) changeJson.get(PROJECT_CHANGE_NEXT);
                            String changeDateString = (String) changeJson.get(PROJECT_CHANGE_DATE);
                            Date changeDate = dateFormat.parse(changeDateString);
                            JSONObject userJson = (JSONObject) changeJson.get(PROJECT_CHANGE_USER);
                            User userEdited = parseUser(userJson);
                            changeLog.add(new Change(changeId, previousSection, nextSection, changeDate, userEdited));
                        }
                        tasks.add(new Task(taskId, taskTitle, taskDescription, taskType, assignedUsers, completion,
                                priority, taskComments, changeLog));
                    }
                    sections.add(new Section(sectionTitle, tasks));
                }

                // user parsing
                JSONArray usersJson = (JSONArray) projectJson.get(PROJECT_USERS);
                ArrayList<User> users = new ArrayList<User>();
                for (int userIndex = 0; userIndex < usersJson.size(); userIndex++) {
                    JSONObject userJson = (JSONObject) usersJson.get(userIndex);
                    users.add(parseUser(userJson));
                }

                // comment parsing
                JSONArray commentsJson = (JSONArray) projectJson.get(PROJECT_COMMENTS);
                ArrayList<Comment> comments = new ArrayList<Comment>();
                for (int commentIndex = 0; commentIndex < commentsJson.size(); commentIndex++) {
                    JSONObject commentJsonRecursive = (JSONObject) commentsJson.get(commentIndex);
                    comments.add(parseComment(commentJsonRecursive));
                }

                projects.add(new Project(id, title, projectOwner, comments, users,
                        sections));
            }
            return projects;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * @param userJson the method parseUser takes in a json object and
     *                 converts the attributes in the json file into object
     *                 constructors
     * @return a new user will be made with the formed constructors
     */
    private static User parseUser(JSONObject userJson) {
        UUID id = UUID.fromString((String) userJson.get(USER_ID));
        String username = (String) userJson.get(USER_USERNAME);
        String password = (String) userJson.get(USER_PASSWORD);
        String email = (String) userJson.get(USER_EMAIL);
        return new User(id, username, password, email);
    }

    /**
     * 
     * @param commentJson the method parseComment takes in a json object and
     *                    converts the attributes in the json file into object
     *                    constructors
     * @return a new comment will be made with the formed constructors
     * @throws ParseException show error message
     */
    private static Comment parseComment(JSONObject commentJson) throws ParseException {
        UUID id = UUID.fromString((String) commentJson.get(PROJECT_COMMENTS_ID));
        String content = (String) commentJson.get(PROJECT_COMMENTS_CONTENT);
        String dateString = (String) commentJson.get(PROJECT_COMMENTS_DATE);
        Date date = dateFormat.parse(dateString);
        User user = parseUser((JSONObject) commentJson.get(PROJECT_COMMENTS_USER));

        // recursively parsing comments
        JSONArray commentsJson = (JSONArray) commentJson.get(PROJECT_COMMENTS_COMMENTS);
        ArrayList<Comment> comments = new ArrayList<Comment>();
        for (int commentIndex = 0; commentIndex < commentsJson.size(); commentIndex++) {
            JSONObject commentJsonRecursive = (JSONObject) commentsJson.get(commentIndex);
            comments.add(parseComment(commentJsonRecursive));
        }
        return new Comment(id, content, user, comments, date);
    }
}