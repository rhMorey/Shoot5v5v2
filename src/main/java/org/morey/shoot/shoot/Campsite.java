package org.morey.shoot.shoot;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.morey.shoot.shoot.commands.*;
import org.morey.shoot.shoot.item.utility.SlowBall;
import org.morey.shoot.shoot.mode.StartGame;
import org.morey.shoot.shoot.mode.option.BlockWarning;
import org.morey.shoot.shoot.mode.option.KillCount;
import org.morey.shoot.shoot.mode.option.optionActivator.ChestListener;
import org.morey.shoot.shoot.mode.option.optionActivator.PlateListener;
import org.morey.shoot.shoot.mode.WinCondition;
import org.morey.shoot.shoot.mode.option.FriendlyFire;
import org.morey.shoot.shoot.utils.EnhanceServer;

import java.awt.*;
import java.util.logging.Logger;

public final class Campsite extends JavaPlugin {

    public static Logger log = Bukkit.getLogger();

    private static Campsite instance;
    public static Campsite plugin;

    public static ChatColor getCampsiteColor = ChatColor.of(new Color(230, 166, 255));
    public static String prefix = ChatColor.of(new Color(230, 166, 255)) + "§lCS §7» §r";
    public static String error = ChatColor.of(new Color(230, 166, 255)) + "§lCS §7» " + ChatColor.of(new Color(230, 166, 255));
    //public static Location spawnLocation = new Location(Bukkit.getWorld("world"), 4977.5, 148, 3761.5);
    @Override
    public void onEnable()
    {
        instance = this;
        plugin = this;
        saveDefaultConfig();

        log.info(getCampsiteColor + "Campsite started!");

        //Commands
        Bukkit.getPluginCommand("start").setExecutor(new CmdsStart());
        Bukkit.getPluginCommand("tcreate").setExecutor(new CmdsTcreate());
        Bukkit.getPluginCommand("timer").setExecutor(new CmdsTimer());
        Bukkit.getPluginCommand("stopgame").setExecutor(new CmdsStopGame());
        Bukkit.getPluginCommand("spawnall").setExecutor(new CmdsSpawnAll());
        Bukkit.getPluginCommand("csteam").setExecutor(new CmdsTeam());
        Bukkit.getPluginCommand("createteam").setExecutor(new CmdsDebugTeam());

        //Listener
        Bukkit.getPluginManager().registerEvents(new EnhanceServer(), this);
        Bukkit.getPluginManager().registerEvents(new WinCondition(), this);
        Bukkit.getPluginManager().registerEvents(new FriendlyFire(), this);
        Bukkit.getPluginManager().registerEvents(new BlockWarning(), this);
        Bukkit.getPluginManager().registerEvents(new SlowBall(), this);
        Bukkit.getPluginManager().registerEvents(new ChestListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlateListener(), this);
        Bukkit.getPluginManager().registerEvents(new KillCount(), this);

    }

    public static Campsite getInstance()
    {
        return instance;
    }

    public static Campsite getPlugin() {
        return plugin;
    }

    @Override
    public void onDisable()
    {
        if(Bukkit.getScoreboardManager().getMainScoreboard().getTeam("bleu") == null && Bukkit.getScoreboardManager().getMainScoreboard().getTeam("rouge") == null)
        {
            //Il doit y avoir un truc pour pouvoir delete et recréer la team sinon, si le jeu start avec des joueurs qui sont dans la team mais déconnecter, ça fait de la merde
            StartGame.stopGame();
        }
        log.info(getCampsiteColor + "Campsite stopped!");

    }
}
