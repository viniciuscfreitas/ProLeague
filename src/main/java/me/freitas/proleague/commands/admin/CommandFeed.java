package me.freitas.proleague.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.MessageManager;

public class CommandFeed implements CommandExecutor {

    private final ProLeagueEssencial plugin;
    private final MessageManager messageManager;

    public CommandFeed(ProLeagueEssencial plugin) {
        this.plugin = plugin;
        this.messageManager = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messageManager.getMessage("only-players"));
            return true;
        }

        if (!sender.hasPermission("proleague.feed")) {
            sender.sendMessage(messageManager.getMessage("no-permission"));
            return true;
        }

        Player player = (Player) sender;

        player.setFoodLevel(20);
        player.setSaturation(10.0F);
        player.sendMessage(messageManager.getMessage("feed-success"));

        return true;
    }
}