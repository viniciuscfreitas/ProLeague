    package me.freitas.proleague.commands;

    import me.freitas.proleague.ProLeagueEssencial;
    import org.bukkit.Bukkit;
    import org.bukkit.ChatColor;
    import org.bukkit.command.Command;
    import org.bukkit.command.CommandExecutor;
    import org.bukkit.command.CommandSender;
    import org.bukkit.entity.Player;

    import java.util.Set;

    public class CommandMute implements CommandExecutor {
        private final ProLeagueEssencial plugin;

        public CommandMute(ProLeagueEssencial plugin) {
            this.plugin = plugin;
        }

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (args.length != 1) {
                sender.sendMessage(ChatColor.RED + "Uso correto: /mute <jogador>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Jogador não encontrado.");
                return true;
            }

            Set<String> mutedPlayers = plugin.getMutedPlayers();
            if (mutedPlayers.contains(target.getName())) {
                mutedPlayers.remove(target.getName());
                sender.sendMessage(ChatColor.GREEN + "✔ Jogador " + target.getName() + " foi desmutado!");
                target.sendMessage(ChatColor.YELLOW + "⚠ Você pode falar novamente no chat!");
            } else {
                mutedPlayers.add(target.getName());
                sender.sendMessage(ChatColor.GREEN + "✔ Jogador " + target.getName() + " foi mutado!");
                target.sendMessage(ChatColor.RED + "⚠ Você foi mutado e não pode falar no chat!");
            }

            plugin.saveConfig();
            return true;
        }

        public static boolean isMuted(ProLeagueEssencial plugin, Player player) {
            return plugin.getMutedPlayers().contains(player.getName());
        }

        public static void unmutePlayer(ProLeagueEssencial plugin, Player player) {
            plugin.getMutedPlayers().remove(player.getName());
            plugin.saveConfig();
        }
    }