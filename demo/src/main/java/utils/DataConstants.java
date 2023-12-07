package utils;

/**
 * @author Matt Linder
 */
public class DataConstants {
    protected static final String BASE_URI = "./demo/src/main/java/json/";
    protected static final String USERS_PATH = "usersProd.json";
    protected static final String PROJECTS_PATH = "projectsProd.json";
    // users
    protected static final String USER_ID = "id";
    protected static final String USER_USERNAME = "username";
    protected static final String USER_PASSWORD = "password";
    protected static final String USER_EMAIL = "email";

    // projects
    protected static final String PROJECT_ID = "id";
    protected static final String PROJECT_OWNER = "owner";
    protected static final String PROJECT_TITLE = "title";
    protected static final String PROJECT_SECTIONS = "sections";
    protected static final String PROJECT_USERS = "users";
    // sections
    protected static final String PROJECT_SECTION_ID = "id";
    protected static final String PROJECT_SECTION_TITLE = "title";
    protected static final String PROJECT_SECTION_TASKS = "tasks";
    // tasks
    protected static final String PROJECT_TASK_ID = "id";
    protected static final String PROJECT_TASK_TITLE = "title";
    protected static final String PROJECT_TASK_DESCRIPTION = "description";
    protected static final String PROJECT_TASK_TYPE = "type";
    protected static final String PROJECT_TASK_ASSIGNED_USERS = "assignedUsers";
    protected static final String PROJECT_TASK_COMPLETION = "completion";
    protected static final String PROJECT_TASK_PRIORITY = "priority";
    protected static final String PROJECT_TASK_COMMENTS = "comments";
    // changelog
    protected static final String PROJECT_TASK_CHANGELOG = "changeLog";
    protected static final String PROJECT_CHANGE_ID = "id";
    protected static final String PROJECT_CHANGE_NEXT = "nextSection";
    protected static final String PROJECT_CHANGE_PREVIOUS = "previousSection";
    protected static final String PROJECT_CHANGE_DATE = "date";
    protected static final String PROJECT_CHANGE_USER = "userEdited";
    // commments
    protected static final String PROJECT_COMMENTS = "comments";
    protected static final String PROJECT_COMMENTS_ID = "id";
    protected static final String PROJECT_COMMENTS_CONTENT = "content";
    protected static final String PROJECT_COMMENTS_DATE = "date";
    protected static final String PROJECT_COMMENTS_USER = "user";
    protected static final String PROJECT_COMMENTS_COMMENTS = "comments";
}
