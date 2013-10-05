package com.minegusta.mauswashere.command;

import com.censoredsoftware.helper.WrappedCommand;
import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class ExplodeCommand extends WrappedCommand {

    public ExplodeCommand() {
        super(false);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("explode");
    }

    @Override
    public boolean processCommand(CommandSender sender, Command command, String[] args) {
        if (!sender.hasPermission("explode.use")) {
            sender.sendMessage("Nice try " + sender + ", no permissions for you >:3");
        } else if (command.getName().equalsIgnoreCase("explode") && sender.hasPermission("explode.use")) {
            try {
                Player target = Bukkit.getPlayer(args[0]);
                target.getWorld().createExplosion(target.getLocation().getX(), target.getLocation().getY(), target.getLocation().getZ(), 4F, false, false);
                Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + sender.getName() + " Exploded " + args[0] + "! That'll teach them.");
            } catch (Exception e) {
                sender.sendMessage("Player is not real or not online. Try again.");
            }
        }
        return true;
    }
}
