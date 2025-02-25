package me.freitas.proleague.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.OfflinePlayer;

public class CommandUnban implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("proleague.admin")) {
            sender.sendMessage("§cVocê não tem permissão para usar este comando.");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage("§cUso correto: /unban <jogador>");
            return true;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (target.isBanned()) {
            target.setBanned(false);
            sender.sendMessage("§aJogador " + target.getName() + " foi desbanido com sucesso!");
        } else {
            sender.sendMessage("§cEste jogador não está banido.");
        }
        return true;
    }
}