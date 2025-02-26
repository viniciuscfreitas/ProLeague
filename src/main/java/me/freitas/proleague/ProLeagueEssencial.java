package me.freitas.proleague;

import me.freitas.proleague.commands.admin.*;
import me.freitas.proleague.commands.items.*;
import me.freitas.proleague.commands.mechanics.CommandKeepInventory;
import me.freitas.proleague.commands.mechanics.CommandKeepXP;
import me.freitas.proleague.commands.misc.CommandExt;
import me.freitas.proleague.commands.teleport.*;
import me.freitas.proleague.commands.utility.*;
import me.freitas.proleague.commands.weather.CommandTime;
import me.freitas.proleague.commands.weather.CommandWeather;
import me.freitas.proleague.listeners.GodModeListener;
import me.freitas.proleague.listeners.PlayerJoinListener;
import me.freitas.proleague.listeners.PlayerListener;
import me.freitas.proleague.managers.MessageManager;
import me.freitas.proleague.utils.LocationUtil;
import me.freitas.proleague.managers.NickManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class ProLeagueEssencial extends JavaPlugin {

    private Location spawnLocation;
    private Set<String> frozenPlayers = new HashSet<>();
    private Set<String> mutedPlayers = new HashSet<>();
    private Set<String> godPlayers = new HashSet<>();

    private NickManager nickManager;
    private MessageManager messageManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();

        this.nickManager = new NickManager(this);
        this.messageManager = new MessageManager(this);

        loadSpawnLocation();
        loadFrozenPlayers();
        loadMutedPlayers();
        loadGodPlayers();

        registerCommands();
        registerListeners();

        getLogger().info(ChatColor.GREEN + "[ProLeagueEssencial] Plugin ativado com sucesso!");
    }

    @Override
    public void onDisable() {
        saveSpawnLocation();
        saveFrozenPlayers();
        saveMutedPlayers();
        saveGodPlayers();
        saveWarns();

        getLogger().info(ChatColor.RED + "[ProLeagueEssencial] Plugin desativado!");
    }

    private void registerCommands() {
        Object[][] comandos = new Object[][]{
                {"setspawn", new CommandSpawn(this)},
                {"spawn", new CommandSpawn(this)},
                {"sethome", new CommandHome(this)},
                {"home", new CommandHome(this)},
                {"delhome", new CommandHome(this)},
                {"listhomes", new CommandHome(this)},
                {"setwarp", new CommandWarp(this)},
                {"warp", new CommandWarp(this)},
                {"delwarp", new CommandWarp(this)},
                {"warps", new CommandWarp(this)},
                {"tp", new CommandTeleport(this)},
                {"tpa", new CommandTeleport(this)},
                {"tpaccept", new CommandTeleport(this)},
                {"tpdeny", new CommandTeleport(this)},
                {"back", new CommandBack()},
                {"gm", new CommandGamemode()},
                {"day", new CommandTime()},
                {"night", new CommandTime()},
                {"ping", new CommandPing()},
                {"horario", new CommandHorario()},
                {"suicide", new CommandSuicide()},
                {"reloadconfig", new CommandReloadConfig(this)},
                {"fly", new CommandFly(this)},
                {"god", new CommandGod(this)},
                {"heal", new CommandHeal(this)},
                {"speed", new CommandSpeed(this)},
                {"sudo", new CommandSudo(this)},
                {"info", new CommandInfo()},
                {"motd", new CommandMotd(this)},
                {"hat", new CommandHat()},
                {"feed", new CommandFeed()},
                {"anvil", new CommandAnvil()},
                {"enderchest", new CommandEnderChest()},
                {"reparar", new CommandReparar()},
                {"broadcast", new CommandBroadcast()},
                {"encantar", new CommandEncantar()},
                {"ext", new CommandExt()},
                {"freeze", new CommandFreeze(this)},
                {"unfreeze", new CommandUnfreeze(this)},
                {"clearchat", new CommandClearChat(this)},
                {"rain", new CommandWeather("rain")},
                {"sun", new CommandWeather("clear")},
                {"thunder", new CommandWeather("thunder")},
                {"vanish", new CommandVanish(this)},
                {"invsee", new CommandInvsee()},
                {"tphere", new CommandTphere()},
                {"clearinventory", new CommandClearInventory()},
                {"tpall", new CommandTpAll()},
                {"keepxp", new CommandKeepXP()},
                {"keepinventory", new CommandKeepInventory()},
                {"deathpoint", new CommandDeathPoint()},
                {"nick", new CommandNick(this)},
                {"realname", new CommandRealName(this)}
        };

        for (Object[] par : comandos) {
            registerCommand((String) par[0], par[1]);
        }
    }

    private void registerCommand(String command, Object executor) {
        if (getCommand(command) != null) {
            getCommand(command).setExecutor((org.bukkit.command.CommandExecutor) executor);
        } else {
            getLogger().warning("Comando '" + command + "' não foi registrado corretamente no plugin.yml!");
        }
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GodModeListener(this), this);
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
        List<String> list = getConfig().getStringList("frozenPlayers");
        if (list != null) {
            frozenPlayers.addAll(list);
        }
    }

    private void saveFrozenPlayers() {
        getConfig().set("frozenPlayers", new ArrayList<>(frozenPlayers));
        saveConfig();
    }

    private void loadMutedPlayers() {
        List<String> list = getConfig().getStringList("mutedPlayers");
        if (list != null) {
            mutedPlayers.addAll(list);
        }
    }

    private void saveMutedPlayers() {
        getConfig().set("mutedPlayers", new ArrayList<>(mutedPlayers));
        saveConfig();
    }

    private void loadGodPlayers() {
        List<String> list = getConfig().getStringList("godPlayers");
        if (list != null) {
            godPlayers.addAll(list);
        }
    }

    private void saveGodPlayers() {
        getConfig().set("godPlayers", new ArrayList<>(godPlayers));
        saveConfig();
    }

    private void loadWarns() {
        if (!getConfig().contains("warns")) {
            getConfig().createSection("warns");
        }
    }

    private void saveWarns() {
        saveConfig();
    }

    public Set<String> getFrozenPlayers() {
        return frozenPlayers;
    }

    public Set<String> getMutedPlayers() {
        return mutedPlayers;
    }

    public Set<String> getGodPlayers() {
        return godPlayers;
    }

    public NickManager getNickManager() {
        return nickManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }
}