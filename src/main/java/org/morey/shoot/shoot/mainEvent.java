package org.morey.shoot.shoot;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.morey.shoot.shoot.mode.option.killCount;
import org.morey.shoot.shoot.mode.timer;
import org.morey.shoot.shoot.mode.winCondition;
import org.morey.shoot.shoot.team.team;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;

import static org.morey.shoot.shoot.team.team.blue;
import static org.morey.shoot.shoot.team.team.red;

public class mainEvent implements Listener
{
    public static final Material blueblock = Material.BIRCH_LOG;
    public static ArrayList<Material> whitelistBlock = new ArrayList<>();

    @EventHandler
    public void onConnect(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        spawnTeleport(player);
        event.setJoinMessage("§a+ §7" + player.getName());
        player.getInventory().clear();
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        event.setQuitMessage("§c- §7" + player.getName());

        blue.unregister();
        red.unregister();
        //PROTECTION
        if(timer.secondsPassed > 0 && timer.secondsPassed < timer.gameTime)
        {
            if(!player.isOp())
            {
                player.ban("Attendez avant de vous reconnecter.", Instant.ofEpochSecond(500),"test");
            }
            if(team.red.getEntries().contains(player.getName()))
            {
                team.red.removeEntry(player.getName());
                winCondition.win("blue", true);
            }
            if(team.blue.getEntries().contains(player.getName()))
            {
                team.blue.removeEntry(player.getName());
                winCondition.win("red", true);
            }
        }
        //PROTECTION
    }

    public static void spawnTeleport(Player player)
    {
        Location loc = new Location(Bukkit.getWorld("world"), 4977.5, 148, 3761.5);
        player.teleport(loc);
        player.sendMessage("§7Vous avez été redirigé vers le spawn.");
        player.setGameMode(GameMode.SURVIVAL);
    }

    public static ChatColor getColor(Player player)
    {
        if(team.blue.getEntries().contains(player.getName()))
        {
            return ChatColor.BLUE;
        }
        else if(team.red.getEntries().contains(player.getName()))
        {
            return ChatColor.RED;
        }
        else
        {
            return ChatColor.WHITE;
        }
    }

    public static void addPlayerKillCount(Player player)
    {
        killCount.killCount.put(player, 0);
    }

    public static boolean getWhitelist(Material mat)
    {
        whitelistBlock.add(Material.DIAMOND_BLOCK);
        whitelistBlock.add(blueblock);
        if(whitelistBlock.contains(mat))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static void stopgame()
    {
        timer.secondsPassed = 0;
        Bukkit.getScheduler().cancelTasks(Shoot.getInstance());
        Shoot.log.info("§7Le timer a été reset.");
        if(timer.bossBar != null)
        {
            timer.bossBar.setVisible(false);
            timer.bossBar.removeAll();
            Shoot.log.info("§7La bossbar a été reset.");
        }
        winCondition.score = 0;
        winCondition.reddead = 0;
        winCondition.bluedead = 0;

        if(Bukkit.getScoreboardManager().getMainScoreboard().getTeam("bleu") == null && Bukkit.getScoreboardManager().getMainScoreboard().getTeam("rouge") == null)
        {
            //Il doit y avoir un truc pour pouvoir delete et recréer la team sinon, si le jeu start avec des joueurs qui sont dans la team mais déconnecter, ça fait de la merde
            team.blue.unregister();
            team.red.unregister();
        }
        for (Player player : Bukkit.getOnlinePlayers())
        {
            spawnTeleport(player);
            player.getInventory().clear();
        }
        Shoot.log.info("§7Le score a été reset.");
    }

    public static void initTeam()
    {
        red = Objects.requireNonNull(Bukkit.getServer().getScoreboardManager()).getMainScoreboard().getTeam("rouge");
        blue = Bukkit.getServer().getScoreboardManager().getMainScoreboard().getTeam("bleu");
        if(blue == null) {
            blue = Objects.requireNonNull(Bukkit.getServer().getScoreboardManager()).getMainScoreboard().registerNewTeam("bleu");
            //sender.sendMessage("§7L'équipe bleu n'existait pas, elle a été créée.");
        }
        if(red == null) {
            red = Objects.requireNonNull(Bukkit.getServer().getScoreboardManager()).getMainScoreboard().registerNewTeam("rouge");
            //sender.sendMessage("§7L'équipe rouge n'existait pas, elle a été créée.");
        }
        blue.setDisplayName("Bleu");
        blue.setColor(org.bukkit.ChatColor.BLUE);
        red.setDisplayName("Rouge");
        red.setColor(ChatColor.RED);
        //sender.sendMessage("§7Équipes configurées.");
    }

    @EventHandler
    public void noFoodChange(FoodLevelChangeEvent event)
    {
        event.setCancelled(true);
    }

    @EventHandler
    public void noCraft(CraftItemEvent event)
    {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event)
    {
        if(!event.getPlayer().isOp())
        {
            event.setCancelled(true);
        }
    }
}
