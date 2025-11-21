package org.r7l.corex;

import org.bukkit.plugin.java.JavaPlugin;

public class CoreX extends JavaPlugin {

    @Override
    public void onEnable() {
        // Register event listeners
        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
        
        // Log plugin enabled
        getLogger().info("CoreX has been enabled!");
        getLogger().info("Custom join/leave messages are now active.");
    }

    @Override
    public void onDisable() {
        getLogger().info("CoreX has been disabled!");
    }
}
