package com.minegusta.mauswashere.command;

import com.censoredsoftware.helper.WrappedCommand;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.Set;

public class MuteCommand extends WrappedCommand {

    public static Map<String, Boolean> mutedMap = Maps.newHashMap();

    public MuteCommand() {
        super(false);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("mausmute", "mausunmute");
    }

    @Override
    public boolean processCommand(CommandSender s, Command cmd,
                                  String[] args) {
        if (cmd.getName().equalsIgnoreCase("mausmute") && s.hasPermission("minegusta.mute")) {
            try {
                String p = Bukkit.getServer().getPlayer(args[0]).getName();
                mutedMap.put(p, true);
                Bukkit.getServer().getPlayer(args[0]).sendMessage(ChatColor.DARK_RED + "You have been muted. This will be undone in 24 hours or when a moderator unmutes you.");
                Bukkit.getServer().getPlayer(args[0]).sendMessage(ChatColor.GREEN + "Muted for a false reason? visit " + ChatColor.AQUA + "http://forum.minegusta.com/");
                s.sendMessage(ChatColor.YELLOW + "You have muted " + p + "." + ChatColor.DARK_GRAY + " Use /MausUnMute <name> to unmute them.");
                return true;
            } catch (Exception e) {
                s.sendMessage(ChatColor.RED + "Invalid playername.");
            }
        } else if (cmd.getName().equalsIgnoreCase("mausunmute") && s.hasPermission("minegusta.mute")) {
            try {
                String p = Bukkit.getServer().getPlayer(args[0]).getName();
                mutedMap.put(p, false);
                Bukkit.getServer().getPlayer(args[0]).sendMessage(ChatColor.GREEN + "You have been unmuted. Please follow the rules to prevent further mutes.");
                s.sendMessage(ChatColor.YELLOW + "You have unmuted " + p + ".");
                return true;
            } catch (Exception e) {
                s.sendMessage(ChatColor.RED + "Invalid playername.");
            }
        }
        return true;
    }
}

