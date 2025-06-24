package org.morey.shoot.shoot.mode.option;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class PlacePressurePlate {

    //public static Location plate1 = new Location(Bukkit.getWorld("world"), 4893, 106, 3932);
    public static Location plate2 = new Location(Bukkit.getWorld("world"), 4946, 117, 3884);
    public static Location plate3 = new Location(Bukkit.getWorld("world"), 4922, 117, 3854);
    //public static Location plate4 = new Location(Bukkit.getWorld("world"), 4870, 117, 3858);

    public static void place()
    {
        //activePressure(plate1);
        activePressure(plate2);
        activePressure(plate3);
        //activePressure(plate4);
    }

    public static void activePressure(Location loc)
    {
        if(loc.getBlock().getType().equals(Material.AIR))
        {
            Block block = loc.getBlock();
            block.setType(Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
        }
    }
}

/*
@EventHandler
    public void onWalkPressurePlate(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        if(loc.getBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
            loc.getBlock().setType(Material.AIR);
            World w = player.getWorld();
            w.createExplosion(loc, 5);
        }
    }
 */