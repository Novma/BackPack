package io.github.Novma.backPack.backpack;

import io.github.Novma.backPack.BackPackPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BackPack {
    private List<ItemStack> contents = new ArrayList<>();
    private final BackPackGUI gui;
    private boolean dirty;

    public BackPack(Player player) {
        this(player, new ArrayList<ItemStack>());
    }

    public BackPack(Player player, List<ItemStack> contents) {
        this.gui = new BackPackGUI(player, this);
        addFirst(contents);
        dirty = false;
    }

    public ItemStack add(ItemStack items) {
        Inventory fake =  Bukkit.createInventory(null, ((contents.size() - 1) / 9 + 1) * 9);
        fake.addItem(contents.toArray(new ItemStack[0]));
        fake.addItem(items);
        contents = new ArrayList<>(Arrays.stream(fake.getContents()).filter(Objects::nonNull).toList());
        dirty = true;
        ItemStack over = null;
        int pages = BackPackPlugin.getBackPackConfig().getPages();
        if (pages >= 1)
            if (contents.size() >= pages * BackPackPlugin.getBackPackConfig().getLines()) {
                over = contents.removeLast();
            }
        return over;
    }

    private void addFirst(List<ItemStack> contents) {
        this.contents.addAll(contents);
    }

    public ItemStack retrieve(int index, int page) {
        int fullIndex = index + (page * 9 * BackPackPlugin.getBackPackConfig().getLines());
        if (fullIndex >= contents.size()) return null;
        dirty = true;
        return contents.remove(fullIndex);
    }

    public List<ItemStack> getContents() {return this.contents;}
    public BackPackGUI getGui() {return this.gui;}
    public boolean isDirty() {return dirty;}
}
