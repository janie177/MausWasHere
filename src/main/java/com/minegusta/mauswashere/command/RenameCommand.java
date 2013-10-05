package com.minegusta.mauswashere.command;

import com.censoredsoftware.helper.WrappedCommand;
import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Set;


public class RenameCommand extends WrappedCommand {

    public RenameCommand() {
        super(false);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("rename");
    }

    @Override
    public boolean processCommand(final CommandSender sender, Command command, String[] args) {
        if (command.getName().equalsIgnoreCase("rename") && sender.hasPermission("donatorstuff.rename") && sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GOLD + "/Rename " + ChatColor.YELLOW + "<New Name> " + ChatColor.DARK_GRAY + ": Lets you rename the held item. Color codes supported!");
                sender.sendMessage(ChatColor.GOLD + "/desc " + ChatColor.YELLOW + "<New Description> " + ChatColor.DARK_GRAY + ": Lets you rename the held item's info. Color codes supported!");
                return true;

            } else {
                if (p.getItemInHand().getType().equals(Material.AIR)) {
                    p.sendMessage(ChatColor.RED + "[Renamer]" + ChatColor.DARK_RED + "I'm not going to rename your hand!");
                    return true;
                } else {

                    Joiner joiner = Joiner.on(" ").skipNulls();
                    String newName = joiner.join(args);
                    if (newName.length() > 62) {
                        p.sendMessage(ChatColor.RED + "[Renamer]" + ChatColor.DARK_RED + "That name is too long! It would crash players.");
                        return true;
                    } else {

                        ItemStack item = p.getItemInHand();
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', newName));
                        item.setItemMeta(meta);
                        p.sendMessage(ChatColor.RED + "[Renamer]" + ChatColor.AQUA + "Successfully renamed item name!");
                        return true;
                    }
                }
            }

        }
        if (command.getName().equalsIgnoreCase("desc") && sender.hasPermission("donatorstuff.desc") && sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GOLD + "/desc " + ChatColor.YELLOW + "<New Description> " + ChatColor.DARK_GRAY + ": Lets you rename the held item's info. Color codes supported!");
                sender.sendMessage(ChatColor.GOLD + "/Rename " + ChatColor.YELLOW + "<New Name> " + ChatColor.DARK_GRAY + ": Lets you rename the held item. Color codes supported!");
                return true;
            } else {
                if (p.getItemInHand().getType().equals(Material.AIR)) {
                    p.sendMessage(ChatColor.RED + "[Renamer]" + ChatColor.DARK_RED + "I'm not going to rename your hand!");
                    return true;
                } else {
                    Joiner joiner = Joiner.on(" ").skipNulls();
                    String newName = joiner.join(args);
                    if (newName.length() > 64) {
                        p.sendMessage(ChatColor.RED + "[Renamer]" + ChatColor.DARK_RED + "That description is too long! It would crash players.");
                        return true;
                    } else {

                        ArrayList<String> lore = new ArrayList<String>();
                        lore.clear();
                        lore.add(ChatColor.translateAlternateColorCodes('&', newName));


                        ItemStack item = p.getItemInHand();
                        ItemMeta meta = item.getItemMeta();
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                        p.sendMessage(ChatColor.RED + "[Renamer]" + ChatColor.AQUA + "Successfully renamed item description!");
                        return true;
                    }
                }
            }
        }
        return true;
    }

}
