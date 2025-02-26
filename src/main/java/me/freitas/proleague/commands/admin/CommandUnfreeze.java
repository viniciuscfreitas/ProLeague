package me.freitas.proleague.commands.admin;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUnfreeze implements CommandExecutor {
    private final ProLeagueEssencial plugin;

    public CommandUnfreeze(ProLeagueEssencial plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Uso correto: /unfreeze <jogador>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Jogador não encontrado.");
            return true;
        }

        CommandFreeze.unfreezePlayer(plugin, target);
        sender.sendMessage(ChatColor.GREEN + "✔ Jogador " + target.getName() + " foi descongelado!");
        target.sendMessage(ChatColor.YELLOW + "⚠ Você pode se mover novamente!");
        return true;
    }
}