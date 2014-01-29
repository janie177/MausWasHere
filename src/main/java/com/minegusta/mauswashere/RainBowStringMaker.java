package com.minegusta.mauswashere;


import org.bukkit.ChatColor;

public class RainBowStringMaker {
    public static String rainbowify(String s){
        StringBuilder builder = new StringBuilder(s);
        if(s.length() == 0)return s;

        ChatColor[] color = {ChatColor.DARK_RED, ChatColor.RED, ChatColor.GOLD, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.DARK_GREEN, ChatColor.AQUA, ChatColor.DARK_AQUA, ChatColor.BLUE, ChatColor.DARK_BLUE, ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE};
        int place = 0;
        for(int index = 0; index < s.length(); index++){
            builder.insert(index, color[place]);
            place = place +1;
            if(place == 11){
                place = 0;
            }
        }
        return builder.toString();
    }

}
