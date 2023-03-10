package me.oop1nk.staffchat.queue.message;

import me.oop1nk.staffchat.utils.Message;
import me.oop1nk.staffchat.queue.Queue;

public class LocalQueue implements Queue {
    public void publishMessage(Message message) {
        getMessageConsumer().accept(message);
    }
}
