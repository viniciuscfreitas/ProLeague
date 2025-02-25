package me.freitas.proleague.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandHat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("proleague.hat")) {
            player.sendMessage("§cVocê não tem permissão para usar este comando.");
            return true;
        }

        ItemStack handItem = player.getInventory().getItemInHand();
        ItemStack helmet = player.getInventory().getHelmet();

        if (handItem.getType() == Material.AIR && helmet != null) {
            player.getInventory().setItemInHand(helmet);
            player.getInventory().setHelmet(null);
            player.sendMessage("§aVocê removeu seu chapéu.");
        } else if (handItem.getType() != Material.AIR) {
            player.getInventory().setHelmet(handItem);
            player.getInventory().setItemInHand(null);
            player.sendMessage("§aVocê colocou " + handItem.getType() + " na cabeça!");
        } else {
            player.sendMessage("§cVocê precisa segurar um item para usar /hat!");
        }

        return true;
    }
}