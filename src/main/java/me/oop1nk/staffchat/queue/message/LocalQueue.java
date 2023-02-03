package me.oop1nk.staffchat.queue.message;

import com.google.common.collect.ImmutableMap;
import dev.waterdog.waterdogpe.ProxyServer;
import me.oop1nk.staffchat.StaffChat;
import me.oop1nk.staffchat.utils.Message;
import me.oop1nk.staffchat.queue.Queue;

import java.util.Map;
import java.util.function.Consumer;

public class LocalQueue implements Queue {
    @Override
    public void connect() {
        // Don't need in local queue
    }

    @Override
    public void publishMessage(Message message) {
        getMessageConsumer().accept(message);
    }

    @Override
    public Consumer<Message> getMessageConsumer() {
        return result -> {
            Map<String, String> placeholders = ImmutableMap.<String, String>builder()
                    .put("$player$", result.getPlayerName())
                    .put("$server$", result.getServerName())
                    .put("$message$", result.getMessage())
                    .put("$region$", result.getRegion())
                    .build();
            ProxyServer.getInstance().getPlayers()
                    .values()
                    .stream()
                    .filter(p -> p.hasPermission("staffchat.use"))
                    .forEach(p -> p.sendMessage(StaffChat.formatMessage(placeholders)));
        };
    }
}
