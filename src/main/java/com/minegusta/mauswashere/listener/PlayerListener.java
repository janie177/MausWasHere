package com.minegusta.mauswashere.listener;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.minegusta.mauswashere.command.EffectCommand;
import com.minegusta.mauswashere.player.MPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.Set;

public class PlayerListener implements Listener {

    private static final int PVPLOG_SECONDS = 8;
    private static final List<String> PVPLOG_ENTER_BATTLE_MESSAGE = Lists.newArrayList(ChatColor.RED + "You are now in combat!", ChatColor.RED + "Do not log out until combat is complete.");
    public static final Set<String> nicksPassword = Sets.newHashSet();

    @EventHandler
    public final void onPlayerJoin(PlayerJoinEvent event) {
        // Define variables
        Player player = event.getPlayer();
        MPlayer mPlayer = MPlayer.Util.getPlayer(player);

        // First join
        if (mPlayer == null) mPlayer = MPlayer.Util.create(player);

        // Set their last login-time
        Long now = System.currentTimeMillis();
        mPlayer.setLastLoginTime(now);

        if (mPlayer.getPunished()) player.sendMessage(mPlayer.getPunishMessage());

        //Put nick in his map
        if (event.getPlayer().getName().equalsIgnoreCase("franchesco14")) {
            nicksPassword.add("Franchesco14");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Set their last logout-time
        Long now = System.currentTimeMillis();
        MPlayer mPlayer = MPlayer.Util.getPlayer(event.getPlayer());
        mPlayer.setLastLogoutTime(now);
        if (mPlayer.getInPvp())
            mPlayer.punish("LordKuso", ChatColor.DARK_RED + "LordKuso hath smitten thee for thine acts of logging out during combat.", "sudo " + mPlayer.getPlayerName() + " /cite " + mPlayer.getPlayerName() + "pvp-logging" + "http://minegusta.com/pvp-log-proof.png");
    }

    @EventHandler
    public void onParticleWalk(PlayerMoveEvent e) {
        if (EffectCommand.currentEffect.containsKey(e.getPlayer().getName())) {
            Player player = e.getPlayer();
            Effect theEffect = EffectCommand.currentEffect.get(player.getName());
            if (theEffect != null)
                player.getWorld().spigot().playEffect(player.getLocation(), theEffect, 0, 0, 1F, 0.1F, 1F, 0.5F, 3, 30);
        }

        //Block nick from moving.
        if (e.getPlayer().getName().equalsIgnoreCase("Franchesco14") && !nicksPassword.isEmpty()) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCombatTag(EntityDamageByEntityEvent e) {
        if (e.getEntityType().equals(EntityType.PLAYER) && e.getDamager().getType().equals(EntityType.PLAYER) && !e.isCancelled()) {
            MPlayer player = MPlayer.Util.getPlayer((Player) e.getEntity());
            MPlayer damager = MPlayer.Util.getPlayer((Player) e.getDamager());
            if (!player.getInPvp()) alertPlayerCombatStatus(e.getEntity());
            if (!damager.getInPvp()) alertPlayerCombatStatus(e.getDamager());
            player.setInPvp(PVPLOG_SECONDS);
            damager.setInPvp(PVPLOG_SECONDS);
        }
    }

    private void alertPlayerCombatStatus(Entity entity) {
        assert (entity instanceof Player);
        Player player = (Player) entity;
        for (String line : PVPLOG_ENTER_BATTLE_MESSAGE)
            player.sendMessage(line);
    }
}
