package org.morey.shoot.shoot.mode;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.morey.shoot.shoot.Campsite;
import org.morey.shoot.shoot.team.ReworkTeam;
import org.morey.shoot.shoot.team.TeamBuilder;
import org.morey.shoot.shoot.utils.EnhanceServer;
import org.morey.shoot.shoot.mode.option.KillCount;

import static org.morey.shoot.shoot.Campsite.getCampsiteColor;

public class WinCondition implements Listener {

    public static int score = 0;
    public static int reddead = 0;
    public static int bluedead = 0;
    public static int strengthTimer = 20;


    @EventHandler
    public void onBreakDiamondBlock(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Location loc = new Location(Bukkit.getWorld("world"), event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ());
        if(!EnhanceServer.getBlockWhitelist(event.getBlock().getType()) && !player.isOp())
        {
            event.setCancelled(true);
        }
        else
        {
            if(event.getBlock().getType().equals(Material.DIAMOND_BLOCK) && ReworkTeam.getTeamPlayer(player).equals("bleu"))
            {
                player.sendMessage(Campsite.prefix + getCampsiteColor + "Votre équipe doit défendre ce bloc.");
                event.setCancelled(true);
            }
            else if (event.getBlock().getType().equals(Material.DIAMOND_BLOCK) && ReworkTeam.getTeamPlayer(player).equals("rouge"))
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
        Bukkit.broadcastMessage(Campsite.prefix + "L'équipe Rouge a remporté un point en détruisant un bloc de diamant, ils gagnent " + getCampsiteColor + "Force 1 pendant 18 secondes§r!");
        for (Player ap : Bukkit.getOnlinePlayers())
        {
            ap.playSound(ap, Sound.ENTITY_WITHER_SPAWN, 7, 0);
            if(ReworkTeam.getTeamPlayer(ap).equals("rouge"))
            {
                ap.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, strengthTimer * 20, 0));
                ap.sendMessage(Campsite.prefix + "Cassez le deuxième bloc de diamant afin de " + getCampsiteColor +"remporter la partie§r.");
            }
            if(ReworkTeam.getTeamPlayer(ap).equals("bleu"))
            {
                ap.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, strengthTimer * 20, 0));
            }
        }
    }

    public static void win(String team, boolean forfait)
    {
        Start.stopgame();
        if(forfait)
        {
            if(team.equalsIgnoreCase("red"))
            {
                for (Player ap : Bukkit.getOnlinePlayers())
                {
                    effectOnWin(ap);
                    Timer.endGame();
                }
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage(ReworkTeam.redColor + "L'équipe Rouge a gagné la manche !");
                Bukkit.broadcastMessage("§7Un joueur de l'équipe bleu a déclaré forfait.");
                Bukkit.broadcastMessage(" ");

            }
            else if (team.equalsIgnoreCase("blue"))
            {
                for (Player ap : Bukkit.getOnlinePlayers())
                {
                    effectOnWin(ap);
                    Timer.endGame();
                }
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage(ReworkTeam.blueColor + "L'équipe Bleu a gagné la manche !");
                Bukkit.broadcastMessage("§7Un joueur de l'équipe rouge a déclaré forfait.");
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
                    Timer.endGame();
                }
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage(ReworkTeam.redColor + "L'équipe Rouge a gagné la manche !");
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage(" ");

            } else if (team.equalsIgnoreCase("blue"))
            {
                for (Player ap : Bukkit.getOnlinePlayers())
                {
                    effectOnWin(ap);
                    Timer.endGame();
                }
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage(ReworkTeam.blueColor + "L'équipe Bleu a gagné la manche !");
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
    public void onDeath(EntityDamageEvent event) {

        int redsize = TeamBuilder.getTeamSize("rouge");
        int bluesize = TeamBuilder.getTeamSize("bleu");
        World w = Bukkit.getWorld("world");
        if(event.getEntity() instanceof Player && event.getDamageSource().getCausingEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player killer = (Player) event.getDamageSource().getCausingEntity();

            //KillCount.manageKillCount(event, player, killer);


            if(event.getFinalDamage() >= player.getHealth())
            {
                KillCount.initialize(player, killer);
                event.setCancelled(true);
                player.teleport(EnhanceServer.spawn);
                player.setGameMode(GameMode.SPECTATOR);
                if (ReworkTeam.getTeamPlayer(player).equals("bleu"))
                {
                    bluedead++;
                    if (bluesize == bluedead) {
                        win("red", false);
                    }
                }
                else if (ReworkTeam.getTeamPlayer(player).equals("rouge"))
                {
                    reddead++;
                    if (redsize == reddead) {
                        win("blue", false);
                    }
                }
            }
        }
    }
}
