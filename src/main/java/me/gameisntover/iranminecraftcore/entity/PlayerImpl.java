package me.gameisntover.iranminecraftcore.entity;

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


public class PlayerImpl<T> implements PlayerInfo{

    private static Map<UUID, T> playerMap = new HashMap<>();
    private final UUID uuid;

    private boolean staff;
    private boolean online;

    protected PlayerImpl(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean canOpenTicket() {
        return getPlayer().hasPermission("iranminecraft.ticket.open");
    }

    @Override
    public boolean isStaff() {
        return staff;
    }

    @Override
    public boolean canAnswerTicket() {
        return getPlayer().hasPermission("iranminecraft.ticket.answer");
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


    public static PlayerImpl of(UUID uuid){
        if (!playerMap.containsKey(uuid)) playerMap.put(uuid,new PlayerImpl(uuid));
        return playerMap.get(uuid);
    }

    public static PlayerImpl of(Player player){
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


    public void teleport(PlayerImpl player) {
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
}
