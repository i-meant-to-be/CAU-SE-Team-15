package Button;

import Data.*;
import Layout.MainFrame;
import ServerConnection.UserData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SearchButton extends JFrame {
    private List<Project> projects;
    private JComboBox<String> projectComboBox;
    private JComboBox<IssueState> stateComboBox;
    private JComboBox<String> assignedComboBox;
    private JComboBox<String> reporterComboBox;
    private JTextArea resultArea;

    public SearchButton(List<Project> projects, UserData userdata) {
        this.projects = projects;
        String[] projectNames = projects.stream().map(Project::getName).toArray(String[]::new);
        initializeUI(this.projects, projectNames, userdata);
    }


    private void initializeUI(List<Project> projects, String[] projectNames,UserData userdata) {
        setTitle("Search Issues");
        setSize(500, 400);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new GridLayout(2, 4, 5, 5));

        projectComboBox = new JComboBox<>();
        projectComboBox.addItem("All Projects");
        for (Project project : this.projects) {
            projectComboBox.addItem(project.getName());
        }

        stateComboBox = new JComboBox<>(IssueState.values());
        assignedComboBox = new JComboBox<>();
        reporterComboBox = new JComboBox<>();

        assignedComboBox.addItem("All Assignees");
        reporterComboBox.addItem("All Reporters");
        for (Project project : projects) {
            for (Issue issue : project.getIssues()) {
                UserData userData = new UserData();
                reporterComboBox.addItem(userData.getUser(issue.getReporterId()).getUsername());
            }
        }
        filterPanel.add(new JLabel("Project:"));
        filterPanel.add(projectComboBox);
        filterPanel.add(new JLabel("State:"));
        filterPanel.add(stateComboBox);
        filterPanel.add(new JLabel("Assigned:"));
        filterPanel.add(assignedComboBox);
        filterPanel.add(new JLabel("Reporter:"));
        filterPanel.add(reporterComboBox);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedProjectName = (String) projectComboBox.getSelectedItem();
                IssueState selectedState = (IssueState) stateComboBox.getSelectedItem();
                String selectedAssignee = (String) assignedComboBox.getSelectedItem();
                String selectedReporter = (String) reporterComboBox.getSelectedItem();

                List<Issue> filteredIssues = filterIssues(selectedProjectName, selectedState, selectedAssignee, selectedReporter);
                displayIssues(filteredIssues);
            }
        });

        add(filterPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(searchButton, BorderLayout.SOUTH);
    }

    private List<Issue> filterIssues(String projectName, IssueState state, String assignee, String reporter) {
        List<Issue> filteredIssues = new ArrayList<>();

        for (Project project : projects) {
            if (projectName.equals("All Projects") || project.getName().equals(projectName)) {
                for (Issue issue : project.getIssues()) {
                    boolean matchesState = (state == null || issue.getState() == state);
                    boolean matchesAssignee = (assignee.equals("All Assignees") || assignee.equals(issue.getAssigneeId()));
                    boolean matchesReporter = (reporter.equals("All Reporters") || reporter.equals(issue.getReporterId()));

                    if (matchesState && matchesAssignee && matchesReporter) {
                        filteredIssues.add(issue);
                    }
                }
            }
        }

        return filteredIssues;
    }


    private void displayIssues(List<Issue> issues) {
        StringBuilder sb = new StringBuilder();
        for (Issue issue : issues) {
            sb.append(issue.getTitle()).append("\n");
        }
        resultArea.setText(sb.toString());
    }
}