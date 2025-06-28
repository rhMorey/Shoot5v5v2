package org.morey.shoot.shoot.mode;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.morey.shoot.shoot.Campsite;
import org.morey.shoot.shoot.mode.option.FirstDiamondBlockBreak;
import org.morey.shoot.shoot.team.ReworkTeam;
import org.morey.shoot.shoot.team.TeamBuilder;
import org.morey.shoot.shoot.utils.EnhanceServer;
import org.morey.shoot.shoot.mode.option.KillCount;

import static org.morey.shoot.shoot.Campsite.getCampsiteColor;
import static org.morey.shoot.shoot.Campsite.plugin;

public class WinCondition implements Listener {

    public static int diamondBlockBreaked = 0;
    public static int reddead = 0;
    public static int bluedead = 0;
    public static int strengthTimer = 20;
    public static int redGlobalScore = PartyManager.getIntScore("red");
    public static int blueGlobalScore = PartyManager.getIntScore("blue");
    public static boolean isFinalParty;
    public static String winDisconnectMessage = "§7Un adversaire s'est deconnecté.";
    public static String winRedMessage = ReworkTeam.redColor + "L'équipe Rouge a gagné la manche !";
    public static String winBlueMessage = ReworkTeam.blueColor + "L'équipe Bleu a gagné la manche !";
    public static String displayFooterHeaderWinMessage = "\n \n";

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
                diamondBlockBreaked++;
                if(diamondBlockBreaked == 1)
                {
                    event.setCancelled(true);
                    event.getBlock().setType(Material.BEDROCK);
                    FirstDiamondBlockBreak.breakFirstDiamondBlock();
                }
                if(diamondBlockBreaked == 2)
                {
                    loc.getBlock().setType(Material.BEDROCK);
                    winParty("red", false);
                }
            }
        }
    }

    public static void winParty(String team, boolean forfait)
    {
        Campsite.log.info("winParty called");
        PartyManager.endParty();
        if(redGlobalScore == 7 || blueGlobalScore == 7)
        {
            winGame(team, forfait);
        }
        else
        {
            if(team.equalsIgnoreCase("red"))
            {
                redGlobalScore = PartyManager.getIntScore("red");
                Campsite.log.info("actual redGlobalScore: " + redGlobalScore);
                PartyManager.setIntScore("red", PartyManager.getIntScore("red") + 1);
                Campsite.log.info("incrementation redGlobalScore: " + redGlobalScore);
                finishParty();
            }
            else if (team.equalsIgnoreCase("blue"))
            {
                blueGlobalScore = PartyManager.getIntScore("blue");
                Campsite.log.info("actual blueGlobalScore: " + blueGlobalScore);
                PartyManager.setIntScore("blue", PartyManager.getIntScore("blue") + 1);
                Campsite.log.info("incrementation blueGlobalScore: " + blueGlobalScore);
                finishParty();
            }
        }
    }

    public static void winGame(String team, boolean forfait)
    {
        Campsite.log.info("winGame called");
        PartyManager.endGame();
        if(forfait)
        {
            if(team.equalsIgnoreCase("red"))
            {
                finishGame();
                displayWinMessage("red", true);
            }
            else if (team.equalsIgnoreCase("blue"))
            {
                finishGame();
                displayWinMessage("blue", true);
            }
        }
        else
        {
            if (team.equalsIgnoreCase("red"))
            {
                finishGame();
                displayWinMessage("red", false);

            } else if (team.equalsIgnoreCase("blue"))
            {
                finishGame();
                displayWinMessage("blue", false);
            }
        }
    }

    public static void finishGame()
    {
        Campsite.log.info("finishGame called");
        for (Player ap : Bukkit.getOnlinePlayers())
        {
            effectOnWin(ap);
            PartyManager.endGame();
        }
    }

    public static void finishParty()
    {
        Campsite.log.info("finishParty called");
        for (Player ap : Bukkit.getOnlinePlayers())
        {
            effectOnPartyEnd(ap);
        }
    }

    public static void displayWinMessage(String winnerTeam, boolean isDisconnectedPlayer)
    {
        if(winnerTeam.equalsIgnoreCase("red") && !isDisconnectedPlayer)
        {
            Bukkit.broadcastMessage(displayFooterHeaderWinMessage);
            Bukkit.broadcastMessage(winRedMessage);
            Bukkit.broadcastMessage(displayFooterHeaderWinMessage);
        }
        if(winnerTeam.equalsIgnoreCase("red") && isDisconnectedPlayer)
        {
            Bukkit.broadcastMessage(displayFooterHeaderWinMessage);
            Bukkit.broadcastMessage(winRedMessage);
            Bukkit.broadcastMessage(winDisconnectMessage);
            Bukkit.broadcastMessage(displayFooterHeaderWinMessage);
        }
        if(winnerTeam.equalsIgnoreCase("blue") && !isDisconnectedPlayer)
        {
            Bukkit.broadcastMessage(displayFooterHeaderWinMessage);
            Bukkit.broadcastMessage(winBlueMessage);
            Bukkit.broadcastMessage(displayFooterHeaderWinMessage);
        }
        if(winnerTeam.equalsIgnoreCase("blue") && isDisconnectedPlayer)
        {
            Bukkit.broadcastMessage(displayFooterHeaderWinMessage);
            Bukkit.broadcastMessage(winBlueMessage);
            Bukkit.broadcastMessage(winDisconnectMessage);
            Bukkit.broadcastMessage(displayFooterHeaderWinMessage);
        }
    }

    public static void effectOnWin(Player ap)
    {
        //if (ap.isOp()) return;
        ap.setGameMode(GameMode.SPECTATOR);
        ap.playSound(ap, Sound.UI_TOAST_CHALLENGE_COMPLETE, 7, 0);
        ap.getInventory().clear();
    }

    public static void effectOnPartyEnd(Player player)
    {
        player.setGameMode(GameMode.SPECTATOR);
        player.playSound(player, Sound.ITEM_TRIDENT_THUNDER, 1, 0);
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
                        winParty("red", false);
                    }
                }
                else if (ReworkTeam.getTeamPlayer(player).equals("rouge"))
                {
                    reddead++;
                    if (redsize == reddead) {
                        winParty("blue", false);
                    }
                }
            }
        }
    }
}
