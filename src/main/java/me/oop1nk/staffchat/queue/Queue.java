package me.oop1nk.staffchat.queue;

import com.google.common.collect.ImmutableMap;
import dev.waterdog.waterdogpe.ProxyServer;
import me.oop1nk.staffchat.StaffChat;
import me.oop1nk.staffchat.utils.Message;

import java.util.Map;
import java.util.function.Consumer;

public interface Queue {
    default void connect() {}

    void publishMessage(Message message);

    default Consumer<Message> getMessageConsumer() {
        return result -> {
            Map<String, String> placeholders = ImmutableMap.<String, String>builder()
                    .put("$player$", result.getPlayerName())
                    .put("$server$", result.getServerName())
                    .put("$message$", result.getContent())
                    .put("$region$", result.getRegion())
                    .build();
            ProxyServer.getInstance().getPlayers()
                    .values()
                    .stream()
                    .filter(p -> p.hasPermission("staffchat.use"))
                    .forEach(p -> p.sendMessage(StaffChat.formatMessage(placeholders, result.getFormatType())));
        };
    }
}
