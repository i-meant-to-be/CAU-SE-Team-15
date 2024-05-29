package ServerConnection;

import Data.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

public class IssueData {
    private Issue [] sd_issue;
    private int issueCnt;

    public Issue[] getAllIssues(UUID projectId){
        return sd_issue;
    }

    public int getIssueCnt(){
        return issueCnt;
    }

    public void addIssue(UUID projectId, Issue issue){
        try {
            URL url = new URL("http://localhost:8080/api/project/"+projectId+"/issue");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("POST"); // http 메서드
            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보
            conn.setDoInput(true);
            conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", issue.getTitle());
            jsonObject.put("description", issue.getDescription());
            jsonObject.put("type", issue.getType().toString());
            jsonObject.put("reporterId", issue.getReporterId());

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

    public void modifyIssue(Project project, Issue issue){

    }

    public void getAllIssue(){
        try {
            URL url = new URL("http://localhost:8080/api/issue");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("GET"); // http 메서드
            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보
            conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

            // 서버로부터 데이터 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = br.readLine()) != null) { // 읽을 수 있을 때 까지 반복
                sb.append(line);
            }

            //String response = sb.toString();
            //System.out.println("Response from server: " + response);

            JSONArray jsonArray = new JSONArray(sb.toString()); // json으로 변경 (역직렬화)

            issueCnt = jsonArray.length();
            //System.out.println(userCnt);
            sd_issue = new Issue[issueCnt];

            for (int i = 0; i < issueCnt; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                UUID id = UUID.fromString(jsonObject.getString("id"));
                String title = jsonObject.getString("title");
                String description = jsonObject.getString("discription");
                IssueType type = IssueType.valueOf(jsonObject.getString("type"));
                IssueState state = IssueState.valueOf(jsonObject.getString("state"));
                String reportedDate = jsonObject.getString("reportedDate");
                LocalDateTime reported = LocalDateTime.parse(reportedDate);

                JSONArray commentArray = jsonObject.getJSONArray("commentIds");
                UUID[] commentIds = new UUID[commentArray.length()];
                String[] comments = new String[commentArray.length()];
                for(int j = 0; j < commentArray.length(); j++) {
                    commentIds[j] = UUID.fromString(commentArray.getString(j));
                    //commentId로 comment string 찾아서 저장하기
                }

                UUID reporterId = UUID.fromString(jsonObject.getString("reporterId"));
                UUID fixerId = UUID.fromString(jsonObject.getString("fixerId"));
                UUID assigneeId = UUID.fromString(jsonObject.getString("assigneeId"));

                JSONArray tagArray = jsonObject.getJSONArray("tag");
                String [] tags = new String[tagArray.length()];
                for(int j = 0; j < tagArray.length(); j++) {
                    tags[j] = tagArray.getString(j);
                }

                //sd_issue[i] = new Issue(title, reporterId, reported, description, assigneeId, type, state, comments);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
