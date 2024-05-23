package org.morey.shoot.shoot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.morey.shoot.shoot.commands.*;
import org.morey.shoot.shoot.item.utility.slowBall;
import org.morey.shoot.shoot.mode.option.blockWarning;
import org.morey.shoot.shoot.mode.option.optionActivator.chestListener;
import org.morey.shoot.shoot.mode.option.optionActivator.plateListener;
import org.morey.shoot.shoot.mode.winCondition;
import org.morey.shoot.shoot.mode.option.friendlyFire;
import org.morey.shoot.shoot.team.team;

import java.util.logging.Logger;

public final class Shoot extends JavaPlugin {

    public static Logger log = Bukkit.getLogger();
    private static Shoot instance;
    //public static Location spawnLocation = new Location(Bukkit.getWorld("world"), 4977.5, 148, 3761.5);
    @Override
    public void onEnable()
    {
        instance = this;

        log.info("Shoot started!");
        mainEvent.initTeam();

        //Commands
        Bukkit.getPluginCommand("start").setExecutor(new startCmds());
        Bukkit.getPluginCommand("tcreate").setExecutor(new tcreateCmds());
        Bukkit.getPluginCommand("timer").setExecutor(new timerCmds());
        Bukkit.getPluginCommand("stopgame").setExecutor(new stopgameCmds());
        Bukkit.getPluginCommand("spawnall").setExecutor(new spawnallCmds());

        //Listener
        Bukkit.getPluginManager().registerEvents(new team(), this);
        Bukkit.getPluginManager().registerEvents(new mainEvent(), this);
        Bukkit.getPluginManager().registerEvents(new winCondition(), this);
        Bukkit.getPluginManager().registerEvents(new friendlyFire(), this);
        Bukkit.getPluginManager().registerEvents(new blockWarning(), this);
        Bukkit.getPluginManager().registerEvents(new slowBall(), this);
        Bukkit.getPluginManager().registerEvents(new chestListener(), this);
        Bukkit.getPluginManager().registerEvents(new plateListener(), this);

    }

    public static Shoot getInstance()
    {
        return instance;
    }

    @Override
    public void onDisable()
    {
        if(Bukkit.getScoreboardManager().getMainScoreboard().getTeam("bleu") == null && Bukkit.getScoreboardManager().getMainScoreboard().getTeam("rouge") == null)
        {
            //Il doit y avoir un truc pour pouvoir delete et recréer la team sinon, si le jeu start avec des joueurs qui sont dans la team mais déconnecter, ça fait de la merde
            mainEvent.stopgame();
        }
        log.info("Shoot stopped!");

    }
}
