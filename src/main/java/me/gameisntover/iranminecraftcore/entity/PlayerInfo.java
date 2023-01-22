package me.gameisntover.iranminecraftcore.entity;

import org.bukkit.Location;

import java.util.UUID;

public interface PlayerInfo {
    boolean isStaff();
    boolean isOnline();
    Location getLocation();
    UUID getUniqueId();
    String getName();
}
