package Button;

import Data.Project;

import javax.swing.*;
import java.util.ArrayList;

public class ProjectButton {

    private ArrayList<Project> projects;

    public ProjectButton(ArrayList<Project> projects) {
        this.projects = projects;
        showProjectOptions();
    }

    private void showProjectOptions() {
        String[] options = {"View All Projects", "Create Project"};
        int choice = JOptionPane.showOptionDialog(null, "Select an option", "Project Options",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            showAllProjects();
        } else if (choice == 1) {
            createNewProject();
        }
    }

    private void showAllProjects() {
        JList<Project> projectList = new JList<>(new DefaultListModel<>());
        DefaultListModel<Project> listModel = (DefaultListModel<Project>) projectList.getModel();
        for (Project project : projects) {
            listModel.addElement(project);
        }

        projectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //하나의 프로젝트만 선택 가능
        projectList.setVisibleRowCount(10);
        projectList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Project selectedProject = projectList.getSelectedValue();
                if (selectedProject != null) {
                    new ProjectDetailFrame(selectedProject);
                }
            }
        });

        JScrollPane listScrollPane = new JScrollPane(projectList);
        JOptionPane.showMessageDialog(null, listScrollPane, "All Projects", JOptionPane.INFORMATION_MESSAGE);
    }

    private void createNewProject() {
        String projectName = JOptionPane.showInputDialog(null, "Enter project name:");
        if (projectName != null && !projectName.trim().isEmpty()) {
            Project newProject = new Project(projectName);
            projects.add(newProject);
            System.out.println("hg");
            JOptionPane.showMessageDialog(null, "Project created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Project name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
