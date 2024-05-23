package org.morey.shoot.shoot.item.utility;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.morey.shoot.shoot.item.mainItem;

import java.util.Random;

public class slowBall implements Listener {


    public static ItemStack slowBall = new ItemStack(mainItem.Item(Material.SNOWBALL, "§eSlowball", "§7Inflige à l'impact Slowness II pendant 2.5 secondes dans les 3 blocs autours."));

    @EventHandler
    public void onSlowBall(ProjectileHitEvent event)
    {
        Entity entity = event.getEntity();
        World w = entity.getWorld();
        if (entity instanceof Snowball) {
            w.createExplosion(entity.getLocation(), 1, false, false);
            for (Entity e : w.getNearbyEntities(entity.getLocation(), 3, 3, 3)) {
                if (e instanceof Player) {
                    Player ap = (Player) e;
                    ap.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 2));
                    ap.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 50, 250));
                    ap.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 50, 2));
                    ap.sendTitle(" ", "§7RALENTI", 10, 70, 20);
                }
            }
        }
    }
}
