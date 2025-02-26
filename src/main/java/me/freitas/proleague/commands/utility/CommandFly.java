package me.freitas.proleague.commands.utility;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFly implements CommandExecutor {
    private final MessageManager messageManager;

    public CommandFly(ProLeagueEssencial plugin) {
        this.messageManager = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messageManager.getMessage("general.no_permission"));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("proleague.fly")) {
            player.sendMessage(messageManager.getMessage("general.no_permission"));
            return true;
        }

        boolean isFlying = player.getAllowFlight();
        player.setAllowFlight(!isFlying);
        player.sendMessage(messageManager.getMessage(isFlying ? "fly.disabled" : "fly.enabled"));
        return true;
    }
}