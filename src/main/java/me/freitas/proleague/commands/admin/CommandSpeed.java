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
        if (!sender.hasPermission("proleague.speed")) {
            sender.sendMessage(messageManager.getMessage("general.no_permission"));
            return true;
        }

        if (args.length < 1 || args.length > 2) {
            sender.sendMessage(messageManager.getMessage("speed.usage"));
            return true;
        }

        Player target;
        if (args.length == 2) {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(messageManager.getMessage("general.player_not_found"));
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(messageManager.getMessage("general.only_players"));
                return true;
            }
            target = (Player) sender;
        }

        try {
            float speed = Float.parseFloat(args[0]);
            if (speed < 0 || speed > 10) {
                sender.sendMessage(messageManager.getMessage("speed.out_of_range"));
                return true;
            }

            float finalSpeed = speed / 10; // Convertendo para o range do Minecraft (0.0 - 1.0)
            if (target.isFlying()) {
                target.setFlySpeed(finalSpeed);
            } else {
                target.setWalkSpeed(finalSpeed);
            }

            if (target.equals(sender)) {
                sender.sendMessage(messageManager.getMessage("speed.success").replace("{speed}", args[0]));
            } else {
                sender.sendMessage(messageManager.getMessage("speed.others_success")
                        .replace("{player}", target.getName())
                        .replace("{speed}", args[0]));
                target.sendMessage(messageManager.getMessage("speed.success").replace("{speed}", args[0]));
            }

        } catch (NumberFormatException e) {
            sender.sendMessage(messageManager.getMessage("speed.invalid_number"));
        }
        return true;
    }
}