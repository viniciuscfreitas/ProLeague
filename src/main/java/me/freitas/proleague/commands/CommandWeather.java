package me.freitas.proleague.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandWeather implements CommandExecutor {
    private final String weatherType;

    public CommandWeather(String weatherType) {
        this.weatherType = weatherType;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        World world = Bukkit.getWorlds().get(0);

        switch (weatherType) {
            case "rain":
                world.setStorm(true);
                sender.sendMessage(ChatColor.BLUE + "🌧 O clima foi alterado para chuva.");
                break;
            case "clear":
                world.setStorm(false);
                sender.sendMessage(ChatColor.YELLOW + "☀ O clima foi alterado para ensolarado.");
                break;
            case "thunder":
                world.setThundering(true);
                sender.sendMessage(ChatColor.DARK_RED + "⛈ O clima foi alterado para tempestade.");
                break;
        }

        return true;
    }
}