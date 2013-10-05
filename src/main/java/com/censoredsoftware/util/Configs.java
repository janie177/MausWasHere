package com.censoredsoftware.util;

import com.minegusta.mauswashere.MausWasHere;
import org.bukkit.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Module to load configuration settings from any passed in PLUGIN's config.yml.
 */
public class Configs {
    /**
     * Constructor to create a new Configs for the given PLUGIN's <code>instance</code>.
     *
     * @param instance The demigods instance the Configs attaches to.
     * @param copyDefaults Boolean for copying the default config.yml found inside this demigods over the config file utilized by this library.
     */
    static {
        Configuration config = MausWasHere.PLUGIN.getConfig();
        config.options().copyDefaults(true);
        MausWasHere.PLUGIN.saveConfig();
    }

    /**
     * Retrieve the Integer setting for String <code>id</code>.
     *
     * @param id The String key for the setting.
     * @return Integer setting.
     */
    public static int getSettingInt(String id) {
        if (MausWasHere.PLUGIN.getConfig().isInt(id)) return MausWasHere.PLUGIN.getConfig().getInt(id);
        else return -1;
    }

    /**
     * Retrieve the String setting for String <code>id</code>.
     *
     * @param id The String key for the setting.
     * @return String setting.
     */
    public static String getSettingString(String id) {
        if (MausWasHere.PLUGIN.getConfig().isString(id)) return MausWasHere.PLUGIN.getConfig().getString(id);
        else return null;
    }

    /**
     * Retrieve the Boolean setting for String <code>id</code>.
     *
     * @param id The String key for the setting.
     * @return Boolean setting.
     */
    public static boolean getSettingBoolean(String id) {
        return !MausWasHere.PLUGIN.getConfig().isBoolean(id) || MausWasHere.PLUGIN.getConfig().getBoolean(id);
    }

    /**
     * Retrieve the Double setting for String <code>id</code>.
     *
     * @param id The String key for the setting.
     * @return Double setting.
     */
    public static double getSettingDouble(String id) {
        if (MausWasHere.PLUGIN.getConfig().isDouble(id)) return MausWasHere.PLUGIN.getConfig().getDouble(id);
        else return -1;
    }

    /**
     * Retrieve the List<String> setting for String <code>id</code>.
     *
     * @param id The String key for the setting.
     * @return List<String> setting.
     */
    public static List<String> getSettingArrayListString(String id) {
        List<String> strings = new ArrayList<String>();
        if (MausWasHere.PLUGIN.getConfig().isList(id)) {
            for (String s : MausWasHere.PLUGIN.getConfig().getStringList(id))
                strings.add(s);
            return strings;
        }
        return null;
    }
}
