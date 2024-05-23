package org.morey.shoot.shoot.mode.option.optionActivator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.morey.shoot.shoot.Shoot;

public class plateListener implements Listener
{

    @EventHandler
    public void pressurePlateBonus(PlayerInteractEvent event)
    {
        if(event.getAction() == Action.PHYSICAL)
        {
            Player player = event.getPlayer();
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 1));
        }
    }

}
