package me.freitas.proleague.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandKeepInventory implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }

        if (!sender.hasPermission("proleague.keepinventory")) {
            sender.sendMessage("§cVocê não tem permissão para usar este comando.");
            return true;
        }

        if (args.length < 1 || (!args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off"))) {
            sender.sendMessage("§cUso correto: /keepinventory <on/off>");
            return true;
        }

        boolean keepInventory = args[0].equalsIgnoreCase("on");

        // Definir regra manualmente usando comandos
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule keepInventory " + (keepInventory ? "true" : "false"));

        sender.sendMessage("§aA regra de manter inventário foi definida para: " + (keepInventory ? "§2Ativado" : "§cDesativado"));
        return true;
    }
}