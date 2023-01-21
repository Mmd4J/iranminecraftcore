package me.gameisntover.iranminecraftcore.command;

import me.gameisntover.iranmcreportsspigot.IranMCReportsSpigot;
import me.gameisntover.iranmcreportsspigot.player.PlayerImpl;
import me.gameisntover.iranminecraftcore.plugin.IranMinecraftPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class IRCommand extends Command implements PluginIdentifiableCommand {
    private static CommandMap commandMap;

    protected PermissionDefault permissionDefault;

    protected IRCommand(String name, String description, PermissionDefault permDefault) {
        super(name);
        setDescription(description);
        if (commandMap == null) {
            try {
                commandMap = (CommandMap) SimplePluginManager.class.getDeclaredField("commandMap").get(Bukkit.getPluginManager());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender.hasPermission(getBukkitPermission())) return false;
        else return perform(PlayerImpl.of((Player) sender), args);
    }

    public abstract boolean perform(PlayerImpl sender, String[] args);

    public List<String> performTab(PlayerImpl sender, String[] args) {
        return new ArrayList<>();
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        List<String> list = performTab(PlayerImpl.of((Player) sender), args);
        if (list.isEmpty()) return super.tabComplete(sender, alias, args);
        else {
            List<String> l = new ArrayList<>();
            for (String s : list)
                if (args[args.length - 1].startsWith(s)) l.add(s);
            if (l.isEmpty()) {
                for (Player player : sender.getServer().getOnlinePlayers()) {
                    if (((Player) sender).canSee(player) && StringUtil.startsWithIgnoreCase(player.getName(), args[args.length - 1]))
                        l.add(player.getName());
                }
            }
            return l;
        }
    }

    @Override
    public Plugin getPlugin() {
        return IranMinecraftPlugin.getInstance();
    }

    public Permission getBukkitPermission() {
        return Bukkit.getPluginManager().getPermission("iranminecraft.tickets." + getName());
    }

    public PermissionDefault getPermissionDefault() {
        return permissionDefault;
    }
}
