package me.gameisntover.iranminecraftcore.ui;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import static me.gameisntover.iranmcreportsspigot.util.StringUtil.color;

public abstract class InventoryButton extends ItemStack {
    protected InventoryButton(XMaterial mat, String name) {
        super(mat.parseMaterial());
        setData(new MaterialData(mat.getId(), mat.getData()));

    }

    protected InventoryButton() {
        this(XMaterial.GRAY_STAINED_GLASS_PANE," ");
    }

    public abstract void onClick(InventoryClickEvent e);

    public void setName(String name) {
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(color(name));
        setItemMeta(meta);
    }

    public void setType(XMaterial material) {
        setType(material.parseMaterial());
        setData(new MaterialData(material.getId(), material.getData()));
    }

    public void addItemFlags(ItemFlag... fLags) {
        ItemMeta meta = getItemMeta();
        meta.addItemFlags(fLags);
        setItemMeta(meta);
    }

    public static ButtonBuilder builder(){
        return new ButtonBuilder();
    }

    public static InventoryButton empty(){
        return builder().build();
    }
}
