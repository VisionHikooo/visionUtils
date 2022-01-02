package visionUtils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import visionUtils.commands.SkipNightCMD;
import visionUtils.listener.*;
import visionUtils.utils.statics.Messages;
import visionUtils.utils.statics.Statics;

public final class Plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        initListener();
        initCommands();
        initUtils();

        Messages.loadMessages();
        Statics.initItems();
    }

    @Override
    public void onDisable() {
        // Safe-Methoden

    }

    private void initCommands() {
        this.getCommand("skip").setExecutor(new SkipNightCMD());
    }

    private void initListener() {
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnerBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnerPlaceListener(), this);
    }

    private void initUtils() {

    }
}
