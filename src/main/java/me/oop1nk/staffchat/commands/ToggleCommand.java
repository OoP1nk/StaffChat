package me.oop1nk.staffchat.commands;

import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import me.oop1nk.staffchat.StaffChat;
import me.oop1nk.staffchat.utils.ConfigKeys;

public class ToggleCommand extends Command {
    public ToggleCommand() {
        super("staffchat", CommandSettings.builder()
                .setPermission("staffchat.use")
                .setDescription("Toggles Staff Chat channel on/off")
                .setAliases(new String[] { "sc" })
                .setPermissionMessage(StaffChat.getInstance().getConfig().getString(ConfigKeys.MESSAGE_NO_PERMISSION))
                .setUsageMessage("/sc")
                .build());
    }

    @Override
    public boolean onExecute(CommandSender sender, String alias, String[] args) {
        if(!sender.isPlayer()) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        boolean isChatToggled = StaffChat.getInstance().isToggled(player);
        StaffChat.getInstance().toggle(player, !isChatToggled);
        sender.sendMessage(isChatToggled
                ? StaffChat.getInstance().getConfig().getString(ConfigKeys.MESSAGE_CHANNEL_OFF)
                : StaffChat.getInstance().getConfig().getString(ConfigKeys.MESSAGE_CHANNEL_ON)
        );
        return true;
    }
}
