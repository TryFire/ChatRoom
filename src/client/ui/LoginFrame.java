package client.ui;

import client.CConstants;
import client.chat.LoginFunc;
import client.ui.resourse.Resourse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Yuriko on 2017/5/7.
 */
public class LoginFrame extends JFrame {
    private String title = "205聊天室";
    private JLabel labelUserId = new JLabel("QQ号：", JLabel.RIGHT);
    private JLabel labelPassword = new JLabel("密码：", JLabel.RIGHT);
    private JTextField textUserId = new JTextField();
    private JPasswordField textPassword = new JPasswordField();
    Font font = new Font("Microsoft YaHei UI", Font.PLAIN, 17);
    private JButton btnLogin = new JButton("登陆");
    public LoginFrame() {
        setSize(550, 400);
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelImage = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                ImageIcon icon = Resourse.login_image;
                int x = 0-(icon.getIconWidth() - getSize().width)/2;
                int y = 0-(icon.getIconHeight() - getSize().height)/2;
                g.drawImage(icon.getImage(), x, y, icon.getIconWidth(), icon.getIconHeight(), icon.getImageObserver());
            }
        };
        //panelImage.setBackground(Color.WHITE);
        add(panelImage, new GBC(0, 0, 2, 1).setFill(GBC.BOTH).setIpad(550, 200));

        JPanel panelLabel = new JPanel();
        panelLabel.setBackground(Color.WHITE);
        add(panelLabel, new GBC(0, 1, 1, 1).setFill(GBC.NONE).setIpad(120, 20));
        labelUserId.setFont(font);
        labelPassword.setFont(font);
        panelLabel.setLayout(new GridBagLayout());
        panelLabel.add(labelUserId, new GBC(0,0,1,1).setFill(GBC.BOTH).setWeight(1,1));
        panelLabel.add(labelPassword, new GBC(0,1,1,1).setFill(GBC.BOTH).setWeight(1,1));

        JPanel panelText = new JPanel();
        panelText.setBackground(Color.WHITE);
        add(panelText, new GBC(1, 1, 1, 1).setFill(GBC.BOTH).setIpad(100, 20));
        panelText.setLayout(new GridBagLayout());
        textUserId.setFont(font);
        textPassword.setFont(font);
        panelText.add(textUserId, new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setWeight(1,1).setInsets(0, 5, 0, 150));
        panelText.add(textPassword, new GBC(0, 1, 1, 1).setFill(GBC.BOTH).setWeight(1,1).setInsets(0, 5, 0, 150));

        JPanel panelLogin = new JPanel();
        panelLogin.setBackground(Color.WHITE);
        add(panelLogin, new GBC(0, 2, 2, 1).setFill(GBC.NONE).setIpad(550, 100).setWeight(1, 1));
        btnLogin.setBackground(Color.LIGHT_GRAY);
        btnLogin.setFont(font);
        panelLogin.setLayout(new GridBagLayout());
        panelLogin.add(btnLogin, new GBC(0, 0, 1, 1).setFill(GBC.BOTH).setWeight(1, 0).setInsets(0, 200, 0, 200));

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnLogin.setEnabled(false);
                String userId = textUserId.getText().trim();
                String password = textPassword.getText();
                if (userId.equals("")) {
                    System.out.println("userid");
                    JOptionPane.showMessageDialog(null, "账号不能为空", "登陆失败", JOptionPane.ERROR_MESSAGE);
                    textPassword.setText("");
                    btnLogin.setEnabled(true);

                    return;
                }
                if (password.equals("")) {
                    System.out.println("pass");
                    JOptionPane.showMessageDialog(null, "密码不能为空", "登陆失败", JOptionPane.ERROR_MESSAGE);
                    textUserId.setText("");
                    btnLogin.setEnabled(true);
                    return;
                }

                boolean isLogin = new LoginFunc().login(userId, password);
                if (isLogin) {
                    System.out.print("login success");
                    //setVisible(false);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "账号或密码错误或重复登陆", "登陆失败", JOptionPane.ERROR_MESSAGE);
                    textPassword.setText("");
                    textUserId.setText("");
                    btnLogin.setEnabled(true);
                }

                //System.out.println("userid" + userId);
                //System.out.println("pass" + password);
            }
        });

    }

    public static void main(String[] args) {
        String a = "123456@123123#123456@123123#";
        String[] user = a.split("#");
        for (String s:user) {
            String[] ss = s.split("@");
            System.out.println("userId:" + ss[0]);
            System.out.println("passs:" + ss[1]);
        }
    }
}
