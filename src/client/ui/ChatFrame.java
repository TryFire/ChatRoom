package client.ui;

import client.CConstants;
import client.chat.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

/**
 * Created by Yuriko on 2017/5/8.
 */
public class ChatFrame extends JFrame {
    Font font = new Font("Microsoft YaHei UI", Font.PLAIN, 17);
    String username = "";
    JLabel label = new JLabel(" 在线好友", JLabel.LEFT);
    JLabel labelUsername = new JLabel("sssssss", JLabel.LEFT);
    JList jList=new JList();
    JTextArea textRe = new JTextArea();
    JTextArea textSend = new JTextArea();
    private String desId = "";
    Vector vector;
    public ChatFrame(Vector vector) {
        this.vector = vector;
        setSize(600, 600);
        setLayout(new GridBagLayout());
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panelRe = new JPanel();
        panelRe.setBackground(Color.LIGHT_GRAY);
        JPanel panelSend = new JPanel();
        panelSend.setBackground(Color.white);
        JPanel panelBtn = new JPanel();
        panelBtn.setBackground(Color.RED);

        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new GridBagLayout());
        JPanel panelRight = new JPanel();
        panelRight.setLayout(new GridBagLayout());
        panelRight.setBackground(Color.PINK);
        add(panelLeft, new GBC(0,0,1,1).setWeight(1,0).setFill(GBC.BOTH));
        add(panelRight, new GBC(1,0,1,1).setWeight(1,1).setFill(GBC.BOTH).setIpad(180, 500));


        textSend.setFont(font);
        textSend.setEnabled(true);
        textSend.setBackground(Color.LIGHT_GRAY);
        textSend.setSize(550, 200);
        textSend.setLineWrap(true);

        textRe.setEnabled(false);
        textRe.setFont(font);
        textRe.setSize(550, 450);
        textRe.setLineWrap(true);
        textRe.setForeground(Color.GRAY);
        JButton button = new JButton("send");
        button.setFont(font);
        button.setBackground(Color.PINK);

        panelLeft.add(panelRe, new GBC(0,0).setWeight(1,1).setFill(GBC.BOTH).setIpad(0, 450));
        panelLeft.add(panelSend, new GBC(0,1).setWeight(0,0).setFill(GBC.BOTH).setIpad(0, 80));
        panelLeft.add(panelBtn, new GBC(0,2).setWeight(1,0).setFill(GBC.NONE).setIpad(550, 40));

        panelRe.setLayout(new BorderLayout());
        panelRe.add(textRe, BorderLayout.CENTER);
        panelSend.setLayout(new BorderLayout());
        panelSend.add(textSend, BorderLayout.CENTER);
        panelBtn.setLayout(new BorderLayout());
        panelBtn.add(button, BorderLayout.CENTER);

        textSend.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String msg = textSend.getText().trim();
                    textSend.setText("");
                    try {
                        Calendar c = Calendar.getInstance();
                        int hour = c.get(Calendar.HOUR);
                        int minute = c.get(Calendar.MINUTE);
                        int second = c.get(Calendar.SECOND);
                        msg = Client.getClient().getCurrUsername() + ":  " + hour + ":" + minute + ":" + second + '\n' + msg;
                        Client.getClient().getOutputStream().writeUTF(msg);

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = textSend.getText().trim();
                try {
                    Calendar c = Calendar.getInstance();
                    int hour = c.get(Calendar.HOUR);
                    int minute = c.get(Calendar.MINUTE);
                    int second = c.get(Calendar.SECOND);
                    msg = Client.getClient().getCurrUsername() + ":  " + hour + ":"+minute+":" +second + '\n' + msg;
                    Client.getClient().getOutputStream().writeUTF(msg);
                    textSend.setText("");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });


        labelUsername.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 25));
        labelUsername.setText("Me: " + Client.getClient().getCurrUsername());
        //labelUsername.setText("ddddddd");


        panelRight.add(labelUsername, new GBC(0, 0, 1, 1).setWeight(1, 0).setFill(GBC.BOTH));



        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(Color.WHITE);
        panelRight.add(panelLabel, new GBC(0, 1, 1, 1).setFill(GBC.BOTH).setWeight(1, 0));

        JPanel panelData = new JPanel();
        panelData.setBackground(Color.LIGHT_GRAY);
        panelRight.add(panelData, new GBC(0, 2, 1, 1).setWeight(1, 1).setFill(GBC.BOTH));


        label.setFont(font);
        panelLabel.setLayout(new BorderLayout());
        panelLabel.add(label, BorderLayout.CENTER);
        panelLabel.setLayout(new GridBagLayout());
        panelLabel.add(label, new GBC(0, 0, 1, 1).setWeight(0, 0).setFill(GBC.NONE));



        jList.setListData(this.vector);
        jList.setFont(font);
        JScrollPane pane = new JScrollPane();
        pane.setViewportView(jList);
        //pane.setPreferredSize(new Dimension(250, 600));
        panelData.setLayout(new GridBagLayout());
        panelData.add(pane, new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setWeight(1,1));

        jList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                /*if (e.getClickCount() == 2) {
                    int index = ((JList) e.getSource()).getSelectedIndex();
                    System.out.println(index);
                    Client.getClient().chat(index);
                }*/
            }
        });

        pack();

    }

    public void listen() {
        new Thread(new FriendThread()).start();
    }

    private void setIds (String users) {
        vector = new Vector<>();
        String[] user = users.split("#");
        for (String s:user) {
            String[] ss = s.split("@");
            //System.out.println("userId:" + ss[0]);
            //ids.add(ss[0]);
            //System.out.println("passs:" + ss[1]);
            vector.add(ss[1]);
        }
        jList.setListData(vector);
    }

    private class FriendThread implements Runnable {
        boolean isListenging = true;
        DataInputStream inputStream = Client.getClient().getInputStream();
        @Override
        public void run() {
            while (isListenging) {
                try {
                    String code = inputStream.readUTF();
                    System.out.println("client get :" + code);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (code.equals(CConstants.ASK_USERS_CODE)) {
                        code = inputStream.readUTF();
                        Client.getClient().setIds(code);
                        setIds(code);
                    } else {
                        String testOrigin = textRe.getText();
                        textRe.setText(testOrigin + '\n' + code +'\n');
                    }
                    /*else if (code.equals(CConstants.POP_CHAT_CODE)) {
                        String userId = inputStream.readUTF();
                        if (Client.getClient().isLunchChatFrame(userId)) {

                        } else {
                            Client.getClient().addChatFrame(userId, new ChatFrame(new Vector()));
                        }
                    }*/

                } catch (IOException e) {
                    isListenging = false;
                    e.printStackTrace();
                }
            }
        }
    }



    public static void main(String[] args) {

        Vector v = new Vector();
        v.addElement("nokia 8850");
        v.addElement("nokia 8250");

        ChatFrame chatFrame = new ChatFrame(v);
        //FriendsFrame friendsFrame = new FriendsFrame(v);


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        v.addElement("notorola v8088");
        v.addElement("motorola v3688");
        v.addElement("panasonic GD92");
        v.addElement("其他");
        v.addElement("notorola v8088");
        v.addElement("motorola v3688");
        v.addElement("panasonic GD92");
        v.addElement("其他");
        chatFrame.jList.setListData(v);
    }
}
