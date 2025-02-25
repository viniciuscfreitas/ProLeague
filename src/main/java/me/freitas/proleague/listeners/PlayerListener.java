package me.freitas.proleague.listeners;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.commands.CommandMute;
import me.freitas.proleague.commands.CommandFreeze;
import me.freitas.proleague.commands.CommandBack;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Player;

public class PlayerListener implements Listener {
    private final ProLeagueEssencial plugin;

    public PlayerListener(ProLeagueEssencial plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        CommandBack.setLastLocation(player, player.getLocation());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        CommandBack.setLastLocation(player, player.getLocation());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (plugin.getFrozenPlayers().contains(player.getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.getMutedPlayers().contains(player.getName())) {
            player.sendMessage("§c⚠ Você está mutado e não pode falar no chat.");
            event.setCancelled(true);
        }
    }
}