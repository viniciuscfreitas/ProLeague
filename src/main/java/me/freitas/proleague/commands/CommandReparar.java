package me.freitas.proleague.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandReparar implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Apenas jogadores podem reparar itens.");
            return true;
        }

        Player target;

        if (args.length == 0) {
            // Se não houver argumentos, repara apenas o item na mão do próprio jogador
            target = (Player) sender;
            repararItemMao(target);
            sender.sendMessage(ChatColor.GREEN + "Seu item na mão foi reparado!");
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("tudo")) {
            // Repara TODO o inventário + armadura do próprio jogador
            target = (Player) sender;
            repararInventario(target);
            repararArmadura(target);
            sender.sendMessage(ChatColor.GREEN + "Todos os seus itens e armadura foram reparados!");
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("tudo")) {
            // Repara TODO o inventário + armadura de outro jogador
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Jogador não encontrado.");
                return true;
            }

            repararInventario(target);
            repararArmadura(target);
            sender.sendMessage(ChatColor.GREEN + "Os itens e armadura de " + target.getName() + " foram reparados!");
            target.sendMessage(ChatColor.GREEN + "Todos os seus itens e armadura foram reparados por " + sender.getName() + "!");
            return true;
        }

        sender.sendMessage(ChatColor.RED + "Uso correto: /reparar [tudo] [jogador]");
        return true;
    }

    private void repararItemMao(Player player) {
        ItemStack item = player.getItemInHand();
        if (item == null || item.getType() == Material.AIR) {
            player.sendMessage(ChatColor.RED + "Você não está segurando um item válido.");
            return;
        }
        item.setDurability((short) 0);
    }

    private void repararInventario(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                item.setDurability((short) 0);
            }
        }
    }

    private void repararArmadura(Player player) {
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && item.getType() != Material.AIR) {
                item.setDurability((short) 0);
            }
        }
    }
}