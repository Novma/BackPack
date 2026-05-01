package io.github.Novma.backPack;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class CropsListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCropsBreak(BlockBreakEvent e) {

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCropsPickup(EntityPickupItemEvent e) {

    }
}
