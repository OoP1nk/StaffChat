package me.oop1nk.staffchat.utils;

import dev.waterdog.waterdogpe.ProxyServer;
import me.oop1nk.staffchat.StaffChat;

import javax.annotation.Nullable;

public class Message {

    private final String playerName;
    private final String serverName;
    private final String message;
    private final String region;

    public Message(String playerName, String serverName, String message) {
        this.playerName = playerName;
        this.serverName = serverName;
        this.message = message;
        this.region = StaffChat.getInstance().getConfig().getString(ConfigKeys.KEY_REGION);
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getServerName() {
        return serverName;
    }

    public String getMessage() {
        return message;
    }

    public String getRegion() {
        return region;
    }
}
