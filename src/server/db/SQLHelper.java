package server.db;

import server.SConstants;

import java.sql.*;

/**
 * Created by Yuriko on 2017/5/6.
 */
public class SQLHelper {
    private static SQLHelper sqlHelper = null;
    private Connection connection = null;
    private boolean isClosed = true;
    public static SQLHelper getInstance() {
        if (sqlHelper == null) {
            sqlHelper = new SQLHelper();
        }
        return sqlHelper;
    }
    private SQLHelper(){

    }
    private Connection connect() { //连接到数据库
        if ((connection!=null) && (!isClosed)) {
            return connection;
        }
        try {
            Class.forName(SConstants.DB_DRIVER);
            connection = DriverManager.getConnection(SConstants.DB_URL, SConstants.DB_USERNAME, SConstants.DB_PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Succeeded connecting to the Database!");
                isClosed = false;
            }
            return connection;
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            isClosed = true;
            e.printStackTrace();
        }
        return connection;
    }
    public void close() { //关闭数据库
        if (!isClosed&&connection!=null) {
            try {
                isClosed = true;
                connection.close();
                connection = null;
            } catch (SQLException e) {
                isClosed = true;
                e.printStackTrace();
            }
        }
    }
    public ResultSet excuteSQL(String sql) { //执行sql
        if (connection == null) {
            System.out.println("connecting is down");
            connection = connect();
        }
        try {
            isClosed = connection.isClosed();
        } catch (SQLException e) {
            isClosed = true;
            e.printStackTrace();
        }
        if (isClosed) {
            System.out.println("connecting is closed");
            connection = connect();
        }
        System.out.println("connecting is keepUp");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
