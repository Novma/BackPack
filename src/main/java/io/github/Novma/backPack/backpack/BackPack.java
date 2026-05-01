package io.github.Novma.backPack.backpack;

import io.github.Novma.backPack.BackPackPlugin;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BackPack {
    private List<ItemStack> contents = new ArrayList<>();
    private boolean debug;

    public BackPack() {}

    public BackPack(ArrayList<ItemStack> contents) {
        this.debug = BackPackPlugin.getInstance().getConfig().getBoolean("debug");
        for (ItemStack item : contents) {
            add(item);
        }
    }

    public void add(ItemStack itemStack) {
        Inventory fake =  Bukkit.createInventory(null, ((contents.size() - 1) / 9 + 1) * 9);
        Map<Integer, ItemStack> left = fake.addItem(contents.toArray(new ItemStack[0]));
        if (debug) {
            BackPackPlugin.getInstance().getLogger().warning("Ci sono item non aggiunti");
        }
        contents = Arrays.stream(fake.getContents()).filter(Objects::nonNull).toList();
    }

    public ItemStack retrieve(int index) {
        return contents.remove(index);
    }
}
