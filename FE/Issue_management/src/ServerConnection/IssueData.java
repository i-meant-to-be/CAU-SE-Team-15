package ServerConnection;

import Data.Issue;
import Data.IssueState;
import Data.IssueType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

public class IssueData {
    private Issue [] sd_issue;
    private int issueCnt;

    public Issue[] getAllIssues(){
        this.getAllIssue();
        return sd_issue;
    }

    public int getIssueNum(UUID projectId){
        this.getAllIssue(projectId);
        return issueCnt;
    }

    public Issue[] getAllIssues(UUID projectId){
        this.getAllIssue(projectId);
        return sd_issue;
    }

    public int getIssueCnt(){
        return issueCnt;
    }

    public void modifyIssueState(Issue issue){
        try {
            URI uri = new URI("http://localhost:8080/api/issue/"+issue.getId().toString());

            // 서버에 데이터 전달
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("state", issue.getState().toString());
            //issue fixer 데이터 추가하기

            HttpClient client = HttpClient.newHttpClient();

            // PATCH 요청 생성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .method("PATCH", BodyPublishers.ofString(jsonObject.toString()))
                    .build();

            // 요청 보내고 응답 받기
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void modifyIssue(Issue issue){
        try {
            URI uri = new URI("http://localhost:8080/api/issue/"+issue.getId().toString());

            // 서버에 데이터 전달
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", issue.getTitle());
            if(issue.getDescription() != null)
                jsonObject.put("description", issue.getDescription());
            jsonObject.put("type", issue.getType().toString());
            jsonObject.put("reporterId", issue.getReporterId().toString());
            if(issue.getAssigneeId()!=null)
                jsonObject.put("assigneeId", issue.getAssigneeId().toString());
            jsonObject.put("state", issue.getState().toString());
            //issue fixer 데이터 추가하기

            HttpClient client = HttpClient.newHttpClient();

            // PATCH 요청 생성
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .method("PATCH", BodyPublishers.ofString(jsonObject.toString()))
                    .build();

            // 요청 보내고 응답 받기
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(response.statusCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteIssue(Issue issue){
        try {
            URL url = new URL("http://localhost:8080/api/project/"+issue.getProjectId().toString()+"/issues/"+issue.getId());
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

    public void addIssue(UUID projectId, Issue issue){
        try {
            URL url = new URL("http://localhost:8080/api/project/"+projectId.toString()+"/issue");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("POST"); // http 메서드
            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보
            conn.setDoInput(true);
            conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", issue.getTitle());
            jsonObject.put("description", issue.getDescription());
            jsonObject.put("type", issue.getType().toString());
            jsonObject.put("reporterId", issue.getReporterId().toString());

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

    public Issue getIssue(UUID issueId){
        Issue issue;
        try {
            URL url = new URL("http://localhost:8080/api/issue/"+issueId.toString());
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
                String title = jsonObject.getString("title");
                String description = jsonObject.getString("description");
                IssueType type = IssueType.valueOf(jsonObject.getString("type"));
                IssueState state = IssueState.valueOf(jsonObject.getString("state"));
                String reportedDate = jsonObject.getString("reportedDate");
                LocalDateTime reported = LocalDateTime.parse(reportedDate);

                UUID reporterId = UUID.fromString(jsonObject.getString("reporterId"));
                //UUID fixerId = UUID.fromString(jsonObject.getString("fixerId"));
                //UUID assigneeId = UUID.fromString(jsonObject.getString("assigneeId"));

                issue = new Issue(title, reporterId, reported, description, type, state);
                issue.setId(id);

                JSONArray commentArray = jsonObject.getJSONArray("commentIds");
                if (commentArray != null) {
                    UUID[] commentIds = new UUID[commentArray.length()];
                    for (int j = 0; j < commentArray.length(); j++) {
                        commentIds[j] = UUID.fromString(commentArray.getString(j));
                        CommentData commentData = new CommentData();
                        issue.addComment(commentData.getComment(commentIds[j]));
                    }
                }
                JSONArray tagArray = jsonObject.getJSONArray("tags");
                if (tagArray != null) {
                    String[] tags = new String[tagArray.length()];
                    for (int j = 0; j < tagArray.length(); j++) {
                        tags[j] = tagArray.getString(j);

                    }
                }
                return issue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getAllIssue(UUID projectId){
        try {
            URL url = new URL("http://localhost:8080/api/project/"+projectId.toString()+"/issue");
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

                issueCnt = jsonArray.length();
                sd_issue = new Issue[issueCnt];

                for (int i = 0; i < issueCnt; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    UUID id = UUID.fromString(jsonObject.getString("id"));
                    String title = jsonObject.getString("title");
                    String description = jsonObject.getString("description");
                    IssueType type = IssueType.valueOf(jsonObject.getString("type"));
                    IssueState state = IssueState.valueOf(jsonObject.getString("state"));
                    String reportedDate = jsonObject.getString("reportedDate");
                    LocalDateTime reported = LocalDateTime.parse(reportedDate);


                    UUID reporterId = UUID.fromString(jsonObject.getString("reporterId"));
                    //UUID fixerId = UUID.fromString(jsonObject.getString("fixerId"));
                    //UUID assigneeId = UUID.fromString(jsonObject.getString("assigneeId"));

                    sd_issue[i] = new Issue(title, reporterId, reported, description, type, state);
                    sd_issue[i].setId(id);
                    //issue 에 assignee와 fixer 도 추가해야됨

                    JSONArray commentArray = jsonObject.getJSONArray("commentIds");
                    UUID[] commentIds = new UUID[commentArray.length()];
                    for (int j = 0; j < commentArray.length(); j++) {
                        commentIds[j] = UUID.fromString(commentArray.getString(j));
                        CommentData commentData = new CommentData();
                        sd_issue[i].addComment(commentData.getComment(commentIds[j]));
                    }

                    JSONArray tagArray = jsonObject.getJSONArray("tags");
                    String[] tags = new String[tagArray.length()];
                    for (int j = 0; j < tagArray.length(); j++) {
                        tags[j] = tagArray.getString(j);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllIssue(){
        try {
            URL url = new URL("http://localhost:8080/api/issue");
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

                issueCnt = jsonArray.length();
                sd_issue = new Issue[issueCnt];

                for (int i = 0; i < issueCnt; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    UUID id = UUID.fromString(jsonObject.getString("id"));
                    String title = jsonObject.getString("title");
                    String description = jsonObject.getString("description");
                    IssueType type = IssueType.valueOf(jsonObject.getString("type"));
                    IssueState state = IssueState.valueOf(jsonObject.getString("state"));
                    String reportedDate = jsonObject.getString("reportedDate");
                    LocalDateTime reported = LocalDateTime.parse(reportedDate);

                    UUID reporterId = UUID.fromString(jsonObject.getString("reporterId"));
                    UUID fixerId;
                    try {
                        fixerId = UUID.fromString(jsonObject.getString("fixerId"));
                    } catch (JSONException e) {
                        fixerId = null;
                    }

                    UUID assigneeId;
                    try {
                        assigneeId = UUID.fromString(jsonObject.getString("assigneeId"));
                    } catch (JSONException e) {
                        assigneeId = null;
                    }

                    sd_issue[i] = new Issue(title, reporterId, reported, description, assigneeId, type, state);
                    sd_issue[i].setId(id);

                    JSONArray commentArray = jsonObject.getJSONArray("commentIds");
                    UUID[] commentIds = new UUID[commentArray.length()];
                    for (int j = 0; j < commentArray.length(); j++) {
                        commentIds[j] = UUID.fromString(commentArray.getString(j));
                        CommentData commentData = new CommentData();
                        sd_issue[i].addComment(commentData.getComment(commentIds[j]));
                    }

                    JSONArray tagArray = jsonObject.getJSONArray("tags");
                    String[] tags = new String[tagArray.length()];
                    for (int j = 0; j < tagArray.length(); j++) {
                        tags[j] = tagArray.getString(j);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
