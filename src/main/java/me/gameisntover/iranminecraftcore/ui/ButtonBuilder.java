package me.gameisntover.iranminecraftcore.ui;

import com.cryptomorin.xseries.XEnchantment;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static me.gameisntover.iranmcreportsspigot.util.StringUtil.color;

public class ButtonBuilder {
    private XMaterial material = XMaterial.GRAY_STAINED_GLASS_PANE;
    private String name = " ";
    public List<String> lores = new ArrayList<>();
    private XEnchantment[] enchantments = new XEnchantment[]{};
    private ItemFlag[] itemFlags = new ItemFlag[]{};
    private Consumer<InventoryClickEvent> consumer = inventoryClickEvent -> {};

    public ButtonBuilder mat(XMaterial material) {
        this.material = material;
        return this;
    }

    public ButtonBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ButtonBuilder lores(String... lores) {
        this.lores = color(Arrays.stream(lores).collect(Collectors.toList()));
        return this;
    }

    public ButtonBuilder enchants(XEnchantment... enchantment) {
        this.enchantments = enchantment;
        return this;
    }

    public ButtonBuilder itemFlags(ItemFlag... flags) {
        this.itemFlags = flags;
        return this;
    }

    public ButtonBuilder clickEvent(Consumer<InventoryClickEvent> e) {
        this.consumer = e;
        return this;
    }

    public InventoryButton build() {
        InventoryButton button = new InventoryButton(material,name) {
            @Override
            public void onClick(InventoryClickEvent e) {
                consumer.accept(e);
            }
        };
        button.addItemFlags(itemFlags);
        for (XEnchantment enchantment : enchantments) button.addEnchantment(enchantment.getEnchant(),1);
        return button;
    }
}
