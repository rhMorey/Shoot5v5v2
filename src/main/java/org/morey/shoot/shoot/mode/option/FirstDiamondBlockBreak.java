package org.morey.shoot.shoot.mode.option;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.morey.shoot.shoot.Campsite;
import org.morey.shoot.shoot.mode.WinCondition;
import org.morey.shoot.shoot.team.ReworkTeam;

import static org.morey.shoot.shoot.Campsite.getCampsiteColor;

public class FirstDiamondBlockBreak {

    public static void breakFirstDiamondBlock()
    {
        Bukkit.broadcastMessage(Campsite.prefix + "L'équipe Rouge a remporté un point en détruisant un bloc de diamant, ils gagnent " + getCampsiteColor + "Force 1 pendant 18 secondes§r!");
        for (Player ap : Bukkit.getOnlinePlayers())
        {
            ap.playSound(ap, Sound.ENTITY_WITHER_SPAWN, 1, 0);
            if(ReworkTeam.getTeamPlayer(ap).equals("rouge"))
            {
                ap.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, WinCondition.strengthTimer * 20, 0));
                ap.sendMessage(Campsite.prefix + "Cassez le deuxième bloc de diamant afin de " + getCampsiteColor +"remporter la partie§r.");
            }
            if(ReworkTeam.getTeamPlayer(ap).equals("bleu"))
            {
                ap.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, WinCondition.strengthTimer * 20, 0));
            }
        }
    }

}
