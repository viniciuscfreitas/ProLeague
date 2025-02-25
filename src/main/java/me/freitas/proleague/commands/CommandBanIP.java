package me.freitas.proleague.commands;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandBanIP implements CommandExecutor {
    private final ProLeagueEssencial plugin;

    public CommandBanIP(ProLeagueEssencial plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Uso correto: /banip <jogador>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Jogador não encontrado.");
            return true;
        }

        String ip = target.getAddress().getAddress().getHostAddress();
        List<String> bannedIPs = plugin.getConfig().getStringList("bannedIPs");
        if (bannedIPs.contains(ip)) {
            sender.sendMessage(ChatColor.RED + "Este IP já está banido.");
            return true;
        }

        bannedIPs.add(ip);
        plugin.getConfig().set("bannedIPs", bannedIPs);
        plugin.saveConfig();

        Bukkit.banIP(ip);
        target.kickPlayer(ChatColor.RED + "Você foi banido pelo seu IP!");

        sender.sendMessage(ChatColor.GREEN + "🚫 O IP " + ip + " foi banido!");
        return true;
    }
}