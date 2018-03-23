package server.chat;

import server.SConstants;
import server.User;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

/**
 * Created by Yuriko on 2017/5/6.
 */
public class SocketHandler {
    private static SocketHandler handler = null;
    private Server server;
    private HashMap<String, Socket> room = new HashMap<>();

    public static SocketHandler getInstance() {
        if (handler == null) {
            handler = new SocketHandler();
        }
        return handler;
    }

    private SocketHandler() {
    }

    public void handle(Socket socket) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    String code = inputStream.readUTF();
                    System.out.println("CODE:"+code);
                    if (code.equals(SConstants.LOGIN_CODE)) {
                        System.out.println("login begin to start");
                        String userId = inputStream.readUTF();
                        String password = inputStream.readUTF();
                        User isLogin = new LoginFunc().login(userId, password);
                        if (isLogin != null) {
                            System.out.println("login true true true");
                            outputStream.writeUTF(isLogin.getUsername());
                            Server.getServer(SConstants.SERVER_PORT).addSocket(isLogin, socket);
                            Server.getServer(SConstants.SERVER_PORT).sendUsers();
                            outputStream.writeUTF(isLogin.getUsername() + "上线了!");
                            HandleClient client = new HandleClient(socket, userId);
                            Server.getServer(SConstants.SERVER_PORT).addClient(client);
                            new Thread(client).start();
                        } else {
                            outputStream.writeUTF(SConstants.LOGIN_FAILED);
                        }
                        System.out.println(isLogin);
                    }
                } catch (EOFException e) {
                    e.printStackTrace();
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
