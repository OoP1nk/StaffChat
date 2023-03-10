package me.oop1nk.staffchat.utils;

import me.oop1nk.staffchat.queue.Queue;
import me.oop1nk.staffchat.queue.message.RedisQueue;

public class ConfigKeys {

    /**
     * Version ConfigKeys Key.
     * Used for debugging and catching errors in case of breaking config
     * changes later down the line.
     */
    public static String VERSION = "version";

    public static String REGION = "region";

    /**
     * StaffChat message format ConfigKeys Key.
     * Used in {@link me.oop1nk.staffchat.StaffChat} when formatting the
     * staffchat message.
     */
    public static String MESSAGE_FORMAT_DEFAULT = "staffchat-format.default";
    public static String MESSAGE_FORMAT_NOTIFICATION = "staffchat-format.notification";

    /**
     * Standardised messages ConfigKeys Keys.
     */
    public static String MESSAGE_NO_PERMISSION = "messages.no-permission";
    public static String MESSAGE_CHANNEL_ON = "messages.channel-on";
    public static String MESSAGE_CHANNEL_OFF = "messages.channel-off";

    /**
     * Queue provider ConfigKeys Key.
     * Used when initialising the {@link Queue}.
     */
    public static String PROVIDER = "provider";

    /**
     * Redis credentials ConfigKeys Keys.
     * Used in {@link RedisQueue} when connecting
     * to redis database.
     */
    public static String REDIS_HOST = "redis.host";
    public static String REDIS_PORT = "redis.port";
    public static String REDIS_PASSWORD = "redis.password";
}
