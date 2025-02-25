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
        if (!player.hasPermission("proleague.nick")) {
            player.sendMessage("§cVocê não tem permissão para usar este comando.");
            return true;
        }

        if (args.length < 1) {
            player.sendMessage("§cUso correto: /nick <novo_nome>");
            return true;
        }

        String newNick = args[0];
        player.setDisplayName(newNick);
        player.setPlayerListName(newNick);
        player.sendMessage("§aSeu apelido foi alterado para " + newNick);

        return true;
    }
}