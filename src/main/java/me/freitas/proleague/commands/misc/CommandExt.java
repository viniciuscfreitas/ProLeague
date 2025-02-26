package me.freitas.proleague.commands.misc;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExt implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Apenas jogadores podem usar este comando.");
            return true;
        }

        Player player = (Player) sender;
        if (player.getFireTicks() > 0) {
            player.setFireTicks(0);
            player.sendMessage(ChatColor.GREEN + "🔥 Você apagou o fogo com sucesso!");
        } else {
            player.sendMessage(ChatColor.YELLOW + "Você não está pegando fogo.");
        }

        return true;
    }
}