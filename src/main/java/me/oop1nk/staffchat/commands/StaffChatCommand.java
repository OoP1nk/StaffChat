package me.oop1nk.staffchat.commands;

import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.command.CommandSettings;
import dev.waterdog.waterdogpe.player.ProxiedPlayer;
import me.oop1nk.staffchat.StaffChat;
import me.oop1nk.staffchat.utils.ConfigKeys;
import me.oop1nk.staffchat.utils.FormatType;
import me.oop1nk.staffchat.utils.Message;

public class StaffChatCommand extends Command {
    public StaffChatCommand() {
        super("s", CommandSettings.builder()
                .setDescription("Send a message to all online staff members")
                .setUsageMessage("/s <message>")
                .setPermission("staffchat.use")
                .setPermissionMessage(StaffChat.getInstance().getConfig().getString(ConfigKeys.MESSAGE_NO_PERMISSION))
                .build());
    }

    @Override
    public boolean onExecute(CommandSender sender, String alias, String[] args) {
        if(!sender.isPlayer()) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        if(args.length == 0) {
            sender.sendMessage("§cPlease specify a message to send.");
            return true;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        Message message = new Message(player.getName(), player.getServerInfo().getServerName(), String.join(" ", args), FormatType.DEFAULT);
        StaffChat.getInstance().getChatManager().sendMessage(message);
        return true;
    }
}
