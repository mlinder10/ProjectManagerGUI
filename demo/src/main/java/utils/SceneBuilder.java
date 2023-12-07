package utils;

import com.example.App;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Comment;
import models.Project;
import models.ProjectFACADE;
import models.Section;
import models.Task;

public class SceneBuilder {
    static DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.getDefault());

    public static void populateNavbar(ProjectFACADE facade, VBox projectBox, VBox taskBox) {
        ArrayList<Task> tasks = facade.getUserTasks();
        ArrayList<Project> projects = facade.getUserProjects();

        projectBox.getChildren().clear();
        taskBox.getChildren().clear();
        for (Task task : tasks) {
            Button title = new Button(task.title);
            title.setStyle("-fx-background-color: inherit; -fx-text-fill: white;");
            title.setOnMouseClicked(event -> {
                facade.openTask(task);
                try {
                    App.setRoot("Task");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            taskBox.getChildren().add(title);
        }
        for (Project project : projects) {
            Button title = new Button(project.title);
            title.setStyle("-fx-background-color: inherit; -fx-text-fill: white;");
            title.setOnMouseClicked(event -> {
                facade.openProject(project);
                try {
                    App.setRoot("Project");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            projectBox.getChildren().add(title);
        }
    }

    public static VBox createDashboardProject(ProjectFACADE facade, Project project) {
        VBox container = new VBox();
        container.setStyle(
                "-fx-alignment: center; -fx-spacing: 4; -fx-background-color: #249296; -fx-background-radius: 10; -fx-padding: 8; -fx-text-wrap: true; -fx-max-width: 120;");
        Button title = new Button(project.title);
        title.setStyle("-fx-background-color: inherit; -fx-text-fill: white;");
        title.setOnMouseClicked(event -> {
            facade.openProject(project);
            try {
                App.setRoot("Project");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        double percentage = project.getPercentage();
        ProgressBar progressBar = new ProgressBar(percentage);
        Label percentageLabel = new Label((int) (percentage * 100) + "%");
        container.getChildren().addAll(title, percentageLabel, progressBar);
        return container;
    }

    public static void populateSections(ProjectFACADE facade, ArrayList<Section> sections, StackPane stack, HBox root) {
        root.getChildren().clear();
        for (Section section : sections) {
            VBox container = new VBox();
            container.setStyle(
                    "-fx-background-color: #249296; -fx-background-radius: 10; -fx-padding: 8; -fx-min-width: 120;");
            HBox upper = new HBox();
            Label title = new Label(section.title);
            title.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18; -fx-padding: 0 0 8 0;");
            upper.getChildren().add(title);
            Button addTask = new Button("+ Task");
            addTask.setStyle("-fx-background-color: inherit; -fx-text-fill: #f2c53d;");
            addTask.setOnMouseClicked(event -> {
                VBox modal = new VBox();
                modal.setAlignment(Pos.CENTER);
                modal.setStyle("-fx-background-color: #000a;");
                VBox modalInternal = new VBox();
                modalInternal.setStyle(
                        "-fx-alignment: center; -fx-spacing: 8; -fx-background-color: #249296aa; -fx-background-radius: 30; -fx-padding: 8;");
                modalInternal.setMaxWidth(260);
                modalInternal.setPrefWidth(300);
                modalInternal.setPrefHeight(200);
                Label inputTitle = new Label("Create Task");
                inputTitle.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18;");

                TextField titleInput = new TextField();
                titleInput.setPromptText("Task Title");
                titleInput.setMaxWidth(200);
                TextField descriptionInput = new TextField();
                descriptionInput.setPromptText("Task Description");
                descriptionInput.setMaxWidth(200);
                TextField priorityInput = new TextField();
                priorityInput.setPromptText("Task Priority (1-3)");
                priorityInput.setMaxWidth(200);
                TextField statusInput = new TextField();
                statusInput.setPromptText("Task Status");
                statusInput.setMaxWidth(200);

                HBox buttons = new HBox();
                buttons.setStyle("-fx-spacing: 8; -fx-alignment: center;");
                Button cancel = new Button("Cancel");
                cancel.setOnMouseClicked(event2 -> {
                    stack.getChildren().remove(modal);
                });
                Button create = new Button("Create");
                create.setOnMouseClicked(event3 -> {
                    Task task = facade.createTask(section, titleInput.getText(), descriptionInput.getText(),
                            Integer.parseInt(priorityInput.getText()), titleInput.getText());
                    container.getChildren().add(createTask(facade, task));
                    stack.getChildren().remove(modal);
                });

                buttons.getChildren().addAll(cancel, create);
                modalInternal.getChildren().addAll(inputTitle, titleInput, descriptionInput, priorityInput, statusInput,
                        buttons);
                modal.getChildren().add(modalInternal);
                stack.getChildren().add(modal);
            });
            upper.getChildren().add(addTask);
            container.getChildren().add(upper);
            for (Task task : section.tasks) {
                Button taskBtn = createTask(facade, task);
                container.getChildren().add(taskBtn);
            }

            container.setOnDragOver(event -> {
                if (event.getGestureSource() != container &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            });

            container.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    try {
                        Task task = DataLoader.parseTask((JSONObject) JSONValue.parse(db.getString()));
                        facade.moveTask(task, section.title);
                        populateSections(facade, sections, stack, root);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            });

            container.setOnDragEntered(event -> {
                /* the drag-and-drop gesture entered the target */
                /* show to the user that it is an actual gesture target */
                if (event.getGestureSource() != container &&
                        event.getDragboard().hasString()) {
                    container.setStyle("-fx-background-color: #148286; -fx-background-radius: 10; -fx-padding: 8; -fx-min-width: 120;");
                }

                event.consume();
            });

            container.setOnDragExited(event -> {
                /* the drag-and-drop gesture entered the target */
                /* show to the user that it is an actual gesture target */
                if (event.getGestureSource() != container &&
                        event.getDragboard().hasString()) {
                    container.setStyle("-fx-background-color: #249296; -fx-background-radius: 10; -fx-padding: 8; -fx-min-width: 120;");
                }

                event.consume();
            });

            root.getChildren().add(container);
        }
    }

    public static Button createTask(ProjectFACADE facade, Task task) {
        Button taskBtn = new Button(task.title);
        taskBtn.setStyle("-fx-background-color: inherit; -fx-text-fill: white; -fx-padding: 0;");
        taskBtn.setMaxWidth(100);
        taskBtn.setOnMouseClicked(event -> {
            facade.openTask(task);
            try {
                App.setRoot("Task");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        taskBtn.setOnDragDetected(event -> {
            Dragboard db = taskBtn.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(DataWriter.getTaskJson(task).toJSONString());
            db.setContent(content);
            event.consume();
        });
        return taskBtn;
    }

    public static VBox createComment(Comment comment, StackPane stack, ProjectFACADE facade) {
        VBox container = new VBox();
        container.setStyle("-fx-spacing: 4;");
        HBox upper = new HBox();
        upper.setStyle("-fx-spacing: 16;");
        Label username = new Label(comment.user.username);
        username.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        Label date = new Label(dateFormatter.format(comment.date));
        date.setStyle("-fx-text-fill: #ddd;");
        upper.getChildren().addAll(username, date);
        container.getChildren().add(upper);
        container.getChildren().add(new Label(comment.content));
        Button reply = new Button("Reply ->");
        reply.setStyle("-fx-background-color: inherit; -fx-text-fill: #f2c53d; -fx-padding: 0;");
        VBox nestedContainer = new VBox();
        nestedContainer.setStyle("-fx-padding: 8 0 0 24;");

        reply.setOnMouseClicked(event -> {
            VBox modal = new VBox();
            modal.setAlignment(Pos.CENTER);
            modal.setStyle("-fx-background-color: #000a;");
            VBox modalInternal = new VBox();
            modalInternal.setStyle(
                    "-fx-alignment: center; -fx-spacing: 8; -fx-background-color: #249296aa; -fx-background-radius: 30; -fx-padding: 8;");
            modalInternal.setMaxWidth(260);
            modalInternal.setPrefWidth(300);
            modalInternal.setPrefHeight(200);
            Label inputTitle = new Label("Create Comment");
            inputTitle.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18;");

            TextField contentInput = new TextField();
            contentInput.setPromptText("Comment Content");
            contentInput.setMaxWidth(200);
            HBox buttons = new HBox();
            buttons.setStyle("-fx-spacing: 8; -fx-alignment: center;");
            Button cancel = new Button("Cancel");
            cancel.setOnMouseClicked(event2 -> {
                stack.getChildren().remove(modal);
            });
            Button create = new Button("Create");
            create.setOnMouseClicked(event3 -> {
                Comment newComment = facade.createComment(comment, contentInput.getText());
                nestedContainer.getChildren().add(createComment(newComment, stack, facade));
                stack.getChildren().remove(modal);
            });

            buttons.getChildren().addAll(cancel, create);
            modalInternal.getChildren().addAll(inputTitle, contentInput, buttons);
            modal.getChildren().add(modalInternal);
            stack.getChildren().add(modal);
        });
        container.getChildren().add(reply);

        if (comment.comments.size() == 0)
            return container;
        for (Comment commentRecursive : comment.comments) {
            nestedContainer.getChildren().add(createComment(commentRecursive, stack, facade));
        }
        container.getChildren().add(nestedContainer);
        return container;
    }
}
