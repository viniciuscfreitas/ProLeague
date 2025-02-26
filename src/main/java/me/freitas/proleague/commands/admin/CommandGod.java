package me.freitas.proleague.commands.admin;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class CommandGod implements CommandExecutor {

    private final MessageManager messageManager;
    private final Set<String> godPlayers; // Lista de jogadores no modo God

    public CommandGod(ProLeagueEssencial plugin) {
        this.messageManager = plugin.getMessageManager();
        this.godPlayers = plugin.getGodPlayers(); // Armazena os jogadores com God Mode
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messageManager.getMessage("general.only_players"));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("proleague.god")) {
            player.sendMessage(messageManager.getMessage("general.no_permission"));
            return true;
        }

        // Alternar o estado do God Mode
        if (godPlayers.contains(player.getName())) {
            godPlayers.remove(player.getName());
            player.sendMessage(messageManager.getMessage("god.disabled"));
        } else {
            godPlayers.add(player.getName());
            player.sendMessage(messageManager.getMessage("god.enabled"));
        }

        return true;
    }
}