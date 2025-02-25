package me.freitas.proleague.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandKeepXP implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }

        if (!sender.hasPermission("proleague.keepxp")) {
            sender.sendMessage("§cVocê não tem permissão para usar este comando.");
            return true;
        }

        if (args.length < 1 || (!args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off"))) {
            sender.sendMessage("§cUso correto: /keepxp <on/off>");
            return true;
        }

        boolean keepXP = args[0].equalsIgnoreCase("on");

        // Definir regra manualmente usando comandos
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule keepExp " + (keepXP ? "true" : "false"));

        sender.sendMessage("§aA regra de manter experiência foi definida para: " + (keepXP ? "§2Ativado" : "§cDesativado"));
        return true;
    }
}