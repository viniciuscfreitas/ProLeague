package me.freitas.proleague.commands;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.utils.LocationUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Set;

public class CommandWarp implements CommandExecutor {
    private final ProLeagueEssencial plugin;

    public CommandWarp(ProLeagueEssencial plugin) {
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

        if (command.equals("setwarp")) {
            if (!player.hasPermission("proleague.admin")) {
                player.sendMessage("§cVocê não tem permissão para criar warps.");
                return true;
            }
            if (args.length < 1) {
                player.sendMessage("§cUso correto: /setwarp <nome>");
                return true;
            }
            LocationUtil.saveLocation(plugin, "warps." + args[0].toLowerCase(), player.getLocation());
            player.sendMessage("§aWarp §e" + args[0] + " §acriado com sucesso!");
            return true;
        }

        if (command.equals("warp")) {
            if (args.length < 1) {
                player.sendMessage("§cUso correto: /warp <nome>");
                return true;
            }
            Location warpLocation = LocationUtil.getLocation(plugin, "warps." + args[0].toLowerCase());
            if (warpLocation == null) {
                player.sendMessage("§cEste warp não existe.");
                return true;
            }
            player.teleport(warpLocation);
            player.sendMessage("§aTeleportado para o warp §e" + args[0] + "!");
            return true;
        }

        if (command.equals("delwarp")) {
            if (!player.hasPermission("proleague.admin")) {
                player.sendMessage("§cVocê não tem permissão para deletar warps.");
                return true;
            }
            if (args.length < 1) {
                player.sendMessage("§cUso correto: /delwarp <nome>");
                return true;
            }
            if (!plugin.getConfig().contains("warps." + args[0].toLowerCase())) {
                player.sendMessage("§cEste warp não existe.");
                return true;
            }
            plugin.getConfig().set("warps." + args[0].toLowerCase(), null);
            plugin.saveConfig();
            player.sendMessage("§aWarp §e" + args[0] + " §aremovida com sucesso!");
            return true;
        }

        if (command.equals("warps")) {
            ConfigurationSection warpsSection = plugin.getConfig().getConfigurationSection("warps");
            if (warpsSection == null || warpsSection.getKeys(false).isEmpty()) {
                player.sendMessage("§cNão há warps definidas.");
                return true;
            }

            Set<String> warps = warpsSection.getKeys(false);
            StringBuilder warpList = new StringBuilder("§aWarps disponíveis: ");
            for (String warp : warps) {
                warpList.append(warp).append(", ");
            }
            player.sendMessage(warpList.substring(0, warpList.length() - 2));
            return true;
        }

        return false;
    }
}