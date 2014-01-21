package com.minegusta.mauswashere.listener;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Set;

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
                if (spawner.getItemMeta().getLore().toString().contains("Spawner")) {
                    CreatureSpawner placedSpawner = (CreatureSpawner) block.getState();
                    if (spawner.getItemMeta().getLore().toString().contains("Pig")) {
                        placedSpawner.setSpawnedType(EntityType.PIG);
                    } else if (spawner.getItemMeta().getLore().toString().contains("Skeleton")) {
                        placedSpawner.setSpawnedType(EntityType.SKELETON);
                    } else if (spawner.getItemMeta().getLore().toString().contains("Spider")) {
                        placedSpawner.setSpawnedType(EntityType.SPIDER);
                    } else if (spawner.getItemMeta().getLore().toString().contains("Zombie")) {
                        placedSpawner.setSpawnedType(EntityType.ZOMBIE);
                    } else if (spawner.getItemMeta().getLore().toString().contains("Cave Spider")) {
                        placedSpawner.setSpawnedType(EntityType.CAVE_SPIDER);
                    } else if (spawner.getItemMeta().getLore().toString().contains("Blaze")) {
                        placedSpawner.setSpawnedType(EntityType.BLAZE);
                    } else if (spawner.getItemMeta().getLore().toString().contains("SilverFish")) {
                        placedSpawner.setSpawnedType(EntityType.SILVERFISH);
                    }
                    placedSpawner.update();
                }
            }
        }

        //Stop Nick from wrecking blawks.

        if (e.getPlayer().getName().equalsIgnoreCase("Franchesco14") && !PlayerListener.nicksPassword.isEmpty()) {
            e.setCancelled(true);
        }
    }

    //Cannons

    @EventHandler
    public void onCannonShoot(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) &&
                (e.getClickedBlock().getType() == Material.STONE_BUTTON)) {
            Block l = e.getClickedBlock();

            if ((l.getRelative(BlockFace.NORTH, 1).getType().equals(Material.IRON_BLOCK)) &&
                    (l.getRelative(BlockFace.NORTH, 2).getType().equals(
                            Material.IRON_BLOCK)) &&
                    (l.getRelative(BlockFace.NORTH).getRelative(BlockFace.UP).getType().equals(Material.REDSTONE_TORCH_ON))) {
                Location barrel = e.getClickedBlock()
                        .getRelative(BlockFace.NORTH, 2).getLocation();

                player.getWorld().spigot().playEffect(barrel, Effect.CLOUD, 0, 0, 0.0F, 0.0F, 0.0F, 1.0F, 50, 2);

                player.playSound(player.getLocation(), Sound.EXPLODE, 0.5F,
                        2.0F);

                TNTPrimed tnt = (TNTPrimed) barrel.getWorld().spawn(
                        barrel.add(0.0D, 0.7D, 0.0D), TNTPrimed.class);

                tnt.setVelocity(tnt.getLocation().getDirection().setY(0.4D)
                        .setZ(-1).setX(0));
            }

            if (!l.getRelative(BlockFace.EAST, 1).getType().equals(Material.IRON_BLOCK) || !l.getRelative(BlockFace.EAST, 2).getType().equals(
                    Material.IRON_BLOCK)) return;

            if (l.getRelative(BlockFace.EAST).getRelative(BlockFace.UP).getType()
                    .equals(Material.REDSTONE_TORCH_ON)) {
                Location barrel = e.getClickedBlock()
                        .getRelative(BlockFace.EAST, 2).getLocation();

                player.getWorld()
                        .spigot()
                        .playEffect(barrel, Effect.CLOUD, 0, 0,
                                0.0F, 0.0F, 0.0F, 1.0F, 50, 2);

                player.playSound(player.getLocation(), Sound.EXPLODE, 0.5F, 2.0F);

                TNTPrimed tnt = barrel.getWorld().spawn(barrel.add(0.0D, 1.0D, 0.0D),
                        TNTPrimed.class);

                tnt.setVelocity(tnt.getLocation().getDirection().setY(0.4D)
                        .setX(1).setZ(0));
            }

            if (l.getRelative(BlockFace.WEST, 1).getType().equals(Material.IRON_BLOCK)) {
                if (l.getRelative(BlockFace.WEST, 2).getType().equals(
                        Material.IRON_BLOCK)) {
                    if (l.getRelative(BlockFace.WEST).getRelative(BlockFace.UP).getType()
                            .equals(Material.REDSTONE_TORCH_ON)) {
                        Location barrel = e.getClickedBlock()
                                .getRelative(BlockFace.WEST, 2).getLocation();

                        player.getWorld()
                                .spigot()
                                .playEffect(barrel, Effect.CLOUD, 0, 0,
                                        0.0F, 0.0F, 0.0F, 1.0F, 50, 2);

                        player.playSound(player.getLocation(), Sound.EXPLODE, 0.5F, 2.0F);

                        TNTPrimed tnt = (TNTPrimed) barrel.getWorld().spawn(barrel.add(0.0D, 1.0D, 0.0D),
                                TNTPrimed.class);

                        tnt.setVelocity(tnt.getLocation().getDirection().setY(0.4D)
                                .setX(-1).setZ(0));
                    }
                }
            }
            if (l.getRelative(BlockFace.SOUTH, 1).getType().equals(Material.IRON_BLOCK)) {
                if (l.getRelative(BlockFace.SOUTH, 2).getType().equals(
                        Material.IRON_BLOCK)) {
                    if (l.getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP).getType()
                            .equals(Material.REDSTONE_TORCH_ON)) {
                        Location barrel = e.getClickedBlock()
                                .getRelative(BlockFace.SOUTH, 2).getLocation();

                        player.getWorld()
                                .spigot()
                                .playEffect(barrel, Effect.CLOUD, 0, 0,
                                        0.0F, 0.0F, 0.0F, 1.0F, 50, 2);

                        player.playSound(player.getLocation(), Sound.EXPLODE, 0.5F, 2.0F);

                        TNTPrimed tnt = (TNTPrimed) barrel.getWorld().spawn(barrel.add(0.0D, 1.0D, 0.0D),
                                TNTPrimed.class);

                        tnt.setVelocity(tnt.getLocation().getDirection().setY(0.4D)
                                .setZ(1).setX(0));
                    }
                }
            }
        }
    }

    //Wool bounce.

    @EventHandler
    public void onWoolBounce(EntityDamageEvent e) {
        if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
            if (e.getEntity().getType().equals(EntityType.PLAYER)) {
                Player player = (Player) e.getEntity();
                if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.WOOL)
                        && player.getLocation().getBlock()
                        .getRelative(BlockFace.DOWN, 2).getType().equals(Material.WOOL) && !(player.isSneaking())) {
                    e.setCancelled(true);
                    player.getWorld().playSound(player.getLocation(), Sound.SLIME_WALK, 0.4F, 0.2F);
                    Vector direction = player.getLocation().getDirection();
                    player.setVelocity(direction.setY(1).multiply(1.1));
                }
                else if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.WOOL)
                        && player.getLocation().getBlock()
                        .getRelative(BlockFace.DOWN, 2).getType().equals(Material.WOOL) && player.isSneaking()) {
                    e.setCancelled(true);
                }
            }
        }
    }

    //Jump pads

    public static Set<String> noFallDamage = Sets.newHashSet();

    @EventHandler
    public void onPressurePress(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if ((e.getAction() == Action.PHYSICAL) &&
                (e.getClickedBlock().getType() == Material.STONE_PLATE))
            try {
                Block block = e.getClickedBlock().getRelative(BlockFace.DOWN);
                if (block.getType().equals(Material.LAPIS_BLOCK)) {
                    rocketPlayer(player);
                    for (Entity nearby : Collections2.filter(player.getNearbyEntities(1.0, 1.0, 1.0), new Predicate<Entity>() {
                        @Override
                        public boolean apply(Entity entity) {
                            return entity instanceof Player;
                        }
                    }))
                        rocketPlayer((Player) nearby);

                }
            } catch (Exception ignored)
            {}
    }

    private static void rocketPlayer(Player player) {
        Vector direction = player.getLocation().getDirection();
        player.getWorld().playSound(player.getLocation(),
                Sound.GHAST_FIREBALL, 1.0F, 1.0F);
        player.setVelocity(new Vector(direction.getX(), 0.5, direction.getZ()).multiply(3));
        noFallDamage.add(player.getName());
    }

    @EventHandler
    public void onLaunchLand(EntityDamageEvent e) {
        if (!e.getCause().equals(EntityDamageEvent.DamageCause.FALL) ||
                !e.getEntity().getType().equals(EntityType.PLAYER)) return;
        String playerName = ((Player) e.getEntity()).getName();
        if (!noFallDamage.contains(playerName)) return;
        e.setCancelled(true);
        noFallDamage.remove(playerName);
    }



}