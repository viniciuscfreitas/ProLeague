package me.freitas.proleague.managers;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.utils.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitManager {

    private final ProLeagueEssencial plugin;
    // cooldowns<NomeDoJogador, <KitName, ÚltimoUsoEmSegundos>>
    private final Map<String, Map<String, Long>> cooldowns = new HashMap<String, Map<String, Long>>();

    public KitManager(ProLeagueEssencial plugin, KitConfigManager kitConfigManager) {
        this.plugin = plugin;
    }

    /**
     * Aplica o kit ao jogador.
     *
     * @param player       O jogador que receberá o kit.
     * @param kitName      Nome do kit conforme configurado.
     * @param bypassChecks Se true, ignora a verificação de permissão e de cooldown.
     */
    public void giveKit(Player player, String kitName, boolean bypassChecks) {
        // Localiza a seção do kit no kits.yml via KitConfigManager
        ConfigurationSection kitSection = plugin.getKitConfigManager().getConfig().getConfigurationSection("kits." + kitName);
        if (kitSection == null) {
            player.sendMessage(ChatColor.RED + "Este kit não existe: " + kitName);
            return;
        }

        if (!bypassChecks) {
            // Verifica a permissão do kit
            String permission = kitSection.getString("permissions");
            if (permission != null && !player.hasPermission(permission)) {
                player.sendMessage(ChatColor.RED + "Você não tem permissão para usar este kit.");
                return;
            }

            // Verifica o cooldown
            int delay = kitSection.getInt("delay", 0);
            String playerName = player.getName();
            long currentTime = System.currentTimeMillis() / 1000;

            if (!cooldowns.containsKey(playerName)) {
                cooldowns.put(playerName, new HashMap<String, Long>());
            }
            Map<String, Long> playerCooldowns = cooldowns.get(playerName);

            if (playerCooldowns.containsKey(kitName)) {
                long lastUsed = playerCooldowns.get(kitName);
                long timeLeft = (lastUsed + delay) - currentTime;
                if (timeLeft > 0) {
                    String formattedTime = TimeUtil.formatTime(timeLeft);
                    player.sendMessage(ChatColor.RED + "Aguarde " + formattedTime + " para usar este kit novamente.");
                    return;
                }
            }
        }

        // Entrega os itens
        List<String> itemsList = kitSection.getStringList("items");
        if (itemsList == null || itemsList.isEmpty()) {
            player.sendMessage(ChatColor.RED + "O kit '" + kitName + "' não possui itens configurados.");
            return;
        }

        for (String itemData : itemsList) {
            // Exemplo de itemData para itens normais: "iron_sword:1 unbreaking:1 sharpness:1"
            // Exemplo para poção: "potion:1:8194 unbreaking:1"
            String[] parts = itemData.split(" ");
            String[] mainPart = parts[0].split(":");
            if (mainPart.length < 2) {
                Bukkit.getLogger().warning("[ProLeague] Formato inválido em: " + itemData);
                continue;
            }

            Material material = Material.getMaterial(mainPart[0].toUpperCase());
            if (material == null) {
                Bukkit.getLogger().warning("[ProLeague] Material inválido: " + mainPart[0]);
                continue;
            }

            int amount = 1;
            try {
                amount = Integer.parseInt(mainPart[1]);
            } catch (NumberFormatException e) {
                // Se ocorrer erro, continua com 1
            }

            ItemStack item;
            // Se o material for POTION, trata o valor de dano
            if (material == Material.POTION) {
                short damage = 0;
                if (mainPart.length >= 3) {
                    try {
                        damage = Short.parseShort(mainPart[2]);
                    } catch (NumberFormatException e) {
                        Bukkit.getLogger().warning("[ProLeague] Valor de dano inválido para a poção em: " + itemData);
                    }
                } else {
                    Bukkit.getLogger().warning("[ProLeague] Poção sem valor de dano definido em: " + itemData);
                }
                item = new ItemStack(material, amount, damage);
            } else {
                item = new ItemStack(material, amount);
            }

            // Aplica encantamentos, se houver
            if (parts.length > 1) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    // Se for poção, e o mainPart tiver 3 elementos, já usamos o terceiro como dano,
                    // mas os encantamentos virão em partes separadas (a partir de parts[1]).
                    for (int i = 1; i < parts.length; i++) {
                        String[] enchantData = parts[i].split(":");
                        if (enchantData.length < 2) {
                            continue;
                        }
                        // Caso seja poção, se o primeiro parte já contém o dano, não reinterprete-o como encantamento
                        // Portanto, não há alteração extra aqui.
                        Enchantment enchant = Enchantment.getByName(enchantData[0].toUpperCase());
                        if (enchant != null) {
                            int lvl = 1;
                            try {
                                lvl = Integer.parseInt(enchantData[1]);
                            } catch (NumberFormatException ignore) {
                            }
                            meta.addEnchant(enchant, lvl, true);
                        }
                    }
                    item.setItemMeta(meta);
                }
            }

            player.getInventory().addItem(item);
        }

        // Atualiza o cooldown apenas se não estiver ignorando as checagens
        if (!bypassChecks) {
            String playerName = player.getName();
            long currentTime = System.currentTimeMillis() / 1000;
            if (!cooldowns.containsKey(playerName)) {
                cooldowns.put(playerName, new HashMap<String, Long>());
            }
            Map<String, Long> playerCooldowns = cooldowns.get(playerName);
            playerCooldowns.put(kitName, currentTime);
        }

        player.sendMessage(ChatColor.GREEN + "Você recebeu o kit '" + ChatColor.YELLOW + kitName + ChatColor.GREEN + "'!");
    }
}