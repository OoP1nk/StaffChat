package me.oop1nk.staffchat;

import dev.waterdog.waterdogpe.event.defaults.PlayerChatEvent;
import dev.waterdog.waterdogpe.event.defaults.PlayerLoginEvent;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import dev.waterdog.waterdogpe.plugin.Plugin;
import dev.waterdog.waterdogpe.utils.config.Configuration;
import lombok.SneakyThrows;
import me.oop1nk.staffchat.commands.StaffChatCommand;
import me.oop1nk.staffchat.commands.ToggleCommand;
import me.oop1nk.staffchat.utils.ConfigKeys;
import me.oop1nk.staffchat.queue.Queue;
import me.oop1nk.staffchat.queue.QueueType;
import me.oop1nk.staffchat.utils.FormatType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class StaffChat extends Plugin {

    private final List<UUID> chatToggles = new ArrayList<>();
    private ChatManager chatManager;

    private static StaffChat instance;
    public static StaffChat getInstance() { return instance; }

    @Override
    @SneakyThrows
    public void onEnable() {
        instance = this;

        if(getConfig().getInt(ConfigKeys.VERSION) > 1) {
            getLogger().error("Incorrect config version! Please update and restart.");
            setEnabled(false);
        }

        chatManager = new ChatManager();

        // Subscribe to Events
        getProxy().getEventManager().subscribe(PlayerChatEvent.class, EventListener::onChat);
        getProxy().getEventManager().subscribe(PlayerLoginEvent.class, EventListener::onJoin);

        // Register Commands
        getProxy().getCommandMap().registerCommand(new ToggleCommand());
        getProxy().getCommandMap().registerCommand(new StaffChatCommand());
    }

    /**
     * Gets the {@link ChatManager} being used to queue messages.
     * @return The active {@link ChatManager}.
     */
    public ChatManager getChatManager() {
        return chatManager;
    }

    /**
     * Toggles a players chatroom between Staff and Server.
     * @param player The {@link ProxiedPlayer} to toggle.
     * @param newState The new state (whether to toggle or not).
     */
    public void toggle(ProxiedPlayer player, boolean newState) {
        if (newState) {
            chatToggles.add(player.getUniqueId());
        } else {
            chatToggles.remove(player.getUniqueId());
        }
    }

    /**
     * Checks whether a specified player has StaffChat toggled.
     * @param player The {@link ProxiedPlayer} to check.
     * @return Whether the chat is toggled for the specified player.
     */
    public Boolean isToggled(ProxiedPlayer player) {
        return this.chatToggles.contains(player.getUniqueId());
    }

    /**
     * Utility function for formatting a StaffChat Message.
     * @param placeholders Placeholder values to format
     * @return The formatted message.
     */
    public static String formatMessage(Map<String, String> placeholders, FormatType formatType) {
        String message = getInstance().getConfig().getString(formatType.getConfigKey());
        for(Map.Entry<String, String> placeholder : placeholders.entrySet()) {
            message = message.replace(placeholder.getKey(), placeholder.getValue());
        }
        return message.replace("&", "§");
    }
}
