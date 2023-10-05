package me.spectralmage12.collectathon.listener;

import me.spectralmage12.collectathon.Collectathon;
import me.spectralmage12.collectathon.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class LobbyListener extends GameListener {
    private final int maxCountdown = 20;
    private int countdown = maxCountdown;
    private int taskID = -1;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(GameManager.doesNotHavePlayer(event.getPlayer().getName()))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(GameManager.doesNotHavePlayer(event.getPlayer().getName()))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player player))
            return;

        if(GameManager.doesNotHavePlayer(player.getName()))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player player))
            return;

        if(GameManager.doesNotHavePlayer(player.getName()))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(GameManager.doesNotHavePlayer(event.getPlayer().getName()))
            return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if(GameManager.doesNotHavePlayer(event.getPlayer().getName()))
            return;

        if(GameManager.getCollector().equals(event.getPlayer().getName()))
            return;

        event.setCancelled(true);
    }

    @Override
    public void onSelect(Collectathon main) {
        taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(main, () -> {
            if(countdown <= 0) {
                GameManager.start(main);

                countdown = maxCountdown;
                Bukkit.getServer().getScheduler().cancelTask(taskID);
            }

            else {
                GameManager.broadcast(ChatColor.GOLD + "Starting in " + ChatColor.WHITE + countdown + ChatColor.GOLD + " seconds!");
                countdown -= 1;
            }
        }, 0L, 20L);
    }
}
