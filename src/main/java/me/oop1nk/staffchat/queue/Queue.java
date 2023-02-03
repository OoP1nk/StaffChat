package me.oop1nk.staffchat.queue;

import me.oop1nk.staffchat.utils.Message;

import java.util.function.Consumer;

public interface Queue {
    void connect();
    void publishMessage(Message message);
    Consumer<Message> getMessageConsumer();
}
