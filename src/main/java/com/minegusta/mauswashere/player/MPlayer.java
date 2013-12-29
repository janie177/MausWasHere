package com.minegusta.mauswashere.player;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.minegusta.mauswashere.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.*;

public class MPlayer implements ConfigurationSerializable {
    private String player, punished;
    private long lastLoginTime, lastLogoutTime;
    private boolean inPvp;
    private Set<String> deaths;

    public MPlayer() {
        inPvp = false;
        punished = "~";
        deaths = Sets.newHashSet();
    }

    public MPlayer(String player, ConfigurationSection conf) {
        this.player = player;
        lastLoginTime = conf.getLong("lastLoginTime");
        lastLogoutTime = conf.getLong("lastLogoutTime");
        inPvp = conf.getBoolean("inPvp");
        punished = conf.getString("punished");
        deaths = Sets.newHashSet(conf.getStringList("deaths"));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lastLoginTime", lastLoginTime);
        map.put("lastLogoutTime", lastLogoutTime);
        map.put("inPvp", inPvp);
        map.put("punished", punished);
        map.put("deaths", Lists.newArrayList(deaths));
        return map;
    }

    void setPlayer(String player) {
        this.player = player;
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.player);
    }

    public void setLastLoginTime(Long time) {
        this.lastLoginTime = time;
        Util.save(this);
    }

    public long getLastLoginTime() {
        return this.lastLoginTime;
    }

    public void setLastLogoutTime(long time) {
        this.lastLogoutTime = time;
        Util.save(this);
    }

    public String getPlayerName() {
        return player;
    }

    public void setInPvp(int seconds) {
        DataManager.saveTimed(player, "inPvp", true, seconds);
        inPvp = true;
    }

    public void punish(String punisher, String reason, String punishmentCommand) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), punishmentCommand);
        punished = reason;
        Player player = getOfflinePlayer().getPlayer();
        LivingEntity fakeKuso = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.SHEEP);
        fakeKuso.setCustomName(punisher);
        fakeKuso.setCustomNameVisible(true);
        double damage = player.getMaxHealth() + 99.0;
        player.setLastDamageCause(new EntityDamageByEntityEvent(fakeKuso, player, EntityDamageEvent.DamageCause.MELTING, damage));
        player.damage(damage);
        fakeKuso.remove();
    }

    public void unpunish() {
        punished = "~";
    }

    public boolean getPunished() {
        return !punished.equals("~");
    }

    public String getPunishMessage() {
        return punished;
    }

    public boolean getInPvp() {
        boolean timedData = DataManager.hasTimed(player, "inPvp");
        if (inPvp && !timedData) {
            inPvp = false;
            if (getOfflinePlayer().isOnline())
                getOfflinePlayer().getPlayer().sendMessage(ChatColor.AQUA + "You may now safely log out.");
        }
        return timedData;
    }

    public void addDeath() {
        deaths.add(new Death(this).getId().toString());
        Util.save(this);
    }

    public void addDeath(MPlayer attacker) {
        deaths.add(new Death(this, attacker).getId().toString());
        Util.save(this);
    }

    public Collection<Death> getDeaths() {
        return Collections2.transform(deaths, new Function<String, Death>() {
            @Override
            public Death apply(String s) {
                try {
                    return Death.Util.load(UUID.fromString(s));
                } catch (Exception ignored) {
                }
                return null;
            }
        });
    }

    public void remove() {
        // First we need to kick the player if they're online
        if (getOfflinePlayer().isOnline())
            getOfflinePlayer().getPlayer().kickPlayer(ChatColor.RED + "Your player save has been cleared.");

        // Now we clear the MPlayer save itself
        Util.delete(getPlayerName());
    }

    public static class Util {
        public static MPlayer create(Player player) {
            MPlayer playerSave = new MPlayer();
            playerSave.setPlayer(player.getName());
            playerSave.setLastLoginTime(player.getLastPlayed());
            save(playerSave);

            return playerSave;
        }

        public static void save(MPlayer player) {
            DataManager.players.put(player.getPlayerName(), player);
        }

        public static void delete(String playerName) {
            DataManager.players.remove(playerName);
        }

        public static MPlayer getPlayer(OfflinePlayer player) {
            return getPlayer(player.getName());
        }

        public static MPlayer getPlayer(String player) {
            return DataManager.players.containsKey(player) ? DataManager.players.get(player) : null;
        }
    }
}
