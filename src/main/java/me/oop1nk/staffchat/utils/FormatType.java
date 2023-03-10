package me.oop1nk.staffchat.utils;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum FormatType {
    DEFAULT(ConfigKeys.MESSAGE_FORMAT_DEFAULT, "default"),
    NOTIFICATION(ConfigKeys.MESSAGE_FORMAT_NOTIFICATION, "notification");

    public static final FormatType[] VALUES = values();

    private final String configKey;
    private final List<String> aliases;

    FormatType(String configKey, String... aliases) {
        this.configKey = configKey;
        this.aliases = ImmutableList.copyOf(aliases);
    }

    public static FormatType parse(String alias) {
        for(FormatType t : VALUES) {
            for (String id : t.getAliases()) {
                if(id.equalsIgnoreCase(alias)) {
                    return t;
                }
            }
        }
        return DEFAULT;
    }

    public String getConfigKey() {
        return configKey;
    }

    public List<String> getAliases() {
        return aliases;
    }
}
