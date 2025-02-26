package me.freitas.proleague.commands.admin;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.net.InetSocketAddress;

public class CommandInfo implements CommandExecutor {
    private final ProLeagueEssencial plugin;
    private final MessageManager messageManager;

    public CommandInfo(ProLeagueEssencial plugin) {
        this.plugin = plugin;
        this.messageManager = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 && sender instanceof Player) {
            return showPlayerInfo(sender, (Player) sender);
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(messageManager.getMessage("info.not-found").replace("{player}", args[0]));
                return true;
            }
            return showPlayerInfo(sender, target);
        } else {
            sender.sendMessage(messageManager.getMessage("info.usage"));
            return true;
        }
    }

    private boolean showPlayerInfo(CommandSender sender, Player player) {
        InetSocketAddress address = player.getAddress();
        String ip = (address != null) ? address.getAddress().getHostAddress() : "Desconhecido";

        sender.sendMessage(messageManager.getMessage("info.header"));
        sender.sendMessage(messageManager.getMessage("info.name").replace("{player}", player.getName()));
        sender.sendMessage(messageManager.getMessage("info.ip").replace("{ip}", ip));
        sender.sendMessage(messageManager.getMessage("info.health").replace("{health}", String.valueOf(player.getHealth())));
        sender.sendMessage(messageManager.getMessage("info.food").replace("{food}", String.valueOf(player.getFoodLevel())));
        sender.sendMessage(messageManager.getMessage("info.exp").replace("{exp}", String.valueOf(player.getLevel())));
        sender.sendMessage(messageManager.getMessage("info.footer"));

        return true;
    }
}