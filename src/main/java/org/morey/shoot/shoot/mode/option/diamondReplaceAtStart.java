package org.morey.shoot.shoot.mode.option;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.morey.shoot.shoot.Shoot;

public class diamondReplaceAtStart implements Listener {

    public static void replaceBedrock()
    {
        Location block1 = new Location(Bukkit.getWorld("world"), 4901, 121, 3847);
        Location block2 = new Location(Bukkit.getWorld("world"), 4946, 112, 3932);
        if(block1.getBlock().getType().equals(Material.BEDROCK) || block2.getBlock().getType().equals(Material.BEDROCK))
        {
            block1.getBlock().setType(Material.DIAMOND_BLOCK);
            block2.getBlock().setType(Material.DIAMOND_BLOCK);
            Shoot.log.info("Les blocs ont été remplacés.");
        }
    }

}
