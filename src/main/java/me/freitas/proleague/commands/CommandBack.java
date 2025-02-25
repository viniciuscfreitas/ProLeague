package me.freitas.proleague.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CommandBack implements CommandExecutor {

    private static final HashMap<UUID, Location> lastLocations = new HashMap<>();

    public static void setLastLocation(Player player, Location location) {
        lastLocations.put(player.getUniqueId(), location);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar esse comando!");
            return true;
        }

        Player player = (Player) sender;

        if (!lastLocations.containsKey(player.getUniqueId())) {
            player.sendMessage("§cNenhuma localização salva para voltar!");
            return true;
        }

        Location backLocation = lastLocations.get(player.getUniqueId());
        player.teleport(backLocation);
        player.sendMessage("§aVocê foi teleportado de volta para sua última localização!");

        return true;
    }
}