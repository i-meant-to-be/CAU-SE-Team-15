package Button;

import Data.Project;
import Layout.MainFrame;
import Layout.ProjectCreator;
import Layout.ProjectDetailFrame;

import javax.swing.*;
import java.util.ArrayList;

public class ProjectButton {

    private MainFrame MF;
    private ArrayList<Project> projects;

    public ProjectButton(MainFrame MF) {
        this.MF = MF;
        projects = MF.get_projects();
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
        //Create Project List
        JList<String> projectList = new JList<>(new DefaultListModel<>());
        DefaultListModel<String> listModel = (DefaultListModel<String>) projectList.getModel();

        //Add Project Name
        for (Project project : projects) {
            listModel.addElement(project.getName()); // Add only Project name
        }

        projectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 하나의 프로젝트만 선택 가능

        projectList.setVisibleRowCount(10);

        projectList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                //Load selected Project's name
                String selectedProjectName = projectList.getSelectedValue();

                Project selectedProject = null;
                for (Project project : projects) {
                    if (project.getName().equals(selectedProjectName)) {
                        selectedProject = project;
                        break;
                    }
                }

                if (selectedProject != null) {
                    new ProjectDetailFrame(selectedProject);
                }
            }
        });

        JScrollPane listScrollPane = new JScrollPane(projectList);

        JOptionPane.showMessageDialog(null, listScrollPane, "All Projects", JOptionPane.INFORMATION_MESSAGE);
    }


    private void createNewProject() {
        ProjectCreator projectCreator = new ProjectCreator(ProjectButton.this);
    }

    public ArrayList<Project> getProjects(){return projects;}

}
