package Layout;

import Data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class IssueChanger extends JFrame {
    private ArrayList<JPanel> comments = new ArrayList<>();
    private Issue issue;
    private int index = -1;

    public IssueChanger(MainFrame MF, Issue issue) {
        super("Change Issue");
        this.issue = issue;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        MF.setEnabled(false);

        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        //issue name
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new FlowLayout());

        JLabel issueNameLabel = new JLabel("Issue Name :");
        issueNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField issueNameField = new JTextField(15);
        issueNameField.setHorizontalAlignment(SwingConstants.CENTER);
        issueNameField.setText(issue.getTitle());
        issueNameField.setEditable(false);
        upperPanel.add(issueNameLabel);
        upperPanel.add(issueNameField);
        pane.add(upperPanel);

        //issue reporter
        JLabel reportLabel = new JLabel("Report :");
        reportLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel reportField = new JLabel();
        reportField.setHorizontalAlignment(SwingConstants.CENTER);
        reportField.setText(issue.getReporterId().toString()); //나중에 리포터 이름이 나오도록 수정해주세요
        upperPanel.add(reportLabel);
        upperPanel.add(reportField);
        pane.add(upperPanel);

        //issue 2nd panel
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(new FlowLayout());
        JLabel dateLabel = new JLabel("Date :");
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel dateField = new JLabel(issue.getReportedDate().toString());
        dateField.setHorizontalAlignment(SwingConstants.CENTER);
        secondPanel.add(dateLabel);
        secondPanel.add(dateField);
        pane.add(secondPanel);

        JLabel typeLabel = new JLabel("Issue Type :");
        JComboBox<IssueType> type = new JComboBox<>(IssueType.values());
        type.setSelectedItem(issue.getType());
        JLabel stateLabel = new JLabel("State :");
        JComboBox<IssueState> state = new JComboBox<>(IssueState.values());
        state.setSelectedItem(issue.getState());
        secondPanel.add(typeLabel);
        secondPanel.add(type);
        secondPanel.add(stateLabel);
        secondPanel.add(state);
        pane.add(secondPanel);

        //issue description
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BorderLayout());
        JLabel descriptionLabel = new JLabel(issue.getDescription());
        JTextArea descriptionField = new JTextArea();
        descriptionField.setEditable(true);
        descriptionField.setText(issue.getDescription());
        descriptionPanel.add(descriptionLabel, BorderLayout.NORTH);
        descriptionPanel.add(descriptionField, BorderLayout.CENTER);
        pane.add(descriptionPanel);


        //issue comment panel
        JPanel commentPanel = new JPanel();
        commentPanel.setLayout(new BorderLayout());

        JPanel commentUpperPanel = new JPanel();
        commentUpperPanel.setLayout(new BoxLayout(commentUpperPanel, BoxLayout.X_AXIS));
        JLabel commentLabel = new JLabel("Comment");

        JButton addComment = new JButton("Add");
        JButton removeComment = new JButton("Remove");
        commentUpperPanel.add(commentLabel);
        commentUpperPanel.add(addComment);
        commentUpperPanel.add(removeComment);
        commentPanel.add(commentUpperPanel, BorderLayout.NORTH);

        JPanel commentsPanel = new JPanel();
        commentsPanel.setLayout(new GridLayout(2, 1));
        commentPanel.add(commentsPanel, BorderLayout.CENTER);
        loadComment(commentsPanel, issue);

        JScrollPane commentsScrollPane = new JScrollPane(commentsPanel);
        commentsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        commentPanel.add(commentsScrollPane, BorderLayout.CENTER);
        pane.add(commentPanel);

        JButton OKButton = new JButton("OK");
        OKButton.setHorizontalAlignment(SwingConstants.CENTER);
        pane.add(OKButton);
        OKButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MF.showIssues();
                dispose();
            }
        });
        addComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IssueDetailFrame issueDetailFrame = new IssueDetailFrame(issue,MF);
                loadComment(commentsPanel, issue);
                issueDetailFrame.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        pane.revalidate();
                        pane.repaint();
                    }
                });
            }
        });

        removeComment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(index == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a comment first", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    int response = JOptionPane.showConfirmDialog(null
                            ,issue.getComments().get(index).getId()+" Remove?","Warning"
                            ,JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                    if(response == JOptionPane.OK_OPTION) {
                        comments.remove(index);
                        issue.removeComment(index);
                        loadComment(commentsPanel, issue);
                        index = -1;
                    }
                }
            }
        });

        add(pane);
        setVisible(true);
    }

    public void loadComment(JPanel commentsPanel, Issue issue) {
        //커멘츠패널은 커멘트 패널을 담는 패널
        //커멘츠는 커멘트 패널들의 어레이리스트
        commentsPanel.removeAll();
        comments.clear();
        commentsPanel.setLayout(new GridLayout(issue.getComments().size(), 1));
        clickPanelListener click = new clickPanelListener();
        for(Comment comment : issue.getComments()) {
            if(comment!=null) {
                //커멘트 패널은 커멘트 패널 하나
                JPanel commentPanel = new JPanel(new GridLayout(3, 1));
                commentPanel.addMouseListener(click);
                commentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                comments.add(commentPanel);
                JLabel commentAuthor = new JLabel();
                if (comment.getAuthorId() != null)
                    commentAuthor.setText("Author :" + comment.getAuthorId().toString());
                JLabel commentText = new JLabel("Text :" + comment.getText());
                JLabel commentDate = new JLabel("Date :" + comment.getTimestamp().toString());
                commentPanel.add(commentAuthor);
                commentPanel.add(commentText);
                commentPanel.add(commentDate);
                commentsPanel.add(commentPanel);
            }
        }
        commentsPanel.revalidate();
        commentsPanel.repaint();
    }

    class clickPanelListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            for(int i = 0; i < comments.size(); i++) {
                if(e.getSource() == comments.get(i)) {
                    int temp = index;
                    index = i;
                    if(index != temp)
                    {
                        if(temp!=-1)
                        {
                            comments.get(index).setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                            comments.get(temp).setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        }
                        else {
                            comments.get(index).setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                        }
                    }
                    else {
                        comments.get(index).setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                        index = -1;
                    }
                    break;
                }
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }

}
