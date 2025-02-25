package me.freitas.proleague.utils;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class LocationUtil {

    public static void saveLocation(ProLeagueEssencial plugin, String path, Location location) {
        if (location == null || location.getWorld() == null) return;
        FileConfiguration config = plugin.getConfig();
        config.set(path + ".world", location.getWorld().getName());
        config.set(path + ".x", location.getX());
        config.set(path + ".y", location.getY());
        config.set(path + ".z", location.getZ());
        config.set(path + ".yaw", location.getYaw());
        config.set(path + ".pitch", location.getPitch());
        plugin.saveConfig();
    }

    public static Location getLocation(ProLeagueEssencial plugin, String path) {
        FileConfiguration config = plugin.getConfig();
        if (!config.contains(path + ".world")) return null;

        String worldName = config.getString(path + ".world");
        double x = config.getDouble(path + ".x");
        double y = config.getDouble(path + ".y");
        double z = config.getDouble(path + ".z");
        float yaw = (float) config.getDouble(path + ".yaw");
        float pitch = (float) config.getDouble(path + ".pitch");

        return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
    }
}