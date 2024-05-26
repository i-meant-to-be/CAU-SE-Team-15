package Button;

import Data.UserType;
import Layout.Log_in;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterButton extends JFrame {
    private JTextField input_ID = new JTextField(20);
    private JPasswordField input_PW = new JPasswordField(20);

    public RegisterButton(){
        super("Register");

        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout(50, 20));
        setSize(400, 200);

        JPanel labels = new JPanel();
        labels.setLayout(new GridLayout(2, 1, 20,20));
        labels.add(new JLabel("ID"));
        labels.add(new JLabel("PW"));

        JPanel areas = new JPanel();
        areas.setLayout(new GridLayout(2, 1, 20,20));
        areas.add(input_ID);
        areas.add(input_PW);

        JPanel log_info = new JPanel();
        log_info.setLayout(new BorderLayout(10, 10));
        log_info.add(labels, BorderLayout.WEST);
        log_info.add(areas, BorderLayout.CENTER);

        JButton Ok = new JButton("Login");
        Button_login_Listener loginListener = new Button_login_Listener();
        Ok.addActionListener(loginListener);
        input_ID.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Ok.doClick();
            }
        });
        input_PW.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Ok.doClick();
            }
        });

        JPanel btn = new JPanel();
        btn.setLayout(new GridLayout(1,1));
        btn.add(Ok);

        pane.add(log_info, BorderLayout.CENTER);
        pane.add(btn, BorderLayout.EAST);

        setLayout(new GridLayout(3,1));
        add(new JPanel());
        add(pane);
        add(new JPanel());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    class Button_login_Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

        }
    }

}
