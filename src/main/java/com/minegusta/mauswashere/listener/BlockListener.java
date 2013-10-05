package com.minegusta.mauswashere.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class BlockListener implements Listener {
    @EventHandler
    public void onSignsCreateEvent(SignChangeEvent event) {
        String text1 = event.getLine(0);
        String text2 = event.getLine(1);
        String text3 = event.getLine(2);
        String text4 = event.getLine(3);
        if (event.getLine(0) != null) {
            event.setLine(0, ChatColor.translateAlternateColorCodes('&', text1));
        }
        if (event.getLine(1) != null) {
            event.setLine(1, ChatColor.translateAlternateColorCodes('&', text2));
        }
        if (event.getLine(2) != null) {
            event.setLine(2, ChatColor.translateAlternateColorCodes('&', text3));
        }
        if (event.getLine(3) != null) {
            event.setLine(3, ChatColor.translateAlternateColorCodes('&', text4));
        }
    }
}