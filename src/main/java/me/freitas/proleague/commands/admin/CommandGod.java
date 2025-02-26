package me.freitas.proleague.commands.admin;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Set;

import java.util.HashSet;

public class CommandGod implements CommandExecutor {
    private final MessageManager messageManager;
    private final Set<String> godPlayers;

    public CommandGod(ProLeagueEssencial plugin) {
        this.messageManager = plugin.getMessageManager();
        this.godPlayers = plugin.getGodPlayers(); // Agora armazena nomes em vez de UUIDs
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

        String playerName = player.getName(); // Utilizando nome do jogador

        if (godPlayers.contains(playerName)) {
            godPlayers.remove(playerName);
            player.sendMessage(messageManager.getMessage("god.disabled"));
        } else {
            godPlayers.add(playerName);
            player.sendMessage(messageManager.getMessage("god.enabled"));
        }

        return true;
    }
}