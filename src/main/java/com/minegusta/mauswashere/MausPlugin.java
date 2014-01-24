package com.minegusta.mauswashere;

import com.censoredsoftware.util.Messages;
import com.minegusta.mauswashere.data.DataManager;
import com.minegusta.mauswashere.data.ThreadManager;
import com.minegusta.mauswashere.data.VotePointsDataManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Class for the Bukkit plugin.
 */
public class MausPlugin extends JavaPlugin {
    /**
     * The Bukkit enable method.
     */
    @Override
    public void onEnable() {
        // Load the game engine.
        MausWasHere.load();


        // Print success!
        Messages.info("Successfully enabled.");
    }

    /**
     * The Bukkit disable method.
     */
    @Override
    public void onDisable() {
        // Save all the data.
        DataManager.save();
        VotePointsDataManager.save();
        Bukkit.getScheduler().cancelTask(MausWasHere.SAVE_TASK);
        // Cancel all threads, Event calls, and connections.
        ThreadManager.stopThreads();
        HandlerList.unregisterAll(this);

        Messages.info("Successfully disabled.");
    }
}
