package org.r7l.corex;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class CoreX extends JavaPlugin {

    private static CoreX instance;
    private BukkitTask reminderTask;

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
        getLogger().info("  - Reminders: " + getConfig().getBoolean("reminders.enabled"));

        if (getConfig().getBoolean("reminders.enabled", true)) {
            long interval = getConfig().getLong("reminders.interval", 300) * 20; // Convert seconds to ticks
            reminderTask = new ReminderTask(this).runTaskTimer(this, 0, interval);
        }
        
        getCommand("corexreload").setExecutor(new ReloadCommand(this));
    }

    @Override
    public void onDisable() {
        if (reminderTask != null) {
            reminderTask.cancel();
        }
        getLogger().info("CoreX has been disabled!");
    }
    
    public static CoreX getInstance() {
        return instance;
    }

    public void reloadPlugin() {
        reloadConfig();

        if (reminderTask != null) {
            reminderTask.cancel();
        }

        if (getConfig().getBoolean("reminders.enabled", true)) {
            long interval = getConfig().getLong("reminders.interval", 300) * 20; // Convert seconds to ticks
            reminderTask = new ReminderTask(this).runTaskTimer(this, 0, interval);
        }
    }
}
