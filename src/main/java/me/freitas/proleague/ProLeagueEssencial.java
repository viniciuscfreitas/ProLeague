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
import me.freitas.proleague.listeners.KitGUIListener;
import me.freitas.proleague.managers.MessageManager;
import me.freitas.proleague.managers.NickManager;
import me.freitas.proleague.managers.KitConfigManager;
import me.freitas.proleague.managers.KitManager;
import me.freitas.proleague.commands.kits.CommandKits;
import me.freitas.proleague.utils.LocationUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class ProLeagueEssencial extends JavaPlugin {

    private Location spawnLocation;
    private Set<String> frozenPlayers = new HashSet<String>();
    private Set<String> mutedPlayers = new HashSet<String>();
    private Set<String> godPlayers = new HashSet<String>();

    private NickManager nickManager;
    private MessageManager messageManager;
    private KitManager kitManager;
    private KitConfigManager kitConfigManager;  // Novo

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        // Inicializa os managers
        nickManager = new NickManager(this);
        messageManager = new MessageManager(this);
        kitConfigManager = new KitConfigManager(this); // Carrega kits.yml
        kitManager = new KitManager(this, kitConfigManager); // Atualizado para usar KitConfigManager

        loadSpawnLocation();
        loadPlayerData();

        registerCommands();
        registerListeners();

        getLogger().info(ChatColor.GREEN + "[ProLeagueEssencial] Plugin ativado com sucesso!");
    }

    @Override
    public void onDisable() {
        saveSpawnLocation();
        savePlayerData();

        frozenPlayers.clear();
        mutedPlayers.clear();
        godPlayers.clear();

        getLogger().info(ChatColor.RED + "[ProLeagueEssencial] Plugin desativado!");
    }

    private void registerCommands() {
        Object[][] comandos = {
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
                {"gm", new CommandGamemode(this)},
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
                {"info", new CommandInfo(this)},
                {"motd", new CommandMotd(this)},
                {"hat", new CommandHat()},
                {"feed", new CommandFeed(this)},
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
                {"realname", new CommandRealName(this)},
                {"kit", new CommandKits(this)}
        };

        for (Object[] par : comandos) {
            registerCommand((String) par[0], par[1]);
        }
    }

    private void registerCommand(String command, Object executor) {
        if (getCommand(command) != null) {
            getCommand(command).setExecutor((org.bukkit.command.CommandExecutor) executor);
        } else {
            getLogger().warning("⚠️ Comando '" + command + "' não foi registrado corretamente no plugin.yml!");
        }
    }

    private void registerListeners() {
        List<Listener> listeners = Arrays.asList(
                new PlayerJoinListener(this),
                new PlayerListener(this),
                new GodModeListener(this),
                new KitGUIListener(this)  // Registro do listener para a GUI dos kits
        );

        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    private void loadSpawnLocation() {
        Location loc = LocationUtil.getLocation(this, "spawn");
        if (loc == null) {
            getLogger().warning("⚠️ Spawn não encontrado! Defina com /setspawn.");
        } else {
            spawnLocation = loc;
        }
    }

    private void saveSpawnLocation() {
        if (spawnLocation != null) {
            LocationUtil.saveLocation(this, "spawn", spawnLocation);
        }
    }

    private void loadPlayerData() {
        frozenPlayers = loadListFromConfig("frozenPlayers");
        mutedPlayers = loadListFromConfig("mutedPlayers");
        godPlayers = loadListFromConfig("godPlayers");
    }

    private void savePlayerData() {
        saveListToConfig("frozenPlayers", frozenPlayers);
        saveListToConfig("mutedPlayers", mutedPlayers);
        saveListToConfig("godPlayers", godPlayers);
    }

    private void saveListToConfig(String path, Set<String> data) {
        getConfig().set(path, new ArrayList<String>(data));
        saveConfig();
    }

    private Set<String> loadListFromConfig(String path) {
        return new HashSet<String>(getConfig().getStringList(path));
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

    public KitManager getKitManager() {
        return kitManager;
    }

    public KitConfigManager getKitConfigManager() {
        return kitConfigManager;
    }
}