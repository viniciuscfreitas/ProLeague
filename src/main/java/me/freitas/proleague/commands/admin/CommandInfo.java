package me.freitas.proleague.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CommandInfo implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 && sender instanceof Player) {
            return showPlayerInfo(sender, (Player) sender);
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Jogador não encontrado.");
                return true;
            }
            return showPlayerInfo(sender, target);
        } else {
            sender.sendMessage(ChatColor.RED + "Uso correto: /info [jogador]");
            return true;
        }
    }

    private boolean showPlayerInfo(CommandSender sender, Player player) {
        InetSocketAddress address = player.getAddress();
        String ip = (address != null) ? address.getAddress().getHostAddress() : "Desconhecido";
        String location = getGeoLocation(ip);

        sender.sendMessage(ChatColor.GOLD + "===== [ Informações do Jogador ] =====");
        sender.sendMessage(ChatColor.YELLOW + "Nome: " + ChatColor.WHITE + player.getName());
        sender.sendMessage(ChatColor.YELLOW + "IP: " + ChatColor.WHITE + ip);
        sender.sendMessage(ChatColor.YELLOW + "Localização: " + ChatColor.WHITE + location);
        sender.sendMessage(ChatColor.YELLOW + "Modo de Jogo: " + ChatColor.WHITE + player.getGameMode().toString());
        sender.sendMessage(ChatColor.YELLOW + "Vida: " + ChatColor.WHITE + player.getHealth() + " ❤");
        sender.sendMessage(ChatColor.YELLOW + "Fome: " + ChatColor.WHITE + player.getFoodLevel() + " 🍗");
        sender.sendMessage(ChatColor.YELLOW + "Nível de Experiência: " + ChatColor.WHITE + player.getLevel());
        sender.sendMessage(ChatColor.GOLD + "=====================================");

        return true;
    }

    private String getGeoLocation(String ip) {
        if (ip.equals("127.0.0.1") || ip.startsWith("192.168.") || ip.startsWith("10.") || ip.startsWith("172.")) {
            return "IP local - Sem geolocalização";
        }

        try {
            URL url = new URL("http://ip-api.com/json/" + ip + "?fields=city,country");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.toString());

            String city = json.get("city") != null ? json.get("city").toString() : "Desconhecida";
            String country = json.get("country") != null ? json.get("country").toString() : "Desconhecido";

            return city + ", " + country;
        } catch (Exception e) {
            return "Erro ao obter localização";
        }
    }
}