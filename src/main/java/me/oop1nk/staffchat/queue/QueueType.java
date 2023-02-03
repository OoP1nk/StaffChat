package me.oop1nk.staffchat.queue;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum QueueType {
    LOCAL("Local", "local"),
    REDIS("Redis", "redis");

    private final String name;
    private final List<String> identifiers;

    QueueType(String name, String... identifiers) {
        this.name = name;
        this.identifiers = ImmutableList.copyOf(identifiers);
    }

    public static QueueType parse(String name, QueueType def) {
        for(QueueType t : values()) {
            for (String id : t.getIdentifiers()) {
                if(id.equalsIgnoreCase(name)) {
                    return t;
                }
            }
        }
        return def;
    }

    public String getName() {
        return name;
    }

    public List<String> getIdentifiers() {
        return identifiers;
    }
}
