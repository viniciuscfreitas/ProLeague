package me.freitas.proleague.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.NickManager;

public class PlayerJoinListener implements Listener {

    private final ProLeagueEssencial plugin;
    private final NickManager nickManager;

    public PlayerJoinListener(ProLeagueEssencial plugin) {
        this.plugin = plugin;
        this.nickManager = plugin.getNickManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Aplica o nick persistente, se houver
        applyPersistentNick(player);

        // Recupera a mensagem de boas-vindas a partir do config.
        // Primeiramente tenta a chave "welcomeMessage" (texto completo),
        // senão usa "mensagem-de-boas-vindas".
        String welcomeMsg = plugin.getConfig().getString("settings.welcomeMessage");
        if (welcomeMsg == null || welcomeMsg.isEmpty()) {
            welcomeMsg = plugin.getConfig().getString("mensagem-de-boas-vindas");
        }
        if (welcomeMsg == null || welcomeMsg.isEmpty()) {
            // Caso nenhuma esteja configurada, usa um padrão
            welcomeMsg = "&aBem-vindo ao servidor, &e{player}&a!";
        }

        // Substitui os placeholders na mensagem (ex.: {player})
        welcomeMsg = formatPlaceholders(welcomeMsg, player);

        // Traduz os códigos de cor e envia a mensagem ao jogador
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', welcomeMsg));
    }

    /**
     * Aplica o nick persistente ao jogador, se estiver definido.
     */
    private void applyPersistentNick(Player player) {
        String nick = nickManager.getNick(player.getName());
        if (nick != null && !nick.isEmpty()) {
            String coloredNick = ChatColor.translateAlternateColorCodes('&', nick);
            player.setDisplayName(coloredNick);
            player.setPlayerListName(coloredNick);
        }
    }

    /**
     * Realiza a substituição de placeholders na mensagem.
     * Atualmente substitui {player} pelo nome do jogador.
     */
    private String formatPlaceholders(String message, Player player) {
        return message.replace("{player}", player.getName());
    }
}