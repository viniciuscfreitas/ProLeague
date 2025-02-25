package me.freitas.proleague.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandNick implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage("§cUso correto: /nick <novo_nick>");
            return true;
        }

        player.setDisplayName(args[0]);
        player.setPlayerListName(args[0]);
        player.sendMessage("§aSeu nick agora é: " + args[0]);

        return true;
    }
}