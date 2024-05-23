package org.morey.shoot.shoot.mode.option;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.morey.shoot.shoot.Shoot;
import org.morey.shoot.shoot.team.team;

public class friendlyFire implements Listener {

    @EventHandler
    public void friendlyFireMelee(EntityDamageByEntityEvent event)
    {
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            if(team.blue.getEntries().contains(player.getName()) && team.blue.getEntries().contains(damager.getName()))
            {
                event.setCancelled(true);
                Shoot.log.info(damager.getName() + " a tenté d'attaquer " + player.getName());
                return;
            }
            if(team.red.getEntries().contains(player.getName()) && team.red.getEntries().contains(damager.getName()))
            {
                event.setCancelled(true);
                Shoot.log.info(damager.getName() + " a tenté d'attaquer " + player.getName());
            }
        }
    }

    @EventHandler
    public void friendlyFireDistance(ProjectileHitEvent projectile)
    {
        if(projectile.getEntity().getShooter() instanceof Player && projectile.getHitEntity() instanceof Player)
        {
            Player projectileSource = (Player) projectile.getEntity().getShooter();
            Player playerHitted = (Player) projectile.getHitEntity();
            if (team.blue.getEntries().contains(projectileSource.getName()) && team.blue.getEntries().contains(playerHitted.getName()))
            {
                projectile.setCancelled(true);
            }
            if (team.red.getEntries().contains(projectileSource.getName()) && team.red.getEntries().contains(playerHitted.getName()))
            {
                projectile.setCancelled(true);
            }
        }
    }
}
