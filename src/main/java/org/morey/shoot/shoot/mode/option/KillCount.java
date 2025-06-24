package org.morey.shoot.shoot.mode.option;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.morey.shoot.shoot.Campsite;
import org.morey.shoot.shoot.utils.EnhanceServer;

import java.util.HashMap;
import java.util.Objects;

public class KillCount implements Listener {

    public static HashMap<Player, Integer> killCount = new HashMap<>();

    public static void initialize(Player victim, Player killer) {
        World world = EnhanceServer.world;
        String defaultKillMessage = Campsite.prefix + EnhanceServer.getColor(victim) + victim.getName() + " §7a été tué par " + EnhanceServer.getColor(killer) + killer.getName();
        world.playSound(killer, Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);

        // Provoque une erreur si pas présente, le hashmap n'est pas initialisé s'il n'y a pas de kill ou si la game n'est pas start.
        if (killCount.get(killer) == null)
        {
            Campsite.log.info(victim + " est tué par " + killer + " || La partie n'a pas commencé, le kill n'a pas été comptabilisé.");
            return;
        }

        // Initialiser le hashmap, le joueur est à 0 par défaut donc on lui rajoute +1 parce qu'il a fait un kill. Condition utilisable uniquement pour le premier kill du joueur.
        if (killCount.get(killer) == 0)
        {
            killCount.put(killer, 1);
            Campsite.log.info(victim.getName() + " est tué par " + killer.getName() + " || defaultkill");
            Bukkit.broadcastMessage(defaultKillMessage);
        }
        else if (killCount.get(killer) > 0)
        {
            if (killCount.get(killer) >= 1)
            {
                killCount.put(killer, killCount.get(killer) + 1);

                //Compteur de kill jusqu'à pentakill.
                switch (killCount.get(killer))
                {
                    case 2:
                        Campsite.log.info(victim + " est tué par " + killer + " || DOUBLEKILL");
                        Bukkit.broadcastMessage(defaultKillMessage + Campsite.getCampsiteColor + " §lDOUBLE KILL!");
                        break;
                    case 3:
                        Campsite.log.info(victim + " est tué par " + killer + " || TRIPLEKILL");
                        Bukkit.broadcastMessage(defaultKillMessage + Campsite.getCampsiteColor + " §lTRIPLE KILL!");
                        break;
                    case 4:
                        Campsite.log.info(victim + " est tué par " + killer + " || QUADRAKILL");
                        Bukkit.broadcastMessage(defaultKillMessage + Campsite.getCampsiteColor + " §lQUADRA KILL!");
                        break;
                    case 5:
                        Campsite.log.info(victim + " est tué par " + killer + " || PENTAKILL");
                        Bukkit.broadcastMessage(defaultKillMessage + Campsite.getCampsiteColor + " §lPENTA KILL!");
                        break;
                    default:
                        Campsite.log.info(victim + " est tué par " + killer + " || defaultkill");
                        Bukkit.broadcastMessage(defaultKillMessage);
                        break;
                }
            }
        }
    }
}

