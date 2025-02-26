package me.freitas.proleague.commands.admin;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandReloadConfig implements CommandExecutor {
    private final ProLeagueEssencial plugin;

    public CommandReloadConfig(ProLeagueEssencial plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.reloadConfig();
        sender.sendMessage("§aConfiguração recarregada com sucesso!");
        return true;
    }
}