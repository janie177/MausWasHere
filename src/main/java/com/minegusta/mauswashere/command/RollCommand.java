package com.minegusta.mauswashere.command;

import com.censoredsoftware.helper.WrappedCommand;
import com.censoredsoftware.util.Randoms;
import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Set;

public class RollCommand extends WrappedCommand {

    public RollCommand() {
        super(false);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("roll");
    }

    @Override
    public boolean processCommand(final CommandSender sender, Command command, String[] args) {
        if (!sender.hasPermission("minegusta.roll")) {
            sender.sendMessage("You do not have permissions to roll. They won't see you rollin'.");
        } else if (command.getName().equalsIgnoreCase("roll")) {
            int number = Randoms.generateIntRange(1, 51);
            Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "-----------------------------");
            Bukkit.getServer().broadcastMessage(ChatColor.GOLD + sender.getName() + " rolled " + ChatColor.DARK_RED + number);
            Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "-----------------------------");
        }
        return true;
    }

}
