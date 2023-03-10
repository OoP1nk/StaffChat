package me.oop1nk.staffchat.utils;

import me.oop1nk.staffchat.StaffChat;

public class Message {

    private final String playerName;
    private final String serverName;
    private final String content;
    private final FormatType formatType;
    private final String region;

    public Message(String playerName, String serverName, String content, FormatType formatType) {
        this.playerName = playerName;
        this.serverName = serverName;
        this.content = content;
        this.formatType = formatType;
        this.region = StaffChat.getInstance().getConfig().getString(ConfigKeys.REGION);
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getServerName() {
        return serverName;
    }

    public String getContent() {
        return content;
    }

    public FormatType getFormatType() {return formatType;}

    public String getRegion() {return region;}
}
