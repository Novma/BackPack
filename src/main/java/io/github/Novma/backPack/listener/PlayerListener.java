package io.github.Novma.backPack.listener;

import io.github.Novma.backPack.backpack.BackPackManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
    private final BackPackManager backPackManager;

    public PlayerListener(BackPackManager backPackManager) {
        this.backPackManager = backPackManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        backPackManager.add(e.getPlayer());
        e.getPlayer().give(new ItemStack(Material.CARROT, 64));
        e.getPlayer().give(new ItemStack(Material.WHEAT, 64));
        e.getPlayer().give(new ItemStack(Material.POTATO, 64));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        backPackManager.remove(e.getPlayer());
    }
}
