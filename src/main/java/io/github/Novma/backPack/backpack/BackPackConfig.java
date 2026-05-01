package io.github.Novma.backPack.backpack;

import io.github.Novma.backPack.BackPackPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;

public class BackPackConfig {
    private final File configFile;
    private YamlConfiguration config;
    private final ItemStack[] utils = new  ItemStack[3];
    private int lines, pages;

    public BackPackConfig() {
        this.configFile = new File(BackPackPlugin.getInstance().getDataFolder(), "backpack.yml");
        load();
    }

    public void load() {
        this.config = YamlConfiguration.loadConfiguration(configFile);
        setupSize();
        setupUtils();
    }

    private void setupSize() {
        this.lines = this.config.getInt("size.lines");
        this.pages = this.config.getInt("size.pages");
    }

    private void setupUtils() {
        utils[0] = createUtil("filler");
        utils[1] = createUtil("previous");
        utils[2] = createUtil("next");
    }

    private ItemStack createUtil(String name) {
        ItemStack item;
        // Create from file
        Material material = Material.getMaterial(config.getString("utils." + name + ".material", ""));
        if (material == null) {
            material = Material.GUNPOWDER;
            item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();
            meta.displayName(Component.text("null"));
            item.setItemMeta(meta);
        } else {
            item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();
            meta.displayName(Component.text(config.getString("utils." + name + ".displayName", "")));
            meta.lore(config.getStringList("utils." + name + ".lore").stream()
                    .map(line -> LegacyComponentSerializer.legacyAmpersand().deserialize(line))
                    .toList());
            meta.setEnchantmentGlintOverride(config.getBoolean("utils." + name + ".glint", true));
            item.setItemMeta(meta);
        }
        return item;
    }

    public int getLines() {
        return this.lines;
    }
    public int getPages() {
        return this.pages;
    }
    public ItemStack getFiller() {
        return this.utils[0];
    }
    public ItemStack getPrevious() {
        return this.utils[1];
    }
    public ItemStack getNext() {
        return this.utils[2];
    }
}
