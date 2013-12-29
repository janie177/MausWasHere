package com.minegusta.mauswashere.listener;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

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

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMineStuff(BlockBreakEvent e) {
        if (!(e.isCancelled()) && e.getPlayer().hasPermission("minegusta.donator")) {
            Material block = e.getBlock().getType();
            if (block.equals(Material.MOB_SPAWNER)) {
                if (e.getPlayer().getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                    e.getPlayer().sendMessage(ChatColor.DARK_RED + "You cannot mine spawners with the fortune enchantment.");
                    e.setCancelled(true);
                } else {


                    CreatureSpawner spawner = (CreatureSpawner) e.getBlock().getState();
                    String mobType = "Pig";
                    EntityType entity = spawner.getSpawnedType();
                    switch (entity) {
                        case PIG:
                            mobType = ChatColor.LIGHT_PURPLE + "Pig";
                            break;
                        case SKELETON:
                            mobType = ChatColor.WHITE + "Skeleton";
                            break;
                        case SPIDER:
                            mobType = ChatColor.BLACK + "Spider";
                            break;
                        case ZOMBIE:
                            mobType = ChatColor.DARK_GREEN + "Zombie";
                            break;
                        case CAVE_SPIDER:
                            mobType = ChatColor.DARK_PURPLE + "Cave Spider";
                            break;
                        case BLAZE:
                            mobType = ChatColor.GOLD + "Blaze";
                            break;
                        case SILVERFISH:
                            mobType = ChatColor.GRAY + "SilverFish";
                            break;
                    }


                    ItemStack mobSpawner = new ItemStack(Material.MOB_SPAWNER, 1);
                    ArrayList<String> spawnerType = Lists.newArrayList();
                    spawnerType.add(mobType + " Spawner");
                    ItemMeta meta = mobSpawner.getItemMeta();
                    meta.setLore(spawnerType);
                    mobSpawner.setItemMeta(meta);


                    e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), mobSpawner);
                    e.setExpToDrop(0);

                }
            }
        }


        //Block Nick from breaking.

        if (e.getPlayer().getName().equalsIgnoreCase("Franchesco14") && !PlayerListener.nicksPassword.isEmpty()) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent e) {
        Block block = e.getBlockPlaced();
        ItemStack spawner = e.getItemInHand();
        if (spawner.getType().equals(Material.MOB_SPAWNER) && !e.isCancelled()) {
            if (spawner.getItemMeta().hasLore()) {
                if (spawner.getItemMeta().getLore().contains("Spawner")) {
                    CreatureSpawner placedSpawner = (CreatureSpawner) block.getState();
                    if (spawner.getItemMeta().getLore().contains("Pig")) {
                        placedSpawner.setSpawnedType(EntityType.PIG);
                    } else if (spawner.getItemMeta().getLore().contains("Skeleton")) {
                        placedSpawner.setSpawnedType(EntityType.SKELETON);
                    } else if (spawner.getItemMeta().getLore().contains("Spider")) {
                        placedSpawner.setSpawnedType(EntityType.SPIDER);
                    } else if (spawner.getItemMeta().getLore().contains("Zombie")) {
                        placedSpawner.setSpawnedType(EntityType.ZOMBIE);
                    } else if (spawner.getItemMeta().getLore().contains("Cave Spider")) {
                        placedSpawner.setSpawnedType(EntityType.CAVE_SPIDER);
                    } else if (spawner.getItemMeta().getLore().contains("Blaze")) {
                        placedSpawner.setSpawnedType(EntityType.BLAZE);
                    } else if (spawner.getItemMeta().getLore().contains("SilverFish")) {
                        placedSpawner.setSpawnedType(EntityType.SILVERFISH);
                    }
                }
            }
        }

        //Stop Nick from wrecking blawks.

        if (e.getPlayer().getName().equalsIgnoreCase("Franchesco14") && !PlayerListener.nicksPassword.isEmpty()) {
            e.setCancelled(true);
        }
    }
}