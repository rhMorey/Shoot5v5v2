package org.morey.shoot.shoot.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.morey.shoot.shoot.mode.StartGame;


public class CmdsStart implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command c, @NotNull String s, @NotNull String[] args)
    {

        Player player = (Player) sender;
        if(s.equalsIgnoreCase("start")) {

            if(args.length <= 0) {
                StartGame.isStarted = true;
                StartGame.startGame();
                return true;
            }
            /*else
            {
                if(args.length == 1)
                {
                    if(args[0].equalsIgnoreCase())
                    {

                    }
                }
            }*/
        }
        return false;
    }
}
