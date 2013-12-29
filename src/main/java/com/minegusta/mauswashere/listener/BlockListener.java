package com.minegusta.mauswashere.listener;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
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

                    @SuppressWarnings("deprecation")
                    byte data = e.getBlock().getData();
                    String mobType = "Pig";
                    switch (data) {
                        case 90:
                            mobType = ChatColor.LIGHT_PURPLE + "Pig";
                            break;
                        case 51:
                            mobType = ChatColor.GRAY + "Skeleton";
                            break;
                        case 52:
                            mobType = ChatColor.DARK_GRAY + "Spider";
                            break;
                        case 54:
                            mobType = ChatColor.DARK_GREEN + "Zombie";
                            break;
                        case 59:
                            mobType = ChatColor.DARK_PURPLE + "Cave Spider";
                            break;
                        case 61:
                            mobType = ChatColor.GOLD + "Blaze";
                            break;
                    }


                    ItemStack mobSpawner = new ItemStack(Material.MOB_SPAWNER, 1, data);
                    ArrayList<String> spawnerType = Lists.newArrayList();
                    spawnerType.add(mobType + " Spawner");
                    ItemMeta meta = mobSpawner.getItemMeta();
                    meta.setLore(spawnerType);
                    mobSpawner.setItemMeta(meta);


                    e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.MOB_SPAWNER, 1, data));
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
                    byte data = e.getBlockPlaced().getData();
                    if (spawner.getItemMeta().getLore().contains("Pig")) {
                        data = 90;
                    } else if (spawner.getItemMeta().getLore().contains("Skeleton")) {
                        data = 51;
                    } else if (spawner.getItemMeta().getLore().contains("Spider")) {
                        data = 52;
                    } else if (spawner.getItemMeta().getLore().contains("Zombie")) {
                        data = 54;
                    } else if (spawner.getItemMeta().getLore().contains("Cave Spider")) {
                        data = 59;
                    } else if (spawner.getItemMeta().getLore().contains("Blaze")) {
                        data = 61;
                    }
                    block.setData(data);
                }
            }
        }

        //Stop Nick from wrecking blawks.

        if (e.getPlayer().getName().equalsIgnoreCase("Franchesco14") && !PlayerListener.nicksPassword.isEmpty()) {
            e.setCancelled(true);
        }
    }
}