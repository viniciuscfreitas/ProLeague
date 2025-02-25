package me.freitas.proleague.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CommandGod implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar esse comando.");
            return true;
        }

        Player player = (Player) sender;
        if (player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) {
            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            player.sendMessage("§cModo Deus desativado!");
        } else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 4));
            player.sendMessage("§aModo Deus ativado!");
        }
        return true;
    }
}