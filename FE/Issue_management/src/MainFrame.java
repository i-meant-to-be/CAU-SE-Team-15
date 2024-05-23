import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {
    boolean loggedIn = false;
    private String ADMIN_ID = "admin";
    private String ADMIN_PW = "1234";
    private ClassDef classDef = new ClassDef();
    ClassDef.User user = classDef.new User();
    private List<ClassDef.Project> projects = new ArrayList<>();
    private Log_in login;

    MainFrame() {
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
        JLabel user_name = new JLabel(user.getUsername(), JLabel.CENTER);
        user_name.setFont(new Font("Serif", Font.BOLD, 15));
        user_info.add(user_name);
        JButton logout = new JButton("Log out");
        user_info.add(logout);
        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                user = classDef.new User();
                user_name.setText(user.getUsername());
                repaint();
                loggedIn = false;
                login = new Log_in(MainFrame.this);
            }
        });

        JButton projectButton = new JButton("Project");
        projectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showProjectOptions();
            }
        });

        JButton create_issue = new JButton("Create Issue");
        JButton browse = new JButton("Browse");
        JButton search = new JButton("Search");

        user_func.add(user_info);
        user_func.add(projectButton);
        user_func.add(create_issue);
        user_func.add(browse);
        user_func.add(search);

        JPanel issue_panel = new JPanel();
        pane.add(user_func, BorderLayout.EAST);
        pane.add(issue_panel, BorderLayout.CENTER);

        add(pane);
        this.setLocationRelativeTo(null);
        setVisible(true);
        if (!loggedIn) {
            login = new Log_in(this);
            login.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) {
                    if (loggedIn) {
                        user_name.setText(user.getUsername());
                        repaint();
                    }
                }
            });
        }

        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
    }

    public String getID() {
        return ADMIN_ID;
    }

    public String getPW() {
        return ADMIN_PW;
    }

    private void showProjectOptions() {
        String[] options = {"View All Projects", "Create Project"};
        int choice = JOptionPane.showOptionDialog(this, "Select an option", "Project Options",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            showAllProjects();
        } else if (choice == 1) {
            createNewProject();
        }
    }

    private void showAllProjects() {
        JList<ClassDef.Project> projectList = new JList<>(new DefaultListModel<>());
        DefaultListModel<ClassDef.Project> listModel = (DefaultListModel<ClassDef.Project>) projectList.getModel();
        for (ClassDef.Project project : projects) {
            listModel.addElement(project);
        }

        projectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //하나의 프로젝트만 선택 가능
        projectList.setVisibleRowCount(10);
        projectList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                ClassDef.Project selectedProject = projectList.getSelectedValue();
                if (selectedProject != null) {
                    new ProjectDetailFrame(selectedProject);
                }
            }
        });

        JScrollPane listScrollPane = new JScrollPane(projectList);
        JOptionPane.showMessageDialog(this, listScrollPane, "All Projects", JOptionPane.INFORMATION_MESSAGE);
    }

    private void createNewProject() {
        String projectName = JOptionPane.showInputDialog(this, "Enter project name:");
        if (projectName != null && !projectName.trim().isEmpty()) {
            ClassDef.Project newProject = classDef.new Project(projectName);
            projects.add(newProject);
            JOptionPane.showMessageDialog(this, "Project created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Project name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
