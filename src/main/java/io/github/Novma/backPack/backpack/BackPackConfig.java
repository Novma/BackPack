package io.github.Novma.backPack.backpack;

import io.github.Novma.backPack.BackPackPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BackPackConfig {
    private final File configFile;
    private YamlConfiguration config;
    private final ItemStack[] utils = new  ItemStack[9];
    private int posPre, posNext;
    private final List<Material> crops = new ArrayList<>();

    public BackPackConfig() {
        this.configFile = new File(BackPackPlugin.getInstance().getDataFolder(), "backpack.yml");
        load();
    }

    public void load() {
        this.config = YamlConfiguration.loadConfiguration(configFile);
        setupUtils();
        setupCrops();
    }

    private void setupUtils() {
        posPre = config.getInt("gui.previous");
        posNext = config.getInt("gui.next");
        if (posPre < 0 || posNext < 0 || posPre > 8 || posNext > 8) {
            BackPackPlugin.getInstance().getLogger().warning("Posizioni dei tasti previous e next non accettate");
            posPre = 1;
            posNext = 7;
        }
        for (int i = 0; i < 9; i++) {
            if (i == posPre) utils[i] = createUtil("previous");
            else if (i == posNext) utils[i] = createUtil("next");
            else utils[i] = createUtil("filler");
        }
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
            meta.displayName(Component.text(config.getString("utils." + name + ".name", "")));
            meta.lore(config.getStringList("utils." + name + ".lore").stream()
                    .map(line -> LegacyComponentSerializer.legacyAmpersand().deserialize(line))
                    .toList());
            meta.setEnchantmentGlintOverride(config.getBoolean("utils." + name + ".glint", true));
            item.setItemMeta(meta);
        }
        return item;
    }

    private void setupCrops() {
        List<String> list = config.getStringList("crops");
        for (String s : list) {
            crops.add(Material.matchMaterial(s));
        }
    }

    public int getLines() {
        return this.config.getInt("size.lines");
    }
    public int getPages() {
        return this.config.getInt("size.pages");
    }
    public ItemStack[] getUtils() {return this.utils;}
    public String getTitle() {return config.getString("gui.title");}
    public int  getPosPre() {return posPre;}
    public int getPosNext() {return posNext;}
    public String getFullInventory() {return config.getString("messages.full-inventory");}
    public List<Material> getCrops() {return crops;}
}
