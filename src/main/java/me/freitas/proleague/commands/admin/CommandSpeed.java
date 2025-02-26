package me.freitas.proleague.commands.admin;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpeed implements CommandExecutor {
    private final MessageManager messageManager;

    public CommandSpeed(ProLeagueEssencial plugin) {
        this.messageManager = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) && args.length < 2) {
            sender.sendMessage(messageManager.getMessage("general.only_players"));
            return true;
        }

        Player target;
        float speed;

        if (args.length == 0) {
            sender.sendMessage(messageManager.getMessage("speed.usage"));
            return true;
        }

        try {
            speed = Float.parseFloat(args[0]) / 10;
        } catch (NumberFormatException e) {
            sender.sendMessage(messageManager.getMessage("speed.invalid_number"));
            return true;
        }

        if (speed < 0 || speed > 1) {
            sender.sendMessage(messageManager.getMessage("speed.out_of_range"));
            return true;
        }

        if (args.length == 2) {
            if (!sender.hasPermission("proleague.speed.others")) {
                sender.sendMessage(messageManager.getMessage("general.no_permission"));
                return true;
            }

            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(messageManager.getMessage("general.player_not_found"));
                return true;
            }
        } else {
            target = (Player) sender;
        }

        if (target.isFlying()) {
            target.setFlySpeed(speed);
        } else {
            target.setWalkSpeed(speed);
        }

        target.sendMessage(messageManager.getMessage("speed.success").replace("{speed}", String.valueOf(speed * 10)));
        if (!target.equals(sender)) {
            sender.sendMessage(messageManager.getMessage("speed.others_success")
                    .replace("{player}", target.getName())
                    .replace("{speed}", String.valueOf(speed * 10)));
        }

        return true;
    }
}