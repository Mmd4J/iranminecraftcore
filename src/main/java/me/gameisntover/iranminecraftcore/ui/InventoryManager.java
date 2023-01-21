package me.gameisntover.iranminecraftcore.ui;

import me.gameisntover.iranminecraftcore.plugin.IranMinecraftPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class InventoryManager implements Listener {
    private static InventoryManager instance;
    public static Map<Inventory,InventoryGUI> guis = new HashMap<>();
    private InventoryManager(){
        instance = this;
        Bukkit.getPluginManager().registerEvents(this, IranMinecraftPlugin.getInstance());
    }


    public static InventoryManager getInstance(){
        if (instance == null) instance = new InventoryManager();
        return instance;
    }

    public InventoryGUI createGUI(String title,int slots){
        InventoryGUI gui = new InventoryGUI(title,slots);
        guis.put(gui.getInventory(), gui);
        return gui;
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e){
        if (!guis.containsKey(e.getClickedInventory())) return;
        InventoryGUI gui = guis.get(e.getClickedInventory());
        if (!gui.contains(e.getSlot())) return;
        e.setCancelled(true);
        InventoryButton button = gui.get(e.getSlot());
        button.onClick(e);
    }

    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent e){
        if (!guis.containsKey(e.getInventory())) return;
        InventoryGUI gui = guis.get(e.getInventory());
        gui.openConsumer.accept(e);
    }
    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent e){
        if (!guis.containsKey(e.getInventory())) return;
        InventoryGUI gui = guis.get(e.getInventory());
        gui.closeConsumer.accept(e);
    }

    public void removeInventory(InventoryGUI gui){
        guis.remove(gui.getInventory());
    }
}
