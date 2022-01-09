package visionUtils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import visionUtils.commands.NpcCMD;
import visionUtils.commands.RainingTrollCMD;
import visionUtils.commands.SkipNightCMD;
import visionUtils.listener.*;
import visionUtils.npc.CorpManager;
import visionUtils.npc.NpcManager;
import visionUtils.utils.FileManager;
import visionUtils.utils.PacketReader;
import visionUtils.utils.statics.Messages;
import visionUtils.utils.statics.Statics;

public final class Plugin extends JavaPlugin {

    private static FileManager fileManager;
    private static NpcManager npcManager;
    private static CorpManager corpManager;

    @Override
    public void onEnable() {
        initListener();
        initCommands();
        initUtils();

        Messages.loadMessages();
        Statics.initItems();

        if (!Bukkit.getOnlinePlayers().isEmpty())
            for (Player player : Bukkit.getOnlinePlayers()) {
                PacketReader reader = new PacketReader();
                reader.inject(player);
            }

    }

    @Override
    public void onDisable() {
        if (!Bukkit.getOnlinePlayers().isEmpty())
            for (Player player : Bukkit.getOnlinePlayers()) {
                PacketReader reader = new PacketReader();
                reader.uninject(player);
            }

        // Safe-Methoden
        fileManager.safeAllNPCs();
        npcManager.clearNPCs();


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
        Bukkit.getPluginManager().registerEvents(new EntityExplodeListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractNpcListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractCorpListener(), this);

        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new GuiClickListener(), this);
    }

    private void initUtils() {
        fileManager = new FileManager(this);
        npcManager = new NpcManager();
        corpManager = new CorpManager();
    }

    public static CorpManager getCorpManager() {
        return corpManager;
    }

    public static FileManager getFileManager() {
        return fileManager;
    }

    public static NpcManager getNpcManager() {
        return npcManager;
    }
}
