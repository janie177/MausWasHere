package com.minegusta.mauswashere.listener;

import com.minegusta.mauswashere.command.EffectCommand;
import com.minegusta.mauswashere.player.MPlayer;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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

    @EventHandler
    public void onParticleWalk(PlayerMoveEvent e) {
        if (EffectCommand.currentEffect.containsKey(e.getPlayer().getName())) {
            Player player = e.getPlayer();
            Effect theEffect = EffectCommand.currentEffect.get(player.getName());
            if (theEffect != null)
                player.getWorld().spigot().playEffect(player.getLocation(), theEffect, 0, 0, 1F, 0.1F, 1F, 0.5F, 3, 1);
        }
    }
}
