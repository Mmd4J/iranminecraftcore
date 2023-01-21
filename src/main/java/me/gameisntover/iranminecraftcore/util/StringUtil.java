package me.gameisntover.iranminecraftcore.util;

import me.gameisntover.iranminecraftcore.config.Config;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
    public static String color(String mes){
        return ChatColor.translateAlternateColorCodes('&',mes);
    }
    public static List<String> color(List<String> mes){
        List<String> strings = new ArrayList<>();
        for (String me : mes) strings.add(color(me));
        return strings;
    }
    public static Config config() {
       return config("config.yml");
    }

    public static Config config(String name) {
        return new Config(name);
    }


}
