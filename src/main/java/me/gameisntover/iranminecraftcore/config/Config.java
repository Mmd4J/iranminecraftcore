package me.gameisntover.iranminecraftcore.config;

import com.cryptomorin.xseries.XSound;
import me.gameisntover.iranminecraftcore.plugin.IranMinecraftPlugin;
import me.gameisntover.iranminecraftcore.response.TextResponse;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static me.gameisntover.iranminecraftcore.util.StringUtil.color;

public class Config extends YamlConfiguration {
    private final File file;
    public String name;


    public Config(File f, String name) {
        try {
            this.name = name;
            file = new File(f, name);
            if (!file.exists()) IranMinecraftPlugin.getInstance().saveResource(name, true);
            load(new File(f, name));
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public Config(String name) {
        this(IranMinecraftPlugin.getInstance().getDataFolder(), name);
    }

    @Override
    public String getString(String path) {
        return color(super.getString(path));
    }

    @Override
    public List<String> getStringList(String path) {
        List<String> strings = new ArrayList<>();
        for (String string : super.getStringList(path)) strings.add(color(string));
        return strings;
    }

    public TextResponse getText(String path) {
        String text = getString(path + ".text");
        XSound sound = null;
        if (isSet(path + ".sound")) sound = XSound.valueOf(getString(path + ".sound"));
        String hoverText = getString(path + ".hover-text");
        ClickEvent.Action type = null;
        if (isSet(path + ".event-type"))
            type = ClickEvent.Action.valueOf(getString(path + ".click-event.type"));
        ClickEvent event = null;
        if (type != null && isSet(path + ".click-event.action"))
            event = new ClickEvent(type, getString(path + ".click-event.action"));
        return new TextResponse(text, sound, hoverText, event);
    }

    public TextResponse getMessage(String path) {
        return getText("messages." + path);
    }
}
