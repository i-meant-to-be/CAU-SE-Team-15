package Button;

import Data.Issue;

import javax.swing.*;
import java.awt.*;

public class IssueDetailFrame extends JFrame {

    public IssueDetailFrame(Issue issue) {
        super("Issue Details - " + issue.getTitle());
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 이슈에 대한 정보를 표시하는 JLabels 추가
        JLabel titleLabel = new JLabel("Title: " + issue.getTitle());
        //JLabel reporterLabel = new JLabel("Reporter: " + issue.getReporter_id());
        //JLabel dateLabel = new JLabel("Reported Date: " + issue.getReported_date());
        JLabel descriptionLabel = new JLabel("Description: " + issue.getDescription());
        JLabel typeLabel = new JLabel("Type: " + issue.getType());
        JLabel stateLabel = new JLabel("State: " + issue.getState());

        // 패널에 JLabels 추가
        panel.add(titleLabel, BorderLayout.NORTH);
        //panel.add(reporterLabel, BorderLayout.CENTER);
        //panel.add(dateLabel, BorderLayout.CENTER);
        panel.add(descriptionLabel, BorderLayout.CENTER);
        panel.add(typeLabel, BorderLayout.CENTER);
        panel.add(stateLabel, BorderLayout.CENTER);

        add(panel);
    }
}