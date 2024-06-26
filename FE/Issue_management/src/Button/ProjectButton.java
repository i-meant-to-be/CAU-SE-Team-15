package Button;

import Data.Project;
import Data.User;
import Data.UserType;
import Layout.MainFrame;
import Layout.ProjectCreator;
import Layout.ProjectDetailFrame;
import ServerConnection.ProjectData;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ProjectButton {

    private MainFrame MF;
    private ArrayList<Project> projects= new ArrayList<>();
    private Project selected_project;

    public ProjectButton(MainFrame MF) {
        this.MF = MF;
        //프로젝트 데이터 서버에서 받기
        ProjectData projectData = new ProjectData();
        //서버에서 받은 프로젝트가 하나라도 있을 경우
        if(projectData.getAllProjects()!=null) {
            Project[] projects1 = projectData.getAllProjects();
            for (Project project : projects1) {
                if (project != null)
                    projects.add(project);
            }
        }
        //projects = MF.get_projects();
        showProjectOptions();
    }

    private void showProjectOptions() {
        String[] options = {"View All Projects", "Create Project"};
        int choice = JOptionPane.showOptionDialog(null, "Select an option", "Project Options",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            showAllProjects();
        } else if (choice == 1) {
            User user = MF.get_user();
            if (user.getType().equals(UserType.ADMIN)){
                ProjectCreator projectCreator = new ProjectCreator(ProjectButton.this);
                MF.setVisible(false);
                projectCreator.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        MF.setVisible(true);
                    }
                });

            }
            else {
                JOptionPane.showMessageDialog(null, "Only ADMIN can create a new project.", "Access Denied", JOptionPane.WARNING_MESSAGE);
            }
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

        JScrollPane listScrollPane = new JScrollPane(projectList);

        int option = JOptionPane.showConfirmDialog(null, listScrollPane, "All Projects", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String selectedProjectName = projectList.getSelectedValue();

            Project selectedProject = null;
            for (Project project : projects) {
                if (project.getName().equals(selectedProjectName)) {
                    selectedProject = project;
                    break;
                }
            }

            if (selectedProject != null) {
                ProjectDetailFrame detailFrame = new ProjectDetailFrame(MF, this, selectedProject);
                detailFrame.toFront();
                detailFrame.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        if (selected_project != null) {
                            MF.setProject(selected_project);
                            MF.showIssues();
                        }
                    }
                });
            }
        }
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProject (Project selectedProject) {
        this.selected_project = selectedProject;
    }
}
