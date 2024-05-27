package Button;

import Data.User;
import Data.UserType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RegisterButton extends JFrame {
    private JTextField input_ID = new JTextField(20);
    private JTextField input_PW = new JPasswordField(20);
    private JComboBox<UserType> position;
    private UserType selected_position;
    private User user;

    public RegisterButton(){
        super("Register");

        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        setSize(400, 200);

        JPanel labels = new JPanel();
        labels.setLayout(new GridLayout(3, 1));
        ArrayList<JLabel> labelarray = new ArrayList<>();
        labelarray.add(new JLabel("ID"));
        labelarray.add(new JLabel("PW"));
        labelarray.add(new JLabel("Position"));
        for(JLabel Labels : labelarray){
            Labels.setHorizontalAlignment(SwingConstants.CENTER);
            labels.add(Labels);
        }

        UserType[] usertypes = UserType.values();
        position = new JComboBox<>(usertypes);
        //Position combobox action definition
        ComboBox_position_Listener position_listener = new ComboBox_position_Listener();
        position.addActionListener(position_listener);

        JPanel areas = new JPanel();
        areas.setLayout(new GridLayout(3, 1));
        areas.add(input_ID);
        areas.add(input_PW);
        areas.add(position);

        JPanel reg_info = new JPanel();
        reg_info.setLayout(new BorderLayout(10, 10));
        reg_info.add(labels, BorderLayout.WEST);
        reg_info.add(areas, BorderLayout.CENTER);

        JButton register = new JButton("Register");
        JButton Cancel = new JButton("Cancel");
        //Register, Cancel button action definition
        Button_register_Listener registerListener = new Button_register_Listener();
        register.addActionListener(registerListener);
        Button_cancel_Listener cancelListener = new Button_cancel_Listener();
        Cancel.addActionListener(cancelListener);

        JPanel btns = new JPanel();
        btns.setLayout(new GridLayout(1,2));
        btns.add(register);
        btns.add(Cancel);

        pane.add(reg_info);
        pane.add(btns);

        setLayout(new GridLayout(1,1));
        add(pane);
        setLocationRelativeTo(null);
        setVisible(true);

    }
    //Register User
    class Button_register_Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //check ID duplicate

            //if there's no such id information then Register User

            //Warning part
            if(input_ID.getText().isEmpty() || input_PW.getText().isEmpty()||selected_position == null){
                JOptionPane.showMessageDialog(RegisterButton.this,
                        "fill every input fields",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
            //Registering to an Admin is blocked
            else if(selected_position == UserType.ADMIN){
                JOptionPane.showMessageDialog(RegisterButton.this,
                        "You can't register to an Admin",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
            else {
                //Register this user to server
                user = new User(input_ID.getText(), input_PW.getText(), selected_position);
            }
        }
    }

    class ComboBox_position_Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            selected_position = (UserType) position.getSelectedItem();
        }
    }
    class Button_cancel_Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

}
