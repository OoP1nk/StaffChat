package me.oop1nk.staffchat;

import dev.waterdog.waterdogpe.utils.config.Configuration;
import lombok.SneakyThrows;
import me.oop1nk.staffchat.queue.Queue;
import me.oop1nk.staffchat.queue.QueueType;
import me.oop1nk.staffchat.queue.message.LocalQueue;
import me.oop1nk.staffchat.queue.message.RedisQueue;
import me.oop1nk.staffchat.utils.ConfigKeys;
import me.oop1nk.staffchat.utils.Message;

public class ChatManager {

    private final Queue queue;
    private final Configuration config = StaffChat.getInstance().getConfig();

    @SneakyThrows
    public ChatManager() {
        this.queue = loadQueue(QueueType.parse(config.getString(ConfigKeys.PROVIDER), QueueType.LOCAL));

        try {
            getQueue().connect();
        } catch(Exception ex) {
            StaffChat.getInstance().getLogger().error("Failed to connect: ", ex);
            StaffChat.getInstance().setEnabled(false);
        }
    }

    /**
     * Queues a message into the active queue.
     * @param message The message to be queued.
     */
    public void sendMessage(Message message) {
        getQueue().publishMessage(message);
    }

    /**
     * Gets the currently active queue
     * @return The {@link Queue} that is currently being used.
     */
    public Queue getQueue() {
        return queue;
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
}
