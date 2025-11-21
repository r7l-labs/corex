package org.r7l.corex;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        
        // Create custom join message with checkmark and grey text
        // ✓ symbol (checkmark) + grey text
        String joinMessage = ChatColor.GREEN + "✓ " + ChatColor.GRAY + playerName + " has joined the game!";
        
        event.setJoinMessage(joinMessage);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        String playerName = event.getPlayer().getName();
        
        // Create custom leave message with cross and grey text
        // ✗ symbol (cross) + grey text
        String leaveMessage = ChatColor.RED + "✗ " + ChatColor.GRAY + playerName + " has left the game!";
        
        event.setQuitMessage(leaveMessage);
    }
}
