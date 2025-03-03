package me.freitas.proleague.managers;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NickManager {
    private final ProLeagueEssencial plugin;
    private final Map<String, String> nickCache = Collections.synchronizedMap(new HashMap<String, String>());
    private File nicksFile;
    private FileConfiguration nicksConfig;

    public NickManager(ProLeagueEssencial plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin não pode ser null!");
        }
        this.plugin = plugin;
        // Garantir que a pasta de dados exista
        File dataFolder = plugin.getDataFolder();
        if (dataFolder == null || (!dataFolder.exists() && !dataFolder.mkdirs())) {
            plugin.getLogger().severe("Não foi possível criar a pasta de dados do plugin!");
            return;
        }
        // Criar o arquivo nicks.yml
        nicksFile = new File(dataFolder, "nicks.yml");
        if (!nicksFile.exists()) {
            try {
                nicksFile.createNewFile();
                plugin.getLogger().info("Arquivo nicks.yml criado com sucesso!");
            } catch (IOException e) {
                plugin.getLogger().severe("Erro ao criar nicks.yml: " + e.getMessage());
                return;
            }
        }
        // Carregar a configuração do arquivo
        nicksConfig = YamlConfiguration.loadConfiguration(nicksFile);
        if (nicksConfig == null) {
            plugin.getLogger().severe("Erro ao carregar nicks.yml!");
            return;
        }
        loadNicks();
    }

    public void setNick(String playerName, String nick) {
        if (playerName == null || nick == null) {
            plugin.getLogger().warning("PlayerName ou nick são null!");
            return;
        }
        nickCache.put(playerName, nick);
        nicksConfig.set("nicks." + playerName, nick);
        saveNicksFile();
    }

    public String getNick(String playerName) {
        if (playerName == null) {
            return "Jogador Desconhecido";
        }
        return nickCache.containsKey(playerName) ? nickCache.get(playerName) : playerName;
    }

    public void removeNick(String playerName) {
        if (playerName == null) {
            plugin.getLogger().warning("PlayerName é null!");
            return;
        }
        nickCache.remove(playerName);
        nicksConfig.set("nicks." + playerName, null);
        saveNicksFile();
    }

    private void loadNicks() {
        if (nicksConfig == null || !nicksConfig.contains("nicks")) {
            plugin.getLogger().info("Nenhum nick salvo encontrado.");
            return;
        }
        for (String key : nicksConfig.getConfigurationSection("nicks").getKeys(false)) {
            nickCache.put(key, nicksConfig.getString("nicks." + key));
        }
    }

    private void saveNicksFile() {
        try {
            nicksConfig.save(nicksFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Erro ao salvar nicks.yml: " + e.getMessage());
        }
    }
}