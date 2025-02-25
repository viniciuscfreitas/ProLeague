package me.freitas.proleague.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class CommandVanish implements CommandExecutor {
    private static final Set<String> vanishedPlayers = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }

        Player player = (Player) sender;
        if (vanishedPlayers.contains(player.getName())) {
            vanishedPlayers.remove(player.getName());
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.showPlayer(player);
            }
            player.sendMessage("§aVocê está visível novamente!");
        } else {
            vanishedPlayers.add(player.getName());
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.hidePlayer(player);
            }
            player.sendMessage("§aVocê está invisível!");
        }
        return true;
    }
}