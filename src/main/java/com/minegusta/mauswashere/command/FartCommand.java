package com.minegusta.mauswashere.command;

import com.censoredsoftware.helper.WrappedCommand;
import com.google.common.collect.Sets;
import com.minegusta.mauswashere.MausWasHere;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class FartCommand extends WrappedCommand {

    public FartCommand() {
        super(false);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("fart");
    }

    @Override
    public boolean processCommand(CommandSender sender, Command command, String[] args) {
        if (command.getName().equalsIgnoreCase("fart") && sender.hasPermission("minegusta.fart")) {
            if (sender instanceof ConsoleCommandSender) return false;
            for (int i = 0; i < 201; i++) {
                final Player player = (Player) sender;
                Bukkit.getScheduler().scheduleSyncDelayedTask(MausWasHere.PLUGIN, new Runnable() {
                    @Override
                    public void run() {
                        fartCloud(player.getLocation());
                    }
                }, i);
            }
            return true;
        } else {
            sender.sendMessage("Something went wrong.");
        }
        return true;
    }

    public static void fartCloud(Location location) {
        location.getWorld().playSound(location, Sound.CHICKEN_EGG_POP, 0.5F, 5F);
        location.getWorld().spigot().playEffect(location, Effect.SMOKE, 1, 1, 3F, 1F, 3F, 1F, 500, 20);
    }
}
