package me.freitas.proleague.commands;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.utils.LocationUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Set;

public class CommandHome implements CommandExecutor {
    private final ProLeagueEssencial plugin;

    public CommandHome(ProLeagueEssencial plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }

        Player player = (Player) sender;
        FileConfiguration config = plugin.getConfig();
        String path = "homes." + player.getUniqueId().toString() + ".";

        if (command.getName().equalsIgnoreCase("sethome")) {
            if (args.length < 1) {
                player.sendMessage("§cUso correto: /sethome <nome>");
                return true;
            }
            LocationUtil.saveLocation(plugin, "homes." + player.getName() + "." + args[0].toLowerCase(), player.getLocation());
            player.sendMessage("§aHome §e" + args[0] + " §adefinida com sucesso!");
            return true;
        }

        if (command.getName().equalsIgnoreCase("home")) {
            if (args.length < 1) {
                player.sendMessage("§cUso correto: /home <nome>");
                return true;
            }
            Location home = LocationUtil.getLocation(plugin, "homes." + player.getName() + "." + args[0].toLowerCase());
            if (home == null) {
                player.sendMessage("§cEsta home não existe.");
                return true;
            }
            player.teleport(home);
            player.sendMessage("§aTeleportado para a home §e" + args[0] + "!");
            return true;
        }

        if (command.getName().equalsIgnoreCase("delhome")) {
            if (args.length < 1) {
                player.sendMessage("§cUso correto: /delhome <nome>");
                return true;
            }
            if (!config.contains(path + args[0].toLowerCase())) {
                player.sendMessage("§cEsta home não existe.");
                return true;
            }
            config.set(path + args[0].toLowerCase(), null);
            plugin.saveConfig();
            player.sendMessage("§aHome §e" + args[0] + " §aremovida com sucesso!");
            return true;
        }

        if (command.getName().equalsIgnoreCase("listhomes")) {
            Set<String> homes = config.getConfigurationSection("homes." + player.getUniqueId().toString()) != null
                    ? config.getConfigurationSection("homes." + player.getUniqueId().toString()).getKeys(false)
                    : null;

            if (homes == null || homes.isEmpty()) {
                player.sendMessage("§cVocê não tem homes definidas.");
                return true;
            }

            StringBuilder homeList = new StringBuilder("§aSuas homes: ");
            for (String home : homes) {
                homeList.append(home).append(", ");
            }
            player.sendMessage(homeList.substring(0, homeList.length() - 2));
            return true;
        }

        return false;
    }
}