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

public class NinjaCommand extends WrappedCommand {

    public NinjaCommand() {
        super(false);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("ninja");
    }

    @Override
    public boolean processCommand(final CommandSender sender, Command command, String[] args) {
        if (command.getName().equalsIgnoreCase("ninja") && sender.hasPermission("minegusta.ninja")) {
            if (sender instanceof ConsoleCommandSender) return false;
            final Location target = Bukkit.getServer().getPlayer(sender.getName()).getLocation();
            for (int i = 0; i < 51; i++) {
                final int k = i;
                Bukkit.getScheduler().scheduleSyncDelayedTask(MausWasHere.PLUGIN, new Runnable() {
                    @Override
                    public void run() {
                        if (k != 50)
                            ninjaCloud(target, k / 20 < 3 ? k / 20 : 3, k / 30, k / 20 < 3 ? k / 20 : 3, k * 100, 500);
                        else {
                            Player player = (Player) sender;
                            player.getWorld().playSound(target, Sound.CHICKEN_EGG_POP, 1F, 1F);
                            player.chat("/spawn");
                            player.getWorld().playSound(player.getWorld().getSpawnLocation(), Sound.CHICKEN_EGG_POP, 1F, 1F);
                        }
                    }
                }, i);
            }
            return true;
        } else {
            sender.sendMessage("Something went wrong.");
        }
        return true;
    }

    public static void ninjaCloud(Location target, float offSetX, float offSetY, float offSetZ, int particles, int viewRadius) {
        target.getWorld().playSound(target, Sound.STEP_SAND, 0.2F, 0F);
        // target.getWorld().playSound(target, Sound.ORB_PICKUP, 0.7F, 0F);
        target.getWorld().spigot().playEffect(target, Effect.PARTICLE_SMOKE, 1, 1, 1F + offSetX, 1F + offSetY, 1F + offSetZ, 1F, particles, viewRadius);
    }
}
