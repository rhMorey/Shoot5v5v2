package org.morey.shoot.shoot.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.morey.shoot.shoot.Shoot;
import org.morey.shoot.shoot.mainEvent;
import org.morey.shoot.shoot.mode.timer;
import org.morey.shoot.shoot.mode.winCondition;

public class stopgameCmds implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(s.equalsIgnoreCase("stopgame"))
        {
            Bukkit.broadcastMessage("§cLa partie a été arrêté par un administrateur.");
            mainEvent.stopgame();
        }
        return false;
    }
}
