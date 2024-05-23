package org.morey.shoot.shoot.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.morey.shoot.shoot.mode.timer;

public class timerCmds implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command c, @NotNull String s, @NotNull String[] strings) {

        if(s.equalsIgnoreCase("timer"))
        {
            sender.sendMessage("§7Le timer est à : §7[§f" + timer.secondsPassed + "/§6" + timer.gameTime + "§7]");
        }
        return false;
    }
}
