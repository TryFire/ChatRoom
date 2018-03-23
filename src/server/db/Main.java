package server.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yuriko on 2017/5/6.
 */
public class Main {
    public static void main(String[] a) {
        String sql = "select password from users where userId = '123456'";
        ResultSet resultSet = SQLHelper.getInstance().excuteSQL(sql);
        ResultSet resultSet1 = SQLHelper.getInstance().excuteSQL("select username from users where userId = '123456'");
        if (resultSet!=null) {
            try {
                if (resultSet.next()) {
                    String userPassword = resultSet.getString("password");
                    System.out.println(userPassword);
                } else {
                    System.out.println("failed login");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        if (resultSet1!=null) {
            try {
                if (resultSet1.next()) {
                    System.out.println(resultSet1.getString("username"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
