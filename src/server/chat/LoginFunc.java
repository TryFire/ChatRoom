package server.chat;

import server.SConstants;
import server.User;
import server.db.SQLHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yuriko on 2017/5/6.
 */
public class LoginFunc {

    public User login(String userId, String password) {
        boolean isContain = Server.getServer(SConstants.SERVER_PORT).isUserOnline(userId);
        if (isContain) {
            return null;
        }
        String sql = "select * from users where userId = '" + userId + "'";
        ResultSet resultSet = SQLHelper.getInstance().excuteSQL(sql);
        if (resultSet!=null) {
            try {
                if (resultSet.next()) {
                    String userPassword = resultSet.getString("password");
                    String username = resultSet.getString("username");
                    //System.out.println(userPassword);
                    if (password.equals(userPassword)) {
                        System.out.println("login success");
                        return new User(userId, username, true);
                    } else {
                        System.out.println("failed login, password is wrong");
                        return null;
                    }
                } else {
                    System.out.println("failed login, userId isnot exist");
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
