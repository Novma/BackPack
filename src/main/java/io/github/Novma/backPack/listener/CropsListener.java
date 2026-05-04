package io.github.Novma.backPack.listener;

import io.github.Novma.backPack.BackPackPlugin;
import io.github.Novma.backPack.backpack.BackPackConfig;
import io.github.Novma.backPack.backpack.BackPackManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class CropsListener implements Listener {
    private final BackPackManager backPackManager;

    public CropsListener(BackPackManager backPackManager) {
        this.backPackManager = backPackManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCropsBreak(BlockBreakEvent e) {

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCropsPickup(PlayerAttemptPickupItemEvent e) {
        if (e.getRemaining() == 0) return;
        if (BackPackPlugin.getDebug()) BackPackPlugin.getInstance().getLogger().info("[DEBUG] ci sono leftover");
        ItemStack item = e.getItem().getItemStack();
        Material type = item.getType();
        if (!isCrop(type)) return;
        if (BackPackPlugin.getDebug()) BackPackPlugin.getInstance().getLogger().info("[DEBUG] l'item è una crop");

        e.setCancelled(true);

        ItemStack inv = new ItemStack(item);
        inv.setAmount(item.getAmount() - e.getRemaining());
        e.getPlayer().getInventory().addItem(inv);

        ItemStack backpack = new ItemStack(item);
        backpack.setAmount(e.getRemaining());
        ItemStack over = backPackManager.get(e.getPlayer().getUniqueId()).add(backpack);
        if (BackPackPlugin.getDebug()) BackPackPlugin.getInstance().getLogger().info("[DEBUG] aggiunto a backpack");

        e.getItem().setItemStack(over);
    }

    private boolean isCrop(Material type) {
        return BackPackPlugin.getBackPackConfig().getCrops().contains(type);
    }
}
