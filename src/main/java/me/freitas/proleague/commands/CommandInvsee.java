package me.freitas.proleague.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CommandInvsee implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }

        Player viewer = (Player) sender;
        if (!viewer.hasPermission("proleague.invsee")) {
            viewer.sendMessage("§cVocê não tem permissão para usar este comando.");
            return true;
        }

        if (args.length < 1) {
            viewer.sendMessage("§cUso correto: /invsee <jogador>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            viewer.sendMessage("§cJogador não encontrado.");
            return true;
        }

        // Criando um inventário virtual para exibir o inventário e a armadura do jogador alvo
        Inventory invsee = Bukkit.createInventory(null, 45, "Inventário de " + target.getName());

        // Copiando o inventário principal
        ItemStack[] targetInventory = target.getInventory().getContents();
        for (int i = 0; i < targetInventory.length; i++) {
            invsee.setItem(i, targetInventory[i]);
        }

        // Copiando a armadura (slots 36-39)
        ItemStack[] armor = target.getInventory().getArmorContents();
        for (int i = 0; i < armor.length; i++) {
            invsee.setItem(36 + i, armor[i]);
        }

        // Abrindo o inventário para o visualizador
        viewer.openInventory(invsee);
        viewer.sendMessage("§aVisualizando o inventário e armadura de " + target.getName());

        return true;
    }
}