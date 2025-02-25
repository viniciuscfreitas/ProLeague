package me.freitas.proleague.commands;

import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPing implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }

        Player player = (Player) sender;
        int ping = ((CraftPlayer) player).getHandle().ping;
        player.sendMessage("§aSeu ping: " + ping + "ms");
        return true;
    }
}