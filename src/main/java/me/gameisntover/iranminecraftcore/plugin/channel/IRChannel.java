package me.gameisntover.iranminecraftcore.plugin.channel;

import me.gameisntover.iranminecraftcore.plugin.IranMinecraftPlugin;
import me.gameisntover.iranminecraftcore.entity.PlayerEntity;
import org.bukkit.Bukkit;
import org.bukkit.plugin.messaging.PluginMessageListener;

public abstract class IRChannel implements PluginMessageListener {
    private final Type type;
    private final String name;

    private boolean registered;

    public IRChannel(String channelName, Type side) {
        this.name = channelName;
        this.type = side;
    }

    public IRChannel(String channelName) {
        this(channelName, Type.BOTH);
    }

    public void register() {
        switch (type) {
            case BOTH:
                Bukkit.getMessenger().registerIncomingPluginChannel(IranMinecraftPlugin.getInstance(), name, this);
                Bukkit.getMessenger().registerOutgoingPluginChannel(IranMinecraftPlugin.getInstance(), name);
                break;
            case INCOMING:
                Bukkit.getMessenger().registerIncomingPluginChannel(IranMinecraftPlugin.getInstance(), name, this);
                break;
            case OUTGOING:
                Bukkit.getMessenger().registerOutgoingPluginChannel(IranMinecraftPlugin.getInstance(), name);
                break;
        }
        registered = true;
    }

    public void unRegister() {
        switch (type) {
            case BOTH:
                Bukkit.getMessenger().unregisterIncomingPluginChannel(IranMinecraftPlugin.getInstance(), name, this);
                Bukkit.getMessenger().unregisterOutgoingPluginChannel(IranMinecraftPlugin.getInstance(), name);
                break;
            case INCOMING:
                Bukkit.getMessenger().unregisterIncomingPluginChannel(IranMinecraftPlugin.getInstance(), name, this);
                break;
            case OUTGOING:
                Bukkit.getMessenger().unregisterOutgoingPluginChannel(IranMinecraftPlugin.getInstance(), name);
                break;
        }
        registered = true;
    }


    public void sendData(byte[] b, PlayerEntity player) {
        player.sendData(b,this);
    }

    public boolean isRegistered() {
        return registered;
    }

    public String getName() {
        return name;
    }

    public static enum Type {
        INCOMING,
        OUTGOING,
        BOTH
    }

    public Type getType() {
        return type;

    }
}
