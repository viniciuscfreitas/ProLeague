package me.freitas.proleague.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class CommandInvsee implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }

        final Player viewer = (Player) sender;
        if (!viewer.hasPermission("proleague.invsee")) {
            viewer.sendMessage("§cVocê não tem permissão para usar este comando.");
            return true;
        }

        if (args.length < 1) {
            viewer.sendMessage("§cUso correto: /invsee <jogador>");
            return true;
        }

        final Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            viewer.sendMessage("§cJogador não encontrado.");
            return true;
        }

        if (viewer.equals(target)) {
            viewer.sendMessage("§cVocê não pode abrir seu próprio inventário.");
            return true;
        }

        // Criar inventário virtual com slots extras para armadura
        final Inventory invsee = Bukkit.createInventory(viewer, 45, "Inventário de " + target.getName());

        // Copiar itens do inventário principal
        for (int i = 0; i < 36; i++) {
            invsee.setItem(i, target.getInventory().getItem(i));
        }

        // Copiar a armadura (capacete, peitoral, calça, bota)
        final ItemStack[] armor = target.getInventory().getArmorContents();
        for (int i = 0; i < 4; i++) {
            invsee.setItem(36 + i, armor[i]); // Slots 36-39 para armadura
        }

        // Abrir inventário para o administrador
        viewer.openInventory(invsee);
        viewer.sendMessage("§aVisualizando o inventário e a armadura de " + target.getName());

        // Criar uma task assíncrona para sincronizar itens e armadura em tempo real
        new BukkitRunnable() {
            @Override
            public void run() {
                if (viewer.getOpenInventory().getTitle().equals("Inventário de " + target.getName())) {
                    PlayerInventory targetInventory = target.getInventory();

                    // Atualizar itens normais
                    for (int i = 0; i < 36; i++) {
                        targetInventory.setItem(i, invsee.getItem(i));
                    }

                    // Atualizar armadura
                    targetInventory.setArmorContents(new ItemStack[]{
                            invsee.getItem(36),
                            invsee.getItem(37),
                            invsee.getItem(38),
                            invsee.getItem(39)
                    });
                } else {
                    this.cancel(); // Cancela a task quando o inventário é fechado
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("ProLeagueEssencial"), 0L, 5L); // Atualiza a cada 5 ticks (1/4 de segundo)

        return true;
    }
}