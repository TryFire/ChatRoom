package client.chat;

import client.CConstants;
import client.ui.ChatFrame;
import client.ui.LoginFrame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by Yuriko on 2017/5/6.
 */
public class Client {

    private HashMap<String, ChatFrame> chatFrames = new HashMap<>();
    //private FriendsFrame friendsFrame;
    public Socket socket;
    private static Client client;
    private ArrayList<String> ids;
    private Vector<String> vector;
    private String currUsername;
    private String currUserId;
    private boolean ischat = false;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public static Client getClient() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }

    public String getPOPID(int index) {
        return ids.get(index);
    }

    public void chat(int index) {
        String remote = ids.get(index);
        try {
            outputStream.writeUTF(CConstants.POP_CHAT_CODE);
            //outputStream.writeUTF(currUserId);
            outputStream.writeUTF(currUserId + "@" + remote);
            if (chatFrames.containsKey(remote)) {

            } else {
                ChatFrame chatFrame = new ChatFrame(new Vector());
                chatFrames.put(remote, chatFrame);
            }
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println();
                }
            }).start();*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Client() {

    }

    public boolean isLunchChatFrame(String des) {
        return chatFrames.containsKey(des);
    }

    public void addChatFrame(String des, ChatFrame c) {
        chatFrames.put(des, c);
    }

    public DataInputStream getInputStream() {
        return inputStream;
    }

    public DataOutputStream getOutputStream() {
        return outputStream;
    }

    public String getCurrUserId() {
        return currUserId;
    }

    public void setCurrUserId(String currUserId) {
        this.currUserId = currUserId;
    }

    public String getCurrUsername() {
        return currUsername;
    }

    public void setCurrUsername(String currUsername) {
        this.currUsername = currUsername;
    }

    /*public void setFriendsFrame(FriendsFrame friendsFrame) {
        this.friendsFrame = friendsFrame;
    }*/

    public void setIds (String users) {
        ids = new ArrayList<>();
        vector = new Vector<>();
        String[] user = users.split("#");
        for (String s:user) {
            String[] ss = s.split("@");
            System.out.println("userId:" + ss[0]);
            ids.add(ss[0]);
            System.out.println("passs:" + ss[1]);
            vector.add(ss[1]);
        }
        /*if (friendsFrame!=null) {
            friendsFrame.setFriends(vector);
            friendsFrame.setUsername(currUsername);
            friendsFrame.setVisible(true);
        }*/
    }

    public void start() {
        new LoginFrame();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client.getClient().start();
    }


}
