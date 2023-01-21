package me.gameisntover.iranminecraftcore.ui;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static me.gameisntover.iranmcreportsspigot.util.StringUtil.color;

public class InventoryGUI {
    private final Inventory inventory;
    private final Map<Integer, InventoryButton> buttonMap = new HashMap<>();
    protected Consumer<InventoryOpenEvent> openConsumer = inventoryOpenEvent -> {
    };
    protected Consumer<InventoryCloseEvent> closeConsumer = inventoryOpenEvent -> {
    };

    protected InventoryGUI(String name, int slots) {
        this.inventory = Bukkit.createInventory(null, slots * 9, color(name));
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setItem(Integer index, InventoryButton button) {
        this.inventory.setItem(index, button);
        this.buttonMap.put(index, button);
    }

    public void addItem(InventoryButton button) {
        for (int i = 0; i < inventory.getSize() - 1; i++) {
            if (!contains(i)) {
                setItem(i, button);
                break;
            }
        }
    }

    public void fillEmpties() {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (!contains(i)) setItem(i,InventoryButton.empty());
        }
    }

    public InventoryButton get(Integer index) {
        return buttonMap.get(index);
    }

    public boolean contains(Integer i) {
        return buttonMap.containsKey(i);
    }

    public void onOpenInventoryEvent(Consumer<InventoryOpenEvent> e) {
        this.openConsumer = e;
    }

    public void onCloseInventoryEvent(Consumer<InventoryCloseEvent> e) {
        this.closeConsumer = e;
    }
}
