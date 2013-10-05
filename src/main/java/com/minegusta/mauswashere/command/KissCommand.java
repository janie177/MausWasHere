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

public class KissCommand extends WrappedCommand {

    public KissCommand() {
        super(false);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("swag", "yolo", "kiss");
    }

    @Override
    public boolean processCommand(CommandSender s, Command command, String[] args) {
        if (command.getName().equalsIgnoreCase("swag") || command.getName().equalsIgnoreCase("yolo")) {
            Player p = (Player) s;
            s.sendMessage(ChatColor.RED + "No. Out. Now.");
            p.setHealth(1.0);

        }
        if (command.getName().equalsIgnoreCase("kiss")) {
            try {
                Player p = Bukkit.getPlayer(args[0]);
                if (s.getName().equalsIgnoreCase(args[0])) {
                    s.sendMessage(ChatColor.LIGHT_PURPLE + "You awkwardly fold your tongue in a failed attempt to kiss yourself.");
                    return true;
                } else if (args[0].equalsIgnoreCase("_Kennon_") && !(s.getName().equalsIgnoreCase("janie177"))) {
                    s.sendMessage(ChatColor.DARK_RED + "STAY AWAY FROM MY KENNON YOU PERV.");
                } else if (args[0].equalsIgnoreCase("Me") || args[0].equalsIgnoreCase("Myself") || args[0].equalsIgnoreCase("Animals") || args[0].equalsIgnoreCase("kiss") || args[0].equalsIgnoreCase("ladygaga")) {
                    s.sendMessage(ChatColor.DARK_RED + "No. Just no. I won't do that. Creep.");
                } else {
                    Player sender = (Player) s;
                    s.sendMessage(ChatColor.LIGHT_PURPLE + "You kissed " + ChatColor.YELLOW + p.getName());
                    p.sendMessage(ChatColor.LIGHT_PURPLE + "You were kissed by " + ChatColor.YELLOW + s.getName());
                    p.getWorld().spigot().playEffect(p.getLocation(), Effect.HEART);
                    sender.getWorld().spigot().playEffect(p.getLocation(), Effect.HEART);
                }

            } catch (Exception e) {
                s.sendMessage(ChatColor.RED + "Player not found.");
            }
            return true;
        }
        return true;
    }
}
