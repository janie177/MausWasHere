package com.minegusta.mauswashere.command;

import com.censoredsoftware.helper.WrappedCommand;
import com.google.common.collect.Sets;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class ColorCommand extends WrappedCommand {

    public ColorCommand() {
        super(false);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("color");
    }

    @Override
    public boolean processCommand(CommandSender s, Command cmd, String[] args) {
        if(cmd.getName().equalsIgnoreCase("color")){
            if(s instanceof ConsoleCommandSender)return false;
            else{
            Player p = (Player) s;
            sendColors(p);
            }
        }
        return true;
    }

    private void sendColors(Player p){
        p.sendMessage(ChatColor.RED + "---------------" + ChatColor.GOLD + "Colors" + ChatColor.RED + "---------------");
        p.sendMessage("& " + ChatColor.DARK_BLUE + "1" + ChatColor.DARK_GREEN + "2" + ChatColor.DARK_AQUA + "3" + ChatColor.DARK_RED + "4" + ChatColor.DARK_PURPLE + "5" + ChatColor.GOLD + "6" + ChatColor.GRAY + "7" + ChatColor.DARK_GRAY + "8" + ChatColor.BLUE + "9" + ChatColor.BLACK + "0" + ChatColor.GREEN + "a" + ChatColor.AQUA + "b" + ChatColor.RED + "c" + ChatColor.LIGHT_PURPLE + "d" + ChatColor.YELLOW + "e");
        p.sendMessage("& " + ChatColor.ITALIC + "o" + ChatColor.BOLD + "l" + ChatColor.STRIKETHROUGH + "n" + ChatColor.UNDERLINE + "m");

    }

}
