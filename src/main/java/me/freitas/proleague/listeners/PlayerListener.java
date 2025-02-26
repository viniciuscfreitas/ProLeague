package me.freitas.proleague.listeners;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Player;
import me.freitas.proleague.commands.teleport.CommandBack;
import me.freitas.proleague.commands.admin.CommandFreeze;

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

}