package me.freitas.proleague.commands;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.utils.TeleportManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTeleport implements CommandExecutor {
    private final ProLeagueEssencial plugin;

    public CommandTeleport(ProLeagueEssencial plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }

        Player player = (Player) sender;
        String command = cmd.getName().toLowerCase();

        if (command.equals("tp")) {
            if (args.length < 1) {
                player.sendMessage("§cUso correto: /tp <jogador>");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage("§cJogador não encontrado.");
                return true;
            }
            player.teleport(target.getLocation());
            player.sendMessage("§aTeleportado para " + target.getName() + "!");
            return true;
        }

        if (command.equals("tpa")) {
            if (args.length < 1) {
                player.sendMessage("§cUso correto: /tpa <jogador>");
                return true;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage("§cJogador não encontrado.");
                return true;
            }
            TeleportManager.sendTeleportRequest(player, target);
            return true;
        }

        if (command.equals("tpaccept")) {
            TeleportManager.acceptTeleportRequest(player);
            return true;
        }

        if (command.equals("tpdeny")) {
            TeleportManager.denyTeleportRequest(player);
            return true;
        }

        return false;
    }
}