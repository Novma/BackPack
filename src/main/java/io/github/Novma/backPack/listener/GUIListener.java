package io.github.Novma.backPack.listener;

import io.github.Novma.backPack.BackPackPlugin;
import io.github.Novma.backPack.backpack.BackPackGUI;
import io.github.Novma.backPack.backpack.BackPackManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public class GUIListener implements Listener {
    private final BackPackManager backpackManager;

    public GUIListener(BackPackManager backpackManager) {
        this.backpackManager = backpackManager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof BackPackGUI)) return;
        e.setCancelled(true);
        Inventory inv = e.getClickedInventory();
        if (inv == null) return;
        if (inv.getHolder() instanceof BackPackGUI) {
            if (BackPackPlugin.getDebug()) BackPackPlugin.getInstance().getLogger().info("[DEBUG] update");
            backpackManager.get(e.getWhoClicked().getUniqueId()).getGui().update(inv, e.getSlot());
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if (BackPackPlugin.getDebug()) BackPackPlugin.getInstance().getLogger().info("[DEBUG] azione intercettata");
        if (e.getInventory().getHolder() instanceof BackPackGUI) {
            if (BackPackPlugin.getDebug()) BackPackPlugin.getInstance().getLogger().info("[DEBUG] azione negata");
            e.setCancelled(true);
        }
    }
}
