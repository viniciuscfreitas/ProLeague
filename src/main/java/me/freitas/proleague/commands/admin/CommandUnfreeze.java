package me.freitas.proleague.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.MessageManager;

public class CommandUnfreeze implements CommandExecutor {

    private final ProLeagueEssencial plugin;
    private final MessageManager messageManager;

    public CommandUnfreeze(ProLeagueEssencial plugin) {
        this.plugin = plugin;
        this.messageManager = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("proleague.unfreeze")) {
            sender.sendMessage(messageManager.getMessage("no-permission"));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(messageManager.getMessage("invalid-usage").replace("{usage}", "/unfreeze <jogador>"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(messageManager.getMessage("player-not-found").replace("{player}", args[0]));
            return true;
        }

        if (CommandFreeze.isFrozen(target)) {
            CommandFreeze.unfreezePlayer(target);
            sender.sendMessage(messageManager.getMessage("freeze-unfrozen").replace("{player}", target.getName()));
            target.sendMessage(messageManager.getMessage("freeze-unfrozen-target"));
        } else {
            sender.sendMessage(messageManager.getMessage("freeze-not-frozen").replace("{player}", target.getName()));
        }

        return true;
    }
}