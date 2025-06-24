package org.morey.shoot.shoot.item.utility;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.morey.shoot.shoot.Campsite;
import org.morey.shoot.shoot.item.ItemManager;

public class SlowBall implements Listener {


    public static ItemStack slowBall = new ItemStack(ItemManager.Item(Material.SNOWBALL, Campsite.getCampsiteColor + "Slowball", "§fInflige à l'impact Slowness II pendant 2.5 secondes dans les 3 blocs autours."));
    private int duration = 60;

    @EventHandler
    public void onSlowBall(ProjectileHitEvent event)
    {
        Entity entity = event.getEntity();
        World w = entity.getWorld();
        if (entity instanceof Snowball) {
            w.createExplosion(entity.getLocation(), 1, false, false);
            for (Entity entityNearby : w.getNearbyEntities(entity.getLocation(), 3, 3, 3)) {
                if (entityNearby instanceof Player) {
                    Player playerNearby = (Player) entityNearby;
                    playerNearby.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, duration, 2));
                    playerNearby.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, duration, 250));
                    playerNearby.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, duration, 2));
                    playerNearby.sendTitle(" ", Campsite.getCampsiteColor + "RALENTI", 0, duration, 0);
                }
            }
        }
    }
}
