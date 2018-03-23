package server.chat;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import server.SConstants;
import server.User;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Yuriko on 2017/5/6.
 */
public class Server {
    private ArrayList<User> onlineUsers;
    private HashMap<String, Socket> socketMap;
    private ServerSocket serverSocket;
    private boolean isListening = false;
    private static Server server;
    private ArrayList<HandleClient> clents;
    public static Server getServer(int port) {
        if (server == null) {
            server = new Server(port);
        }
        return server;
    }
    private Server(int port) {
        try {
            clents = new ArrayList<>(); //服务器处理每个socket的程序
            serverSocket = new ServerSocket(port); //启动服务器
            isListening = true; //是否在监听
            onlineUsers = new ArrayList<>(); //在线用户
            socketMap = new HashMap<>(); //在线用户的账号与 对应的socket
            System.out.println("server is running");
        } catch (IOException e) {
            System.out.println("failed start server");
            isListening = false;
            e.printStackTrace();
        }
    }
    public void listen() {
        new Thread(new ListenThread()).start();
    }

    private void addOnlineUser(User user) {
        onlineUsers.add(user);
    }

    public void addClient(HandleClient client) {
        this.clents.add(client);
    }

    public ArrayList<HandleClient> getClents() {
        return this.clents;
    }

    public void removeClient(HandleClient client) {
        this.clents.remove(client);
    }

    public String getUserNameByUserId(String userId) {
        for (User u: onlineUsers) {
            if (u.getUserId().equals(userId)) {
                return u.getUsername();
            }
        }
        return "";
    }

    public void removeOnlineUser(String userId) {
        for (User u: onlineUsers) {
            if (u.getUserId().equals(userId)) {
                onlineUsers.remove(u);
                break;
            }
        }
        socketMap.remove(userId);
        sendUsers();
    }

    public void addSocket(String userId, Socket socket) {
        if (socketMap.containsKey(userId)) {
            socketMap.replace(userId, socket);
        } else {
            socketMap.put(userId, socket);
        }
    }

    public void addSocket(User user, Socket socket) {
        addSocket(user.getUserId(), socket);
        for (User u: onlineUsers) {
            if (user.equals(u)) {
                return;
            }
        }
        addOnlineUser(user);
    }

    public Socket getClientSocket(String userId) {
        return socketMap.get(userId);
    }

    public boolean isUserOnline(String userId) {
        return socketMap.containsKey(userId);
    }

    private class ListenThread implements Runnable {
        @Override
        public void run() {
            while (isListening) {
                try {
                    Socket socket = serverSocket.accept();
                    SocketHandler.getInstance().handle(socket);
                    //new Thread(new HandleClient(socket)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void sendUsers() {
        String us = "";
        for (User u:onlineUsers) {
            us = us + u.getUserId() + "@" + u.getUsername() + "#";
        }
        final String finalUs = us;
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Socket socket: socketMap.values()) {
                    try {
                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                        out.writeUTF(SConstants.ASK_USERS_CODE);
                        out.writeUTF(finalUs);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public static void main(String[] args) {
        Server.getServer(SConstants.SERVER_PORT).listen();
        //new Server(SConstants.SERVER_PORT).listen();
    }
}
