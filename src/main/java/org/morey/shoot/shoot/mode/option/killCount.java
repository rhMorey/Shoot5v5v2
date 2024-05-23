package org.morey.shoot.shoot.mode.option;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.morey.shoot.shoot.Shoot;
import org.morey.shoot.shoot.mainEvent;

import java.util.HashMap;
import java.util.Objects;

public class killCount implements Listener {

    public static HashMap<Player, Integer> killCount = new HashMap<>();

    public static void manageKillCount(PlayerDeathEvent event, Player player, Player killer)
    {
        if(killCount.get(killer) == 0)
        {
            killCount.put(killer,  1);
            event.setDeathMessage(mainEvent.getColor(player) + player.getName() + " §7a été tué par " + mainEvent.getColor(killer) + killer.getName());
            sendDeathMessage(event);
            //Shoot.log.info("test1" + killCount.toString());
        }
        else if(killCount.get(killer) > 0)
        {
            //Shoot.log.info("test2" + killCount.toString());
            if(killCount.get(killer) >= 1)
            {
                //Shoot.log.info("test3" + killCount.toString());
                killCount.put(killer,  killCount.get(killer) + 1);
                //Shoot.log.info("test4" + killCount.toString());
                switch (killCount.get(killer))
                {
                    case 2:
                        event.setDeathMessage(mainEvent.getColor(player) + player.getName() + " §7a été tué par " + mainEvent.getColor(killer) + killer.getName() + " §e§lDOUBLE KILL!");
                        sendDeathMessage(event);
                        break;
                    case 3:
                        event.setDeathMessage(mainEvent.getColor(player) + player.getName() + " §7a été tué par " + mainEvent.getColor(killer) + killer.getName() + " §e§lTRIPLE KILL!");
                        sendDeathMessage(event);
                        break;
                    case 4:
                        event.setDeathMessage(mainEvent.getColor(player) + player.getName() + " §7a été tué par " + mainEvent.getColor(killer) + killer.getName() + " §6§lQUADRA KILL!");
                        sendDeathMessage(event);
                        break;
                    case 5:
                        event.setDeathMessage(mainEvent.getColor(player) + player.getName() + " §7a été tué par " + mainEvent.getColor(killer) + killer.getName() + " §4§lPENTA KILL!");
                        sendDeathMessage(event);
                        break;
                    default:
                        event.setDeathMessage(mainEvent.getColor(player) + player.getName() + " §7a été tué par " + mainEvent.getColor(killer) + killer.getName());
                        sendDeathMessage(event);
                        break;
                }
            }
        }
    }

    public static void sendDeathMessage(PlayerDeathEvent event)
    {
        if (event.getDeathMessage() != null) Bukkit.broadcastMessage(Objects.requireNonNull(event.getDeathMessage()));
    }
}
