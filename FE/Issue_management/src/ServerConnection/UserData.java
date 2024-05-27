package ServerConnection;

import Data.User;
import Data.UserType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class UserData {
    private User[] sd_user;
    private int userCnt;

    public User getUser(String username) {
        this.getAllUser();
        for (User user : sd_user) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(User user) {
        try {
            URL url = new URL("http://localhost:8080/api/user");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("POST"); // http 메서드
            conn.setRequestProperty("Content-Type", "application/json"); // header Content-Type 정보
            conn.setDoInput(true);
            conn.setDoOutput(true); // 서버로부터 받는 값이 있다면 true

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", user.getUsername());
            jsonObject.put("password", user.getPassword());
            jsonObject.put("type", user.getType().toString());

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

    public int getUserNum(){
        this.getAllUser();
        return userCnt;
    }

    public User[] getAllUsers(){
        this.getAllUser();
        return sd_user;
    }

    public void getAllUser() {
        try {
            URL url = new URL("http://localhost:8080/api/user");
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

            userCnt = jsonArray.length();
            //System.out.println(userCnt);
            sd_user = new User[userCnt];

            for (int i = 0; i < userCnt; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                UUID id = UUID.fromString(jsonObject.getString("id"));
                String name = jsonObject.getString("name");
                String password = jsonObject.getString("password");
                UserType type = UserType.valueOf(jsonObject.getString("type"));
                sd_user[i] = new User(name, password, type);
                sd_user[i].setUUID(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
