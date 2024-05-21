import javax.swing.*;

public class MainFrame extends JFrame{
    boolean loggedIn = false;
    private String ID = "admin";
    private String PW = "1234";

    MainFrame(){
        super("Main page");
        setSize(800, 600);
        JPanel pane = new JPanel();
        add(pane);
        this.setLocationRelativeTo(null);
        setVisible(true);
        if(!loggedIn){
            Log_in login = new Log_in(this);
        }
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
    }

    public String getID(){
        return ID;
    }
    public String getPW(){
        return PW;
    }
}