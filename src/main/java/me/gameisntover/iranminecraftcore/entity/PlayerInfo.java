package me.gameisntover.iranminecraftcore.entity;

import org.bukkit.Location;

import java.util.UUID;

public interface PlayerInfo {
    boolean canOpenTicket();

    boolean isStaff();

    boolean canAnswerTicket();

    boolean isOnline();

    Location getLocation();

    UUID getUniqueId();

    String getName();
}
