package me.freitas.proleague.commands.admin;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGamemode implements CommandExecutor {

    private final ProLeagueEssencial plugin;
    private final MessageManager messageManager;

    public CommandGamemode(ProLeagueEssencial plugin) {
        this.plugin = plugin;
        this.messageManager = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("proleague.gamemode")) {
            sender.sendMessage(messageManager.getMessage("no-permission"));
            return true;
        }

        if (args.length < 1 || args.length > 2) {
            sender.sendMessage(messageManager.getMessage("gamemode-usage"));
            return true;
        }

        GameMode gameMode;
        switch (args[0].toLowerCase()) {
            case "0":
            case "survival":
                gameMode = GameMode.SURVIVAL;
                break;
            case "1":
            case "creative":
                gameMode = GameMode.CREATIVE;
                break;
            case "2":
            case "adventure":
                gameMode = GameMode.ADVENTURE;
                break;
            case "3":
            default:
                sender.sendMessage(messageManager.getMessage("invalid-gamemode"));
                return true;
        }

        Player target;
        if (args.length == 2) {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(messageManager.getMessage("player-not-found"));
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(messageManager.getMessage("only-players"));
                return true;
            }
            target = (Player) sender;
        }

        target.setGameMode(gameMode);
        target.sendMessage(messageManager.getMessage("gamemode-changed").replace("%gamemode%", gameMode.name()));
        if (target != sender) {
            sender.sendMessage(messageManager.getMessage("gamemode-updated").replace("%player%", target.getName()).replace("%gamemode%", gameMode.name()));
        }

        return true;
    }
}