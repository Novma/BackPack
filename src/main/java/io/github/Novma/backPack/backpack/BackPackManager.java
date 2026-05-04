package io.github.Novma.backPack.backpack;

import io.github.Novma.backPack.BackPackPlugin;
import io.github.Novma.backPack.util.YamlManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class BackPackManager {
    private final Map<UUID, BackPack> backPacks;

    public BackPackManager() {
        backPacks = new HashMap<UUID, BackPack>();
        if (!YamlManager.createFolder("backpacks")) BackPackPlugin.getInstance().getLogger().log(Level.WARNING, "Could not create folder backpacks");
    }

    public void add(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(BackPackPlugin.getInstance(), () -> {
            backPacks.put(player.getUniqueId(), load(player));
        });
    }

    private BackPack load(Player player) {
        BackPack backPack;
        try {
            List<ItemStack> list = (List<ItemStack>) YamlManager.getList("backpacks/" + player.getUniqueId(), "backpack");
            if (list == null) backPack = new BackPack(player);
            else backPack = new BackPack(player, list);
        } catch (Exception ex) {
            backPack = new BackPack(player);
            BackPackPlugin.getInstance().getLogger().log(Level.WARNING, "Backpack di " + player.getUniqueId() + " non reperibile", ex);
        }
        return backPack;
    }

    public void remove(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(BackPackPlugin.getInstance(), () -> {
            save(player.getUniqueId());
            backPacks.remove(player.getUniqueId());
        });
    }

    private void save(UUID uuid) {
        BackPack backPack = backPacks.get(uuid);
        if (backPack.isDirty()) {
            YamlManager.saveList("backpacks/" + uuid, backPack.getContents(), "backpack");
        }
    }

    public void removeAll() {
        saveAllDirty();
        backPacks.clear();
    }

    private void saveAllDirty() {
        for (Map.Entry<UUID, BackPack> entry : backPacks.entrySet()) {
            if (entry.getValue().isDirty()) save(entry.getKey());
        }
    }

    public BackPack get(UUID uuid) {
        return backPacks.get(uuid);
    }
}
