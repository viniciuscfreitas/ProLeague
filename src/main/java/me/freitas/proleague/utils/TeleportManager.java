package me.freitas.proleague.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TeleportManager {
    private static final Map<Player, Player> teleportRequests = new HashMap<>();

    public static void sendTeleportRequest(Player sender, Player target) {
        teleportRequests.put(target, sender);
        target.sendMessage("§e" + sender.getName() + " quer se teleportar para você. Digite §a/tpaccept §epara aceitar ou §c/tpdeny §epara recusar.");
        sender.sendMessage("§aPedido de teleporte enviado para " + target.getName() + "!");
    }

    public static void acceptTeleportRequest(Player target) {
        if (!teleportRequests.containsKey(target)) {
            target.sendMessage("§cVocê não tem pedidos de teleporte pendentes.");
            return;
        }
        Player requester = teleportRequests.remove(target);
        if (requester != null && requester.isOnline()) {
            requester.teleport(target.getLocation());
            requester.sendMessage("§aPedido aceito! Você foi teleportado para " + target.getName() + ".");
        }
    }

    public static void denyTeleportRequest(Player target) {
        if (!teleportRequests.containsKey(target)) {
            target.sendMessage("§cVocê não tem pedidos de teleporte pendentes.");
            return;
        }
        Player requester = teleportRequests.remove(target);
        if (requester != null && requester.isOnline()) {
            requester.sendMessage("§cSeu pedido de teleporte para " + target.getName() + " foi negado.");
        }
        target.sendMessage("§aPedido de teleporte negado.");
    }
}