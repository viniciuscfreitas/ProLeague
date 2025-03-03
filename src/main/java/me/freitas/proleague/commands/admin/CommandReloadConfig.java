package me.freitas.proleague.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.MessageManager;

public class CommandReloadConfig implements CommandExecutor {

    private final ProLeagueEssencial plugin;
    private final MessageManager messageManager;

    public CommandReloadConfig(ProLeagueEssencial plugin) {
        this.plugin = plugin;
        this.messageManager = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("proleague.admin")) {
            sender.sendMessage(messageManager.getMessage("general.no_permission"));
            return true;
        }

        plugin.reloadConfig();
        messageManager.reloadMessages(); // Certifique-se de que esse método existe no MessageManager.java
        sender.sendMessage(messageManager.getMessage("admin.reload_success"));

        return true;
    }
}