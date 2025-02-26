package me.freitas.proleague.listeners;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.entity.Player;

public class GodModeListener implements Listener {

    private final ProLeagueEssencial plugin;

    public GodModeListener(ProLeagueEssencial plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (plugin.getGodPlayers().contains(player.getName())) {
                event.setCancelled(true);
            }
        }
    }
}