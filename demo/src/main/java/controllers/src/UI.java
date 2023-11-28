package controllers.src;

import java.io.IOException;

public class UI {
    public static void main(String[] args) {
        ProjectFACADE facade = new ProjectFACADE();
        facade.login("atticusmadden", "password");
        facade.openProject("Electric Missiles");
        Task taskOne = facade.createTask("TODO", "Initialize super algorithm to detonate at warp speed.", "", 1,
                "New Feature");
        facade.createComment(taskOne, "Avoid civillians Jeff");

        Task taskTwo = facade.getTask("Curve the metal to make a cylindrical shape");
        taskTwo = facade.moveTask(taskTwo, "DOING");
        taskTwo.removeAssignedUser(facade.getUser());
        taskTwo.addAssignedUser(facade.getUser("jeffgoldblum"));
        Comment comment = facade.getComment(taskTwo, "Not cylindrical enough");
        facade.createComment(comment, "How about you do it Jeff");

        facade.createSection("Abandoned");
        Task taskThree = facade.getTask("Make impossible burger possible");
        facade.moveTask(taskThree, "Abandoned");

        facade.saveData();
        try {
            DataWriter.TextFileWriter(facade.getProject("Electric Missiles"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
