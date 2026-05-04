package io.github.Novma.backPack.util;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class YamlManager {
    private static Plugin plugin;

    public static void setup(Plugin plugin) {
        YamlManager.plugin = plugin;
    }

    public static boolean createFolder(String path) {
        File folder = new File(plugin.getDataFolder(), path);
        if (!folder.exists()) {
            return folder.mkdirs();
        }
        return true;
    }

    public static List<?> getList(String path, String key) {
        return YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), path + ".yml")).getList(key);
    }

    public static void saveList(String path, List<ItemStack> items, String key) {
        YamlConfiguration config = new  YamlConfiguration();
        config.set(key, items);
        try {
            config.save(new File(plugin.getDataFolder(), path + ".yml"));
        } catch (IOException e) {
            plugin.getLogger().severe(e.getMessage());
        }
    }
}
