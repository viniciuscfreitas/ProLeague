package me.freitas.proleague.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.MessageManager;

public class CommandClearChat implements CommandExecutor {

    private final ProLeagueEssencial plugin;
    private final MessageManager messageManager;

    public CommandClearChat(ProLeagueEssencial plugin) {
        this.plugin = plugin;
        this.messageManager = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messageManager.getMessage("only-players"));
            return true;
        }

        if (!sender.hasPermission("proleague.clearchat")) {
            sender.sendMessage(messageManager.getMessage("no-permission"));
            return true;
        }

        for (int i = 0; i < 100; i++) {
            Bukkit.broadcastMessage("");
        }

        Bukkit.broadcastMessage(messageManager.getMessage("chat-cleared").replace("%player%", sender.getName()));
        return true;
    }
}