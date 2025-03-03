package me.freitas.proleague.listeners;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.gui.KitGUI;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

public class KitGUIListener implements Listener {

    private final ProLeagueEssencial plugin;

    public KitGUIListener(ProLeagueEssencial plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() == null || event.getView().getTitle() == null) return;
        // Verifica se o inventário clicado é o de Kits Disponíveis
        if (!event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GREEN + "Kits Disponíveis")) {
            return;
        }

        event.setCancelled(true); // Impede movimentação dos itens

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta() || clickedItem.getItemMeta().getDisplayName() == null) {
            return;
        }

        // Obtém o nome do kit (removendo cores)
        String kitName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());
        Player player = (Player) event.getWhoClicked();
        plugin.getKitManager().giveKit(player, kitName, false);
        player.closeInventory();
    }
}