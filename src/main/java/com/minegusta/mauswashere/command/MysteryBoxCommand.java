package com.minegusta.mauswashere.command;

import com.censoredsoftware.helper.WrappedCommand;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Set;

public class MysteryBoxCommand extends WrappedCommand {

    public MysteryBoxCommand() {
        super(false);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("box");
    }

    @Override
    public boolean processCommand(CommandSender s, Command cmd, String[] args) {
        if(cmd.getName().equalsIgnoreCase("box")){
            if(args.length == 0)return false;
            else{
                try{
                    Player p = Bukkit.getPlayer(args[0]);
                    p.getInventory().addItem(getBox());
                    p.updateInventory();
                    return true;

                } catch(Exception e){}
            }
        }
        return true;
    }

    public ItemStack getBox(){
        ItemStack box = new ItemStack(Material.CHEST, 1);

        ItemMeta meta = box.getItemMeta();
        List<String> lore = meta.getLore();
        lore.add("Rightclick the air to open!");
        meta.setLore(lore);
        box.setItemMeta(meta);
        return box;
    }
}
