package me.freitas.proleague.listeners;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Player;
import me.freitas.proleague.commands.CommandBack;
import me.freitas.proleague.commands.CommandFreeze;
import me.freitas.proleague.commands.CommandMute;

public class PlayerListener implements Listener {

    private final ProLeagueEssencial plugin;

    // Atualize o construtor para receber o plugin
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
        if (CommandFreeze.isFrozen(plugin, player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (CommandMute.isMuted(plugin, player)) {
            player.sendMessage("§c⚠ Você está mutado e não pode falar no chat.");
            event.setCancelled(true);
        }
    }
}