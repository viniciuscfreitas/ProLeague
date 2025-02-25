package me.freitas.proleague.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGamemode implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1 || args.length > 2) {
            sender.sendMessage("§cUso correto: /gm <0|1|2> [jogador]");
            return true;
        }

        Player target;
        if (args.length == 2) {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§cJogador não encontrado.");
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cApenas jogadores podem alterar seu próprio modo de jogo.");
                return true;
            }
            target = (Player) sender;
        }

        GameMode mode;
        switch (args[0]) {
            case "0":
                mode = GameMode.SURVIVAL;
                break;
            case "1":
                mode = GameMode.CREATIVE;
                break;
            case "2":
                mode = GameMode.ADVENTURE;
                break;
            default:
                sender.sendMessage("§cModo inválido! Use: 0 (Sobrevivência), 1 (Criativo), 2 (Aventura).");
                return true;
        }

        target.setGameMode(mode);
        sender.sendMessage("§aModo de jogo de " + target.getName() + " alterado para " + mode.name().toLowerCase() + "!");
        return true;
    }
}