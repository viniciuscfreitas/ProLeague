package me.freitas.proleague.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.MessageManager;

public class CommandClearChat implements CommandExecutor {
    private final MessageManager messageManager;

    public CommandClearChat(ProLeagueEssencial plugin) {
        this.messageManager = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Verifica se o usuário tem permissão
        if (!sender.hasPermission("proleague.clearchat")) {
            sender.sendMessage(messageManager.getMessage("general.no_permission"));
            return true;
        }

        // Limpa o chat enviando 100 linhas vazias
        for (int i = 0; i < 100; i++) {
            Bukkit.broadcastMessage("");
        }

        // Mensagem para todos os jogadores
        Bukkit.broadcastMessage(messageManager.getMessage("admin.chat_cleared").replace("{player}", sender.getName()));

        // Mensagem de confirmação para o console
        if (!(sender instanceof org.bukkit.entity.Player)) {
            sender.sendMessage("[ProLeague] O chat foi limpo com sucesso.");
        }

        return true;
    }
}