package me.freitas.proleague.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRealName implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("proleague.realname")) {
            sender.sendMessage("§cVocê não tem permissão para usar este comando.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§cUso correto: /realname <nickname>");
            return true;
        }

        String nickname = args[0];
        Player foundPlayer = null;

        // Verifica se algum jogador online tem esse nickname
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getDisplayName().equalsIgnoreCase(nickname)) {
                foundPlayer = player;
                break;
            }
        }

        if (foundPlayer == null) {
            sender.sendMessage("§cNenhum jogador encontrado com esse nickname.");
            return true;
        }

        sender.sendMessage("§aO nome real de §e" + nickname + " §aé §e" + foundPlayer.getName());
        return true;
    }
}