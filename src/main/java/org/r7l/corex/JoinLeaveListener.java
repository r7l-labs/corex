package org.r7l.corex;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class JoinLeaveListener implements Listener {

    private final CoreX plugin;

    public JoinLeaveListener(CoreX plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        
        // Handle join message
        if (plugin.getConfig().getBoolean("messages.join.enabled", true)) {
            String joinMessage = plugin.getConfig().getString("messages.join.format", "&a✓ &7{player} has joined the game!");
            joinMessage = ChatColor.translateAlternateColorCodes('&', joinMessage);
            joinMessage = joinMessage.replace("{player}", playerName);
            event.setJoinMessage(joinMessage);
        } else {
            event.setJoinMessage(null);
        }
        
        // Handle title
        if (plugin.getConfig().getBoolean("title.enabled", true)) {
            String title = plugin.getConfig().getString("title.title", "&b&lWelcome!");
            String subtitle = plugin.getConfig().getString("title.subtitle", "&7Hello, &e{player}&7!");
            int fadeIn = plugin.getConfig().getInt("title.fadeIn", 10);
            int stay = plugin.getConfig().getInt("title.stay", 70);
            int fadeOut = plugin.getConfig().getInt("title.fadeOut", 20);
            
            title = ChatColor.translateAlternateColorCodes('&', title);
            subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
            subtitle = subtitle.replace("{player}", playerName);
            
            player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
        }
        
        // Handle sound
        if (plugin.getConfig().getBoolean("sounds.join.enabled", false)) {
            try {
                String soundName = plugin.getConfig().getString("sounds.join.sound", "ENTITY_PLAYER_LEVELUP");
                float volume = (float) plugin.getConfig().getDouble("sounds.join.volume", 1.0);
                float pitch = (float) plugin.getConfig().getDouble("sounds.join.pitch", 1.0);
                
                Sound sound = Sound.valueOf(soundName);
                player.playSound(player.getLocation(), sound, volume, pitch);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid sound name in config: " + plugin.getConfig().getString("sounds.join.sound"));
            }
        }
        
        // Handle fireworks
        if (plugin.getConfig().getBoolean("fireworks.enabled", false)) {
            List<String> enabledWorlds = plugin.getConfig().getStringList("fireworks.enabledWorlds");
            String currentWorld = player.getWorld().getName();
            
            if (enabledWorlds.contains(currentWorld)) {
                int delay = plugin.getConfig().getInt("fireworks.delay", 5);
                
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        spawnFireworks(player);
                    }
                }.runTaskLater(plugin, delay);
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        String playerName = event.getPlayer().getName();
        
        // Handle leave message
        if (plugin.getConfig().getBoolean("messages.leave.enabled", true)) {
            String leaveMessage = plugin.getConfig().getString("messages.leave.format", "&c✗ &7{player} has left the game!");
            leaveMessage = ChatColor.translateAlternateColorCodes('&', leaveMessage);
            leaveMessage = leaveMessage.replace("{player}", playerName);
            event.setQuitMessage(leaveMessage);
        } else {
            event.setQuitMessage(null);
        }
        
        // Handle sound
        if (plugin.getConfig().getBoolean("sounds.leave.enabled", false)) {
            try {
                String soundName = plugin.getConfig().getString("sounds.leave.sound", "BLOCK_NOTE_BLOCK_PLING");
                float volume = (float) plugin.getConfig().getDouble("sounds.leave.volume", 0.5);
                float pitch = (float) plugin.getConfig().getDouble("sounds.leave.pitch", 0.5);
                
                Sound sound = Sound.valueOf(soundName);
                // Play sound to all players except the one leaving
                plugin.getServer().getOnlinePlayers().forEach(p -> {
                    if (!p.equals(event.getPlayer())) {
                        p.playSound(p.getLocation(), sound, volume, pitch);
                    }
                });
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid sound name in config: " + plugin.getConfig().getString("sounds.leave.sound"));
            }
        }
    }
    
    private void spawnFireworks(Player player) {
        Location location = player.getLocation();
        int amount = plugin.getConfig().getInt("fireworks.amount", 1);
        int power = plugin.getConfig().getInt("fireworks.power", 1);
        
        for (int i = 0; i < amount; i++) {
            Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
            FireworkMeta meta = firework.getFireworkMeta();
            
            // Set power
            meta.setPower(power);
            
            // Add effects from config
            List<FireworkEffect> effects = new ArrayList<>();
            
            var effectsList = plugin.getConfig().getConfigurationSection("fireworks.effects");
            if (effectsList != null) {
                for (String key : effectsList.getKeys(false)) {
                    String path = "fireworks.effects." + key + ".";
                    
                    try {
                        // Get effect type
                        String typeName = plugin.getConfig().getString(path + "type", "BALL");
                        FireworkEffect.Type type = FireworkEffect.Type.valueOf(typeName);
                        
                        // Get colors
                        List<Color> colors = new ArrayList<>();
                        List<String> colorStrings = plugin.getConfig().getStringList(path + "colors");
                        for (String colorStr : colorStrings) {
                            String[] rgb = colorStr.split(",");
                            if (rgb.length == 3) {
                                int r = Integer.parseInt(rgb[0].trim());
                                int g = Integer.parseInt(rgb[1].trim());
                                int b = Integer.parseInt(rgb[2].trim());
                                colors.add(Color.fromRGB(r, g, b));
                            }
                        }
                        
                        // Get fade colors
                        List<Color> fadeColors = new ArrayList<>();
                        List<String> fadeColorStrings = plugin.getConfig().getStringList(path + "fadeColors");
                        for (String colorStr : fadeColorStrings) {
                            String[] rgb = colorStr.split(",");
                            if (rgb.length == 3) {
                                int r = Integer.parseInt(rgb[0].trim());
                                int g = Integer.parseInt(rgb[1].trim());
                                int b = Integer.parseInt(rgb[2].trim());
                                fadeColors.add(Color.fromRGB(r, g, b));
                            }
                        }
                        
                        boolean flicker = plugin.getConfig().getBoolean(path + "flicker", false);
                        boolean trail = plugin.getConfig().getBoolean(path + "trail", false);
                        
                        // Build effect
                        FireworkEffect.Builder builder = FireworkEffect.builder()
                                .with(type)
                                .withColor(colors);
                        
                        if (flicker) {
                            builder.withFlicker();
                        }
                        if (trail) {
                            builder.withTrail();
                        }
                        
                        if (!fadeColors.isEmpty()) {
                            builder.withFade(fadeColors);
                        }
                        
                        effects.add(builder.build());
                        
                    } catch (Exception e) {
                        plugin.getLogger().warning("Error parsing firework effect: " + e.getMessage());
                    }
                }
            }
            
            // Add all effects to firework
            effects.forEach(meta::addEffect);
            
            firework.setFireworkMeta(meta);
        }
    }
}
