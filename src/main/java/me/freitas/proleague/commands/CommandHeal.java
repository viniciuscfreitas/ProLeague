package me.freitas.proleague.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHeal implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cUso correto: /heal <jogador>");
                return true;
            }
            Player player = (Player) sender;
            player.setHealth(player.getMaxHealth());
            sender.sendMessage("§aVocê foi curado!");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cJogador não encontrado.");
            return true;
        }

        target.setHealth(target.getMaxHealth());
        sender.sendMessage("§a" + target.getName() + " foi curado!");
        return true;
    }
}