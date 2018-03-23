package server.chat;

import server.SConstants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Yuriko on 2017/5/6.
 */
public class HandleClient implements Runnable {
    private String currUserId;
    private Socket currSocket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private boolean isThisClientConnected = false;
    private boolean isChating = false;

    public HandleClient(Socket socket, String userId) {
        //targets = new ArrayList<>();
        this.currSocket = socket;
        this.currUserId = userId;
        try {
            inputStream = new DataInputStream(currSocket.getInputStream());
            outputStream = new DataOutputStream(currSocket.getOutputStream());
            isThisClientConnected = true;
        } catch (IOException e) {
            System.out.println("stream wrong!");
            e.printStackTrace();
        }
    }

    private void send(String msg) {
        if (!isThisClientConnected) {
            //// TODO: 2017/5/6 save in database
            return;
        }
        try {
            outputStream.writeUTF(msg);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("write wrong!");
            e.printStackTrace();
        }
    }

    public boolean isChating() {
        return isChating;
    }

    public void setChating(boolean chating) {
        isChating = chating;
    }


    @Override
    public void run() {

        try {
            while (isThisClientConnected) {
                //DataInputStream dis = new DataInputStream(currSocket.getInputStream());
                String msg = inputStream.readUTF();
                for (HandleClient c:Server.getServer(SConstants.SERVER_PORT).getClents()) {
                    c.send(msg);
                }
            }
        } catch (IOException e) {
            System.out.println("read wrong! this socket is closed" + currUserId);
            String userName = Server.getServer(SConstants.SERVER_PORT).getUserNameByUserId(currUserId);
            Server.getServer(SConstants.SERVER_PORT).removeOnlineUser(currUserId);
            Server.getServer(SConstants.SERVER_PORT).removeClient(this);
            for (HandleClient c:Server.getServer(SConstants.SERVER_PORT).getClents()) {
                c.send(userName + "下线了");
            }
            isThisClientConnected = false;
            isChating = false;
            e.printStackTrace();
        }

    }
}
