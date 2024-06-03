package Layout;

import Data.*;
import ServerConnection.CommentData;
import ServerConnection.IssueData;
import ServerConnection.UserData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IssueChanger extends JFrame {
    private ArrayList<JPanel> comments = new ArrayList<>();
    private Issue issue;
    private int index = -1;

    public IssueChanger(MainFrame MF, UUID issueId) {
        super("Change Issue");
        IssueData issueData = new IssueData();
        this.issue = issueData.getIssue(issueId);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 600);
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
        JLabel reportLabel = new JLabel("Reporter :");
        reportLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel reportField = new JLabel();
        reportField.setHorizontalAlignment(SwingConstants.CENTER);
        UserData userData = new UserData();
        try {
            reportField.setText(userData.getUser(issue.getReporterId()).getUsername()); //나중에 리포터 이름이 나오도록 수정해주세요
        }
        catch (Exception e) {
            reportField.setText("missing No.");
        }
        upperPanel.add(reportLabel);
        upperPanel.add(reportField);
        pane.add(upperPanel);

        //2nd panel
        JPanel secondPanel = new JPanel();
        JLabel issueAssigneeLabel = new JLabel("Issue Assignee :");
        issueAssigneeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        ArrayList<User> dev = new ArrayList<>();
        UserData userData1 = new UserData();

        JLabel fixer = new JLabel("Fixer :");
        fixer.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel fixerField = new JLabel();
        fixerField.setHorizontalAlignment(SwingConstants.CENTER);
        fixerField.setEnabled(false);

        //dev에 유저 리스트 추가
        for(int i = 0; i <MF.getProject().getUsers().size(); i++){
            User u = userData1.getUser(MF.getProject().getUsers().get(i));
            if(u==null)
                continue;
            if(u.getType()==UserType.DEVELOPER){
                dev.add(u);
            }
        }

        //콤보박스에 dev 추가
        JComboBox issueAssigneeCombo = new JComboBox();
        //프로젝트 유저들 불러오기
        for(User u:dev){
            //issueAssigneeCombo에 아이템 add
            if(u!=null) {
                issueAssigneeCombo.addItem(u.getUsername());
            }
        }
        User assignee = userData1.getUser(issue.getAssigneeId());
        if(assignee==null) {
            issueAssigneeCombo.insertItemAt("", 0);
            issueAssigneeCombo.setSelectedIndex(0);
        }
        else {
            issueAssigneeCombo.setSelectedItem(assignee.getUsername());
            fixerField.setText(assignee.getUsername());
        }

        JButton recommendButton = new JButton("Recommend");
        recommendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //조그마한 창 하나 뜨고 목록 가져오기
                //Create Project List
                JList<String> devList = new JList<>(new DefaultListModel<>());
                DefaultListModel<String> listModel = (DefaultListModel<String>) devList.getModel();
                //Add dev name
                IssueData issueData = new IssueData();
                List<User> recommendDev = issueData.recommendDev(issueId);
                for (User user : recommendDev) {
                    for(User u : dev){
                        if(u.getUsername().equals(user.getUsername())){
                            listModel.addElement(user.getUsername());
                        }
                    }
                }

                devList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 하나의 프로젝트만 선택 가능

                devList.setVisibleRowCount(10);

                JScrollPane listScrollPane = new JScrollPane(devList);

                JOptionPane.showMessageDialog(null, listScrollPane, "Recommended developer", JOptionPane.INFORMATION_MESSAGE);

            }
        });
        secondPanel.add(issueAssigneeLabel);
        secondPanel.add(issueAssigneeCombo);
        secondPanel.add(recommendButton);
        secondPanel.add(fixer);
        secondPanel.add(fixerField);
        pane.add(secondPanel);


        //issue 2nd panel
        JPanel thirdPanel = new JPanel();
        thirdPanel.setLayout(new FlowLayout());
        JLabel dateLabel = new JLabel("Date :");
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel dateField = new JLabel(issue.getReportedDate().toString());
        dateField.setHorizontalAlignment(SwingConstants.CENTER);
        thirdPanel.add(dateLabel);
        thirdPanel.add(dateField);
        pane.add(thirdPanel);

        JLabel typeLabel = new JLabel("Issue Type :");
        JComboBox<IssueType> type = new JComboBox<>(IssueType.values());
        type.setSelectedItem(issue.getType());
        JLabel stateLabel = new JLabel("State :");
        JComboBox<IssueState> state = new JComboBox<>(IssueState.values());
        state.setSelectedItem(issue.getState());
        thirdPanel.add(typeLabel);
        thirdPanel.add(type);
        thirdPanel.add(stateLabel);
        thirdPanel.add(state);
        pane.add(thirdPanel);

        //issue description
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BorderLayout());
        JLabel descriptionLabel = new JLabel("Description");
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

        JPanel buttonPanel = new JPanel(); //버튼 크기 설정
        buttonPanel.setLayout(new GridLayout(1, 2, 4, 4));

        JButton OKButton = new JButton("OK");
        JButton CANCELButton = new JButton("CANCEL");

        OKButton.setHorizontalAlignment(SwingConstants.CENTER);
        CANCELButton.setHorizontalAlignment(SwingConstants.CENTER);

        buttonPanel.add(OKButton);
        buttonPanel.add(CANCELButton);
        pane.add(buttonPanel);

        OKButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int ok = 0;
                IssueData issueD = new IssueData();
                UserData userData2 = new UserData();
                Issue originalI = issue;
                issue.setDescription(descriptionField.getText());
                if(type.getSelectedItem() != originalI.getType()){
                    if(MF.get_user().getType()==UserType.ADMIN || MF.get_user().getType()==UserType.MANAGER){
                        issue.setType((IssueType) type.getSelectedItem());
                    }
                    else ok = 1;
                }

                if(MF.get_user().getType()==UserType.TESTER){
                    if((!originalI.getState().equals(state.getSelectedItem())) && originalI.getState()==IssueState.FIXED) {
                        if (state.getSelectedItem() == IssueState.REOPENED || state.getSelectedItem() == IssueState.RESOLVED) {
                            if(originalI.getReporterId().equals(MF.get_user().getUUID()))
                                issue.setState((IssueState) state.getSelectedItem());
                            else ok = 5;
                        } else {
                            ok = 2;
                        }
                    }
                    else if(originalI.getState().equals(state.getSelectedItem())&&originalI.getType().equals(type.getSelectedItem())&&originalI.getReporterId().equals(MF.get_user().getUUID())){
                        ok = 0;
                        System.out.println(issue.getDescription());
                    }else{
                        ok = 7;
                    }
                }
                else if(MF.get_user().getType()==UserType.DEVELOPER){
                    if((!originalI.getState().equals(state.getSelectedItem())) && (originalI.getState()==IssueState.ASSIGNED || originalI.getState()==IssueState.REOPENED)) {
                        if(state.getSelectedItem()==IssueState.FIXED) {
                            if(originalI.getAssigneeId().equals(MF.get_user().getUUID())) {
                                issue.setState((IssueState) state.getSelectedItem());
                                issue.setFixerId(MF.get_user().getUUID());
                            }
                            else ok = 6;
                        }
                        else ok = 3;
                    }
                    else ok = 8;
                }
                else {issue.setState((IssueState) state.getSelectedItem());}

                /*if(userData2.getUser((String) issueAssigneeCombo.getSelectedItem())==null){
                    MF.setEnabled(true);
                    dispose();
                    return;
                }*/

                if(userData2.getUser((String) issueAssigneeCombo.getSelectedItem())!=null) {
                    if ((!(userData2.getUser((String) issueAssigneeCombo.getSelectedItem()).getUUID().equals(originalI.getAssigneeId())))) {
                        if (MF.get_user().getType() == UserType.ADMIN || MF.get_user().getType() == UserType.MANAGER) {
                            if (!issueAssigneeCombo.getSelectedItem().equals(""))
                                issue.setAssigneeId((userData2.getUser((String) issueAssigneeCombo.getSelectedItem()).getUUID()));
                        } else {
                            ok = 4;
                        }
                    }
                }


                if(ok==0) {
                    issueD.modifyIssue(issue);
                    issue = issueD.getIssue(issue.getId());
                    MF.showIssues();
                    MF.setEnabled(true);
                    dispose();
                }
                else if(ok==1) {
                    JOptionPane.showMessageDialog(null, "You don't have permission to change type", "Change failed", JOptionPane.WARNING_MESSAGE);
                }
                else if(ok==2) {
                    JOptionPane.showMessageDialog(null, "You only have permission to change state REOPENED/RESOLVED", "Change failed", JOptionPane.WARNING_MESSAGE);
                }
                else if(ok==3) {
                    JOptionPane.showMessageDialog(null, "You only have permission to change state FIXED", "Change failed", JOptionPane.WARNING_MESSAGE);
                }
                else if(ok==4) {
                    JOptionPane.showMessageDialog(null, "You don't have permission to change Assignee", "Change failed", JOptionPane.WARNING_MESSAGE);
                }
                else if(ok==5) {
                    JOptionPane.showMessageDialog(null, "You are not Reporter of this issue", "Change failed", JOptionPane.WARNING_MESSAGE);
                }
                else if(ok==6) {
                    JOptionPane.showMessageDialog(null, "You are not Assignee of this issue", "Change failed", JOptionPane.WARNING_MESSAGE);
                }
                else if(ok==7) {
                    JOptionPane.showMessageDialog(null, "Issue has not been fixed yet", "Change failed", JOptionPane.WARNING_MESSAGE);
                }
                else if(ok==8) {
                    JOptionPane.showMessageDialog(null, "Issue has not been assigned yet", "Change failed", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        CANCELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MF.setEnabled(true);  // MainFrame을 다시 활성화
                dispose();  // 현재 창을 닫기
            }
        });



        addComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IssueDetailFrame issueDetailFrame = new IssueDetailFrame(issue,MF);
                loadComment(commentsPanel, issue);
                issueDetailFrame.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        IssueData issueData = new IssueData();
                        IssueChanger.this.issue = issueData.getIssue(issueId);
                        loadComment(commentsPanel, issue);
                        IssueChanger.this.repaint();
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
                        CommentData commentData = new CommentData();
                        commentData.deleteComment(issue.getComment(index));
                        //comments.remove(index);
                        //issue.removeComment(index);
                        IssueData issueData = new IssueData();
                        issue = issueData.getIssue(issue.getId());
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
                if (comment.getAuthorId() != null) {
                    UserData userData = new UserData();
                    commentAuthor.setText("Author : " + userData.getUser(comment.getAuthorId()).getUsername());
                }
                JLabel commentText = new JLabel("Text : " + comment.getText());
                JLabel commentDate = new JLabel("Date : " + comment.getTimestamp().toString());
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