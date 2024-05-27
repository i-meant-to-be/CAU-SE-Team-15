package Layout;

import Data.Issue;
import Data.Project;

import javax.swing.*;
import java.awt.*;

public class ProjectDetailFrame extends JFrame {
    private Project project;

    public ProjectDetailFrame(Project project) {
        super("Project Details - " + project.getName());
        this.project = project;
        setSize(800, 300);
        setLocationRelativeTo(null);

        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout(10, 10));

        JLabel projectNameLabel = new JLabel("Project Name: " + project.getName()); // 프로젝트 이름 표시
        pane.add(projectNameLabel, BorderLayout.NORTH);

        JPanel issuePanel = new JPanel(new GridLayout(project.getIssues().size(), 1));

        for (Issue issue : project.getIssues()) {
            JButton issueButton = new JButton("Title: " + issue.getTitle() + ", Type: " + issue.getType());
            issueButton.addActionListener(e -> showIssueDetails(issue)); // 버튼을 클릭하면 해당 이슈에 대한 세부 정보를 보여주는 메서드 호출
            issuePanel.add(issueButton);
        }

        JScrollPane issueScrollPane = new JScrollPane(issuePanel);
        pane.add(issueScrollPane, BorderLayout.CENTER);

        add(pane);
        setVisible(true);

        setAlwaysOnTop(true);
        setVisible(true);
        toFront();
        setAlwaysOnTop(false);
    }

    // 이슈에 대한 세부 정보를 보여주는 메서드
    private void showIssueDetails(Issue issue) {
        IssueDetailFrame detailFrame = new IssueDetailFrame(issue);
        detailFrame.setVisible(true);
    }

}
