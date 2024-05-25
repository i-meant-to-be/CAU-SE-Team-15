package Button;

import Data.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ProjectDetailFrame extends JFrame {
    private Project project;

    public ProjectDetailFrame(Project project) {
        super("Project Details - " + project.getName());
        this.project = project;
        setSize(800, 300);
        setLocationRelativeTo(null);

        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout(10, 10));

        JLabel projectNameLabel = new JLabel("Project Name: " + project.getName()); //프로젝트 이름 표시
        pane.add(projectNameLabel, BorderLayout.NORTH);

        JPanel issuePanel = new JPanel(new GridLayout(project.getIssues().size(), 1));

        for (Issue issue : project.getIssues()) {
            JLabel issueLabel = new JLabel("Title: " + issue.getTitle() + ", Type: " + issue.getType(), JLabel.CENTER);
            issuePanel.add(issueLabel);
        }

        JScrollPane issueScrollPane = new JScrollPane(issuePanel);
        pane.add(issueScrollPane, BorderLayout.CENTER);

        add(pane);
        setVisible(true);
    }
}
