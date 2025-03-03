package me.freitas.proleague.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.MessageManager;

public class CommandFeed implements CommandExecutor {

    private final MessageManager messageManager;

    public CommandFeed(ProLeagueEssencial plugin) {
        this.messageManager = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messageManager.getMessage("general.no_permission"));
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.setFoodLevel(20);
            player.sendMessage(messageManager.getMessage("feed.feed_success"));
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(messageManager.getMessage("general.player_not_found"));
                return true;
            }
            target.setFoodLevel(20);
            target.sendMessage(messageManager.getMessage("feed.feed_received").replace("{player}", player.getName()));
            player.sendMessage(messageManager.getMessage("feed.feed_other_success").replace("{player}", target.getName()));
        }
        return true;
    }
}