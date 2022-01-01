package visionUtils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import visionUtils.listener.BlockBreakListener;
import visionUtils.listener.PlayerInteractListener;
import visionUtils.listener.SpawnerBreakListener;
import visionUtils.utils.statics.Messages;
import visionUtils.utils.statics.Statics;

public final class Plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        initListener();
        initCommands();

        Messages.loadMessages();
        Statics.initItems();
    }

    @Override
    public void onDisable() {
        // Safe-Methoden

    }

    private void initCommands() {

    }

    private void initListener() {
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnerBreakListener(), this);
    }
}
