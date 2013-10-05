package com.minegusta.mauswashere.command;

import com.censoredsoftware.helper.WrappedCommand;
import com.google.common.collect.Sets;
import com.minegusta.mauswashere.MausWasHere;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class DiscoCommand extends WrappedCommand {

    public DiscoCommand() {
        super(false);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("disco");
    }

    @Override
    public boolean processCommand(CommandSender sender, Command c, String[] args) {
        if (c.getName().equalsIgnoreCase("disco") && sender.hasPermission("explode.disco")) {
            if (sender instanceof ConsoleCommandSender) return false;
            for (int i = 0; i < 600; i++) {
                Player player = (Player) sender;
                final Location location = player.getLocation();
                Bukkit.getScheduler().scheduleSyncDelayedTask(MausWasHere.PLUGIN, new Runnable() {
                    @Override
                    public void run() {
                        disco(location);
                    }
                }, i);
            }
            return true;
        } else {
            sender.sendMessage("Something went wrong.");
        }
        return true;
    }

    public static void disco(Location location) {
        location.getWorld().spigot().playEffect(location, Effect.COLOURED_DUST, 1, 1, 15F, 15F, 15F, 1F, 1000, 25);
    }
}