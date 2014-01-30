package com.minegusta.mauswashere.command;

import com.censoredsoftware.helper.WrappedCommand;
import com.google.common.collect.Sets;
import com.minegusta.mauswashere.data.VotePointsDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class VoteRedeemCommand extends WrappedCommand {

    public VoteRedeemCommand() {
        super(false);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("vote");
    }

    @Override
    public boolean processCommand(CommandSender s, Command cmd, String[] args) {
        if(cmd.getName().equalsIgnoreCase("vote")){
            if(s instanceof ConsoleCommandSender)return false;
            Player p = (Player) s;
            if(p.getWorld().getName().equalsIgnoreCase("donator")){
                p.sendMessage(ChatColor.DARK_RED + "You cannot redeem mystery boxes in this world!");
                return true;
            }
            else if(!(VotePointsDataManager.getPlayerVotes(p.getUniqueId()) > 0)){
                p.sendMessage(ChatColor.DARK_RED + "You did not vote!" + ChatColor.YELLOW + " http://www.minegusta.com/Vote.html");
                return true;
            }
            else{
                VotePointsDataManager.removePoints(p.getUniqueId());
                p.sendMessage(ChatColor.YELLOW + "You redeemed a vote and you have " + ChatColor.AQUA + VotePointsDataManager.getPlayerVotes(p.getUniqueId()) + ChatColor.YELLOW + " redeems left.");
                p.getInventory().addItem(getBox());
                p.updateInventory();
            }
        }
        return true;
    }

    public ItemStack getBox(){
        ItemStack box = new ItemStack(Material.CHEST, 1);

        ItemMeta meta = box.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Rightclick the air to open!");
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.GOLD + "Mystery Box");
        box.setItemMeta(meta);
        return box;
    }
}
