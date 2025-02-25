package me.freitas.proleague.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandHorario implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sender.sendMessage("§aHorário do servidor: §e" + sdf.format(new Date()));
        return true;
    }
}