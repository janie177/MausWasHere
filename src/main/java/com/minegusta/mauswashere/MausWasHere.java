package com.minegusta.mauswashere;

import com.censoredsoftware.util.Configs;
import com.censoredsoftware.util.Messages;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.minegusta.mauswashere.data.ThreadManager;
import com.minegusta.mauswashere.listener.ChatListener;
import com.minegusta.mauswashere.listener.PlayerListener;
import com.minegusta.mauswashere.listener.ZoneListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.Set;

public class MausWasHere {
    // Constants
    public static String SAVE_PATH;

    // Public Static Access
    public static final MausPlugin PLUGIN;

    // Disabled Stuff
    public static ImmutableSet<String> DISABLED_WORLDS;

    // Load what is possible to load right away.
    static {
        // Allow static access.
        PLUGIN = (MausPlugin) Bukkit.getServer().getPluginManager().getPlugin("MausWasHere");
    }

    // Load everything else.
    protected static void load() {
        // Start the data
        SAVE_PATH = PLUGIN.getDataFolder() + "/data/"; // Don't change this.

        // Check if there are no enabled worlds
        if (!loadWorlds()) {
            Messages.severe("MausWasHere was unable to load any worlds.");
            Messages.severe("Please enable at least 1 world.");
            PLUGIN.getServer().getPluginManager().disablePlugin(PLUGIN);
        }

        // Load listeners
        loadListeners();

        // Start threads
        ThreadManager.startThreads();
    }

    private static boolean loadWorlds() {
        Set<String> disabledWorlds = Sets.newHashSet();
        for (String world : Collections2.filter(Configs.getSettingArrayListString("restrictions.disabled_worlds"), new Predicate<String>() {
            @Override
            public boolean apply(String world) {
                return PLUGIN.getServer().getWorld(world) != null;
            }
        }))
            if (PLUGIN.getServer().getWorld(world) != null) disabledWorlds.add(world);
        DISABLED_WORLDS = ImmutableSet.copyOf(disabledWorlds);
        return PLUGIN.getServer().getWorlds().size() != DISABLED_WORLDS.size();
    }

    private static void loadListeners() {
        PluginManager register = Bukkit.getServer().getPluginManager();

        // Engine
        for (ListedListener listener : ListedListener.values())
            register.registerEvents(listener.getListener(), PLUGIN);

        // Disabled worlds
        if (!DISABLED_WORLDS.isEmpty()) register.registerEvents(new ZoneListener(), PLUGIN);
    }

    // Listeners
    public enum ListedListener {
        CHAT(new ChatListener()), PLAYER(new PlayerListener());

        private Listener listener;

        private ListedListener(Listener listener) {
            this.listener = listener;
        }

        public Listener getListener() {
            return listener;
        }
    }
}
