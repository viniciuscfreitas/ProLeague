package me.freitas.proleague.commands;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHistory implements CommandExecutor {

    private final ProLeagueEssencial plugin;

    // Construtor atualizado para receber a instância do plugin
    public CommandHistory(ProLeagueEssencial plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Uso correto: /history <jogador>");
            return true;
        }
        Player target = plugin.getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Jogador não encontrado.");
            return true;
        }
        // Exemplo: busca e exibe o histórico de punições do jogador a partir do config
        sender.sendMessage(ChatColor.YELLOW + "Histórico de punições de " + target.getName() + ":");
        // Aqui você pode implementar a lógica de exibição do histórico (por exemplo, ler uma lista do config)
        // Exemplo simplificado:
        if (plugin.getConfig().contains("warns." + target.getName())) {
            for (String warn : plugin.getConfig().getStringList("warns." + target.getName())) {
                sender.sendMessage(ChatColor.GRAY + "- " + warn);
            }
        } else {
            sender.sendMessage(ChatColor.GRAY + "Nenhum histórico encontrado.");
        }
        return true;
    }
}