package com.minegusta.mauswashere.listener;

import com.minegusta.mauswashere.player.MPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Define variables
        Player player = event.getPlayer();
        MPlayer mPlayer = MPlayer.Util.getPlayer(player);

        // First join
        if (mPlayer == null) mPlayer = MPlayer.Util.create(player);

        // Set their last login-time
        Long now = System.currentTimeMillis();
        mPlayer.setLastLoginTime(now);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Set their last logout-time
        Long now = System.currentTimeMillis();
        MPlayer.Util.getPlayer(event.getPlayer()).setLastLogoutTime(now);
    }
}
