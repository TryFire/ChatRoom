package client.chat;

import client.CConstants;
import client.ui.ChatFrame;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

/**
 * Created by Yuriko on 2017/5/8.
 */
public class LoginFunc {

    public boolean login(String userId, String password) {
        try {
            Socket socket = new Socket("127.0.0.1", CConstants.CLIENT_PORT);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            outputStream.writeUTF(CConstants.LOGIN_CODE);
            outputStream.writeUTF(userId);
            outputStream.writeUTF(password);
            String loginCode = inputStream.readUTF();
            if (loginCode.equals(CConstants.LOGIN_FAILED)) {
                return false;
            } else {
                Client.getClient().setSocket(socket);
                System.out.print("login success");
                Client.getClient().setCurrUserId(userId);
                Client.getClient().setCurrUsername(loginCode);
                ChatFrame chatFrame = new ChatFrame(new Vector());
                chatFrame.listen();
                //Client.getClient().setFriendsFrame(new ChatFrame(new Vector()));
                //new Thread(new FriendThread()).start();
                return true;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }

}
