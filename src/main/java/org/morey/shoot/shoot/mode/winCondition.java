package org.morey.shoot.shoot.mode;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.morey.shoot.shoot.Shoot;
import org.morey.shoot.shoot.mainEvent;
import org.morey.shoot.shoot.mode.option.killCount;
import org.morey.shoot.shoot.team.team;

public class winCondition implements Listener {

    public static int score = 0;
    public static int reddead = 0;
    public static int bluedead = 0;
    public static int strengthTimer = 20;


    @EventHandler
    public void onBreakDiamondBlock(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Location loc = new Location(Bukkit.getWorld("world"), event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ());
        if(!mainEvent.getWhitelist(event.getBlock().getType()) && !player.isOp())
        {
            event.setCancelled(true);
        }
        else
        {
            if(event.getBlock().getType().equals(Material.DIAMOND_BLOCK) && team.blue.getEntries().contains(player.getName()))
            {
                player.sendMessage("§cVotre équipe doit défendre ce bloc, pas le casser.");
                event.setCancelled(true);
            }
            else if (event.getBlock().getType().equals(Material.DIAMOND_BLOCK) && team.red.getEntries().contains(player.getName()))
            {
                score++;
                if(score == 1)
                {
                    event.setCancelled(true);
                    event.getBlock().setType(Material.BEDROCK);
                    breakFirstDiamondBlock();
                }
                if(score == 2)
                {
                    loc.getBlock().setType(Material.BEDROCK);
                    win("red", false);
                }
            }
        }
    }

    public void breakFirstDiamondBlock()
    {
        Bukkit.broadcastMessage("§7L'équipe §cRouge §7a remporté un point en détruisant un bloc de diamant, ils gagnent §cForce 1 §7pendant 18 secondes!");
        for (Player ap : Bukkit.getOnlinePlayers())
        {
            ap.playSound(ap, Sound.ENTITY_WITHER_SPAWN, 7, 0);
            if(team.red.getEntries().contains(ap.getName()))
            {
                ap.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, strengthTimer * 20, 0));
                ap.sendMessage("§7Cassez le deuxième bloc de diamant afin de §eremporter la partie§7.");
            }
            if(team.blue.getEntries().contains(ap.getName()))
            {
                ap.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, strengthTimer * 20, 0));
            }
        }
    }

    public static void win(String team, boolean forfait)
    {
        mainEvent.stopgame();
        if(forfait)
        {
            if(team.equalsIgnoreCase("red"))
            {
                for (Player ap : Bukkit.getOnlinePlayers())
                {
                    effectOnWin(ap);
                    timer.endGame();
                }
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage("§7§lL'équipe §c§lRouge §7§la gagné la manche !");
                Bukkit.broadcastMessage(" §7Un joueur de l'équipe bleu a déclaré forfait.");
                Bukkit.broadcastMessage(" ");

            }
            else if (team.equalsIgnoreCase("blue"))
            {
                for (Player ap : Bukkit.getOnlinePlayers())
                {
                    effectOnWin(ap);
                    timer.endGame();
                }
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage("§7§lL'équipe §9§lBleu §7§la gagné la manche !");
                Bukkit.broadcastMessage(" §7Un joueur de l'équipe bleu a déclaré forfait.");
                Bukkit.broadcastMessage(" ");
            }
        }
        else
        {
            if (team.equalsIgnoreCase("red"))
            {
                for (Player ap : Bukkit.getOnlinePlayers())
                {
                    effectOnWin(ap);
                    timer.endGame();
                }
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage("§7§lL'équipe §c§lRouge §7§la gagné la manche !");
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage(" ");

            } else if (team.equalsIgnoreCase("blue"))
            {
                for (Player ap : Bukkit.getOnlinePlayers())
                {
                    effectOnWin(ap);
                    timer.endGame();
                }
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage("§7§lL'équipe §9§lBleu §7§la gagné la manche !");
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage(" ");
            }
        }
    }


    public static void effectOnWin(Player ap)
    {
        //if (ap.isOp()) return;
        ap.setGameMode(GameMode.SPECTATOR);
        ap.playSound(ap, Sound.UI_TOAST_CHALLENGE_COMPLETE, 7, 0);
        ap.getInventory().clear();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        int redsize = team.red.getEntries().size();
        int bluesize = team.blue.getEntries().size();
        World w = Bukkit.getWorld("world");
        event.setCancelled(true);
        Player player = event.getPlayer();
        Player killer = event.getPlayer().getKiller();
        player.setGameMode(GameMode.SPECTATOR);
        w.playSound(killer, Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
        killCount.manageKillCount(event, player, killer);

        if (team.blue.getEntries().contains(player.getName()))
        {
            bluedead++;
            Shoot.log.info(bluesize + " + " + bluedead);
            if(bluesize == bluedead)
            {
                win("red", false);
            }
        }
        else if (team.red.getEntries().contains(player.getName()))
        {
            reddead++;
            Shoot.log.info(redsize + " + " + reddead);
            if(redsize == reddead)
            {
                win("blue", false);
            }
        }
    }
}
