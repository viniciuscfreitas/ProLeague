package me.freitas.proleague.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTpAll implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }
        if (!sender.hasPermission("proleague.admin")) {
            sender.sendMessage("§cVocê não tem permissão para usar este comando.");
            return true;
        }

        Player player = (Player) sender;
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (!target.equals(player)) {
                target.teleport(player.getLocation());
                target.sendMessage("§aVocê foi teleportado para " + player.getName());
            }
        }

        sender.sendMessage("§aTodos os jogadores foram teleportados para você!");
        return true;
    }
}