package me.oop1nk.staffchat;

import dev.waterdog.waterdogpe.event.defaults.PlayerChatEvent;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import dev.waterdog.waterdogpe.plugin.Plugin;
import me.oop1nk.staffchat.commands.StaffChatCommand;
import me.oop1nk.staffchat.commands.ToggleCommand;
import me.oop1nk.staffchat.queue.message.LocalQueue;
import me.oop1nk.staffchat.utils.ConfigKeys;
import me.oop1nk.staffchat.utils.Message;
import me.oop1nk.staffchat.queue.Queue;
import me.oop1nk.staffchat.queue.message.RedisQueue;
import me.oop1nk.staffchat.queue.QueueType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class StaffChat extends Plugin {

    private Queue queue;
    private final List<UUID> chatToggles = new ArrayList<>();

    private static StaffChat instance;
    public static StaffChat getInstance() { return instance; }

    @Override
    public void onEnable() {
        instance = this;
        queue = loadQueue(QueueType.parse(getConfig().getString(ConfigKeys.KEY_PROVIDER), QueueType.LOCAL));
        queue.connect();

        // Subscribe to chat event
        getProxy().getEventManager().subscribe(PlayerChatEvent.class, this::onChat);

        // Register Commands
        getProxy().getCommandMap().registerCommand(new ToggleCommand());
        getProxy().getCommandMap().registerCommand(new StaffChatCommand());
    }

    public void sendMessage(Message message) {
        getQueue().publishMessage(message);
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
     * Creates a StaffChat Message queue based on config.
     * @param type The {@link QueueType} to initialise with.
     * @return The {@link Queue} in use.
     */
    private Queue loadQueue(QueueType type) {
        switch(type) {
            case LOCAL:
                return new LocalQueue();
            case REDIS:
                return new RedisQueue();
            default:
                throw new RuntimeException("Unknown provider: " + type);
        }
    }

    /**
     * Gets the active queue that is being used.
     * @return The {@link Queue} in use.
     */
    public Queue getQueue() {
        return queue;
    }

    /**
     * Runs whenever a player sends a message in chat.
     * Checks whether StaffChat is toggled and handles message accordingly.
     * @param event The {@link PlayerChatEvent}.
     */
    private void onChat(PlayerChatEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if(isToggled(player)) {
            event.setCancelled();
            Message message = new Message(player.getName(), player.getServerInfo().getServerName(), event.getMessage());
            sendMessage(message);
        }
    }

    /**
     * Utility function for formatting a StaffChat Message.
     * @param placeholders Placeholder values to format
     * @return The formatted message.
     */
    public static String formatMessage(Map<String, String> placeholders) {
        String message = StaffChat.getInstance().getConfig().getString(ConfigKeys.KEY_MESSAGE_FORMAT, "&8[&4StaffChat: %server%&8] &f%player%: %message%");
        for(Map.Entry<String, String> placeholder : placeholders.entrySet()) {
            message = message.replace(placeholder.getKey(), placeholder.getValue());
        }
        return message.replace("&", "§");
    }
}
