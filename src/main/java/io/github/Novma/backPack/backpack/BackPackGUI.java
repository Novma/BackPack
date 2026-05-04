package io.github.Novma.backPack.backpack;

import io.github.Novma.backPack.BackPackPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackPackGUI implements InventoryHolder {
    private int page;
    private final Player player;
    private final BackPack backpack;
    private boolean canModify = true;

    public BackPackGUI(Player player, BackPack backpack) {
        this.page = 0;
        this.player = player;
        this.backpack = backpack;
    }

    public void open(Player called, boolean canModify) {
        page = 0;
        this.canModify = canModify;
        int lines = BackPackPlugin.getBackPackConfig().getLines();
        Inventory inventory = Bukkit.createInventory(this, (lines + 1) * 9, Component.text(BackPackPlugin.getBackPackConfig().getTitle()
                .replace("%act%", page + 1 +"").replace("%max%", Integer.toString((backpack.getContents().size() - 1) / (lines * 9) + 1))));

        inventory.setStorageContents(build(backpack.getContents()));

        called.openInventory(inventory);
    }

    public void update(Inventory inv, int index) {
        // Check block
        if (index == BackPackPlugin.getBackPackConfig().getPosPre() + (BackPackPlugin.getBackPackConfig().getLines() * 9)) {
            if (BackPackPlugin.getDebug()) BackPackPlugin.getInstance().getLogger().info("[DEBUG] previous cliccato");
            if (page == 0) return;
            page--;
        } else if (index == BackPackPlugin.getBackPackConfig().getPosNext() + (BackPackPlugin.getBackPackConfig().getLines() * 9)) {
            if (BackPackPlugin.getDebug()) BackPackPlugin.getInstance().getLogger().info("[DEBUG] next cliccato");
            if ((page + 1) * BackPackPlugin.getBackPackConfig().getLines() * 9 >= backpack.getContents().size()) return;
            page++;
        } else {
            // Get item
            if (!canModify) return;
            if (BackPackPlugin.getDebug()) BackPackPlugin.getInstance().getLogger().info("[DEBUG] item cliccato");
            if (player.getInventory().firstEmpty() == -1) {
                player.sendMessage(Component.text(BackPackPlugin.getBackPackConfig().getFullInventory()));
                return;
            }
            if (BackPackPlugin.getDebug()) BackPackPlugin.getInstance().getLogger().info("[DEBUG] spazio trovato");
            ItemStack retrieved = backpack.retrieve(index, page);
            if (retrieved == null) return;
            player.getInventory().addItem(retrieved);
        }
        inv.setStorageContents(build(backpack.getContents()));
    }

    private ItemStack[] build(List<ItemStack> contents) {
        int lines = BackPackPlugin.getBackPackConfig().getLines();
        List<ItemStack> items = new ArrayList<>(contents.subList(page * lines * 9, Math.min((lines * 9) * (1 + page), contents.size())));
        for (int i = items.size(); i < lines * 9; i++) {
            items.add(null);
        }
        List<ItemStack> utils = Arrays.asList(BackPackPlugin.getBackPackConfig().getUtils());
        for (int i = lines * 9; i < (lines + 1) * 9; i++) {
            items.add(i, utils.get(i % 9));
        }
        return items.toArray(new ItemStack[0]);
    }

    @Override
    public @NonNull Inventory getInventory() {return Bukkit.createInventory(null, 9);}
}
