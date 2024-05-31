package Button;

import Data.*;
import Layout.MainFrame;
import Layout.IssueChanger;
import ServerConnection.UserData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SearchButton extends JFrame {
    private List<Project> projects;
    private JComboBox<String> projectComboBox;
    private JComboBox<IssueState> stateComboBox;
    private JComboBox<String> assignedComboBox;
    private JComboBox<String> reporterComboBox;
    private JList<String> resultList;
    private DefaultListModel<String> listModel;

    private MainFrame mainFrame;

    public SearchButton(List<Project> projects, UserData userdata, MainFrame mainFrame) {
        this.projects = projects;
        this.mainFrame = mainFrame;
        String[] projectNames = projects.stream().map(Project::getName).toArray(String[]::new);
        initializeUI(this.projects, projectNames, userdata);
    }

    private void initializeUI(List<Project> projects, String[] projectNames, UserData userdata) {
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

        Set<String> uniqueReporters = new HashSet<>(); // Remove duplicate reporters
        for (Project project : projects) {
            for (Issue issue : project.getIssues()) {
                User reporter = userdata.getUser(issue.getReporterId());
                if (reporter != null) {
                    uniqueReporters.add(reporter.getUsername());
                }
            }
        }

        for (String reporter : uniqueReporters) {
            reporterComboBox.addItem(reporter);
        }

        filterPanel.add(new JLabel("Project:"));
        filterPanel.add(projectComboBox);
        filterPanel.add(new JLabel("State:"));
        filterPanel.add(stateComboBox);
        filterPanel.add(new JLabel("Assigned:"));
        filterPanel.add(assignedComboBox);
        filterPanel.add(new JLabel("Reporter:"));
        filterPanel.add(reporterComboBox);

        listModel = new DefaultListModel<>();
        resultList = new JList<>(listModel);
        resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = resultList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        String selectedIssueTitle = listModel.getElementAt(index);
                        Issue selectedIssue = findIssueByTitle(selectedIssueTitle);
                        if (selectedIssue != null) {
                            new IssueChanger(mainFrame, selectedIssue.getId());
                        }

                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(resultList);

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
                    User reporterUser = new UserData().getUser(issue.getReporterId());
                    boolean matchesReporter = (reporter.equals("All Reporters") ||
                            (reporterUser != null && reporter.equals(reporterUser.getUsername())));

                    if (matchesState && matchesAssignee && matchesReporter) {
                        filteredIssues.add(issue);
                    }
                }
            }
        }

        return filteredIssues;
    }

    private void displayIssues(List<Issue> issues) {
        listModel.clear();
        for (Issue issue : issues) {
            listModel.addElement(issue.getTitle());
        }
    }

    private Issue findIssueByTitle(String title) {
        for (Project project : projects) {
            for (Issue issue : project.getIssues()) {
                if (issue.getTitle().equals(title)) {
                    return issue;
                }
            }
        }
        return null;
    }
}
