package me.freitas.proleague.managers;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MessageManager {
    private final ProLeagueEssencial plugin;
    private FileConfiguration messagesConfig;
    private File messagesFile;

    public MessageManager(ProLeagueEssencial plugin) {
        this.plugin = plugin;
        loadMessages();
    }

    public void loadMessages() {
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");

        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }

        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', messagesConfig.getString(path, "&cMensagem não configurada: " + path));
    }

    public void reloadMessages() {
        loadMessages();
    }
}