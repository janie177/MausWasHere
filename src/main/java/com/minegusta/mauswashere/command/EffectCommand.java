package com.minegusta.mauswashere.command;

import com.censoredsoftware.helper.WrappedCommand;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class EffectCommand extends WrappedCommand {

    public EffectCommand() {
        super(false);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("particle");
    }


    public static ConcurrentMap<String, Effect> currentEffect = Maps.newConcurrentMap();

    @Override
    public boolean processCommand(CommandSender sender, Command command, String[] args) {


        if (command.getName().equalsIgnoreCase("particle") && sender.hasPermission("minegusta.particles")) {
            if (sender instanceof ConsoleCommandSender) return false;

            String logo = ChatColor.DARK_RED + "[" + ChatColor.RED + "MineGusta Particles" + ChatColor.DARK_RED + "]";
            String header = ChatColor.AQUA + "=================================================";
            String list = ChatColor.BLUE + "/Particle Flames/Smoke/Hearts/Ender/Rainbow/Off";
            String list2 = ChatColor.BLUE + "/Particle Magic/Green/Cloud/Note/Glyph/Portal/Off";

            if (args.length == 0) {
                sender.sendMessage(logo);
                sender.sendMessage(header);
                sender.sendMessage(list);
                sender.sendMessage(list2);
                return (true);

            } else {

                String whichEffect = args[0].toLowerCase();

                if (whichEffect.equalsIgnoreCase("magic")) {
                    currentEffect.put(sender.getName(), Effect.WITCH_MAGIC);

                } else if (whichEffect.equalsIgnoreCase("smoke")) {
                    currentEffect.put(sender.getName(), Effect.SMOKE);
                } else if (whichEffect.equalsIgnoreCase("ender")) {
                    currentEffect.put(sender.getName(), Effect.ENDER_SIGNAL);
                } else if (whichEffect.equalsIgnoreCase("hearts")) {
                    currentEffect.put(sender.getName(), Effect.HEART);
                } else if (whichEffect.equalsIgnoreCase("rainbow")) {
                    currentEffect.put(sender.getName(), Effect.COLOURED_DUST);
                } else if (whichEffect.equalsIgnoreCase("flames")) {
                    currentEffect.put(sender.getName(), Effect.MOBSPAWNER_FLAMES);
                } else if (whichEffect.equalsIgnoreCase("green")) {
                    currentEffect.put(sender.getName(), Effect.HAPPY_VILLAGER);
                } else if (whichEffect.equalsIgnoreCase("cloud")) {
                    currentEffect.put(sender.getName(), Effect.VILLAGER_THUNDERCLOUD);
                } else if (whichEffect.equalsIgnoreCase("note")) {
                    currentEffect.put(sender.getName(), Effect.NOTE);
                } else if (whichEffect.equalsIgnoreCase("glyph")) {
                    currentEffect.put(sender.getName(), Effect.FLYING_GLYPH);
                } else if (whichEffect.equalsIgnoreCase("portal")) {
                    currentEffect.put(sender.getName(), Effect.PORTAL);

                } else if (whichEffect.equalsIgnoreCase("off")) {
                    currentEffect.remove(sender.getName());
                    sender.sendMessage(logo);
                    sender.sendMessage(header);
                    sender.sendMessage(ChatColor.BLUE + "You now have " + ChatColor.RED + "no" + ChatColor.BLUE + " effect enabled.");
                    return true;
                } else whichEffect = null;
                sender.sendMessage(logo);
                sender.sendMessage(header);
                if (whichEffect != null)
                    sender.sendMessage(ChatColor.BLUE + "You now have the " + ChatColor.RED + whichEffect + ChatColor.BLUE + " effect enabled.");
                else {
                    sender.sendMessage(ChatColor.RED + "That is an unknown type of particle!");
                    sender.sendMessage(list);
                    sender.sendMessage(list2);
                }
            }
        }
        return true;
    }
}
