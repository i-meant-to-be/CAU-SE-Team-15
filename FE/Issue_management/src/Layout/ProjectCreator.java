package Layout;

import Data.Project;
import Data.User;
import Data.UserType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.UUID;

public class ProjectCreator extends JFrame {
    private JTextField projectNameField = new JTextField(20);
    private ArrayList<User> devUsers = new ArrayList<>();
    private ArrayList<User> testUsers = new ArrayList<>();
    private ArrayList<User> manageUsers = new ArrayList<>();
    private JComboBox<UserType> userTypeComboBox;
    private JComboBox<String> userComboBox;
    private UserType selectedUserType;
    private User selectedUser;
    private ArrayList<Project> projects;

    public ProjectCreator(ArrayList<Project> projects) {
        super("Project Creator");
        this.projects = projects;
        setPreferredSize(new Dimension(600, 600));

        JScrollPane scrollPane = new JScrollPane();

        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout(10, 10));
        pane.add(scrollPane);

        //Load every user of the type selected at the comboBox from server
        //this is example
        User tempUser1 = new User();
        tempUser1.setUUID(UUID.randomUUID());
        tempUser1.setUser("hi", "1234",UserType.Dev);
        User tempUser2 = new User();
        tempUser2.setUUID(UUID.randomUUID());
        tempUser2.setUser("hello", "1234",UserType.Tester);
        if(tempUser1.getType() == UserType.Dev){
            devUsers.add(tempUser1);
        } else if(tempUser1.getType() == UserType.Tester){
            testUsers.add(tempUser1);
        } else if(tempUser1.getType() == UserType.Manager){
            manageUsers.add(tempUser1);
        }

        if(tempUser2.getType() == UserType.Dev){
            devUsers.add(tempUser2);
        } else if(tempUser2.getType() == UserType.Tester){
            testUsers.add(tempUser2);
        } else if(tempUser2.getType() == UserType.Manager){
            manageUsers.add(tempUser2);
        }

        //Project Name panel
        JPanel projectNamePanel = new JPanel();
        projectNamePanel.setLayout(new GridLayout(1, 2, 10, 10));
        JLabel projectName = new JLabel("Project Name");
        projectName.setHorizontalAlignment(SwingConstants.CENTER);
        projectNamePanel.add(projectName);
        projectNamePanel.add(projectNameField);

        //Project Member panel
        JPanel projectMemberPanel1 = new JPanel();
        JPanel projectMemberPanel2 = new JPanel();
        JPanel projectMemberPanel2_1 = new JPanel();
        JPanel projectMemberPanel2_2 = new JPanel();
        JPanel projectMemberPanel = new JPanel();
        userTypeComboBox = new JComboBox<>(UserType.values());
        userTypeComboBox.setSelectedItem(null);
        userComboBox = new JComboBox<>();
        userComboBox.setSelectedItem(null);

        JButton addUserButton = new JButton("Add User");
        projectMemberPanel.setLayout(new GridLayout(1, 2, 10, 10));
        JPanel memberPanel = new JPanel(new GridLayout(2, 1));
        JLabel projectMemberLabel = new JLabel("Project Member");
        JTextArea projectDescription = new JTextArea("Description :\n");
        JScrollPane memberscrollpane = new JScrollPane();
        memberscrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        Button_add_Listener addListener = new Button_add_Listener();
        addUserButton.addActionListener(addListener);
        projectMemberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        projectMemberPanel1.add(projectMemberLabel);
        projectMemberPanel2.setLayout(new BorderLayout(10, 10));
        projectMemberPanel2_1.setLayout(new GridLayout(10, 1, 10, 10));
        projectMemberPanel2_2.setLayout(new GridLayout(1, 3, 10, 10));
        projectMemberPanel2_2.add(userTypeComboBox);
        projectMemberPanel2_2.add(userComboBox);
        projectMemberPanel2_2.add(addUserButton);
        projectMemberPanel2_1.add(projectMemberPanel2_2);
        projectMemberPanel2.add(projectMemberPanel2_1, BorderLayout.CENTER);
        projectMemberPanel2.add(memberscrollpane, BorderLayout.EAST);
        projectMemberPanel.add(projectMemberPanel1);
        projectMemberPanel.add(projectMemberPanel2);
        memberPanel.add(projectMemberPanel);
        memberPanel.add(projectDescription);

        Combobox_type_Listener typeListener = new Combobox_type_Listener();
        userTypeComboBox.addActionListener(typeListener);
        Combobox_user_Listener userListener = new Combobox_user_Listener();
        userComboBox.addActionListener(userListener);

        //Button Panel
        JButton create = new JButton("Create");
        JButton cancel = new JButton("Cancel");
        //Create, Cancel button action definition
        Button_create_Listener createListener = new Button_create_Listener();
        create.addActionListener(createListener);
        Button_cancel_Listener cancelListener = new Button_cancel_Listener();
        cancel.addActionListener(cancelListener);

        JPanel btns = new JPanel();
        btns.setLayout(new GridLayout(1,2));
        btns.add(create);
        btns.add(cancel);

        pane.add(projectNamePanel, BorderLayout.NORTH);
        pane.add(memberPanel, BorderLayout.CENTER);
        pane.add(btns, BorderLayout.SOUTH);

        add(pane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    class Combobox_type_Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            userComboBox.removeAllItems();
            selectedUserType = (UserType) userTypeComboBox.getSelectedItem();
            switch (selectedUserType) {
                case Dev:
                    for (User u : devUsers) {
                        userComboBox.addItem(u.getUsername());
                    }
                    break;
                case Tester:
                    for (User u : testUsers) {
                        userComboBox.addItem(u.getUsername());
                    }
                    break;
                case Manager:
                    for (User u : manageUsers) {
                        userComboBox.addItem(u.getUsername());
                    }
                    break;
            }
        }
    }

    class Combobox_user_Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String selected = (String) userComboBox.getSelectedItem();
            switch (selectedUserType) {
                case Dev:
                    for (User u : devUsers) {
                        if (selected.equals(u.getUsername())) {
                            selectedUser = u;
                            break;
                        }
                    }
                    break;
                case Tester:
                    for (User u : testUsers) {
                        if (selected.equals(u.getUsername())) {
                            selectedUser = u;
                            break;
                        }
                    }
                    break;
                case Manager:
                    for (User u : manageUsers) {
                        if (selected.equals(u.getUsername())) {
                            selectedUser = u;
                            break;
                        }
                    }
                    break;
            }
        }
    }

    class Button_add_Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (selectedUser != null) {
                JOptionPane.showMessageDialog(null, "User " + selectedUser.getUsername() + " added.");
            } else {
                JOptionPane.showMessageDialog(null, "No user selected.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class Button_create_Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String projectName = projectNameField.getText().trim();
            if (!projectName.isEmpty()) {
                Project newProject = new Project(projectName);
                projects.add(newProject);
                JOptionPane.showMessageDialog(null, "Project created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close the ProjectCreator window
            } else {
                JOptionPane.showMessageDialog(null, "Project name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class Button_cancel_Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose(); // Close the ProjectCreator window
        }
    }
}
