package org.r7l.corex;

import org.bukkit.plugin.java.JavaPlugin;

public class CoreX extends JavaPlugin {

    private static CoreX instance;

    @Override
    public void onEnable() {
        instance = this;
        
        // Save default config if it doesn't exist
        saveDefaultConfig();
        
        // Register event listeners
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(this), this);
        
        // Log plugin enabled
        getLogger().info("CoreX has been enabled!");
        getLogger().info("Configuration loaded successfully.");
        getLogger().info("Features enabled:");
        getLogger().info("  - Join/Leave Messages: " + getConfig().getBoolean("messages.join.enabled"));
        getLogger().info("  - Titles: " + getConfig().getBoolean("title.enabled"));
        getLogger().info("  - Fireworks: " + getConfig().getBoolean("fireworks.enabled"));
        getLogger().info("  - Sounds: " + getConfig().getBoolean("sounds.join.enabled"));
    }

    @Override
    public void onDisable() {
        getLogger().info("CoreX has been disabled!");
    }
    
    public static CoreX getInstance() {
        return instance;
    }
}
