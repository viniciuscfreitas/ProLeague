package me.freitas.proleague.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.NickManager; // Certifique-se de importar

public class PlayerJoinListener implements Listener {

    private final ProLeagueEssencial plugin;
    private final NickManager nickManager;

    public PlayerJoinListener(ProLeagueEssencial plugin) {
        this.plugin = plugin;
        this.nickManager = plugin.getNickManager(); // Corrigindo inicialização do NickManager
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Obtém e aplica o nick persistente
        String nick = nickManager.getNick(player.getName());
        if (nick != null && !nick.isEmpty()) {
            player.setDisplayName(ChatColor.translateAlternateColorCodes('&', nick));
            player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', nick));
        }

        // Obtém a mensagem de boas-vindas do config.yml
        String welcomeMessage = plugin.getConfig().getString("mensagem-de-boas-vindas");

        // Se for nulo ou vazio, define um padrão
        if (welcomeMessage == null || welcomeMessage.isEmpty()) {
            welcomeMessage = "&aBem-vindo ao servidor, &e" + player.getName() + "&a!";
        }

        // Traduz os códigos de cor e envia a mensagem ao jogador
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', welcomeMessage));
    }
}