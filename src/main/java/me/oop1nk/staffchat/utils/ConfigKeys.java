package me.oop1nk.staffchat.utils;

public class ConfigKeys {

    /**
     * Version Config Key.
     * Used for debugging and catching errors in case of breaking config
     * changes later down the line.
     */
    public static String KEY_CONFIG_VERSION = "version";

    public static String KEY_REGION = "region";

    /**
     * StaffChat message format Config Key.
     * Used in {@link me.oop1nk.staffchat.StaffChat} when formatting the
     * staffchat message.
     */
    public static String KEY_MESSAGE_FORMAT = "message-format";

    /**
     * Standardised messages Config Keys.
     */
    public static String KEY_MESSAGE_NO_PERMISSION = "messages.no-permission";
    public static String KEY_MESSAGE_CHANNEL_ON = "messages.channel-on";
    public static String KEY_MESSAGE_CHANNEL_OFF = "messages.channel-off";

    /**
     * Queue provider Config Key.
     * Used when initialising the {@link me.oop1nk.staffchat.queue.Queue}.
     */
    public static String KEY_PROVIDER = "provider";

    /**
     * Redis credentials Config Keys.
     * Used in {@link me.oop1nk.staffchat.queue.message.RedisQueue} when connecting
     * to redis database.
     */
    public static String KEY_REDIS_HOST = "redis.host";
    public static String KEY_REDIS_PORT = "redis.port";
    public static String KEY_REDIS_PASSWORD = "redis.password";
}
