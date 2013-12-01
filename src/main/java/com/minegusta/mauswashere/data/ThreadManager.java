package com.minegusta.mauswashere.data;

import com.censoredsoftware.util.Configs;
import com.censoredsoftware.util.Messages;
import com.minegusta.mauswashere.MausWasHere;
import com.minegusta.mauswashere.trigger.Trigger;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

@SuppressWarnings("deprecation")
public class ThreadManager {
    public static void startThreads() {
        // Start sync runnable
        Bukkit.getScheduler().scheduleSyncRepeatingTask(MausWasHere.PLUGIN, Util.getSyncRealityRunnable(), 20, 20);

        // Start async runnable
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(MausWasHere.PLUGIN, Util.getAsyncRealityRunnable(), 20, 20);

        // Start saving runnable
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(MausWasHere.PLUGIN, Util.getSaveRunnable(), 20, (Configs.getSettingInt("saving.freq") * 20));
    }

    public static void stopThreads() {
        MausWasHere.PLUGIN.getServer().getScheduler().cancelTasks(MausWasHere.PLUGIN);
    }

    private static class Util {
        private final static BukkitRunnable sync, async, save;

        static {
            sync = new BukkitRunnable() {
                @Override
                public void run() {
                    // Process Triggers
                    for (Trigger trigger : Trigger.Util.getAll())
                        trigger.processSync();
                }
            };
            async = new BukkitRunnable() {
                @Override
                public void run() {
                    // Update Timed Data
                    TimedData.Util.updateTimedData();

                    // Process Triggers
                    for (Trigger trigger : Trigger.Util.getAll())
                        trigger.processAsync();
                }
            };
            save = new BukkitRunnable() {
                @Override
                public void run() {
                    // Save data
                    DataManager.save();

                    // Send the save message to the console
                    Messages.info(Bukkit.getOnlinePlayers().length + " of " + DataManager.players.size() + " total players saved.");
                }
            };
        }

        /**
         * Returns the main sync MausWasHere runnable. Methods requiring the Bukkit API and a constant
         * update should go here.
         *
         * @return the runnable to be enabled.
         */
        public static BukkitRunnable getSyncRealityRunnable() {
            return sync;
        }

        /**
         * Returns the main asynchronous MausWasHere runnable. Methods NOT requiring the Bukkit API and a constant
         * update should go here.
         *
         * @return the runnable to be enabled.
         */
        public static BukkitRunnable getAsyncRealityRunnable() {
            return async;
        }

        /**
         * Returns the runnable that handles all data saving.
         *
         * @return the runnable to be enabled.
         */
        public static BukkitRunnable getSaveRunnable() {
            return save;
        }
    }
}
