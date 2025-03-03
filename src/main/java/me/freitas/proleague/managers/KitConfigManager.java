package me.freitas.proleague.managers;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class KitConfigManager {

    private final ProLeagueEssencial plugin;
    private File kitsFile;
    private FileConfiguration kitsConfig;

    public KitConfigManager(ProLeagueEssencial plugin) {
        this.plugin = plugin;
        createFile();
        loadConfig();
    }

    private void createFile() {
        kitsFile = new File(plugin.getDataFolder(), "kits.yml");
        if (!kitsFile.exists()) {
            plugin.saveResource("kits.yml", false);
        }
    }

    public void loadConfig() {
        kitsConfig = YamlConfiguration.loadConfiguration(kitsFile);
    }

    public void saveConfig() {
        try {
            kitsConfig.save(kitsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return kitsConfig;
    }
}