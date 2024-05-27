package Layout;

import Data.Issue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class IssueDetailFrame extends JFrame {
    private Issue issue;
    private JTextArea commentsTextArea; // 코멘트를 입력할 텍스트 에리어
    private JTextArea commentsDisplayArea; // 코멘트를 표시할 텍스트 에리어

    public IssueDetailFrame(Issue issue) {
        super("Issue Details - " + issue.getTitle());
        this.issue = issue;
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout(10, 10));

        JLabel issueTitleLabel = new JLabel("Issue Title: " + issue.getTitle());
        pane.add(issueTitleLabel, BorderLayout.NORTH);

        JTextArea issueDescriptionTextArea = new JTextArea("Description: " + issue.getDescription());
        issueDescriptionTextArea.setEditable(false);
        pane.add(new JScrollPane(issueDescriptionTextArea), BorderLayout.CENTER);

        JPanel commentPanel = new JPanel(new BorderLayout());
        commentPanel.setBorder(BorderFactory.createTitledBorder("Comments")); // 코멘트 테두리 추가

        commentsTextArea = new JTextArea(5, 20);
        commentsTextArea.setLineWrap(true);
        commentPanel.add(new JScrollPane(commentsTextArea), BorderLayout.NORTH);

        JButton addCommentButton = new JButton("Add Comment");
        addCommentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addComment();
            }
        });
        commentPanel.add(addCommentButton, BorderLayout.SOUTH);

        pane.add(commentPanel, BorderLayout.SOUTH);

        // 코멘트를 표시할 텍스트 에리어 추가
        commentsDisplayArea = new JTextArea();
        commentsDisplayArea.setEditable(false);
        pane.add(new JScrollPane(commentsDisplayArea), BorderLayout.CENTER);

        // 현재 이슈의 코멘트를 표시
        displayComments();

        add(pane);
        setVisible(true);
    }

    private void addComment() {
        String commentText = commentsTextArea.getText().trim();
        if (!commentText.isEmpty()) {
            issue.addComment(commentText); // 이슈에 코멘트 추가
            JOptionPane.showMessageDialog(null, "Comment added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            commentsTextArea.setText(""); // 코멘트 입력 필드 비우기

            // 코멘트 표시 업데이트
            displayComments();
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a comment.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 코멘트를 표시하는 메서드
    private void displayComments() {
        StringBuilder sb = new StringBuilder();
        String[] comments = issue.getComments();
        if (comments != null) {
            for (String comment : comments) {
                sb.append(comment).append("\n");
            }
        }
        commentsDisplayArea.setText(sb.toString());
    }
}
