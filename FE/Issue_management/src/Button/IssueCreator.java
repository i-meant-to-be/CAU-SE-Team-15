package Button;

import Data.*;
import javax.swing.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class IssueCreator {

    public void addIssueToProject(Project project, User user) {
        while(true){
            String issueTitle = JOptionPane.showInputDialog(null, "Enter issue title:"); //이슈 제목 입력
            if (issueTitle != null && !issueTitle.trim().isEmpty()) {
                Issue newIssue = new Issue(issueTitle, user.getUsername(), LocalDateTime.now(), "Description", null, IssueType.MINOR, IssueState.NEW, new UUID[0]);
                project.addIssue(newIssue);
                JOptionPane.showMessageDialog(null, "Issue created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                break; //제목을 제대로 입력하면 반복 중단
            } else {
                JOptionPane.showMessageDialog(null, "Issue title cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }//이슈 제목을 제대로 입력하도록 반복
        }
    }

}
