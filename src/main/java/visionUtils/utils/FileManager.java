package visionUtils.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.entity.EntityDamageEvent;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class FileManager {

    private FileConfiguration config;
    private File configFile;

    public FileManager(FileConfiguration config) {
        this.config = config;
        config.options().copyDefaults(true);
    }

    public FileConfiguration getConfig() {
        return config;
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
}
