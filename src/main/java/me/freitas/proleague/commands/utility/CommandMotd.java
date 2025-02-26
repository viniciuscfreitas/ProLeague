package me.freitas.proleague.commands.utility;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandMotd implements CommandExecutor {
    private final ProLeagueEssencial plugin;

    public CommandMotd(ProLeagueEssencial plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Apenas jogadores podem ver o MOTD.");
            return true;
        }

        Player player = (Player) sender;
        FileConfiguration config = plugin.getConfig();

        // Obtém a mensagem de boas-vindas do config.yml
        String motd = config.getString("motd", "&6Bem-vindo ao servidor ProLeagueEssencial!");

        // Envia o MOTD formatado
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', motd));
        return true;
    }
}