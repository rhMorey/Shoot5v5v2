package org.morey.shoot.shoot.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.morey.shoot.shoot.Campsite;
import org.morey.shoot.shoot.mode.Timer;

import static org.morey.shoot.shoot.mode.Timer.gameTime;
import static org.morey.shoot.shoot.mode.Timer.secondsPassed;

public class CmdsTimer implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command c, @NotNull String s, @NotNull String[] strings) {

        String timerFormat = "§7[" + Campsite.getCampsiteColor + secondsPassed + "§7/§f" + gameTime + "§7]";
        if(s.equalsIgnoreCase("timer"))
        {
            sender.sendMessage(Campsite.prefix + "§fLe timer est à : " + timerFormat);
        }
        return false;
    }
}
