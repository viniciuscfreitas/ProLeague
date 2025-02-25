package me.freitas.proleague.commands;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.utils.LocationUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpawn implements CommandExecutor {
    private final ProLeagueEssencial plugin;

    public CommandSpawn(ProLeagueEssencial plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }

        Player player = (Player) sender;
        String command = cmd.getName().toLowerCase();

        if (command.equals("setspawn")) {
            if (!player.hasPermission("proleague.admin")) {
                player.sendMessage("§cVocê não tem permissão para definir o spawn.");
                return true;
            }

            LocationUtil.saveLocation(plugin, "spawn", player.getLocation());
            player.sendMessage("§aSpawn definido com sucesso!");
            return true;
        }

        if (command.equals("spawn")) {
            if (!plugin.getConfig().contains("spawn.world")) {
                player.sendMessage("§cO spawn ainda não foi definido.");
                return true;
            }

            player.teleport(LocationUtil.getLocation(plugin, "spawn"));
            player.sendMessage("§aTeleportado para o spawn!");
            return true;
        }

        return false;
    }
}