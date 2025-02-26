package me.freitas.proleague.commands.admin;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.managers.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHeal implements CommandExecutor {

    private final MessageManager messageManager;

    public CommandHeal(ProLeagueEssencial plugin) {
        this.messageManager = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(messageManager.getMessage("general.only_players"));
            return true;
        }

        Player target;

        // Se um nome for fornecido, tentar encontrar o jogador
        if (args.length > 0) {
            target = sender.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(messageManager.getMessage("general.player_not_found").replace("{player}", args[0]));
                return true;
            }
        } else {
            target = (Player) sender;
        }

        // Verificar permissões
        if (!sender.hasPermission("proleague.heal")) {
            sender.sendMessage(messageManager.getMessage("general.no_permission"));
            return true;
        }

        // Restaurar a vida do jogador
        target.setHealth(target.getMaxHealth());
        target.setFoodLevel(20);
        target.sendMessage(messageManager.getMessage("heal.healed"));

        // Se o comando foi usado em outra pessoa, avisar quem curou
        if (!target.equals(sender)) {
            sender.sendMessage(messageManager.getMessage("heal.success").replace("{player}", target.getName()));
        }

        return true;
    }
}