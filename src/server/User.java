package server;

/**
 * Created by Yuriko on 2017/5/6.
 */
public class User {
    private String userId;
    private String username;
    private boolean isOnline;

    public User(String userId, String username, boolean isOnline) {
        this.userId = userId;
        this.username = username;
        this.isOnline = isOnline;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object obj) {
        return this.userId.equals(((User)obj).userId);
    }
}
