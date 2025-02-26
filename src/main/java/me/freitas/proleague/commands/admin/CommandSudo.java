package me.freitas.proleague.commands.admin;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSudo implements CommandExecutor {
    private final MessageManager messageManager;

    public CommandSudo(ProLeagueEssencial plugin) {
        this.messageManager = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("proleague.sudo")) {
            sender.sendMessage(messageManager.getMessage("general.no_permission"));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(messageManager.getMessage("sudo.usage"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(messageManager.getMessage("general.player_not_found"));
            return true;
        }

        StringBuilder actionBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            actionBuilder.append(args[i]).append(" ");
        }
        String action = actionBuilder.toString().trim();

        if (action.startsWith("/")) {
            target.performCommand(action.substring(1));
            sender.sendMessage(messageManager.getMessage("sudo.success_command")
                    .replace("{player}", target.getName())
                    .replace("{command}", action));
        } else {
            target.chat(action);
            sender.sendMessage(messageManager.getMessage("sudo.success_chat")
                    .replace("{player}", target.getName())
                    .replace("{message}", action));
        }

        return true;
    }
}