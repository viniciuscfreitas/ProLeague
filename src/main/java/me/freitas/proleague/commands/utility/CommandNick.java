package me.freitas.proleague.commands.utility;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.NickManager; // Adicione esta importação
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandNick implements CommandExecutor {
    private final NickManager nickManager;
    private final ProLeagueEssencial plugin;

    public CommandNick(ProLeagueEssencial plugin) {
        this.plugin = plugin;
        this.nickManager = plugin.getNickManager(); // Corrigindo a inicialização
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Apenas jogadores podem usar este comando.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("proleague.nick")) {
            player.sendMessage(ChatColor.RED + "Você não tem permissão para usar este comando.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Uso correto: /nick <novo_nome> ou /nick off para remover.");
            return true;
        }

        if (args[0].equalsIgnoreCase("off")) {
            nickManager.removeNick(player.getName());
            player.setDisplayName(player.getName());
            player.setPlayerListName(player.getName());
            player.sendMessage(ChatColor.GREEN + "Seu nick foi removido.");
            return true;
        }

        String newNick = args[0];
        nickManager.setNick(player.getName(), newNick);
        player.setDisplayName(ChatColor.translateAlternateColorCodes('&', newNick));
        player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', newNick));

        player.sendMessage(ChatColor.GREEN + "Seu nick foi alterado para " + newNick);
        return true;
    }
}