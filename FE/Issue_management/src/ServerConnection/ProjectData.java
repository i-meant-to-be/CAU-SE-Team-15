package ServerConnection;

import Data.Project;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Member;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProjectData {
    private Project[] sd_project;
    private int projectCnt = 0;
    private List<UUID> userList = new ArrayList<>();

    public Project[] getAllProjects(){
        this.getAllProject();
        return sd_project;
    }

    public int getProjectNum(){
        this.getAllProject();
        return projectCnt;
    }

    public Project getProject(UUID projectId){
        Project project;
        try {
            URL url = new URL("http://localhost:8080/api/project/"+projectId.toString());
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("GET"); // http 메서드
            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보
            conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 서버로부터 데이터 읽어오기
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
                    sb.append(line);
                }
                JSONObject jsonObject = new JSONObject(sb.toString()); // json으로 변경 (역직렬화)

                UUID id = UUID.fromString(jsonObject.getString("id"));

                JSONArray userIdArray = jsonObject.getJSONArray("userIds");
                for (int j = 0; j < userIdArray.length(); j++) {
                    userList.add(UUID.fromString(userIdArray.getString(j)));
                }

                String title = jsonObject.getString("title");
                String description = jsonObject.getString("description");
                String createdTime = jsonObject.getString("createdDate");
                LocalDateTime created = LocalDateTime.parse(createdTime);

                project = new Project(title, description, userList);
                project.setCreationDate(created);
                project.setId(id);

                JSONArray issueIdArray = jsonObject.getJSONArray("issueIds");
                for (int j = 0; j < issueIdArray.length(); j++) {
                    UUID issueId = UUID.fromString(issueIdArray.getString(j));
                    IssueData issueData = new IssueData();
                    project.addIssue(issueData.getIssue(issueId));
                }

                return project;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteProject(Project project){
        try {
            URL url = new URL("http://localhost:8080/api/project"+project.getId());
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("DELETE"); // http 메서드
            conn.setDoInput(true); // 서버에 전달할 값이 있다면 true

            // 서버로부터 데이터 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
                sb.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProject(Project project){
        try {
            URL url = new URL("http://localhost:8080/api/project");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("POST"); // http 메서드
            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보
            conn.setDoInput(true);
            conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", project.getName());
            jsonObject.put("description", project.getDescription());

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            bw.write(jsonObject.toString()); // 버퍼에 담기
            bw.flush(); // 버퍼에 담긴 데이터 전달
            bw.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
                sb.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProjectMember(UUID projectId, UUID memberId){
        try {
            URL url = new URL("http://localhost:8080/api/project/"+projectId.toString()+"/members/"+memberId.toString());
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("POST"); // http 메서드
            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보
            conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
                sb.append(line);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllProject() {
        try {
            URL url = new URL("http://localhost:8080/api/project");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("GET"); // http 메서드
            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보
            conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 서버로부터 데이터 읽어오기
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
                    sb.append(line);
                }

                JSONArray jsonArray = new JSONArray(sb.toString()); // json으로 변경 (역직렬화)

                projectCnt = jsonArray.length();
                sd_project = new Project[projectCnt];

                for (int i = 0; i < projectCnt; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    UUID id = UUID.fromString(jsonObject.getString("id"));

                    JSONArray userIdArray = jsonObject.getJSONArray("userIds");
                    for (int j = 0; j < userIdArray.length(); j++) {
                        userList.add(UUID.fromString(userIdArray.getString(j)));
                    }

                    String title = jsonObject.getString("title");
                    String description = jsonObject.getString("description");
                    String createdTime = jsonObject.getString("createdDate");
                    LocalDateTime created = LocalDateTime.parse(createdTime);
                    sd_project[i] = new Project(title, description, userList);
                    sd_project[i].setCreationDate(created);
                    sd_project[i].setId(id);

                    JSONArray issueIdArray = jsonObject.getJSONArray("issueIds");
                    for (int j = 0; j < issueIdArray.length(); j++) {
                        UUID issueId = UUID.fromString(issueIdArray.getString(j));
                        IssueData issueData = new IssueData();
                        sd_project[i].addIssue(issueData.getIssue(issueId));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
