package Button;

import Data.*;
import ServerConnection.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreandButton {
    //프로젝트 당 이슈 개수(이슈 상태로 분류)
    //날짜별 이슈, 코멘트 개수
    public TreandButton(){
        ProjectData pd = new ProjectData();
        Project[] projects = pd.getAllProjects();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Trend");
        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);



    }
}
