package com.minegusta.mauswashere.listener;

import com.censoredsoftware.util.Zones;
import com.minegusta.mauswashere.player.MPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class ZoneListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldSwitch(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        MPlayer mPlayer = MPlayer.Util.getPlayer(player);

        // Leaving a disabled world
        if (Zones.isNoTeachJavaWorld(event.getFrom()) && !Zones.isNoTeachJavaWorld(player.getWorld())) {
            player.sendMessage(ChatColor.YELLOW + "MausWasHere is enabled in this world.");

            // First join
            if (mPlayer == null) {
                mPlayer = MPlayer.Util.create(player);

                player.setMaxHealth(40);
                player.setHealthScaled(true);
                player.setHealthScale(6);
            }

            // Set their last login-time
            Long now = System.currentTimeMillis();
            mPlayer.setLastLoginTime(now);
        }

        // Entering a disabled world
        else if (!Zones.isNoTeachJavaWorld(event.getFrom()) && Zones.isNoTeachJavaWorld(player.getWorld())) {
            player.sendMessage(ChatColor.GRAY + "MausWasHere is disabled in this world.");

            // Set their last logout-time
            Long now = System.currentTimeMillis();
            MPlayer.Util.getPlayer(event.getPlayer()).setLastLogoutTime(now);
        }
    }
}
