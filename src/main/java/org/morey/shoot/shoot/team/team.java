package org.morey.shoot.shoot.team;

import org.apache.logging.log4j.util.IndexedStringMap;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scoreboard.Team;
import org.morey.shoot.shoot.Shoot;

import java.util.Objects;

public class team implements Listener
{

    public static Team blue = Bukkit.getServer().getScoreboardManager().getMainScoreboard().getTeam("blue");
    // == purple
    public static Team red = Bukkit.getServer().getScoreboardManager().getMainScoreboard().getTeam("red");
    // == yellow

    public static void createTeam()
    {
        if(Bukkit.getServer().getScoreboardManager().getMainScoreboard().getTeam("bleu") == null) {
            blue = Bukkit.getServer().getScoreboardManager().getMainScoreboard().registerNewTeam("bleu");
            blue.setDisplayName("Bleu");
            blue.setColor(ChatColor.BLUE);
        }
        else if(Bukkit.getServer().getScoreboardManager().getMainScoreboard().getTeam("rouge") == null) {
            red = Bukkit.getServer().getScoreboardManager().getMainScoreboard().registerNewTeam("rouge");
            red.setDisplayName("Rouge");
            red.setColor(ChatColor.RED);
        }
        else
        {
            Shoot.log.info("Les équipes sont déjà préfaites.");
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
            if(Bukkit.getScoreboardManager().getMainScoreboard().getTeam("rouge") != null || Bukkit.getScoreboardManager().getMainScoreboard().getTeam("bleu") != null)
            {
                blue.addEntry(event.getPlayer().getName());
                player.sendMessage("§7Vous êtes maintenant dans l'équipe §9Bleu§7.");
                player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 1, 1);
            }
            else
            {
                player.sendMessage("§7Une erreur est survenue. Vérifiez que les équipes soient bien initiliasées");
            }
        }
        else if(event.getClickedBlock().getType() == Material.RED_GLAZED_TERRACOTTA)
        {
            if(Bukkit.getScoreboardManager().getMainScoreboard().getTeam("rouge") != null || Bukkit.getScoreboardManager().getMainScoreboard().getTeam("bleu") != null)
            {
            red.addEntry(event.getPlayer().getName());
            player.sendMessage("§7Vous êtes maintenant dans l'équipe §cRouge§7.");
            player.playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 1, 1);
            }
            else
            {
                player.sendMessage("§7Une erreur est survenue. Vérifiez que les équipes soient bien initiliasées");
            }
        }
    }
}
