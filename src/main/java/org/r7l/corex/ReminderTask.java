package org.r7l.corex;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class ReminderTask extends BukkitRunnable {

    private final CoreX plugin;
    private final List<String> messages;
    private final String title;
    private final String subtitle;
    private final int fadeIn;
    private final int stay;
    private final int fadeOut;
    private final String soundName;
    private final float volume;
    private final float pitch;
    private final Random random = new Random();

    public ReminderTask(CoreX plugin) {
        this.plugin = plugin;
        this.messages = plugin.getConfig().getStringList("reminders.messages");
        this.title = plugin.getConfig().getString("reminders.title.title", "");
        this.subtitle = plugin.getConfig().getString("reminders.title.subtitle", "");
        this.fadeIn = plugin.getConfig().getInt("reminders.title.fadeIn", 10);
        this.stay = plugin.getConfig().getInt("reminders.title.stay", 70);
        this.fadeOut = plugin.getConfig().getInt("reminders.title.fadeOut", 20);
        this.soundName = plugin.getConfig().getString("reminders.sound.name", "BLOCK_NOTE_BLOCK_PLING");
        this.volume = (float) plugin.getConfig().getDouble("reminders.sound.volume", 1.0);
        this.pitch = (float) plugin.getConfig().getDouble("reminders.sound.pitch", 1.0);
    }

    @Override
    public void run() {
        if (messages.isEmpty()) {
            return;
        }

        String message = messages.get(random.nextInt(messages.size()));
        message = ChatColor.translateAlternateColorCodes('&', message);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);

            if (plugin.getConfig().getBoolean("reminders.title.enabled", true)) {
                String finalTitle = ChatColor.translateAlternateColorCodes('&', title.replace("{player}", player.getName()));
                String finalSubtitle = ChatColor.translateAlternateColorCodes('&', subtitle.replace("{player}", player.getName()));
                player.sendTitle(finalTitle, finalSubtitle, fadeIn, stay, fadeOut);
            }

            if (plugin.getConfig().getBoolean("reminders.sound.enabled", true)) {
                try {
                    Sound sound = Sound.valueOf(soundName);
                    player.playSound(player.getLocation(), sound, volume, pitch);
                } catch (IllegalArgumentException e) {
                    plugin.getLogger().warning("Invalid sound name in config for reminders: " + soundName);
                }
            }
        }
    }
}
