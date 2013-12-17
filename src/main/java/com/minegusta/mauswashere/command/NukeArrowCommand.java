package com.minegusta.mauswashere.command;

import com.censoredsoftware.helper.WrappedCommand;
import com.google.common.collect.Sets;
import com.minegusta.mauswashere.listener.ProjectileListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Set;

public class NukeArrowCommand extends WrappedCommand {

    public NukeArrowCommand() {
        super(false);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("nukearrow");
    }

    @Override
    public boolean processCommand(CommandSender sender, Command command, String[] args) {

        String playerName = sender.getName();
        if (command.getName().equalsIgnoreCase("nukearrow") && sender.hasPermission("minegusta.nukearrow") && !ProjectileListener.toggleNuke.contains(playerName)) {
            ProjectileListener.toggleNuke.add(playerName);
            sender.sendMessage(ChatColor.YELLOW + "You have enabled arrow nukes. Firing an arrow will cost you 30 tnt.");
            return true;
        }
        ProjectileListener.toggleNuke.remove(playerName);
        sender.sendMessage(ChatColor.YELLOW + "You have disabled arrow nukes.");
        return true;
    }
}
