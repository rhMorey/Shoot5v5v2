package org.morey.shoot.shoot.mode.option;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;

public class placeChest {

    public static Location chest1 = new Location(Bukkit.getWorld("world"), 4943, 126, 3889);
    public static Location chest2 = new Location(Bukkit.getWorld("world"), 4921, 118, 3833);

    public static void place()
    {
        if(chest1.getBlock().getType().equals(Material.AIR))
        {
            Block block = chest1.getBlock();
            block.setType(Material.CHEST);
        }
        if(chest2.getBlock().getType().equals(Material.AIR))
        {
            Block block = chest2.getBlock();
            block.setType(Material.CHEST);
        }
    }

}
