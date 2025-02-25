package me.freitas.proleague.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandKick implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("proleague.admin")) {
            sender.sendMessage("§cVocê não tem permissão para usar este comando.");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage("§cUso correto: /kick <jogador> [motivo]");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cJogador não encontrado.");
            return true;
        }

        // Concatenação manual do motivo, compatível com Java 7
        StringBuilder reason = new StringBuilder("Sem motivo especificado");
        if (args.length > 1) {
            reason = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                reason.append(args[i]).append(" ");
            }
        }

        target.kickPlayer("§cVocê foi expulso do servidor.\n§eMotivo: " + reason.toString().trim());
        sender.sendMessage("§aJogador " + target.getName() + " foi expulso com sucesso!");

        return true;
    }
}