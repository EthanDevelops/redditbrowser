package me.delta2force.redditbrowser;

import org.bukkit.configuration.file.FileConfiguration;

public class Client {

    private String username;
    private String password;
    private String clientId;
    private String clientSecret;
    private boolean commentsEnabled;
    private int maxPosts;
    private int screenWidth;
    private int screenHeight;
    private int roomDepth;

    public Client(RedditBrowserPlugin reddit) {
        FileConfiguration config = reddit.getConfig();
        this.username = config.getString("username");
        this.password = config.getString("password");
        this.clientId = config.getString("clientId");
        this.clientSecret = config.getString("clientSecret");
        this.maxPosts = config.getInt("maxPosts");
        this.commentsEnabled = config.getBoolean("commentsEnabled");
        this.screenWidth = config.getInt("screenWidth");
        this.screenHeight = config.getInt("screenHeight");
        this.roomDepth = config.getInt("roomDepth");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public boolean isCommentsEnabled() {
        return commentsEnabled;
    }

    public void setCommentsEnabled(boolean commentsEnabled) {
        this.commentsEnabled = commentsEnabled;
    }

    public int getMaxPosts() {
        return maxPosts;
    }

    public void setMaxPosts(int maxPosts) {
        this.maxPosts = maxPosts;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getRoomDepth() {
        return roomDepth;
    }

    public void setRoomDepth(int roomDepth) {
        this.roomDepth = roomDepth;
    }
}
