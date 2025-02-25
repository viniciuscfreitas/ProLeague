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
        // Crie um array de pares comando/instância.
        Object[][] comandos = new Object[][] {
                { "setspawn", new CommandSpawn(this) },
                { "spawn", new CommandSpawn(this) },
                { "sethome", new CommandHome(this) },
                { "home", new CommandHome(this) },
                { "delhome", new CommandHome(this) },
                { "listhomes", new CommandHome(this) },
                { "setwarp", new CommandWarp(this) },
                { "warp", new CommandWarp(this) },
                { "delwarp", new CommandWarp(this) },
                { "warps", new CommandWarp(this) },
                { "tp", new CommandTeleport(this) },
                { "tpa", new CommandTeleport(this) },
                { "tpaccept", new CommandTeleport(this) },
                { "tpdeny", new CommandTeleport(this) },
                { "back", new CommandBack() },
                { "gm", new CommandGamemode() },
                { "day", new CommandTime() },
                { "night", new CommandTime() },
                { "ping", new CommandPing() },
                { "horario", new CommandHorario() },
                { "suicide", new CommandSuicide() },
                { "reloadconfig", new CommandReloadConfig(this) },
                { "fly", new CommandFly() },
                { "god", new CommandGod() },
                { "heal", new CommandHeal() },
                { "speed", new CommandSpeed() },
                { "sudo", new CommandSudo() },
                { "info", new CommandInfo() },
                { "motd", new CommandMotd(this) },
                { "hat", new CommandHat() },
                { "feed", new CommandFeed() },
                { "anvil", new CommandAnvil() },
                { "enderchest", new CommandEnderChest() },
                { "reparar", new CommandReparar() },
                { "broadcast", new CommandBroadcast() },
                { "encantar", new CommandEncantar() },
                { "ext", new CommandExt() },
                { "freeze", new CommandFreeze(this) },
                { "unfreeze", new CommandUnfreeze(this) },
                { "warn", new CommandWarn(this) },
                { "history", new CommandHistory(this) },
                { "mute", new CommandMute(this) },
                { "unmute", new CommandUnmute(this) },
                { "clearchat", new CommandClearChat() },
                { "banip", new CommandBanIP(this) },
                { "unbanip", new CommandUnbanIP(this) },
                { "rain", new CommandWeather("rain") },
                { "sun", new CommandWeather("clear") },
                { "thunder", new CommandWeather("thunder") },
                { "kick", new CommandKick() },
                { "ban", new CommandBan() },
                { "unban", new CommandUnban() },
                { "vanish", new CommandVanish() },
        };

        for (Object[] par : comandos) {
            String comando = (String) par[0];
            Object executor = par[1];
            registerCommand(comando, executor);
        }
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