package me.gameisntover.iranminecraftcore.plugin;

import me.gameisntover.iranminecraftcore.command.IRCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class IranMinecraftPlugin extends JavaPlugin {
    private static IranMinecraftPlugin instance;
    protected CommandMap commandMap;
    public void registerCommand(IRCommand command){
        commandMap.register("iranminecraft",command);
        String perm = "iranminecraft.tickets." + getName();
        if (Bukkit.getPluginManager().getPermission(perm) == null)
            Bukkit.getPluginManager().addPermission(new Permission(perm));
        Permission permission = Bukkit.getPluginManager().getPermission(perm);
        permission.setDefault(command.getPermissionDefault());
        permission.setDescription(command.getDescription());
        permission.addParent("iranminecraft.tickets", true);
    }
    @Override
    public void onEnable() {
        instance = this;

    }

    public static IranMinecraftPlugin getInstance() {
        return instance;
    }
}
