package server;

/**
 * Created by Yuriko on 2017/5/6.
 */
public class SConstants {
    public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/chatRoom?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "123456";

    public static final int SERVER_PORT = 8888;

    public static final String LOGIN_CODE = "code_login@chatroom_login";
    public static final String REGISTER_CODE = "code_register@chatroom_register";
    public static final String POP_CHAT_CODE = "code_chat_point_to_point@chatroom_chat_POP";
    public static final String PUBLIC_CHAT_CODE = "code_chat_in_public@chatroom_chat_in_public";
    public static final String ASK_USERS_CODE = "code_ask_for_users@chatroom_ask";

    public static final String LOGIN_SUCCESS = "1";
    public static final String LOGIN_FAILED = "0";
}
