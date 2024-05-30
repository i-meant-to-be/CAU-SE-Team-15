package Button;

import Data.Issue;
import Data.Project;
import ServerConnection.IssueData;
import ServerConnection.ProjectData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

public class TreandButton extends JFrame {
    //프로젝트 당 이슈 개수(이슈 상태로 분류)
    //날짜별 이슈, 코멘트 개수
    private ProjectData pd;
    private Project[] projects;
    private IssueData issueData;
    private Issue[] issues;

    public TreandButton(){
        pd = new ProjectData();
        projects = pd.getAllProjects();
        issueData = new IssueData();
        issues = issueData.getAllIssues();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Trend");
        setSize( 600,500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton projIssue = new JButton("project");
        JButton dateIssue = new JButton("issue");
        JButton dateComment = new JButton("comment");

        Dimension buttonSize = new Dimension(120, 30);
        projIssue.setPreferredSize(buttonSize);
        dateIssue.setPreferredSize(buttonSize);
        dateComment.setPreferredSize(buttonSize);

        panel.add(projIssue);
        panel.add(dateIssue);
        panel.add(dateComment);

        add(panel, BorderLayout.NORTH);

        setVisible(true);

        projIssue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showProjIssue();

            }
        });
        dateIssue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDateIssue();
            }
        });
        dateComment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDateComment();
            }
        });

    }
    private void showProjIssue(){
        JPanel projectPanel = new JPanel();
        int[] issueNum = new int[projects.length];
        for(int i = 0; i < projects.length; i++){
            IssueData issueData = new IssueData();
            issueNum[i] = issueData.getIssueNum(projects[i].getId());
        }

        projectPanel.setPreferredSize(new Dimension(600, 400));
        projectPanel.setLayout(new GridLayout(projects.length,2));


        for(int i = 0; i < projects.length; i++){
            JLabel nameLabel = new JLabel("      [ " + projects[i].getName()+ " ]");
            String issueNumStr = " ";
            for(int j = 0; j < issueNum[i]; j++){
                issueNumStr = issueNumStr + "@ ";
            }
            issueNumStr = issueNumStr + " ( " + issueNum[i]+ " ) ";
            JLabel issueLabel = new JLabel(issueNumStr);

            projectPanel.add(nameLabel);
            projectPanel.add(issueLabel);
        }
        add(projectPanel,BorderLayout.CENTER);
        setVisible(true);
    }
    private void showDateIssue(){


        Map<LocalDateTime, Integer> issueCountByDate = new TreeMap<>(); // TreeMap 사용하여 정렬
        for (Issue issue : issues) {
            if(issue!=null) {
                LocalDateTime date = issue.getReportedDate().toLocalDate().atStartOfDay(); // 시간을 무시하여 날짜만 사용
                issueCountByDate.put(date, issueCountByDate.getOrDefault(date, 0) + 1);
            }
        }

        JPanel IssuePanel = new JPanel();
        IssuePanel.setPreferredSize(new Dimension(600, 400));
        IssuePanel.setLayout(new GridLayout(issueCountByDate.size(),2));

        for (Map.Entry<LocalDateTime, Integer> entry : issueCountByDate.entrySet()) {

            LocalDateTime date = entry.getKey();
            JLabel nameLabel = new JLabel("      [ " + date.toLocalDate().toString()+ " ]");
            int issueCount = entry.getValue();
            String issueNumStr = " ";
            for(int j = 0; j < issueCount; j++){
                issueNumStr = issueNumStr + "@ ";
            }
            issueNumStr = issueNumStr + " ( " + issueCount+ " ) ";
            JLabel issueLabel = new JLabel(issueNumStr);

            IssuePanel.add(nameLabel);
            IssuePanel.add(issueLabel);
            //System.out.println(date + " : " + issueCount + "개의 이슈");
        }
        add(IssuePanel,BorderLayout.CENTER);
        setVisible(true);


    }
    private void showDateComment(){

    }
}
