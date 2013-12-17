package com.minegusta.mauswashere.command;

import com.censoredsoftware.helper.WrappedCommand;
import com.google.common.collect.Sets;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MilkCommand extends WrappedCommand {

    public MilkCommand() {
        super(true);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("milk");
    }

    @Override
    public boolean processCommand(CommandSender sender, Command command, String[] args) {
        if (!sender.hasPermission("minegusta.milk")) {
            sender.sendMessage("Only Crunchy may give milk!");
        } else if (command.getName().equalsIgnoreCase("milk")) {
            try {
                if (args[0].equals("Penis")) {
                    sender.sendMessage("Magic.");
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]).getPlayer();
                target.getAddress(); // To throw if not actual player.
                return milk(sender, target);
            } catch (Exception ignored) {
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (stripColor(online.getDisplayName()).equals(args[0])) return milk(sender, online);
                }
            }
        }
        sender.sendMessage("That is not a valid player name. Try harder.");
        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, final String[] args) {
        if (command.getName().equalsIgnoreCase("milk") && args.length == 1) {
            return new ArrayList<String>() {
                private static final long serialVersionUID = -2742327262849134306L;

                {
                    if ("penis".startsWith(args[0].toLowerCase())) add("Penis");
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        if (stripColor(online.getDisplayName()).toLowerCase().startsWith(args[0].toLowerCase()))
                            add(stripColor(online.getDisplayName()));
                        if (!stripColor(online.getName()).equals(stripColor(online.getDisplayName())) && stripColor(online.getName()).toLowerCase().startsWith(args[0].toLowerCase()))
                            add(online.getName());
                    }
                }
            };
        }
        return null;
    }

    private static String stripColor(String string) {
        return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', string));
    }

    private static boolean milk(CommandSender sender, Player target) {
        target.sendMessage(ChatColor.DARK_AQUA + "Drink your milk my child!");
        target.sendMessage(ChatColor.DARK_AQUA + "You wouldn't want to get weak... and die. Now would you?");
        sender.sendMessage(ChatColor.YELLOW + "You milked " + target.getName());
        Location location = target.getLocation();
        ItemStack drop = new ItemStack(Material.MILK_BUCKET, 1);
        World world = target.getWorld();
        world.dropItem(target.getLocation().add(0, 3, 0), drop);
        final Effect effect = Effect.SNOWBALL_BREAK;
        target.getWorld().spigot().playEffect(location, effect, 1, 1, 1F, 1.2F, 1F, 1F, 150, 3);
        return true;
    }
}
