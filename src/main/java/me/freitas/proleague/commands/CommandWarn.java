package me.freitas.proleague.commands;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandWarn implements CommandExecutor {
    private final ProLeagueEssencial plugin;

    public CommandWarn(ProLeagueEssencial plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Uso correto: /warn <jogador> <motivo>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Jogador não encontrado.");
            return true;
        }

        StringBuilder reasonBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            reasonBuilder.append(args[i]).append(" ");
        }
        String reason = reasonBuilder.toString().trim();

        List<String> warns = plugin.getConfig().getStringList("warns." + target.getName());
        warns.add(reason);
        plugin.getConfig().set("warns." + target.getName(), warns);
        plugin.saveConfig();

        target.sendMessage(ChatColor.YELLOW + "⚠ Você recebeu um aviso: " + ChatColor.RED + reason);
        sender.sendMessage(ChatColor.GREEN + "✔ Aviso enviado para " + target.getName() + ": " + reason);

        return true;
    }
}