package me.freitas.proleague.commands.admin;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandClearChat implements CommandExecutor {
    private final MessageManager messageManager;

    public CommandClearChat(ProLeagueEssencial plugin) {
        this.messageManager = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("proleague.clearchat")) {
            sender.sendMessage(messageManager.getMessage("general.no_permission"));
            return true;
        }

        for (int i = 0; i < 100; i++) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(" ");
            }
        }

        Bukkit.broadcastMessage(messageManager.getMessage("clearchat.success")
                .replace("{player}", sender.getName()));

        return true;
    }
}