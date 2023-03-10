package me.oop1nk.staffchat;

import com.google.common.collect.ImmutableMap;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.event.defaults.PlayerChatEvent;
import dev.waterdog.waterdogpe.event.defaults.PlayerDisconnectEvent;
import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import me.oop1nk.staffchat.utils.FormatType;
import me.oop1nk.staffchat.utils.Message;

import java.util.Map;

public class EventListener {

    /**
     * Runs whenever a player sends a message in chat.
     * Checks whether StaffChat is toggled and handles message accordingly.
     * @param event The {@link PlayerChatEvent}.
     */
    public static void onChat(PlayerChatEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if(StaffChat.getInstance().isToggled(player)) {
            event.setCancelled();
            Message message = new Message(player.getName(), player.getServerInfo().getServerName(), event.getMessage(), FormatType.DEFAULT);
            StaffChat.getInstance().getChatManager().sendMessage(message);
        }
    }

    public static void onJoin(PlayerLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        Map<String, String> placeholders = ImmutableMap.<String, String>builder().put(
                "$message$", player.getName() + " has come §aonline§r!"
        ).build();
        for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers().values()) {
            if(p.hasPermission("staffchat.use")) {
                p.sendMessage(StaffChat.formatMessage(placeholders, FormatType.NOTIFICATION));
            }
        }
    }

    public static void onQuit(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        Map<String, String> placeholders = ImmutableMap.<String, String>builder().put(
                "$message$", player.getName() + " has gone §coffline§r!"
        ).build();
        for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers().values()) {
            if(p.hasPermission("staffchat.use")) {
                p.sendMessage(StaffChat.formatMessage(placeholders, FormatType.NOTIFICATION));
            }
        }
    }
}
