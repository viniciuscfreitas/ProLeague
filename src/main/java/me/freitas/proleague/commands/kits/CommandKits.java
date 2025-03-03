package me.freitas.proleague.commands.kits;

import me.freitas.proleague.ProLeagueEssencial;
import me.freitas.proleague.gui.KitGUI;
import me.freitas.proleague.managers.KitManager;
import me.freitas.proleague.managers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class CommandKits implements CommandExecutor {

    private final ProLeagueEssencial plugin;
    private final KitManager kitManager;
    private final MessageManager messageManager;

    public CommandKits(ProLeagueEssencial plugin) {
        this.plugin = plugin;
        this.kitManager = plugin.getKitManager();
        this.messageManager = plugin.getMessageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Apenas jogadores podem usar este comando
        if (!(sender instanceof Player)) {
            sender.sendMessage(messageManager.getMessage("kit.apenas_jogadores"));
            return true;
        }
        Player jogador = (Player) sender;

        // Se não houver argumentos, abre a GUI
        if (args.length < 1) {
            jogador.openInventory(KitGUI.createKitInventory(plugin, jogador));
            return true;
        }

        String subcomando = args[0].toLowerCase();

        switch (subcomando) {
            case "criar":
                return criarKit(jogador, args);
            case "deletar":
                return deletarKit(jogador, args);
            case "dar":
                return darKit(jogador, args);
            case "rename":
                return renomearKit(jogador, args);
            case "gui":
            case "listar":
                jogador.openInventory(KitGUI.createKitInventory(plugin, jogador));
                return true;
            default:
                // Se o argumento não for subcomando, assume que é o nome do kit para pegar para si
                return aplicarKit(jogador, subcomando);
        }
    }

    /**
     * Aplica um kit para o próprio jogador.
     * Uso: /kit <nome>
     */
    private boolean aplicarKit(Player jogador, String nomeKit) {
        kitManager.giveKit(jogador, nomeKit, false);
        Bukkit.getLogger().info("[ProLeague-Kits] " + jogador.getName() + " aplicou o kit " + nomeKit + " para si mesmo.");
        return true;
    }

    /**
     * Cria um kit baseado no inventário atual do jogador.
     * Uso: /kit criar <nome> [permissão]
     */
    private boolean criarKit(Player jogador, String[] args) {
        if (!jogador.hasPermission("proleague.kit.criar")) {
            jogador.sendMessage(messageManager.getMessage("kit.sem_permissao_criar"));
            return true;
        }
        if (args.length < 2) {
            jogador.sendMessage(messageManager.getMessage("kit.uso_criar"));
            return true;
        }
        String nomeKit = args[1].toLowerCase();
        // Usando o kits.yml através do plugin (KitConfigManager já está integrado)
        ConfigurationSection kitsSection = plugin.getKitConfigManager().getConfig().getConfigurationSection("kits");
        if (kitsSection == null) {
            kitsSection = plugin.getKitConfigManager().getConfig().createSection("kits");
        }
        if (kitsSection.getConfigurationSection(nomeKit) != null) {
            jogador.sendMessage(messageManager.getMessage("kit.ja_existe").replace("{kit}", nomeKit));
            return true;
        }
        ConfigurationSection kitSection = kitsSection.createSection(nomeKit);
        kitSection.set("display-name", "&aKit " + nomeKit);
        kitSection.set("delay", 600); // Delay padrão: 10 minutos

        // Permissão personalizada se fornecida, ou padrão "proleague.kit.<nome>"
        if (args.length >= 3) {
            kitSection.set("permissions", args[2]);
        } else {
            kitSection.set("permissions", "proleague.kit." + nomeKit);
        }

        // Salva os itens do inventário do jogador no kit
        List<String> items = kitSection.getStringList("items");
        for (ItemStack item : jogador.getInventory().getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                StringBuilder linha = new StringBuilder(item.getType().name().toLowerCase() + ":" + item.getAmount());
                ItemMeta meta = item.getItemMeta();
                if (meta != null && meta.hasEnchants()) {
                    for (Map.Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
                        linha.append(" ").append(entry.getKey().getName().toLowerCase())
                                .append(":").append(entry.getValue());
                    }
                }
                items.add(linha.toString());
            }
        }
        kitSection.set("items", items);

        // Salva as alterações no kits.yml
        plugin.getKitConfigManager().saveConfig();
        jogador.sendMessage(messageManager.getMessage("kit.criado").replace("{kit}", nomeKit));
        Bukkit.getLogger().info("[ProLeague-Kits] " + jogador.getName() + " criou o kit " + nomeKit + ".");
        return true;
    }

    /**
     * Deleta um kit.
     * Uso: /kit deletar <nome>
     */
    private boolean deletarKit(Player jogador, String[] args) {
        if (!jogador.hasPermission("proleague.kit.deletar")) {
            jogador.sendMessage(messageManager.getMessage("kit.sem_permissao_deletar"));
            return true;
        }
        if (args.length < 2) {
            jogador.sendMessage(messageManager.getMessage("kit.uso_deletar"));
            return true;
        }
        String nomeKit = args[1].toLowerCase();
        ConfigurationSection kitsSection = plugin.getKitConfigManager().getConfig().getConfigurationSection("kits");
        if (kitsSection == null || kitsSection.getConfigurationSection(nomeKit) == null) {
            jogador.sendMessage(messageManager.getMessage("kit.nao_existe").replace("{kit}", nomeKit));
            return true;
        }
        kitsSection.set(nomeKit, null);
        plugin.getKitConfigManager().saveConfig();
        jogador.sendMessage(messageManager.getMessage("kit.deletado").replace("{kit}", nomeKit));
        Bukkit.getLogger().info("[ProLeague-Kits] " + jogador.getName() + " deletou o kit " + nomeKit + ".");
        return true;
    }

    /**
     * Dá um kit para outro jogador, ignorando verificação de permissão e cooldown.
     * Uso: /kit dar <nome> <jogador>
     */
    private boolean darKit(Player jogador, String[] args) {
        if (!jogador.hasPermission("proleague.kit.dar")) {
            jogador.sendMessage(messageManager.getMessage("kit.sem_permissao_dar"));
            return true;
        }
        if (args.length < 3) {
            jogador.sendMessage(messageManager.getMessage("kit.uso_dar"));
            return true;
        }
        String nomeKit = args[1].toLowerCase();
        ConfigurationSection kitsSection = plugin.getKitConfigManager().getConfig().getConfigurationSection("kits");
        if (kitsSection == null || kitsSection.getConfigurationSection(nomeKit) == null) {
            jogador.sendMessage(messageManager.getMessage("kit.nao_existe").replace("{kit}", nomeKit));
            return true;
        }
        Player alvo = Bukkit.getPlayer(args[2]);
        if (alvo == null) {
            jogador.sendMessage(messageManager.getMessage("kit.jogador_nao_encontrado").replace("{jogador}", args[2]));
            return true;
        }
        kitManager.giveKit(alvo, nomeKit, true);
        jogador.sendMessage(messageManager.getMessage("kit.aplicado")
                .replace("{kit}", nomeKit)
                .replace("{jogador}", alvo.getName()));
        Bukkit.getLogger().info("[ProLeague-Kits] " + jogador.getName() + " deu o kit " + nomeKit + " para " + alvo.getName() + " (bypass).");
        return true;
    }

    /**
     * Renomeia um kit.
     * Uso: /kit rename <antigo> <novo>
     */
    private boolean renomearKit(Player jogador, String[] args) {
        if (!jogador.hasPermission("proleague.kit.rename")) {
            jogador.sendMessage(messageManager.getMessage("kit.sem_permissao_rename"));
            return true;
        }
        if (args.length < 3) {
            jogador.sendMessage(messageManager.getMessage("kit.uso_rename"));
            return true;
        }
        String antigoNome = args[1].toLowerCase();
        String novoNome = args[2].toLowerCase();
        ConfigurationSection kitsSection = plugin.getKitConfigManager().getConfig().getConfigurationSection("kits");
        if (kitsSection == null || kitsSection.getConfigurationSection(antigoNome) == null) {
            jogador.sendMessage(messageManager.getMessage("kit.nao_existe").replace("{kit}", antigoNome));
            return true;
        }
        if (kitsSection.getConfigurationSection(novoNome) != null) {
            jogador.sendMessage(messageManager.getMessage("kit.ja_existe").replace("{kit}", novoNome));
            return true;
        }
        // Clona a seção do kit antigo para o novo
        kitsSection.createSection(novoNome, kitsSection.getConfigurationSection(antigoNome).getValues(true));
        // Remove o kit antigo
        kitsSection.set(antigoNome, null);
        plugin.getKitConfigManager().saveConfig();
        jogador.sendMessage(messageManager.getMessage("kit.rename_sucesso")
                .replace("{old}", antigoNome)
                .replace("{new}", novoNome));
        Bukkit.getLogger().info("[ProLeague-Kits] " + jogador.getName() + " renomeou o kit de " + antigoNome + " para " + novoNome + ".");
        return true;
    }
}