package me.freitas.proleague.managers;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MessageManager {

    private static MessageManager instance;
    private final ProLeagueEssencial plugin;
    private static File messagesFile;
    private static FileConfiguration messagesConfig;

    public MessageManager(ProLeagueEssencial plugin) {
        this.plugin = plugin;
        instance = this;
        loadMessages();
    }

    public static MessageManager getInstance() {
        return instance;
    }

    public void loadMessages() {
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");

        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }

        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public static String getMessage(String key) {
        if (messagesConfig == null) {
            return ChatColor.RED + "[Erro] Arquivo de mensagens não carregado!";
        }
        String message = messagesConfig.getString(key);
        if (message == null) {
            return ChatColor.RED + "[Erro] Mensagem não configurada: " + key;
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void reloadMessages() {
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }
}