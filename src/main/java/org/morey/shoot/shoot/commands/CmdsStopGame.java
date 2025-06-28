package org.morey.shoot.shoot.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.morey.shoot.shoot.Campsite;
import org.morey.shoot.shoot.mode.StartGame;

public class CmdsStopGame implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(s.equalsIgnoreCase("stopgame"))
        {
            if(StartGame.isStarted) {
                StartGame.isStarted = false;
                StartGame.stopGame();
                Campsite.log.info("La partie a été stoppé de force.");
                sender.sendMessage(Campsite.prefix + "Vous avez arrêté la partie.");
                Bukkit.broadcastMessage(Campsite.prefix + Campsite.getCampsiteColor + "§lLa partie a été arrêté par un administrateur.");
            }
            else
            {
                sender.sendMessage(Campsite.prefix + Campsite.getCampsiteColor + "La partie n'a pas commencée.");
            }
        }
        return false;
    }
}
