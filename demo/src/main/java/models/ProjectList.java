package models;

/**
 * @author Gavin Orme
 */

import java.util.ArrayList;

import utils.DataLoader;
import utils.DataWriter;

public class ProjectList {

    private static ProjectList projectList;
    public ArrayList<Project> projects;
    public Project currentProject;

    private ProjectList() {
        this.projects = DataLoader.loadProjects();
        this.currentProject = null;
    }

    /**
     * This creates a list of projects if there is not one already
     * 
     * @return returns a new project list
     */
    public static ProjectList getProjectList() {
        if (projectList == null)
            return new ProjectList();
        return projectList;
    }

    /**
     * Takes you to the current project you are working on
     * 
     * @return returns the current project
     */
    public Project getCurrentProject() {
        return currentProject;
    }

    /**
     * Opens the project if the title matches the project title
     * 
     * @param title this is the title of the current project
     * @return returns nothing
     */
    public Project openProject(String title) {
        for (Project project : projects) {
            if (project.title.equals(title)) {
                currentProject = project;
                return project;
            }
        }
        return null;
    }

    /**
     * Allows the user to create a project
     * 
     * @param user  This is the current user accesing the new project
     * @param title This is the title of the new project
     * @return returns a new project with the user who created the project and the
     *         name of it
     */
    public boolean createProject(User user, String title) {
        Project newProject = new Project(title, user);
        projects.add(newProject);
        DataWriter.saveProjects(projects);
        return true;
    }

    /**
     * Removes the project when prompted and gives a success message after deleting
     * 
     * @param project This is the project that is going to be deleted
     * @return returns the success of deleting the project
     */
    public boolean deleteProject(Project project) {
        projects.remove(project);
        DataWriter.saveProjects(projects);
        return true;
    }

    /**
     * Creates a new section
     * 
     * @param title Holds the title of the new section in a string
     * @return returns the new section
     */
    public Section createSection(String title) {
        Section section = new Section(title);
        currentProject.createSection(section);
        return section;
    }

    /**
     * Allows the user to remove a section inside the project
     * 
     * @param section this is the section inside the project that is going to be
     *                removed
     * @return returns a deleted section
     */
    public boolean removeSection(Section section) {
        return currentProject.deleteSection(section);
    }
}
