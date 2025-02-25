package me.freitas.proleague.commands;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUnmute implements CommandExecutor {
    private final ProLeagueEssencial plugin;

    public CommandUnmute(ProLeagueEssencial plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Uso correto: /unmute <jogador>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Jogador não encontrado.");
            return true;
        }

        CommandMute.unmutePlayer(plugin, target);
        sender.sendMessage(ChatColor.GREEN + "✔ Jogador " + target.getName() + " foi desmutado!");
        target.sendMessage(ChatColor.YELLOW + "⚠ Você pode falar novamente!");
        return true;
    }
}