package me.freitas.proleague.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSudo implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("proleague.sudo")) {
            sender.sendMessage("§cVocê não tem permissão para usar este comando.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("§cUso correto: /sudo <jogador> <mensagem/comando>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cJogador não encontrado.");
            return true;
        }

        // Corrigindo erro do `String.join(...)`
        StringBuilder message = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            message.append(args[i]).append(" ");
        }

        String commandToRun = message.toString().trim();
        if (commandToRun.startsWith("/")) {
            target.performCommand(commandToRun.substring(1));
            sender.sendMessage("§aForçou o jogador " + target.getName() + " a executar: " + commandToRun);
        } else {
            target.chat(commandToRun);
            sender.sendMessage("§aForçou o jogador " + target.getName() + " a dizer: " + commandToRun);
        }

        return true;
    }
}