package me.freitas.proleague.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import me.freitas.proleague.ProLeagueEssencial;

public class PlayerJoinListener implements Listener {

    private final ProLeagueEssencial plugin;

    public PlayerJoinListener(ProLeagueEssencial plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Obtém a mensagem de boas-vindas do config.yml
        String welcomeMessage = plugin.getConfig().getString("mensagem-de-boas-vindas");

        // Se for nulo ou vazio, define um padrão
        if (welcomeMessage == null || welcomeMessage.isEmpty()) {
            welcomeMessage = "&aBem-vindo ao servidor, &e" + player.getName() + "&a!";
        }

        // Traduz os códigos de cor e envia a mensagem
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', welcomeMessage));
    }
}