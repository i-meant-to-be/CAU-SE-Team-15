package Button;

import Data.*;
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

        JLabel projectNameLabel = new JLabel("Project Name: " + project.getName()); //프로젝트 이름 표시
        pane.add(projectNameLabel, BorderLayout.NORTH);

        JTextArea issueListTextArea = new JTextArea();
        issueListTextArea.setEditable(false); //수정 불가
        for (Issue issue : project.getIssues()) {
            issueListTextArea.append(issue.getTitle() + "\n");
        }
        JScrollPane issueScrollPane = new JScrollPane(issueListTextArea); //텍스트 영역에 스크롤 추가
        pane.add(issueScrollPane, BorderLayout.CENTER);

        add(pane);
        setVisible(true);
    }
}
