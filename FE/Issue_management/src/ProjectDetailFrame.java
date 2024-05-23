import javax.swing.*;
import java.awt.*;

public class ProjectDetailFrame extends JFrame {
    private ClassDef.Project project;

    public ProjectDetailFrame(ClassDef.Project project) {
        super("Project Details - " + project.getName());
        this.project = project;
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout(10, 10));

        JLabel projectNameLabel = new JLabel("Project Name: " + project.getName());
        pane.add(projectNameLabel, BorderLayout.NORTH);

        JTextArea issueListTextArea = new JTextArea();
        issueListTextArea.setEditable(false);
        for (ClassDef.Issue issue : project.getIssues()) {
            issueListTextArea.append(issue.title + "\n");
        }
        JScrollPane issueScrollPane = new JScrollPane(issueListTextArea);
        pane.add(issueScrollPane, BorderLayout.CENTER);

        add(pane);
        setVisible(true);
    }
}
