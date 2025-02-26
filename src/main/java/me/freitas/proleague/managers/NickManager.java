package me.freitas.proleague.managers;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class NickManager {
    private final ProLeagueEssencial plugin;
    private final Map<String, String> nickCache = new HashMap<>();

    public NickManager(ProLeagueEssencial plugin) {
        this.plugin = plugin;
        loadNicks();
    }

    public void setNick(String playerName, String nick) {
        nickCache.put(playerName, nick);
        plugin.getConfig().set("nicks." + playerName, nick);
        plugin.saveConfig();
    }

    public String getNick(String playerName) {
        return nickCache.containsKey(playerName) ? nickCache.get(playerName) : playerName;
    }

    public void removeNick(String playerName) {
        nickCache.remove(playerName);
        plugin.getConfig().set("nicks." + playerName, null);
        plugin.saveConfig();
    }

    private void loadNicks() {
        FileConfiguration config = plugin.getConfig();
        if (config.contains("nicks")) {
            for (String playerName : config.getConfigurationSection("nicks").getKeys(false)) {
                nickCache.put(playerName, config.getString("nicks." + playerName));
            }
        }
    }
}