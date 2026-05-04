package io.github.Novma.backPack;

import io.github.Novma.backPack.backpack.BackPackConfig;
import io.github.Novma.backPack.backpack.BackPackManager;
import io.github.Novma.backPack.command.BackPackCommand;
import io.github.Novma.backPack.listener.CropsListener;
import io.github.Novma.backPack.listener.GUIListener;
import io.github.Novma.backPack.listener.PlayerListener;
import io.github.Novma.backPack.util.YamlManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class BackPackPlugin extends JavaPlugin {

    private static Plugin instance;
    private static BackPackConfig backPackConfig;
    private static BackPackManager backPackManager;
    private static boolean debug;

    @Override
    public void onEnable() {
        instance = this;
        YamlManager.setup(this);
        backPackConfig = new BackPackConfig();
        backPackManager = new BackPackManager();
        debug = getConfig().getBoolean("debug");
        if (debug) {getLogger().info("[DEBUG] Debug mode enabled");}

        saveFiles();
        setupBackPackConfig();
        setupCommands();
        setupListeners();
    }

    @Override
    public void onDisable() {
        backPackManager.removeAll();
    }

    private void saveFiles() {
        saveDefaultConfig();
        saveResource("backpack.yml", false);
    }

    private void setupCommands() {
        getCommand("backpack").setExecutor(new BackPackCommand(backPackManager));
    }

    private void setupListeners() {
        getServer().getPluginManager().registerEvents(new GUIListener(backPackManager), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(backPackManager), this);
        getServer().getPluginManager().registerEvents(new CropsListener(backPackManager), this);
    }

    private void setupBackPackConfig() {
        backPackConfig = new BackPackConfig();
    }

    public static Plugin getInstance() {return instance;}

    public static boolean getDebug() {return debug;}

    public static BackPackConfig getBackPackConfig() {return backPackConfig;}
}
