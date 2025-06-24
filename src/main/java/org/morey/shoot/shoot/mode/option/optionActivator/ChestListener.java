package org.morey.shoot.shoot.mode.option.optionActivator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.morey.shoot.shoot.Campsite;
import org.morey.shoot.shoot.item.utility.SlowBall;

import java.util.ArrayList;

public class ChestListener implements Listener {

    @EventHandler
    public void giftOnChest(PlayerInteractEvent event)
    {
        if(event.getClickedBlock() == null) return;
        ArrayList<Location> chestLocation = new ArrayList<>();
        chestLocation.add(new Location(Bukkit.getWorld("world"), 4943, 126, 3889));
        chestLocation.add(new Location(Bukkit.getWorld("world"), 4921, 118, 3833));

        if(event.getClickedBlock().getType().equals(Material.CHEST) && chestLocation.contains(event.getClickedBlock().getLocation()))
        {
            Player player = event.getPlayer();
            event.setCancelled(true);
            player.getInventory().addItem(SlowBall.slowBall);
            player.sendMessage("§rVous avez reçu " + Campsite.getCampsiteColor + SlowBall.slowBall.getItemMeta().getDisplayName());
            player.getWorld().playSound(player, Sound.BLOCK_CHEST_OPEN, 1, 2);
            event.getClickedBlock().setType(Material.AIR);
        }
    }

}
