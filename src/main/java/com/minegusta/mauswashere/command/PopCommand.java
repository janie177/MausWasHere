package com.minegusta.mauswashere.command;

import com.censoredsoftware.helper.WrappedCommand;
import com.google.common.collect.Sets;
import com.minegusta.mauswashere.listener.EntityListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Set;

public class PopCommand extends WrappedCommand {

    public PopCommand() {
        super(false);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("pop");
    }

    @Override
    public boolean processCommand(CommandSender sender, Command command, String[] args) {
        if (!sender.hasPermission("minegusta.pop")) {
            sender.sendMessage("Nope, No pop for you.");
        } else if (command.getName().equalsIgnoreCase("pop")) {
            if (EntityListener.toggleData.containsKey(sender.getName().toLowerCase()) && EntityListener.toggleData.get(sender.getName().toLowerCase())) {
                EntityListener.toggleData.put(sender.getName().toLowerCase(), false);
                sender.sendMessage(ChatColor.GOLD + "Pop disabled. Please come again.");
            } else {
                EntityListener.toggleData.put(sender.getName().toLowerCase(), true);
                sender.sendMessage(ChatColor.GOLD + "Pop enabled. Let the games begin!");
            }
            return true;
        }
        return false;
    }
}
