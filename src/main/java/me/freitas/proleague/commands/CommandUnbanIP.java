package me.freitas.proleague.commands;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandUnbanIP implements CommandExecutor {
    private final ProLeagueEssencial plugin;

    public CommandUnbanIP(ProLeagueEssencial plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Uso correto: /unbanip <ip>");
            return true;
        }

        String ip = args[0];
        List<String> bannedIPs = plugin.getConfig().getStringList("bannedIPs");

        if (!bannedIPs.contains(ip)) {
            sender.sendMessage(ChatColor.RED + "O IP fornecido não está banido.");
            return true;
        }

        bannedIPs.remove(ip);
        plugin.getConfig().set("bannedIPs", bannedIPs);
        plugin.saveConfig();

        Bukkit.unbanIP(ip);
        sender.sendMessage(ChatColor.GREEN + "✔ O IP " + ip + " foi desbanido!");
        return true;
    }
}