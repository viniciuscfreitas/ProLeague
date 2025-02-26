package me.freitas.proleague.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.MessageManager;
import java.util.HashSet;
import java.util.Set;

public class CommandFreeze implements CommandExecutor, Listener {

    private static final Set<String> frozenPlayers = new HashSet<>();
    private final ProLeagueEssencial plugin;
    private final MessageManager messageManager;

    public CommandFreeze(ProLeagueEssencial plugin) {
        this.plugin = plugin;
        this.messageManager = plugin.getMessageManager();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("proleague.freeze")) {
            sender.sendMessage(messageManager.getMessage("no-permission"));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(messageManager.getMessage("invalid-usage").replace("{usage}", "/freeze <jogador>"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(messageManager.getMessage("player-not-found").replace("{player}", args[0]));
            return true;
        }

        if (isFrozen(target)) {
            unfreezePlayer(target);
            sender.sendMessage(messageManager.getMessage("freeze-unfrozen").replace("{player}", target.getName()));
            target.sendMessage(messageManager.getMessage("freeze-unfrozen-target"));
        } else {
            freezePlayer(target);
            sender.sendMessage(messageManager.getMessage("freeze-frozen").replace("{player}", target.getName()));
            target.sendMessage(messageManager.getMessage("freeze-frozen-target"));
        }

        return true;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (isFrozen(event.getPlayer())) {
            event.setTo(event.getFrom());
        }
    }

    // Método para verificar se o jogador está congelado
    public static boolean isFrozen(Player player) {
        return frozenPlayers.contains(player.getName());
    }

    // Método para congelar um jogador
    public static void freezePlayer(Player player) {
        frozenPlayers.add(player.getName());
    }

    // Método para descongelar um jogador
    public static void unfreezePlayer(Player player) {
        frozenPlayers.remove(player.getName());
    }
}