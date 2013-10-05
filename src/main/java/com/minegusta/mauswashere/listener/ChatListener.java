package com.minegusta.mauswashere.listener;

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
        if (MuteCommand.mutedMap.containsKey(p) && MuteCommand.mutedMap.get(p)) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "You are muted. This will be undone when a moderator undoes it or at a server reboot. (9AM GMT).");
            e.getPlayer().sendMessage(ChatColor.GREEN + "Muted for a false reason? Visit: " + ChatColor.AQUA + "http://forum.minegusta.com/");
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
    }
}