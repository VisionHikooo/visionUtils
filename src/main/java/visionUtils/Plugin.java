package visionUtils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import visionUtils.commands.NpcCMD;
import visionUtils.commands.RainingTrollCMD;
import visionUtils.commands.SkipNightCMD;
import visionUtils.listener.*;
import visionUtils.npc.NpcManager;
import visionUtils.utils.FileManager;
import visionUtils.utils.statics.Messages;
import visionUtils.utils.statics.Statics;

public final class Plugin extends JavaPlugin {

    private static FileManager fileManager;
    private static NpcManager npcManager;

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
        this.getCommand("rain").setExecutor(new RainingTrollCMD());
        this.getCommand("npc").setExecutor(new NpcCMD());
    }

    private void initListener() {
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnerBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnerPlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeaveListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
    }

    private void initUtils() {
        fileManager = new FileManager();
        npcManager = new NpcManager();
    }

    public static FileManager getFileManager() {
        return fileManager;
    }

    public static NpcManager getNpcManager() {
        return npcManager;
    }
}
