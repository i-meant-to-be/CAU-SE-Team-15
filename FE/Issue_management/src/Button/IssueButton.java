package Button;

import Data.*;
import Layout.MainFrame;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class IssueButton {

    public IssueButton(MainFrame MF, ArrayList<Project> projects) {
        User user = MF.get_user();
        if(user.getType().equals(UserType.TESTER)) {
            if (projects.isEmpty()) { //프로젝트 리스트가 비어 있는지 확인
            JOptionPane.showMessageDialog(null, "No projects available. Please create a project first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
            }

            String[] projectNames = projects.stream().map(Project::getName).toArray(String[]::new);
            String selectedProjectName = (String) JOptionPane.showInputDialog(null, "Select a project to add the issue:", "Select Project",
                    JOptionPane.PLAIN_MESSAGE, null, projectNames, projectNames[0]); //프로젝트 선택 창 표시

            if (selectedProjectName != null) { //사용자가 프로젝트 클릭한 경우
                for (Project project : projects) {
                    if (project.getName().equals(selectedProjectName)) {
                        addIssueToProject(project, MF.get_user());
                        break;
                    }
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Only TESTER can create a new issue","Access Denied", JOptionPane.WARNING_MESSAGE);
        }

    }

    public void addIssueToProject(Project project, User user) {
        while (true) {
            String issueTitle = JOptionPane.showInputDialog(null, "Enter issue title:"); //이슈 제목 입력
            if (issueTitle != null && !issueTitle.trim().isEmpty()) {
                // Issue 유형 선택을 위한 JComboBox
                JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"BLOCKER", "CRITICAL", "MAJOR", "MINOR", "TRIVIAL"});
                int result = JOptionPane.showConfirmDialog(null, typeComboBox, "Select issue type", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    // 선택한 유형 가져오기
                    String selectedType = (String) typeComboBox.getSelectedItem();

                    // 선택한 유형에 따라서 IssueType 설정
                    IssueType type;
                    switch (selectedType) {
                        case "BLOCKER":
                            type = IssueType.BLOCKER;
                            break;
                        case "CRITICAL":
                            type = IssueType.CRITICAL;
                            break;
                        case "MAJOR":
                            type = IssueType.MAJOR;
                            break;
                        case "MINOR":
                            type = IssueType.MINOR;
                            break;
                        case "TRIVIAL":
                            type = IssueType.TRIVIAL;
                            break;
                        default:
                            type = IssueType.MINOR; // 기본값은 MINOR로 설정
                            break;
                    }

                    // Issue 생성 및 추가
                    Issue newIssue = new Issue(issueTitle, user.getUsername(), LocalDateTime.now().withNano(0), "Description", null, type, IssueState.NEW, new String[0]);
                    project.addIssue(newIssue);
                    JOptionPane.showMessageDialog(null, "Issue created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    break; //제목을 제대로 입력하면 반복 중단
                } else {
                    // 사용자가 취소한 경우
                    break;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Issue title cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }//이슈 제목을 제대로 입력하도록 반복
        }
    }

}
