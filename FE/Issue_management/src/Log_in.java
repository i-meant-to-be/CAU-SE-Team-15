import Data.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Log_in extends JFrame {

    private JTextField input_ID = new JTextField(20);
    private JPasswordField input_PW = new JPasswordField(20);
    MainFrame MF;
    private User user;

    Log_in(MainFrame MF){
        super("Log in");
        this.MF = MF;
        user = MF.get_user();

        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout(50, 20));
        setSize(400, 200);

        JPanel labels = new JPanel();
        labels.setLayout(new GridLayout(2, 1, 20,20));
        labels.add(new JLabel("ID"));
        labels.add(new JLabel("PW"));

        input_PW.setEchoChar('*');
        JPanel areas = new JPanel();
        areas.setLayout(new GridLayout(2, 1, 20,20));
        areas.add(input_ID);
        areas.add(input_PW);

        JPanel log_info = new JPanel();
        log_info.setLayout(new BorderLayout(10, 10));
        log_info.add(labels, BorderLayout.WEST);
        log_info.add(areas, BorderLayout.CENTER);

        JButton Ok = new JButton("Log in");
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
    //어드민 로그인 + 유저타입 어드민으로 설정
    class Button_login_Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String pw = new String(input_PW.getPassword());
            if(input_ID.getText().equals(MF.getID()) && pw.equals(MF.getPW())){
                user.setUser(input_ID.getText(), pw, UserType.Admin);
                MF.loggedIn = true;
                dispose();
            }
        }
    }

    //서버를 통해 아이디 비밀번호 확인 후 로그인 + 유저정보 설정

}
