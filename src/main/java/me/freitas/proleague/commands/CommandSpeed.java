package me.freitas.proleague.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpeed implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage("§cUso correto: /speed <1-10>");
            return true;
        }

        try {
            float speed = Float.parseFloat(args[0]) / 10;
            if (speed < 0 || speed > 1) {
                player.sendMessage("§cVelocidade inválida! Use um valor entre 1 e 10.");
                return true;
            }

            if (player.isFlying()) {
                player.setFlySpeed(speed);
                player.sendMessage("§aVelocidade de voo ajustada para " + args[0]);
            } else {
                player.setWalkSpeed(speed);
                player.sendMessage("§aVelocidade de caminhada ajustada para " + args[0]);
            }
        } catch (NumberFormatException e) {
            player.sendMessage("§cNúmero inválido! Use um valor entre 1 e 10.");
        }

        return true;
    }
}