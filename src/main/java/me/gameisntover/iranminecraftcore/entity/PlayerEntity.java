package me.gameisntover.iranminecraftcore.entity;

import me.gameisntover.iranminecraftcore.plugin.IranMinecraftPlugin;
import me.gameisntover.iranminecraftcore.plugin.channel.IRChannel;
import me.gameisntover.iranminecraftcore.response.TextResponse;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static me.gameisntover.iranminecraftcore.util.StringUtil.color;


public class PlayerEntity implements PlayerInfo{

    private static Map<UUID, PlayerEntity> playerMap = new HashMap<>();
    private final UUID uuid;

    private boolean staff;
    private boolean online;

    protected PlayerEntity(UUID uuid) {
        this.uuid = uuid;
    }


    @Override
    public boolean isStaff() {
        return staff;
    }

    @Override
    public boolean isOnline() {
        return online;
    }

    public void sendMessage(String message) {
        getPlayer().sendMessage(color(message));
    }
    @Override
    public Location getLocation() {
        return getPlayer().getLocation();
    }


    public static PlayerEntity of(UUID uuid){
        if (!playerMap.containsKey(uuid)) playerMap.put(uuid,new PlayerEntity(uuid));
        return playerMap.get(uuid);
    }

    public static PlayerEntity of(Player player){
        return of(player.getUniqueId());
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    @Override
    public String getName() {
        return getPlayer().getName();
    }


    public void teleport(PlayerEntity player) {
        getPlayer().teleport(player.getPlayer());
    }

    public void sendMessage(TextResponse response){
        String text = color(response.getValue());
        response.getSound().ifPresent(sound -> playSound(sound,1,1));
        BaseComponent[] component = TextComponent.fromLegacyText(text);
        for (BaseComponent baseComponent : component) {
            response.getClickEvent().ifPresent(baseComponent::setClickEvent);
            response.getHoverEvent().ifPresent(baseComponent::setHoverEvent);
        }
        getPlayer().spigot().sendMessage(component);
    }

    public void playSound(Sound sound,float vol,float pitch) {
    getPlayer().playSound(getLocation(),sound,vol,pitch);
    }

    public boolean hasPermission(String perm){
        return getPlayer().hasPermission(perm);
    }

    public void teleport(Location loc){
        getPlayer().teleport(loc);
    }

    public void sendData(byte[] b, IRChannel channel) {
        getPlayer().sendPluginMessage(IranMinecraftPlugin.getInstance(), channel.getName(), b);
    }
}
