package org.morey.shoot.shoot.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.morey.shoot.shoot.Campsite;

public class CmdsSpawnAll implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(s.equalsIgnoreCase("spawnall"))
        {
            sender.sendMessage(Campsite.prefix + Campsite.getCampsiteColor + "Tous les joueurs ont étés téléportés au spawn.");
            for (Player ap : Bukkit.getOnlinePlayers())
            {
                Location loc = new Location(Bukkit.getWorld("world"), 4977.5, 148, 3761.5);
                ap.teleport(loc);
            }
        }

        return false;
    }
}
