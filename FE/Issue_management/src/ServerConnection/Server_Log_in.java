package ServerConnection;

import Data.User;

public class Server_Log_in {

    public boolean login_success(String id, String password){
        UserData loginData = new UserData();
        int num = loginData.getUserNum();
        User[] users = loginData.getAllUsers();

        for(int i=0; i<num; i++){
            if(id.equals(users[i].getUsername()) && password.equals(users[i].getPassword()))
                return true;
        }
        return false;
    }
}
