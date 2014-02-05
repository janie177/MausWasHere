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
        else if(e.getPlayer().getName().equalsIgnoreCase("_Kennon_")){
            String send = e.getMessage();
            send = send.toLowerCase().replace("rylie", "potato");
            send = send.toLowerCase().replace("tim", "that guy better than my dad");
            send = send.toLowerCase().replace("jason", "hopper");
            send = send.toLowerCase().replace("soccer", "borderless person");
            send = send.toLowerCase().replace("alpha", "black kid");
            send = send.toLowerCase().replace("lol", "-giggling inside-");
            send = send.toLowerCase().replace("dick", "floppy wiener");
            send = send.toLowerCase().replace("sem", "FishFetus");
            send = send.toLowerCase().replace("brb", "I have to go! I will soon be back my friends!!");
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
