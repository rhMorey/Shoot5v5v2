package org.morey.shoot.shoot.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.morey.shoot.shoot.Campsite;
import org.morey.shoot.shoot.mode.Start;
import org.morey.shoot.shoot.team.ReworkTeam;
import org.morey.shoot.shoot.utils.EnhanceServer;
import org.morey.shoot.shoot.mode.option.DiamondReplaceAtStart;
import org.morey.shoot.shoot.mode.option.PlaceChest;
import org.morey.shoot.shoot.mode.option.PlacePressurePlate;
import org.morey.shoot.shoot.mode.Timer;
import org.morey.shoot.shoot.mode.WinCondition;
import org.morey.shoot.shoot.team.inventory.InvManager;

import java.util.*;

import static org.morey.shoot.shoot.mode.Timer.startTimer;


public class CmdsStart implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command c, @NotNull String s, @NotNull String[] strings)
    {

        Player player = (Player) sender;
        if(s.equalsIgnoreCase("start")) {
            Start.isStarted = true;
            Start.startGame();
        }
        return false;
    }
}
