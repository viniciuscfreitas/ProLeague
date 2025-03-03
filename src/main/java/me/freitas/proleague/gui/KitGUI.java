package me.freitas.proleague.gui;

import me.freitas.proleague.ProLeagueEssencial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KitGUI {

    /**
     * Cria um inventário personalizado para listar os kits.
     *
     * @param plugin O plugin principal.
     * @param player O jogador que está abrindo a GUI.
     * @return O inventário com os kits.
     */
    public static Inventory createKitInventory(ProLeagueEssencial plugin, Player player) {
        // Obtenha a seção "kits" do config
        ConfigurationSection kitsSection = plugin.getKitConfigManager().getConfig().getConfigurationSection("kits");
        if (kitsSection == null || kitsSection.getKeys(false).isEmpty()) {
            // Se não houver kits, retorna um inventário com 9 slots e título informativo
            return Bukkit.createInventory(null, 9, ChatColor.RED + "Nenhum Kit Disponível");
        }

        // Calcula o número de kits e determina o tamanho do inventário (múltiplo de 9)
        int kitCount = kitsSection.getKeys(false).size();
        int size = ((kitCount - 1) / 9 + 1) * 9;
        if (size > 54) { // Limite máximo de slots em um inventário tipo baú
            size = 54;
        }

        Inventory kitInv = Bukkit.createInventory(null, size, ChatColor.DARK_GREEN + "Kits Disponíveis");

        int index = 0;
        // Para cada kit, cria um ícone representativo
        for (String kitName : kitsSection.getKeys(false)) {
            // Ícone padrão: usamos um CHEST
            ItemStack icon = new ItemStack(Material.CHEST, 1);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + kitName);

            // Adiciona lore com informações extras (delay e permissão)
            ConfigurationSection kitSec = kitsSection.getConfigurationSection(kitName);
            int delay = kitSec.getInt("delay", 0);
            String permission = kitSec.getString("permissions", "Nenhuma");
            List<String> lore = new ArrayList<String>();
            lore.add(ChatColor.YELLOW + "Delay: " + delay + "s");
            lore.add(ChatColor.YELLOW + "Permissão: " + permission);
            meta.setLore(lore);

            icon.setItemMeta(meta);
            kitInv.setItem(index, icon);
            index++;
            if (index >= size) break; // Caso haja mais kits que slots disponíveis
        }
        return kitInv;
    }
}