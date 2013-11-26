package com.minegusta.mauswashere;

import com.censoredsoftware.helper.WrappedCommand;
import com.censoredsoftware.util.Messages;
import com.minegusta.mauswashere.command.*;
import com.minegusta.mauswashere.data.ThreadManager;
import com.minegusta.mauswashere.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class MausWasHere {
    // Constants
    public static String SAVE_PATH;

    // Public Static Access
    public static final MausPlugin PLUGIN;

    // Load what is possible to load right away.
    static {
        // Allow static access.
        PLUGIN = (MausPlugin) Bukkit.getServer().getPluginManager().getPlugin("MausWasHere");
    }

    // Load everything else.
    protected static void load() {
        // Start the data
        SAVE_PATH = PLUGIN.getDataFolder() + "/data/"; // Don't change this.

        // Load listeners
        loadListeners();
        loadCommands();

        // Start threads
        ThreadManager.startThreads();
    }

    private static void loadListeners() {
        PluginManager register = Bukkit.getServer().getPluginManager();

        // Engine
        for (ListedListener listener : ListedListener.values())
            register.registerEvents(listener.getListener(), PLUGIN);
    }

    private static void loadCommands() {
        int count = 0;
        for (ListedCommand command : ListedCommand.values())
            count += command.getCommand().getCommands().size();
        Messages.info(count + " commands loaded.");
    }

    // Listeners
    public enum ListedListener {
        BLOCK(new BlockListener()), CHAT(new ChatListener()), ENTITY(new EntityListener()), PLAYER(new PlayerListener()), PROJECTILE(new ProjectileListener());

        private Listener listener;

        private ListedListener(Listener listener) {
            this.listener = listener;
        }

        public Listener getListener() {
            return listener;
        }
    }

    // Commands
    public enum ListedCommand {
        DISCO(new DiscoCommand()), EGG(new EggCommand()), EXPLODE(new ExplodeCommand()), FART(new FartCommand()), HIGH_FIVE(new HighFiveCommand()), HUG(new HugCommand()), KISS(new KissCommand()), MILK(new MilkCommand()), MUTE(new MuteCommand()), NINJA(new NinjaCommand()), NUKE_ARROW(new NukeArrowCommand()), NUKE(new NukeCommand()), POP(new PopCommand()), POTION(new PotionCommands()), RENAME(new RenameCommand()), ROLL(new RollCommand()), PARTICLE(new EffectCommand());

        private WrappedCommand command;

        private ListedCommand(WrappedCommand command) {
            this.command = command;
        }

        public WrappedCommand getCommand() {
            return command;
        }
    }
}
