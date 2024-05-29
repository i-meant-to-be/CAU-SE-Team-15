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

public class CommentData {
    private Comment[] sd_comments ;
    private int commentCnt;

    public void getIssueComment(UUID issueId){
        try {
            URL url = new URL("http://localhost:8080/api/issue/"+issueId+"/comment");
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

            commentCnt = jsonArray.length();
            //System.out.println(userCnt);
            sd_comments = new Comment[commentCnt];

            for (int i = 0; i < commentCnt; i++) {// 여기부터
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                UUID id = UUID.fromString(jsonObject.getString("id"));
                String text = jsonObject.getString("body");
                UUID authorId = UUID.fromString(jsonObject.getString("authorId"));
                String createdDate = jsonObject.getString("Date");
                LocalDateTime created = LocalDateTime.parse(createdDate);
                sd_comments[i] = new Comment(id,text,created,authorId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addComment(UUID issueId, Comment comment){
        try {
            URL url = new URL("http://localhost:8080/api/issue/"+issueId+"/comment");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("POST"); // http 메서드
            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보
            conn.setDoInput(true);
            conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("body", comment.getText());
            jsonObject.put("authorId", comment.getAuthorId());

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
    public void deleteComment(UUID issueId, Comment comment){
        try {
            URL url = new URL("http://localhost:8080/api/issue/"+issueId+"/comment");
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
}
