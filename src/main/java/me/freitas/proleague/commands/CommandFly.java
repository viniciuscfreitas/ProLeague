package me.freitas.proleague.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFly implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar esse comando.");
            return true;
        }

        Player player = (Player) sender;
        boolean isFlying = player.getAllowFlight();
        player.setAllowFlight(!isFlying);
        player.sendMessage(isFlying ? "§cModo voo desativado!" : "§aModo voo ativado!");

        return true;
    }
}