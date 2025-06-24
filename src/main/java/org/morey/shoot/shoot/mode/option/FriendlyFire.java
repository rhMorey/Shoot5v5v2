package org.morey.shoot.shoot.mode.option;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.morey.shoot.shoot.Campsite;
import org.morey.shoot.shoot.team.ReworkTeam;

public class FriendlyFire implements Listener {

    @EventHandler
    public void friendlyFireMelee(EntityDamageByEntityEvent event)
    {
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            if(ReworkTeam.getTeamPlayer(player).equals("bleu") && ReworkTeam.getTeamPlayer(damager).equals("bleu"))
            {
                event.setCancelled(true);
                Campsite.log.info(damager.getName() + " a tenté d'attaquer " + player.getName());
                return;
            }
            if(ReworkTeam.getTeamPlayer(player).equals("rouge") && ReworkTeam.getTeamPlayer(damager).equals("rouge"))
            {
                event.setCancelled(true);
                Campsite.log.info(damager.getName() + " a tenté d'attaquer " + player.getName());
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
            if (ReworkTeam.getTeamPlayer(projectileSource).equals("bleu") && ReworkTeam.getTeamPlayer(playerHitted).equals("bleu"))
            {
                projectile.setCancelled(true);
            }
            if (ReworkTeam.getTeamPlayer(projectileSource).equals("rouge") && ReworkTeam.getTeamPlayer(playerHitted).equals("rouge"))
            {
                projectile.setCancelled(true);
            }
        }
    }
}
