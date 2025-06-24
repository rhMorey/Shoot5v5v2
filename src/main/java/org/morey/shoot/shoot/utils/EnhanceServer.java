package org.morey.shoot.shoot.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.morey.shoot.shoot.Campsite;
import org.morey.shoot.shoot.mode.option.KillCount;
import org.morey.shoot.shoot.mode.Timer;
import org.morey.shoot.shoot.mode.WinCondition;
import org.morey.shoot.shoot.team.ReworkTeam;
import org.morey.shoot.shoot.team.TeamBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;

public class EnhanceServer implements Listener
{
    public static final Material blueblock = Material.BIRCH_LOG;
    public static ArrayList<Material> whitelistBlock = new ArrayList<>();
    public static final World world = Bukkit.getWorld("world");
    public static final Location spawn = new Location(world, 4977.5, 148, 3761.5);

    @EventHandler
    public void onConnect(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        spawnTeleport(player);
        setColoredNameForAll();
        event.setJoinMessage("§a+ §7" + player.getName());
        player.getInventory().clear();
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        event.setQuitMessage("§c- §7" + player.getName());

        ReworkTeam.unregister(player);

        //PROTECTION
        if(Timer.secondsPassed > 0 && Timer.secondsPassed < Timer.gameTime)
        {
            if(!player.isOp())
            {
                player.ban("Attendez avant de vous reconnecter.", Instant.ofEpochSecond(500),"test");
            }
            if(ReworkTeam.getTeamPlayer(player).equals("rouge"))
            {
                ReworkTeam.removePlayer(player.getName(), "rouge");
                WinCondition.win("blue", true);
            }
            if(ReworkTeam.getTeamPlayer(player).equals("bleu"))
            {
                ReworkTeam.removePlayer(player.getName(), "bleu");
                WinCondition.win("red", true);
            }
        }
        //PROTECTION
    }

    public static void spawnTeleport(Player player)
    {
        Location loc = spawn;
        player.teleport(loc);
        player.sendMessage(Campsite.prefix + "§fVous avez été redirigé vers le spawn.");
        player.setGameMode(GameMode.SURVIVAL);
    }

    public static net.md_5.bungee.api.ChatColor getColor(Player player)
    {
        if(ReworkTeam.getTeamPlayer(player).equals("bleu"))
        {
            return ReworkTeam.blueColor;
        }
        else if(ReworkTeam.getTeamPlayer(player).equals("rouge"))
        {
            return ReworkTeam.redColor;
        }
        else
        {
            return ChatColor.GRAY;
        }
    }

    public static void addPlayerKillCount(Player player)
    {
        KillCount.killCount.put(player, 0);
    }

    public static boolean getBlockWhitelist(Material mat)
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

    TeamBuilder builder = new TeamBuilder();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {
        TeamBuilder rouge = builder
                .setTeamName("rouge")
                .setTeamColorHex(ReworkTeam.redColor.getColor());

        TeamBuilder bleu = builder
                .setTeamName("bleu")
                .setTeamColorHex(ReworkTeam.blueColor.getColor());

        Player player = event.getPlayer();
        Campsite.log.info(ReworkTeam.getPlayersInTeam("rouge").toString());
        Campsite.log.info(ReworkTeam.getPlayersInTeam("bleu").toString());
        if(ReworkTeam.getPlayersInTeam("rouge").contains(player.getName()))
        {
            Campsite.log.info("§7Le joueur " + player.getName() + " est dans l'équipe rouge.");
            event.setFormat(rouge.getTeamPrefix(player) + " " + player.getName() + " §7»§r " + event.getMessage());
        }
        else if(ReworkTeam.getPlayersInTeam("bleu").contains(player.getName()))
        {
            Campsite.log.info("§7Le joueur " + player.getName() + " est dans l'équipe bleu.");
            event.setFormat(bleu.getTeamPrefix(player) + " " + player.getName() + " §7»§r " + event.getMessage());
        }
        else
        {
            event.setFormat("§7§lSPECTATEUR §7" + player.getName() + " §7»§r " + event.getMessage());
        }
    }

    public static void setColoredNameForAll()
    {
        for(Player viewer : Bukkit.getOnlinePlayers())
        {
            if(ReworkTeam.getTeamPlayer(viewer) == null)
            {
                viewer.getPlayer().setPlayerListName("§7" + viewer.getName());
            }
            else if(ReworkTeam.getTeamPlayer(viewer).equalsIgnoreCase("rouge"))
            {
                viewer.getPlayer().setPlayerListName(ReworkTeam.redColor + viewer.getName());
            }
            else if(ReworkTeam.getTeamPlayer(viewer).equalsIgnoreCase("bleu"))
            {
                viewer.getPlayer().setPlayerListName(ReworkTeam.blueColor + viewer.getName());
            }
        }
    }

    @EventHandler
    public void onClickTeam(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        if(event.getClickedBlock() == null) return;
        if (Objects.equals(event.getHand(), EquipmentSlot.OFF_HAND)) return;
        //
        if(event.getClickedBlock().getType() == Material.BLUE_GLAZED_TERRACOTTA)
        {
            if(ReworkTeam.getPlayersInTeam("bleu").contains(player.getName()) || ReworkTeam.getPlayersInTeam("rouge").contains(player.getName()))
            {
                player.sendMessage(Campsite.prefix + Campsite.getCampsiteColor + "Vous êtes déjà dans une équipe.");
                return;
            }
            else
            {
                ReworkTeam.addPlayer(player.getName(), "bleu");
                player.sendMessage(Campsite.prefix + "§fVous êtes maintenant dans l'équipe" + ReworkTeam.blueColor + " Bleu§f.");
                player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 1, 1);

                // MISE A JOUR
                EnhanceServer.setColoredNameForAll();
            }
        }
        else if(event.getClickedBlock().getType() == Material.RED_GLAZED_TERRACOTTA)
        {
            if(ReworkTeam.getPlayersInTeam("bleu").contains(player.getName()) || ReworkTeam.getPlayersInTeam("rouge").contains(player.getName()))
            {
                player.sendMessage(Campsite.prefix + Campsite.getCampsiteColor + "Vous êtes déjà dans une équipe.");
                return;
            }
            else
            {
                ReworkTeam.addPlayer(player.getName(), "rouge");
                player.sendMessage(Campsite.prefix + "§fVous êtes maintenant dans l'équipe" + ReworkTeam.redColor + " Rouge§f.");
                player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 1, 1);

                // MISE A JOUR
                EnhanceServer.setColoredNameForAll();
            }
        }
        else if(event.getClickedBlock().getType() == Material.WHITE_GLAZED_TERRACOTTA)
        {
            if(!ReworkTeam.getPlayersInTeam("bleu").contains(player.getName()) && !ReworkTeam.getPlayersInTeam("rouge").contains(player.getName()))
            {
                player.sendMessage(Campsite.prefix + Campsite.getCampsiteColor + "Ce bloc permet de quitter votre équipe, hors vous n'êtes dans aucune équipe.");
                return;
            }
            else
            {
                ReworkTeam.removePlayer(player.getName(), ReworkTeam.getTeamPlayer(player));
                player.sendMessage(Campsite.prefix + "Vous avez quitter votre équipe.");
                player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 1, 1);

                // MISE A JOUR
                EnhanceServer.setColoredNameForAll();
            }
        }
    }

}
