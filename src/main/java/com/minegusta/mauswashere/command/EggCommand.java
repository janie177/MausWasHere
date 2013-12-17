package com.minegusta.mauswashere.command;

import com.censoredsoftware.helper.WrappedCommand;
import com.google.common.collect.Sets;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class EggCommand extends WrappedCommand {

    public EggCommand() {
        super(false);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("egg");
    }

    @Override
    public boolean processCommand(CommandSender sender, Command c, String[] args) {
        if (!sender.hasPermission("minegusta.egg")) {
            sender.sendMessage("You do not have permission to be a chicken. You can be a pig though.");
        } else if (c.getName().equalsIgnoreCase("egg")) {
            try {
                Player target = Bukkit.getPlayer(args[0]).getPlayer();
                target.sendMessage(ChatColor.YELLOW + "Bwuk! Bwuk!");
                Location location = target.getLocation();
                ItemStack eggs = new ItemStack(Material.EGG, 1);
                ItemStack feathers = new ItemStack(Material.FEATHER, 2);
                World world = target.getWorld();
                world.dropItem(target.getLocation().add(0, 1, 0), eggs);
                world.dropItem(target.getLocation().add(0, 2, 0), feathers);
                Sound sound = Sound.CHICKEN_EGG_POP;
                float volume = 5F;
                float pitch = 1F;
                target.playSound(location, sound, volume, pitch);
                Sound sound2 = Sound.CHICKEN_HURT;
                target.playSound(location, sound2, volume, pitch);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.DARK_RED + "Invalid arguments mate. But don't worry, I'm sure you will fix it :)");
            }
        }
        return true;
    }
}
