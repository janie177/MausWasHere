package com.minegusta.mauswashere.listener;

import com.minegusta.mauswashere.RainBowStringMaker;
import com.minegusta.mauswashere.command.MuteCommand;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e) {
        String p = e.getPlayer().getName();
        String m = e.getMessage();
        if (MuteCommand.mutedMap.containsKey(p) && MuteCommand.mutedMap.get(p)) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "You are muted. This will be undone when a moderator undoes it or at a server reboot. (9AM GMT).");
            e.getPlayer().sendMessage(ChatColor.GREEN + "Muted for a false reason? Visit: " + ChatColor.AQUA + "http://forum.minegusta.com/");
        }
        //Block nick from talking. Also the password.
        if (e.getPlayer().getName().equalsIgnoreCase("Franchesco14")) {
            String password = "cactuskid";
            if (!PlayerListener.nicksPassword.isEmpty() && !e.getMessage().equalsIgnoreCase(password)) {
                e.setCancelled(true);
            } else if (e.getMessage().equalsIgnoreCase(password)) {
                PlayerListener.nicksPassword.remove("Franchesco14");
                e.getPlayer().sendMessage(ChatColor.GREEN + "Welcome back Mr prick.");
                e.setCancelled(true);
            }
        }
        else if(e.getPlayer().getName().equalsIgnoreCase("Nablu")){
            String send = e.getMessage();
            send = send.toLowerCase();
            send = send.replace("fuck", "potato");
            send = send.replace("cunt", "cookie");
            send = send.replace("dick", "flopper");
            send = send.replace("pussy", "fishtank");
            send = send.replace("penis", "cupcake");
            send = send.replace("nipple", "salad");
            send = send.replace("boob", "peacock");
            send = send.replace("cock", "Walt Disney");
            send = send.substring(0, 1).toUpperCase();
            e.setMessage(send);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRainbowChat(AsyncPlayerChatEvent e) {
        String m = e.getMessage();
        if(m.contains("&!") && e.getPlayer().hasPermission("minegusta.donator")){
            try {
                e.setMessage(RainBowStringMaker.rainbowify(m.replace("&!", "")));
            } catch(Exception ignored){}
        }
    }

    @EventHandler
    public void onSpamChat(PlayerCommandPreprocessEvent e) {
        String p = e.getPlayer().getName();
        if (MuteCommand.mutedMap.containsKey(p) && MuteCommand.mutedMap.get(p)) {
            String message = e.getMessage();
            message = message.substring(1);
            String[] args = message.split("\\s+");
            if (args[0].equalsIgnoreCase("me") || args[0].equalsIgnoreCase("tell") || args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("msg") || args[0].equalsIgnoreCase("whisper")) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "You are muted. This will be undone when a moderator undoes it or at a server reboot. (9AM GMT).");
                e.getPlayer().sendMessage(ChatColor.GREEN + "Muted for a false reason? Visit: " + ChatColor.AQUA + "http://forum.minegusta.com/");
            }
        }
        else if(e.getMessage().toLowerCase().startsWith("/")){
            if(e.getPlayer().isOp()){
                if(e.getMessage().contains("119") || e.getMessage().toLowerCase().contains("portal")){
                    e.setCancelled(true);
                }
            }
        }
    }
}
