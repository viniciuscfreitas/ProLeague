package me.freitas.proleague.commands.admin;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class CommandVanish implements CommandExecutor {
    private static final Set<String> vanishedPlayers = new HashSet<>();
    private final MessageManager messageManager;

    public CommandVanish(ProLeagueEssencial plugin) {
        this.messageManager = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messageManager.getMessage("general.no_permission"));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("proleague.vanish")) {
            player.sendMessage(messageManager.getMessage("general.no_permission"));
            return true;
        }

        if (vanishedPlayers.contains(player.getName())) {
            vanishedPlayers.remove(player.getName());
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.showPlayer(player);
            }
            player.sendMessage(messageManager.getMessage("admin.vanish_disabled"));
        } else {
            vanishedPlayers.add(player.getName());
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.hidePlayer(player);
            }
            player.sendMessage(messageManager.getMessage("admin.vanish_enabled"));
        }
        return true;
    }
}