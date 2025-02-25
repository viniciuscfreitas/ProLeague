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
            sender.sendMessage("§cApenas jogadores podem usar esse comando.");
            return true;
        }

        Player player = (Player) sender;
        ItemStack itemInHand = player.getItemInHand();

        if (itemInHand.getType() == Material.AIR) {
            player.sendMessage("§cVocê precisa estar segurando um item para usá-lo como chapéu.");
            return true;
        }

        ItemStack helmet = player.getInventory().getHelmet();
        player.getInventory().setHelmet(itemInHand);
        player.setItemInHand(helmet);
        player.sendMessage("§aItem definido como chapéu!");

        return true;
    }
}