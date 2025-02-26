package me.freitas.proleague.commands.admin;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHeal implements CommandExecutor {
    private final MessageManager messageManager;

    public CommandHeal(ProLeagueEssencial plugin) {
        this.messageManager = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messageManager.getMessage("general.no_permission"));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("proleague.heal")) {
            player.sendMessage(messageManager.getMessage("general.no_permission"));
            return true;
        }

        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.sendMessage(messageManager.getMessage("heal.success"));
        return true;
    }
}