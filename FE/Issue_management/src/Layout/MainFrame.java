package Layout;

import Data.*;
import Button.*;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    boolean loggedIn = false;

    private String ADMIN_ID = "admin";
    private String ADMIN_PW = "1234";
    private User user = new User();
    private Log_in login;
    private JLabel user_name;

    private ArrayList<Project> projects = new ArrayList<>();
    private ArrayList<Issue> issues = new ArrayList<>();

    private Project selectedProject;

    private JPanel issue_panel;

    public MainFrame() {
        super("Main page");
        setSize(800, 600);
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout(10, 10));

        JPanel user_func = new JPanel();
        user_func.setLayout(new GridLayout(5, 1));

        JPanel user_info = new JPanel();
        user_info.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        user_info.setLayout(new GridLayout(3, 1));
        user_info.add(new JLabel("Welcome!", JLabel.CENTER));
        user_name = new JLabel(user.getUsername(), JLabel.CENTER);
        user_name.setFont(new Font("Serif", Font.BOLD, 15));
        user_info.add(user_name);
        JButton logout = new JButton("Logout");
        user_info.add(logout);
        logout.addActionListener(new ActionListener() {     //로그아웃 -> anonymous
            public void actionPerformed(ActionEvent e) {
                user = new User();
                user_name.setText(user.getUsername());
                repaint();
                loggedIn = false;
                login = new Log_in(MainFrame.this);
            }
        });

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               if(login.getUser().getType().equals(UserType.ADMIN)) {new RegisterButton();}
               else{
                   JOptionPane.showMessageDialog(MainFrame.this,
                           "You don't have permission to register.",
                           "Warning",
                           JOptionPane.WARNING_MESSAGE);
               }
            }
        });

        JButton projectButton = new JButton("Project");
        projectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ProjectButton(MainFrame.this);
            }
        });

        JButton issueButton = new JButton("Issue");
        issueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { new IssueButton(MainFrame.this, projects); }
        });

        JButton search = new JButton("Search");

        user_func.add(user_info);
        user_func.add(registerButton);
        user_func.add(projectButton);
        user_func.add(issueButton);
        user_func.add(search);

        issue_panel = new JPanel();
        pane.add(user_func, BorderLayout.EAST);
        pane.add(issue_panel, BorderLayout.CENTER);

        add(pane);
        this.setLocationRelativeTo(null);
        setVisible(true);
        if (!loggedIn) {
            login = new Log_in(this);
        }
        login.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                if(loggedIn) {
                    user_name.setText(user.getUsername());
                    repaint();
                }
            }
        });
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
    }

    public String getID() {
        return ADMIN_ID;
    }

    public String getPW() {
        return ADMIN_PW;
    }

    public User get_user() {return user;}

    public ArrayList<Project> get_projects() {return projects;}

    public void setProject(Project project) {
        this.selectedProject = project;
    }

    public JLabel getuserlabel(){return user_name;}


    public void showIssues(){
        AbstractBorder border = new LineBorder(Color.BLACK, 2);

        issues = selectedProject.getIssues();
        issue_panel.removeAll();
        issue_panel.setLayout(new BorderLayout(10, 10));

        JPanel detail_panel = new JPanel();
        detail_panel.setLayout(new GridLayout(1, 6));
        JLabel issue_title = new JLabel("Issue_title", JLabel.CENTER);
        issue_title.setBorder(border);
        JLabel issue_reporter = new JLabel("Issue_reporter", JLabel.CENTER);
        issue_reporter.setBorder(border);
        JLabel issue_date = new JLabel("Issue_date", JLabel.CENTER);
        issue_date.setBorder(border);
        JLabel issue_type = new JLabel("Issue_type", JLabel.CENTER);
        issue_type.setBorder(border);
        JLabel issue_assignee = new JLabel("Issue_assignee", JLabel.CENTER);
        issue_assignee.setBorder(border);
        JLabel issue_state = new JLabel("Issue_state", JLabel.CENTER);
        issue_state.setBorder(border);
        detail_panel.add(issue_title);
        detail_panel.add(issue_reporter);
        detail_panel.add(issue_date);
        detail_panel.add(issue_type);
        detail_panel.add(issue_assignee);
        detail_panel.add(issue_state);

        //panel은 이슈 목록
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        ArrayList<JPanel> issue_panels = new ArrayList<>();
        for(Issue issue : issues){
            JPanel new_Panel = new JPanel();
            new_Panel.setLayout(new GridLayout(1, 6));
            new_Panel.setBorder(BorderFactory.createLineBorder(Color.black, 3));
            new_Panel.add(new JLabel(issue.getTitle(), JLabel.CENTER));
            new_Panel.add(new JLabel(issue.getReporterId(), JLabel.CENTER));
            new_Panel.add(new JLabel(issue.getReportedDate().toString(), JLabel.CENTER));
            new_Panel.add(new JLabel(issue.getType().toString(), JLabel.CENTER));
            if(issue.getAssigneeId()==null){
                new_Panel.add(new JLabel("Not assigned yet", JLabel.CENTER));
            }
            else {
                new_Panel.add(new JLabel(issue.getAssigneeId().toString(), JLabel.CENTER));
            }
            new_Panel.add(new JLabel(issue.getState().toString(), JLabel.CENTER));
            issue_panels.add(new_Panel);
        }
        for(int i = 0; i < issue_panels.size(); i++){
            panel.add(issue_panels.get(i));
        }
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setViewportView(panel);

        issue_panel.add(detail_panel, BorderLayout.NORTH);
        issue_panel.add(panel, BorderLayout.CENTER);
        issue_panel.updateUI();
    }

}