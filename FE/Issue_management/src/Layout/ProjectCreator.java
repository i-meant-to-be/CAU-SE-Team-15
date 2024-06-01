package Layout;

import Data.Project;
import Data.User;
import Data.UserType;
import Button.ProjectButton;
import ServerConnection.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProjectCreator extends JFrame {
    private ProjectButton projectButton;
    private ArrayList<Project> projects;

    private JTextField projectNameField = new JTextField(20);
    private JTextArea projectDescription = new JTextArea("Description :\n");
    private JScrollPane memberscrollpane;

    private List<UUID> users = new ArrayList<>();
    private ArrayList<User> devUsers = new ArrayList<>();
    private ArrayList<User> testUsers = new ArrayList<>();
    private ArrayList<User> manageUsers = new ArrayList<>();

    private ArrayList<JComboBox<UserType>> userTypeComboBoxList = new ArrayList<>();
    private ArrayList<JComboBox<String>> userComboBoxList = new ArrayList<>();
    private ArrayList<JButton> buttonX = new ArrayList<>();

    private UserType selectedUserType;
    private User selectedUser;

    private ArrayList<JPanel> panels = new ArrayList<>();
    private JPanel pane;
    private JPanel projectMemberPanel2_1;

    private ArrayList<Combobox_type_Listener> typeListeners = new ArrayList<>();
    private ArrayList<Combobox_user_Listener> userListeners = new ArrayList<>();
    private ComboboxMouseListener mouseListener;
    private ArrayList<Button_x_Listener> buttonListeners = new ArrayList<>();


    private int index;

    public ProjectCreator(ProjectButton projectButton) {
        super("Project Creator");
        this.projectButton = projectButton;
        this.projects = projectButton.getProjects();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setPreferredSize(new Dimension(750, 600));

        pane = new JPanel();
        pane.setLayout(new BorderLayout(10, 10));

        // Example User data
        /*User tempUser1 = new User();
        tempUser1.setUUID(UUID.randomUUID());
        tempUser1.setUser("hi", "1234", UserType.DEVELOPER);
        User tempUser2 = new User();
        tempUser2.setUUID(UUID.randomUUID());
        tempUser2.setUser("hello", "1234", UserType.TESTER);
        addUserToList(tempUser1);
        addUserToList(tempUser2);
        */
        UserData userData = new UserData();
        User[] sd_users = userData.getAllUsers();
        for (User user : sd_users) {
            addUserToList(user);
        }

        // Project Name panel
        JPanel projectNamePanel = new JPanel();
        projectNamePanel.setLayout(new GridLayout(1, 2, 10, 10));
        JLabel projectName = new JLabel("Project Name");
        projectName.setHorizontalAlignment(SwingConstants.CENTER);
        projectNamePanel.add(projectName);
        projectNamePanel.add(projectNameField);

        // Project Member panel
        JPanel projectMemberPanel1 = new JPanel();
        JPanel projectMemberPanel2 = new JPanel();
        projectMemberPanel2_1 = new JPanel();
        JPanel projectMemberPanel2_2 = new JPanel();
        JPanel projectMemberPanel = new JPanel();
        userTypeComboBoxList.add(new JComboBox<>(UserType.values()));
        userComboBoxList.add(new JComboBox<>());

        JButton addUserButton = new JButton("Add");
        projectMemberPanel.setLayout(new GridLayout(1, 2, 10, 10));
        JPanel memberPanel = new JPanel(new GridLayout(2, 1));
        JLabel projectMemberLabel = new JLabel("Project Member");
        memberscrollpane = new JScrollPane();
        Button_add_Listener addListener = new Button_add_Listener();
        addUserButton.addActionListener(addListener);
        buttonX.add(addUserButton);
        buttonListeners.add(null);

        //Panel merging
        projectMemberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        projectMemberPanel1.add(projectMemberLabel);
        projectMemberPanel2.setLayout(new BorderLayout(10, 10));
        projectMemberPanel2_1.setLayout(new GridLayout(10, 1));
        projectMemberPanel2_2.setLayout(new GridLayout(1, 3, 10, 10));

        projectMemberPanel2_2.add(userTypeComboBoxList.get(0));
        projectMemberPanel2_2.add(userComboBoxList.get(0));
        projectMemberPanel2_2.add(addUserButton);

        projectMemberPanel2_1.add(projectMemberPanel2_2);
        panels.add(projectMemberPanel2_2);

        projectMemberPanel2.add(projectMemberPanel2_1, BorderLayout.CENTER);
        projectMemberPanel2.add(memberscrollpane, BorderLayout.EAST);
        memberscrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        projectMemberPanel.add(projectMemberPanel1);
        projectMemberPanel.add(projectMemberPanel2);

        memberPanel.add(projectMemberPanel);
        memberPanel.add(projectDescription);

        typeListeners.add(new Combobox_type_Listener());
        userTypeComboBoxList.get(0).addActionListener(typeListeners.get(0));
        userListeners.add(new Combobox_user_Listener());
        userComboBoxList.get(0).addActionListener(userListeners.get(0));
        panels.get(0).addMouseListener(mouseListener);

        // Button Panel
        JButton create = new JButton("Create");
        JButton cancel = new JButton("Cancel");
        // Create, Cancel button action definition
        Button_create_Listener createListener = new Button_create_Listener();
        create.addActionListener(createListener);
        Button_cancel_Listener cancelListener = new Button_cancel_Listener();
        cancel.addActionListener(cancelListener);

        JPanel btns = new JPanel();
        btns.setLayout(new GridLayout(1, 2));
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

    private void addUserToList(User user) {
        users.add(user.getUUID());
        switch (user.getType()) {
            case DEVELOPER:
                devUsers.add(user);
                break;
            case TESTER:
                testUsers.add(user);
                break;
            case MANAGER:
                manageUsers.add(user);
                break;
        }
    }

    class Combobox_type_Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JComboBox<UserType> comboBox = (JComboBox<UserType>) e.getSource();
            index = userTypeComboBoxList.indexOf(comboBox); // update current index of Combobox
            userComboBoxList.get(index).removeAllItems();
            selectedUserType = (UserType) userTypeComboBoxList.get(index).getSelectedItem();
            if (selectedUserType != null) {
                switch (selectedUserType) {
                    case DEVELOPER:
                        for (User u : devUsers) {
                            userComboBoxList.get(index).addItem(u.getUsername());
                        }
                        break;
                    case TESTER:
                        for (User u : testUsers) {
                            userComboBoxList.get(index).addItem(u.getUsername());
                        }
                        break;
                    case MANAGER:
                        for (User u : manageUsers) {
                            userComboBoxList.get(index).addItem(u.getUsername());
                        }
                        break;
                }
            }
        }
    }

    class Combobox_user_Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
            index = userComboBoxList.indexOf(comboBox); //update current index of combobox
            String selected = (String) userComboBoxList.get(index).getSelectedItem();
            if (selected == null) {
                return;
            }
            switch (selectedUserType) {
                case DEVELOPER:
                    for (User u : devUsers) {
                        if (selected.equals(u.getUsername())) {
                            selectedUser = u;
                            break;
                        }
                    }
                    break;
                case TESTER:
                    for (User u : testUsers) {
                        if (selected.equals(u.getUsername())) {
                            selectedUser = u;
                            break;
                        }
                    }
                    break;
                case MANAGER:
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

    //Mouse Action when mouse is pressed
    class ComboboxMouseListener implements MouseListener {
        public void mousePressed(MouseEvent e) {
            JComboBox<?> comboBox;
            JButton button;
            index = -1;
            if(e.getSource() instanceof JComboBox) {
                comboBox = (JComboBox<?>) e.getSource();
                if (userTypeComboBoxList.contains(comboBox)) {
                    index = userTypeComboBoxList.indexOf(comboBox);
                } else if (userComboBoxList.contains(comboBox)) {
                    index = userComboBoxList.indexOf(comboBox);
                }
            }
            else if(e.getSource() instanceof JButton && e.getButton() != 0) {
                button = (JButton) e.getSource();
                if (buttonX.contains(button)) {
                    index = buttonX.indexOf(button);
                }
            }

        }

        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }

    // Add button
    class Button_add_Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int size = panels.size();
            if (size >= 10) {
                projectMemberPanel2_1.setLayout(new GridLayout(size + 1, 1));
            }
            JPanel newPanel = new JPanel(new GridLayout(1, 3, 10, 10));
            panels.add(newPanel);

            JComboBox<UserType> newUserTypeComboBox = new JComboBox<>(UserType.values());
            userTypeComboBoxList.add(newUserTypeComboBox);
            typeListeners.add(new Combobox_type_Listener());
            userTypeComboBoxList.get(size).addActionListener(typeListeners.get(size));

            JComboBox<String> newUserComboBox = new JComboBox<>();
            userComboBoxList.add(newUserComboBox);
            userListeners.add(new Combobox_user_Listener());
            userComboBoxList.get(size).addActionListener(userListeners.get(size));

            JButton newButtonX = new JButton("X");
            buttonX.add(newButtonX);
            buttonListeners.add(new Button_x_Listener());
            buttonX.get(size).addActionListener(buttonListeners.get(size));

            panels.get(size).addMouseListener(mouseListener);

            newPanel.add(userTypeComboBoxList.get(size));
            newPanel.add(userComboBoxList.get(size));
            newPanel.add(buttonX.get(size));

            projectMemberPanel2_1.add(newPanel);
            memberscrollpane.setViewportView(projectMemberPanel2_1);

            pane.updateUI();
        }
    }
    //Delete panel
    class Button_x_Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton sourceButton = (JButton) e.getSource();
            index = buttonX.indexOf(sourceButton);

            if (index != -1) {
                projectMemberPanel2_1.remove(panels.get(index));
                panels.remove(index);
                userTypeComboBoxList.remove(index);
                userComboBoxList.remove(index);
                buttonX.remove(index);
                typeListeners.remove(index);
                userListeners.remove(index);
                buttonListeners.remove(index);
                if(panels.size()>10)
                    projectMemberPanel2_1.setLayout(new GridLayout(panels.size(), 1));
                else
                    projectMemberPanel2_1.setLayout(new GridLayout(10, 1));
                pane.updateUI();
            }
        }
    }

    class Button_create_Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(projectNameField.getText().isEmpty())
                JOptionPane.showMessageDialog(projectMemberPanel2_1, "Please enter the project name!");
            else {
                UserData userData = new UserData();
                List<UUID> userIds = new ArrayList<>();
                for (int i = 0; i < userComboBoxList.size()-1; i++) {
                    for (int j = i + 1; j < userComboBoxList.size(); j++) {
                        if (userComboBoxList.get(i).getSelectedItem() != null && userComboBoxList.get(i).getSelectedItem().equals(userComboBoxList.get(j).getSelectedItem())) {
                            JOptionPane.showMessageDialog(null, "There's the same user in members", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }
                for(int i=0; i<userComboBoxList.size(); i++) {
                    if(userComboBoxList.get(i).getSelectedItem()!=null) {
                        userIds.add(userData.getUser((String)userComboBoxList.get(i).getSelectedItem()).getUUID());
                    }
                }
                Project project = new Project(projectNameField.getText(), projectDescription.getText(), userIds);
                //projects.add(project);
                ProjectData projectData = new ProjectData();
                projectData.addProject(project);

                JOptionPane.showMessageDialog(null, "Project Created", "Success", JOptionPane.PLAIN_MESSAGE);
                dispose();
            }
        }
    }

    class Button_cancel_Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

}
