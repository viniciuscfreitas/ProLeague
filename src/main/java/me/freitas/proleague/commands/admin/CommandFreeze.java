package me.freitas.proleague.commands.admin;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class CommandFreeze implements CommandExecutor {
    private final ProLeagueEssencial plugin;

    public CommandFreeze(ProLeagueEssencial plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Uso correto: /freeze <jogador>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Jogador não encontrado.");
            return true;
        }

        Set<String> frozenPlayers = plugin.getFrozenPlayers();
        if (frozenPlayers.contains(target.getName())) {
            frozenPlayers.remove(target.getName());
            sender.sendMessage(ChatColor.GREEN + "✔ Jogador " + target.getName() + " foi descongelado!");
            target.sendMessage(ChatColor.YELLOW + "⚠ Você foi descongelado e pode se mover novamente!");
        } else {
            frozenPlayers.add(target.getName());
            sender.sendMessage(ChatColor.GREEN + "✔ Jogador " + target.getName() + " foi congelado!");
            target.sendMessage(ChatColor.RED + "⚠ Você foi congelado e não pode se mover!");
        }

        plugin.saveConfig();
        return true;
    }

    public static boolean isFrozen(ProLeagueEssencial plugin, Player player) {
        return plugin.getFrozenPlayers().contains(player.getName());
    }

    public static void unfreezePlayer(ProLeagueEssencial plugin, Player player) {
        plugin.getFrozenPlayers().remove(player.getName());
        plugin.saveConfig();
    }
}