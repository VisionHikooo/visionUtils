package visionUtils.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.entity.EntityDamageEvent;
import visionUtils.Plugin;
import visionUtils.npc.Corp;
import visionUtils.npc.VisionNPC;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileManager {

    private Plugin plugin;

    private FileConfiguration config;
    private String defaultPath;

    public FileManager(Plugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        config.options().copyDefaults(true);
        saveConfig();

        defaultPath = plugin.getDataFolder().getPath();
    }

    public void saveConfig() {
        plugin.saveConfig();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void setupSaveStatsFolder() {
        File folder = new File(defaultPath + "/saves");
        if (!folder.exists())
            folder.mkdirs();
    }

    public HashMap<EntityDamageEvent.DamageCause, List<String>> getDeathMessages() {
        HashMap<EntityDamageEvent.DamageCause, List<String>> map = new HashMap<>();

        config.getConfigurationSection("deathMessages").getValues(false)
                .forEach( (string, object) -> {
                    EntityDamageEvent.DamageCause cause = EntityDamageEvent.DamageCause.valueOf(string);
                    List<String> messages = config.getStringList("deathMessages." + string);
                    map.put(cause, messages);
                });

        return map;
    }



    public void safeAllNPCs() {
        System.out.println("Npcs werden gespeichert");
        setupSaveStatsFolder();

        Plugin.getNpcManager().getNpcs().forEach(visionNPC -> {
            safeNpc(visionNPC);
        });

        Plugin.getCorpManager().getCorps().forEach(corp -> {
            safeNpc(corp);
        });


    }

    private void safeNpc(VisionNPC visionNPC) {
        JsonObject object = visionNPC.getJsonObject();
        try {
            System.out.println("Npc wird gespeichert.");
            File file = new File(defaultPath + "/saves/" + visionNPC.getUuid().toString() + ".json");
            file.createNewFile();
            System.out.println("File wurde erstellt");

            FileWriter fw = new FileWriter(file);
            fw.write(object.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadNPCs() {
        setupSaveStatsFolder();
        File folder = new File(defaultPath + "/saves");

        for (File file : folder.listFiles()) {
            if (!file.getName().endsWith(".json"))
                continue;

            System.out.println("Ein Npc wird geladen!");

            try {
                String content = new String(Files.readAllBytes(file.toPath()));

                if (content.isEmpty())
                    continue;

                JsonObject json = JsonParser.parseString(content).getAsJsonObject();

                if (json.has("whenDied"))
                    Plugin.getCorpManager().addCorp(json);
                else
                    Plugin.getNpcManager().addNPC(json);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
