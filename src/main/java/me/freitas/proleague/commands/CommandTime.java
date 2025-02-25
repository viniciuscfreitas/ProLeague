package me.freitas.proleague.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTime implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }

        Player player = (Player) sender;
        World world = player.getWorld();

        if (command.getName().equalsIgnoreCase("day")) {
            world.setTime(1000);
            player.sendMessage("§aHorário definido para dia.");
        } else if (command.getName().equalsIgnoreCase("night")) {
            world.setTime(13000);
            player.sendMessage("§aHorário definido para noite.");
        }

        return true;
    }
}