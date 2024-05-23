import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame{
    boolean loggedIn = false;
    private String ADMIN_ID = "admin";
    private String ADMIN_PW = "1234";
    private ClassDef classDef = new ClassDef();
    ClassDef.User user = classDef.new User();

    private Log_in login;



    MainFrame(){
        super("Main page");
        setSize(800, 600);
        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout(10,10));

        JPanel user_func = new JPanel();
        user_func.setLayout(new GridLayout(5, 1));

        JPanel user_info = new JPanel();
        user_info.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        user_info.setLayout(new GridLayout(3,1));
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

        JButton create_project = new JButton("Create Project");
        JButton create_issue = new JButton("Create Issue");
        JButton browse = new JButton("Browse");
        JButton search = new JButton("Search");

        user_func.add(user_info);
        user_func.add(create_project);
        user_func.add(create_issue);
        user_func.add(browse);
        user_func.add(search);

        JPanel issue_panel = new JPanel();
        pane.add(user_func, BorderLayout.EAST);
        pane.add(issue_panel, BorderLayout.CENTER);

        add(pane);
        this.setLocationRelativeTo(null);
        setVisible(true);
        if(!loggedIn){
            login = new Log_in(this);
            login.addWindowListener(new WindowAdapter() {
              public void windowClosed(WindowEvent e) {
                  if(loggedIn) {
                      user_name.setText(user.getUsername());
                      repaint();
                  }
              }
            });
        }

        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
    }

    public String getID(){
        return ADMIN_ID;
    }
    public String getPW(){
        return ADMIN_PW;
    }

}