package me.freitas.proleague.commands.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CommandEncantar implements CommandExecutor {

    // Mapeia nomes comuns para os encantamentos do Bukkit 1.5.2
    private static final Map<String, Enchantment> ENCHANTMENTS = new HashMap<>();

    static {
        ENCHANTMENTS.put("PROTECAO", Enchantment.PROTECTION_ENVIRONMENTAL);
        ENCHANTMENTS.put("PROTECTION", Enchantment.PROTECTION_ENVIRONMENTAL);
        ENCHANTMENTS.put("FOGO", Enchantment.PROTECTION_FIRE);
        ENCHANTMENTS.put("FIRE_PROTECTION", Enchantment.PROTECTION_FIRE);
        ENCHANTMENTS.put("QUEDA", Enchantment.PROTECTION_FALL);
        ENCHANTMENTS.put("FEATHER_FALLING", Enchantment.PROTECTION_FALL);
        ENCHANTMENTS.put("EXPLOSAO", Enchantment.PROTECTION_EXPLOSIONS);
        ENCHANTMENTS.put("BLAST_PROTECTION", Enchantment.PROTECTION_EXPLOSIONS);
        ENCHANTMENTS.put("PROJETEIS", Enchantment.PROTECTION_PROJECTILE);
        ENCHANTMENTS.put("PROJECTILE_PROTECTION", Enchantment.PROTECTION_PROJECTILE);
        ENCHANTMENTS.put("AQUECIMENTO", Enchantment.OXYGEN);
        ENCHANTMENTS.put("RESPIRACAO", Enchantment.OXYGEN);
        ENCHANTMENTS.put("EFFICIENCIA", Enchantment.DIG_SPEED);
        ENCHANTMENTS.put("EFFICIENCY", Enchantment.DIG_SPEED);
        ENCHANTMENTS.put("TOQUE_SUAVE", Enchantment.SILK_TOUCH);
        ENCHANTMENTS.put("SILK_TOUCH", Enchantment.SILK_TOUCH);
        ENCHANTMENTS.put("AFIACAO", Enchantment.DAMAGE_ALL);
        ENCHANTMENTS.put("SHARPNESS", Enchantment.DAMAGE_ALL);
        ENCHANTMENTS.put("ASPECTO_FLAMEJANTE", Enchantment.FIRE_ASPECT);
        ENCHANTMENTS.put("FIRE_ASPECT", Enchantment.FIRE_ASPECT);
        ENCHANTMENTS.put("FORCA", Enchantment.ARROW_DAMAGE);
        ENCHANTMENTS.put("POWER", Enchantment.ARROW_DAMAGE);
        ENCHANTMENTS.put("INFINIDADE", Enchantment.ARROW_INFINITE);
        ENCHANTMENTS.put("INFINITY", Enchantment.ARROW_INFINITE);
        ENCHANTMENTS.put("IMPACTO", Enchantment.ARROW_KNOCKBACK);
        ENCHANTMENTS.put("PUNCH", Enchantment.ARROW_KNOCKBACK);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Apenas jogadores podem usar este comando.");
            return true;
        }

        Player player = (Player) sender;

        // Verifica se o jogador forneceu os argumentos corretos
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Uso correto: /encantar <encantamento> <nível>");
            return true;
        }

        // Obtém o item na mão do jogador
        ItemStack item = player.getItemInHand(); // 1.5.2 usa getItemInHand()
        if (item == null || item.getType() == Material.AIR) {
            player.sendMessage(ChatColor.RED + "Você precisa segurar um item para encantá-lo.");
            return true;
        }

        // Obtém o encantamento pelo nome corrigido
        String enchantmentName = args[0].toUpperCase().replace("-", "_");
        Enchantment enchantment = ENCHANTMENTS.get(enchantmentName);

        if (enchantment == null) {
            player.sendMessage(ChatColor.RED + "Encantamento inválido! Tente usar nomes como PROTECTION, SHARPNESS, FIRE_ASPECT.");
            return true;
        }

        // Verifica o nível do encantamento
        int level;
        try {
            level = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "O nível do encantamento deve ser um número.");
            return true;
        }

        // Agora o nível permitido será de 1 a 10, ignorando as limitações do jogo
        if (level < 1 || level > 10) {
            player.sendMessage(ChatColor.RED + "Nível inválido! O nível deve estar entre 1 e 10.");
            return true;
        }

        // Aplica o encantamento no item, mesmo se ultrapassar o nível padrão do Minecraft
        item.addUnsafeEnchantment(enchantment, level);
        player.sendMessage(ChatColor.GREEN + "Item encantado com sucesso com " + ChatColor.AQUA + enchantment.getName() + " " + level + "!");
        return true;
    }
}