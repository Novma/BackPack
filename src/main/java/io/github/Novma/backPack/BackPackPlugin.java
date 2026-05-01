package io.github.Novma.backPack;

import io.github.Novma.backPack.backpack.BackPackConfig;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class BackPackPlugin extends JavaPlugin {

//    private final Database database;
    private static Plugin instance;
    private static BackPackConfig backPackConfig;

    @Override
    public void onEnable() {
        instance = this;

        saveFiles();
        setupBackPackConfig();
//      setupDatabase();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void saveFiles() {
        saveDefaultConfig();
        saveResource("backpack.yml", false);
    }

    private void setupBackPackConfig() {
        backPackConfig = new BackPackConfig();
    }

//    private void setupDatabase() {
//        String dbType = this.globalConfig.getString("database.type").toLowerCase();
//        if ("mysql".equals(dbType)) {
//            String host = this.globalConfig.getString("database.mysql.host");
//            int port = this.globalConfig.getInt("database.mysql.port");
//            String dbName = this.globalConfig.getString("database.mysql.database");
//            String username = this.globalConfig.getString("database.mysql.username");
//            String password = this.globalConfig.getString("database.mysql.password");
//            this.database = (Database)new MySQLDatabase(this, host, port, dbName, username, password);
//        } else {
//            String sqliteFile = this.globalConfig.getString("database.sqlite-file");
//            if (sqliteFile == null || sqliteFile.isEmpty())
//                sqliteFile = "monkeycore.db";
//            this.database = (Database)new SQLiteDatabase(this, sqliteFile);
//        }
//        this.database.connect();
//    }

    public static Plugin getInstance() {return instance;}
}
