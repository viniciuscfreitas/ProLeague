package me.freitas.proleague.commands;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRealName implements CommandExecutor {
    private final ProLeagueEssencial plugin;

    public CommandRealName(ProLeagueEssencial plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("proleague.realname")) {
            sender.sendMessage(ChatColor.RED + "Você não tem permissão para usar este comando.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Uso correto: /realname <nick>");
            return true;
        }

        String nick = args[0];
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (plugin.getNickManager().getNick(player.getName()).equalsIgnoreCase(nick)) {
                sender.sendMessage(ChatColor.GREEN + "O verdadeiro nome de " + nick + " é " + player.getName());
                return true;
            }
        }

        sender.sendMessage(ChatColor.RED + "Não foi possível encontrar um jogador com esse nick.");
        return true;
    }
}