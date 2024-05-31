package Layout;

import Data.Issue;
import Data.Project;
import Data.User;
import Data.UserType;
import Button.ProjectButton;
import ServerConnection.ProjectData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

public class ProjectDetailFrame extends JFrame {
    private Project project;

    public ProjectDetailFrame(MainFrame MF, ProjectButton pb, Project project) {
        super("Project view");
        setSize(500, 500);

        this.project = project;
        setPreferredSize(new Dimension(600, 600));
        JPanel pane = new JPanel();

        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        JPanel name = new JPanel();
        name.setLayout(new BoxLayout(name, BoxLayout.X_AXIS));
        name.add(new JLabel("Project Name:"));
        name.add(new JLabel(project.getName()));

        JPanel ID = new JPanel();
        ID.setLayout(new BoxLayout(ID, BoxLayout.X_AXIS));
        ID.add(new JLabel("Project ID:"));
        ID.add(new JLabel(project.getId().toString()));

        JPanel description = new JPanel();
        description.setLayout(new BorderLayout(10, 10));
        JLabel descriptionLabel = new JLabel("Description:");
        description.add(descriptionLabel, BorderLayout.NORTH);
        descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
        JTextArea descriptionText = new JTextArea(project.getDescription());
        descriptionText.setEditable(false);
        description.add(descriptionText, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

        JButton OK = new JButton("OK");
        OK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pb.setProject(project);
                JOptionPane.showMessageDialog(ProjectDetailFrame.this, "Project selected.");
                dispose();
            }
        });
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JButton delete = new JButton("Remove");
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "do you want to delete this project?", "Warning", JOptionPane.WARNING_MESSAGE);
                if(response == JOptionPane.YES_OPTION) {
                    //프로젝트 삭제 코드
                    ProjectData projectData = new ProjectData();
                    projectData.deleteProject(project);
                    dispose();
                }
            }
        });
        buttons.add(new JPanel());
        buttons.add(OK);
        buttons.add(new JPanel());
        buttons.add(delete);
        buttons.add(new JPanel());
        buttons.add(cancel);
        buttons.add(new JPanel());

        pane.add(name);
        pane.add(ID);
        pane.add(description);
        pane.add(buttons);

        add(pane);
        setVisible(true);
        setLocationRelativeTo(null);
    }

}
