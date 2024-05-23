package org.morey.shoot.shoot.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.morey.shoot.shoot.Shoot;
import org.morey.shoot.shoot.mainEvent;
import org.morey.shoot.shoot.mode.option.diamondReplaceAtStart;
import org.morey.shoot.shoot.mode.option.placeChest;
import org.morey.shoot.shoot.mode.option.placePressurePlate;
import org.morey.shoot.shoot.mode.timer;
import org.morey.shoot.shoot.mode.winCondition;
import org.morey.shoot.shoot.team.inventory.invManager;
import org.morey.shoot.shoot.team.team;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import static org.morey.shoot.shoot.mode.timer.startTimer;


public class startCmds implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command c, @NotNull String s, @NotNull String[] strings)
    {

        Location r1, r2, r3, r4, r5, b1, b2, b3, b4, b5;
        World w = Bukkit.getWorld("world");
        r1 = new Location(w, 4808.5, 99, 3907.5, 0, 0);
        r2 = new Location(w, 4809.5, 99, 3905.5, 0, 0);
        r3 = new Location(w, 4811.5, 99, 3907.5, 0, 0);
        r4 = new Location(w, 4813.5, 99, 3909.5, 0, 0);
        r5 = new Location(w, 4811.5, 99, 3910.5, 0, 0);

        b1 = new Location(w, 4994.5, 125, 3851.5, 90, 0);
        b2 = new Location(w, 4995.5, 125, 3853.5, 90, 0);
        b3 = new Location(w, 4995.5, 125, 3856.5, 90, 0);
        b4 = new Location(w, 4995.5, 125, 3859.5, 90, 0);
        b5 = new Location(w, 4994.5, 125, 3861.5, 90, 0);

        ArrayList<Location> blueTeamLocations = new ArrayList<>();
        blueTeamLocations.add(b1);
        blueTeamLocations.add(b2);
        blueTeamLocations.add(b3);
        blueTeamLocations.add(b4);
        blueTeamLocations.add(b5);
        ArrayList<Location> redTeamLocations = new ArrayList<>();
        redTeamLocations.add(r1);
        redTeamLocations.add(r2);
        redTeamLocations.add(r3);
        redTeamLocations.add(r4);
        redTeamLocations.add(r5);
        HashSet<Location> usedLocations = new HashSet<>();

        Player player = (Player) sender;
        if(s.equalsIgnoreCase("start"))
        {
            placeChest.place();
            placePressurePlate.place();
            diamondReplaceAtStart.replaceBedrock();
            winCondition.bluedead = 0;
            winCondition.reddead = 0;
            player.performCommand("tcreate");
            if (Bukkit.getServer().getScoreboardManager().getMainScoreboard().getTeam("bleu") == null || Bukkit.getServer().getScoreboardManager().getMainScoreboard().getTeam("red") == null) {
                team.createTeam();
                player.sendMessage("§7Initialisation des équipes terminées.");
            }
                startTimer();
                Bukkit.broadcastMessage("§eLe jeu va pouvoir commencer.");
                for (Player ap : Bukkit.getOnlinePlayers()) {
                    if (team.blue.getEntries().contains(ap.getName())) {
                        ap.setHealth(ap.getMaxHealth());
                        mainEvent.addPlayerKillCount(ap);
                        ap.setGameMode(GameMode.SURVIVAL);
                        ap.getInventory().clear();
                        Location randomLocation = getRandomUnusedLocation(blueTeamLocations, usedLocations);
                        if (randomLocation != null) {
                            ap.teleport(randomLocation);
                            invManager.blueInv(ap);
                            ap.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (timer.prepa * 20) - 20, 1));
                            Shoot.log.info("Le joueur " + ap.getName() + " de l'équipe bleu a été téléporté à la location { " + randomLocation + " }");
                            usedLocations.add(randomLocation);
                        }
                    }
                    if (team.red.getEntries().contains(ap.getName())) {
                        ap.setHealth(ap.getMaxHealth());
                        mainEvent.addPlayerKillCount(ap);
                        ap.setGameMode(GameMode.SURVIVAL);
                        ap.getInventory().clear();
                        Location randomLocation = getRandomUnusedLocation(redTeamLocations, usedLocations);
                        if (randomLocation != null) {
                            ap.teleport(randomLocation);
                            invManager.redInv(ap);
                            Shoot.log.info("Le joueur " + ap.getName() + " de l'équipe rouge a été téléporté à la location { " + randomLocation + " }");
                            usedLocations.add(randomLocation);
                        }
                    }
                }
        }
        return false;
    }

    private Location getRandomUnusedLocation(ArrayList<Location> locations, HashSet<Location> usedLocations) {
        ArrayList<Location> availableLocations = new ArrayList<>(locations);
        availableLocations.removeAll(usedLocations);
        if (availableLocations.isEmpty()) {
            return null; // Tous les emplacements ont été utilisés
        }
        Random random = new Random();
        return availableLocations.get(random.nextInt(availableLocations.size()));
    }
}
