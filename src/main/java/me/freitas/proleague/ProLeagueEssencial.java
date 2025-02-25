package me.freitas.proleague;

import me.freitas.proleague.commands.*;
import me.freitas.proleague.listeners.PlayerJoinListener;
import me.freitas.proleague.listeners.PlayerListener;
import me.freitas.proleague.utils.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class ProLeagueEssencial extends JavaPlugin {

    private Location spawnLocation;
    private Set<String> frozenPlayers = new HashSet<>();
    private Set<String> mutedPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();

        loadSpawnLocation();
        loadFrozenPlayers();
        loadMutedPlayers();
        loadWarns();
        loadBanIPs();

        registerCommands();
        registerListeners();
        getLogger().info(ChatColor.GREEN + "[ProLeagueEssencial] Plugin ativado com sucesso!");
    }

    @Override
    public void onDisable() {
        saveSpawnLocation();
        saveFrozenPlayers();
        saveMutedPlayers();
        saveWarns();
        saveBanIPs();
        getLogger().info(ChatColor.RED + "[ProLeagueEssencial] Plugin desativado!");
    }

    private void registerCommands() {
        registerCommand("setspawn", new CommandSpawn(this));
        registerCommand("spawn", new CommandSpawn(this));
        registerCommand("sethome", new CommandHome(this));
        registerCommand("home", new CommandHome(this));
        registerCommand("delhome", new CommandHome(this));
        registerCommand("listhomes", new CommandHome(this));
        registerCommand("setwarp", new CommandWarp(this));
        registerCommand("warp", new CommandWarp(this));
        registerCommand("delwarp", new CommandWarp(this));
        registerCommand("warps", new CommandWarp(this));
        registerCommand("freeze", new CommandFreeze(this));
        registerCommand("unfreeze", new CommandUnfreeze(this));
        registerCommand("mute", new CommandMute(this));
        registerCommand("unmute", new CommandUnmute(this));
        registerCommand("warn", new CommandWarn(this));
        registerCommand("banip", new CommandBanIP(this));
        registerCommand("unbanip", new CommandUnbanIP(this));
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    private void registerCommand(String command, Object executor) {
        if (getCommand(command) != null) {
            getCommand(command).setExecutor((org.bukkit.command.CommandExecutor) executor);
        }
    }

    private void loadSpawnLocation() {
        spawnLocation = LocationUtil.getLocation(this, "spawn");
    }

    private void saveSpawnLocation() {
        if (spawnLocation != null) {
            LocationUtil.saveLocation(this, "spawn", spawnLocation);
        }
    }

    private void loadFrozenPlayers() {
        frozenPlayers.clear();
        frozenPlayers.addAll(getConfig().getStringList("frozenPlayers"));
    }

    private void saveFrozenPlayers() {
        getConfig().set("frozenPlayers", frozenPlayers);
        saveConfig();
    }

    public Set<String> getFrozenPlayers() {
        return frozenPlayers;
    }

    private void loadMutedPlayers() {
        mutedPlayers.clear();
        mutedPlayers.addAll(getConfig().getStringList("mutedPlayers"));
    }

    private void saveMutedPlayers() {
        getConfig().set("mutedPlayers", mutedPlayers);
        saveConfig();
    }

    public Set<String> getMutedPlayers() {
        return mutedPlayers;
    }

    private void loadWarns() {
        if (!getConfig().contains("warns")) {
            getConfig().createSection("warns");
        }
    }

    private void saveWarns() {
        saveConfig();
    }

    private void loadBanIPs() {
        if (!getConfig().contains("bannedIPs")) {
            getConfig().createSection("bannedIPs");
        }
    }

    private void saveBanIPs() {
        saveConfig();
    }
}