package io.github.Novma.backPack.command;

import io.github.Novma.backPack.BackPackPlugin;
import io.github.Novma.backPack.backpack.BackPack;
import io.github.Novma.backPack.backpack.BackPackManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BackPackCommand implements CommandExecutor {
    private final BackPackManager manager;

    public BackPackCommand(BackPackManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can execute this command");
            return true;
        }
        if (args.length == 0) {
            manager.get(player.getUniqueId()).getGui().open(player, true);
            return true;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                reload(player);
                return true;
            } else {
                if (others(player, args[0])) {
                    return true;
                } else {
                    sender.sendMessage("Player " + args[0] + " non trovato");
                    return false;
                }
            }
        }
        return false;
    }

    private void reload(Player player) {
        if (!player.hasPermission("backpack.reload")) player.sendMessage(Component.text("Non puoi usare questo comando"));
        BackPackPlugin.getBackPackConfig().load();
    }

    private boolean others(Player player, String name) {
        if (!player.hasPermission("backpack.others")) {
            player.sendMessage(Component.text("Non puoi usare questo comando"));
            return true;
        }
        Player other = Bukkit.getPlayer(name);
        if (other == null) return false;
        UUID uuid = other.getUniqueId();
        BackPack backpack = manager.get(uuid);
        if (backpack == null) return false;
        manager.get(uuid).getGui().open(player, player.hasPermission("backpack.modify"));
        return true;
    }
}
