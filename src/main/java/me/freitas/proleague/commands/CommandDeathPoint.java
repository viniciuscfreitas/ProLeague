package me.freitas.proleague.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CommandDeathPoint implements CommandExecutor {

    private final HashMap<UUID, Location> deathPoints = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }

        Player player = (Player) sender;
        Location deathLocation = deathPoints.get(player.getUniqueId());

        if (deathLocation == null) {
            player.sendMessage("§cNenhuma morte registrada.");
            return true;
        }

        player.teleport(deathLocation);
        player.sendMessage("§aVocê foi teleportado para sua última morte.");
        return true;
    }
}