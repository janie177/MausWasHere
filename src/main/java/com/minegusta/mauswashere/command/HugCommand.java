package com.minegusta.mauswashere.command;

import com.censoredsoftware.helper.WrappedCommand;
import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class HugCommand extends WrappedCommand {

    public HugCommand() {
        super(false);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("hug");
    }

    @Override
    public boolean processCommand(CommandSender sender, Command command, String[] args) {
        if (command.getName().equalsIgnoreCase("hug") && sender.hasPermission("explode.hug")) {
            try {
                if (args[0].equals("*") && sender.hasPermission("hug.all")) {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "You are now hugging " + online.getName());
                        online.sendMessage(ChatColor.LIGHT_PURPLE + sender.getName() + " hugged you!");
                        online.getWorld().spigot().playEffect(online.getLocation(), Effect.HEART, 1, 1, 1F, 1F, 1F, 1F, 80, 15);
                    }
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "You are now hugging " + target.getName());
                    target.sendMessage(ChatColor.LIGHT_PURPLE + sender.getName() + " hugged you!");
                    target.getWorld().spigot().playEffect(target.getLocation(), Effect.HEART, 1, 1, 1F, 1F, 1F, 1F, 80, 15);
                }
            } catch (Exception e) {
                sender.sendMessage("Invalid player :c");
            }

        }
        return true;
    }

}
