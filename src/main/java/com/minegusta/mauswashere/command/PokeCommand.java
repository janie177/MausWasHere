package com.minegusta.mauswashere.command;

import com.censoredsoftware.helper.WrappedCommand;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class PokeCommand extends WrappedCommand {

    public PokeCommand() {
        super(false);
    }

    @Override
    public Set<String> getCommands() {
        return Sets.newHashSet("poke");
    }

    @Override
    public boolean processCommand(CommandSender s, Command cmd, String[] args) {
        List<String> help = Lists.newArrayList("Wrong arguments! Use it like this:", "/Poke <Name> <object>", "So like: /Poke _Kennon_ A Stinky Fish.");
        List<String> wrongPlayer = Lists.newArrayList(ChatColor.RED + "Player not found!!");
        Player p = (Player) s;

        if(args.length < 2){
            sendText(p, help);
            return true;
        }
        try{
            Player victim = Bukkit.getOfflinePlayer(args[0]).getPlayer();
            Joiner joiner = Joiner.on(" ").skipNulls();
            List<String> objectList = Lists.newArrayList(args);
            objectList.remove(args[0]);
            String object = joiner.join(objectList);

            p.sendMessage(ChatColor.YELLOW + "You poked " + ChatColor.ITALIC + victim.getName() + ChatColor.YELLOW + " with " + ChatColor.RED + ChatColor.ITALIC + object + ChatColor.YELLOW + "!");
            victim.sendMessage(ChatColor.YELLOW + "You were poked by " + ChatColor.ITALIC + p.getName() + ChatColor.YELLOW + " with " + ChatColor.RED + ChatColor.ITALIC + object + ChatColor.YELLOW + "!");

        } catch(Exception e){
            sendText(p, wrongPlayer);
            return true;
        }
        return true;
    }


    private void sendText(Player p, List<String> l){
        p.sendMessage(ChatColor.RED + "-------" + ChatColor.GOLD + "Poke" + ChatColor.RED + "-------");
        for(String s : l){
            p.sendMessage(ChatColor.YELLOW + s);
        }
    }
}