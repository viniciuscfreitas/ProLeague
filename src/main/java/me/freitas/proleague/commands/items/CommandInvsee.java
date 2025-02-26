package me.freitas.proleague.commands.items;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CommandInvsee implements CommandExecutor, Listener {

    private final Map<String, String> viewingInventory = new HashMap<>(); // Admin -> Jogador observado

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

        if (viewer.getName().equalsIgnoreCase(target.getName())) {
            viewer.sendMessage("§cVocê não pode abrir seu próprio inventário!");
            return true;
        }

        // Criar inventário espelho
        Inventory invsee = Bukkit.createInventory(viewer, 45, "Inventário de " + target.getName());

        // Copiar inventário do jogador
        for (int i = 0; i < 36; i++) {
            invsee.setItem(i, target.getInventory().getItem(i));
        }

        // Copiar armadura (slots 36-39)
        ItemStack[] armor = target.getInventory().getArmorContents();
        for (int i = 0; i < armor.length; i++) {
            invsee.setItem(36 + i, armor[i]);
        }

        // Armazenar referência para controle do inventário
        viewingInventory.put(viewer.getName(), target.getName());

        // Abrir inventário para o administrador
        viewer.openInventory(invsee);

        return true;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        HumanEntity entity = event.getWhoClicked();
        if (!(entity instanceof Player)) return;

        Player admin = (Player) entity;
        String targetName = viewingInventory.get(admin.getName());

        if (targetName == null) return; // Não é um inventário do /invsee

        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            viewingInventory.remove(admin.getName());
            return;
        }

        Inventory invsee = event.getInventory(); // Corrigido para 1.5.2
        int slot = event.getRawSlot();

        // Evita cliques fora do inventário alvo
        if (slot < 0 || slot >= invsee.getSize()) return;

        event.setCancelled(false); // Permitir edição do inventário

        // Se for dentro do inventário visualizado
        if (slot < 36) { // Inventário principal
            target.getInventory().setItem(slot, invsee.getItem(slot));
        } else if (slot >= 36 && slot < 40) { // Armadura
            ItemStack[] armor = target.getInventory().getArmorContents();
            armor[slot - 36] = invsee.getItem(slot);
            target.getInventory().setArmorContents(armor);
        }

        target.updateInventory();
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player admin = (Player) event.getPlayer();
        String targetName = viewingInventory.remove(admin.getName());

        if (targetName == null) return;

        Player target = Bukkit.getPlayer(targetName);
        if (target == null) return;

        target.updateInventory();
    }
}