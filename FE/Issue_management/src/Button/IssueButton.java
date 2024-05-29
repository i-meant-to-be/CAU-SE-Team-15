package Button;

import Data.*;
import Layout.MainFrame;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class IssueButton {

    public IssueButton(MainFrame MF, ArrayList<Project> projects) {
        User user = MF.get_user();
        if(user.getType().equals(UserType.TESTER) || user.getType().equals(UserType.ADMIN)) {
            if (projects.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No projects available. Please create a project first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] projectNames = projects.stream().map(Project::getName).toArray(String[]::new);
            String selectedProjectName = (String) JOptionPane.showInputDialog(null, "Select a project to add the issue:", "Select Project",
                    JOptionPane.PLAIN_MESSAGE, null, projectNames, projectNames[0]);

            if (selectedProjectName != null) {
                for (Project project : projects) {
                    if (project.getName().equals(selectedProjectName)) {
                        addIssueToProject(project, MF.get_user());
                        break;
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Only TESTER or ADMIN can create a new issue", "Access Denied", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void addIssueToProject(Project project, User user) {
        while (true) {
            String issueTitle = JOptionPane.showInputDialog(null, "Enter issue title:");
            if (issueTitle != null && !issueTitle.trim().isEmpty()) {
                JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"BLOCKER", "CRITICAL", "MAJOR", "MINOR", "TRIVIAL"});
                int result = JOptionPane.showConfirmDialog(null, typeComboBox, "Select issue type", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String selectedType = (String) typeComboBox.getSelectedItem();

                    IssueType type;
                    switch (selectedType) {
                        case "BLOCKER":
                            type = IssueType.BLOCKER;
                            break;
                        case "CRITICAL":
                            type = IssueType.CRITICAL;
                            break;
                        case "MAJOR":
                            type = IssueType.MAJOR;
                            break;
                        case "MINOR":
                            type = IssueType.MINOR;
                            break;
                        case "TRIVIAL":
                            type = IssueType.TRIVIAL;
                            break;
                        default:
                            type = IssueType.MINOR;
                            break;
                    }

                    Issue newIssue = new Issue(issueTitle, user.getUsername(), LocalDateTime.now().withNano(0), "Description", null, type, IssueState.NEW);
                    project.addIssue(newIssue);
                    JOptionPane.showMessageDialog(null, "Issue created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    break;
                } else {
                    break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Issue title cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
