package org.morey.shoot.shoot.mode;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.morey.shoot.shoot.Campsite;
import org.morey.shoot.shoot.mode.option.DiamondReplaceAtStart;
import org.morey.shoot.shoot.mode.option.PlaceChest;
import org.morey.shoot.shoot.mode.option.PlacePressurePlate;
import org.morey.shoot.shoot.team.ReworkTeam;
import org.morey.shoot.shoot.utils.EnhanceServer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class StartParty {

    public static void startParty()
    {
        Campsite.log.info("startParty called");
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
        blueTeamLocations.add(b1); blueTeamLocations.add(b2); blueTeamLocations.add(b3); blueTeamLocations.add(b4); blueTeamLocations.add(b5);
        ArrayList<Location> redTeamLocations = new ArrayList<>();
        redTeamLocations.add(r1); redTeamLocations.add(r2); redTeamLocations.add(r3); redTeamLocations.add(r4); redTeamLocations.add(r5);
        HashSet<Location> usedLocations = new HashSet<>();

        // INITIALISATION DE LA MAP
        initMap();
        Campsite.log.info("Map initialisée");

        // INITIALISATION DU TIMER
        PartyManager.startTimer();
        Campsite.log.info("Timer initialisée");

        Bukkit.broadcastMessage(Campsite.prefix + Campsite.getCampsiteColor + "La manche " + PartyManager.partyNumber + " commence !");
        for (Player allPlayers : Bukkit.getOnlinePlayers()) {
            // VERIFICATION SI LE JOUEUR EST BIEN DANS LA TEAM BLUE, SI NON, ALORS ON VERIFIE LA TEAM ROUGE JUSTE APRES.
            if (ReworkTeam.getTeamPlayer(allPlayers).equalsIgnoreCase("bleu")) {

                // REINITIALISATION DU JOUEUR POUR EVITER LES PROBLEMES DE VIE BASSE, DE FAIM, DE STUFF ETC.
                initPlayer(allPlayers);

                Location randomLocation = getRandomUnusedLocation(blueTeamLocations, usedLocations);
                if (randomLocation != null) {
                    allPlayers.setGameMode(GameMode.SURVIVAL);
                    allPlayers.teleport(randomLocation);
                    allPlayers.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (PartyManager.prepa * 20) - 20, 1));
                    Campsite.log.info("Le joueur " + allPlayers.getName() + " de l'équipe bleu a été téléporté à la location { " + randomLocation + " }");
                    usedLocations.add(randomLocation);
                }
            }
            // VERIFICATION SI LE JOUEUR EST BIEN DANS LA TEAM RED
            if (ReworkTeam.getTeamPlayer(allPlayers).equalsIgnoreCase("rouge")) {

                // REINITIALISATION DU JOUEUR POUR EVITER LES PROBLEMES DE VIE BASSE, DE FAIM, DE STUFF ETC.
                initPlayer(allPlayers);

                Location randomLocation = getRandomUnusedLocation(redTeamLocations, usedLocations);
                if (randomLocation != null) {
                    allPlayers.setGameMode(GameMode.SURVIVAL);
                    allPlayers.teleport(randomLocation);
                    Campsite.log.info("Le joueur " + allPlayers.getName() + " de l'équipe rouge a été téléporté à la location { " + randomLocation + " }");
                    usedLocations.add(randomLocation);
                }
            }
        }
    }

    // stopParty est called uniquement quand la commande a été exécutée.
    public static void stopParty()
    {
        Campsite.log.info("stopParty called");
        PartyManager.secondsPassed = 0;
        Bukkit.getScheduler().cancelTasks(Campsite.getInstance());
        if(PartyManager.bossBar != null)
        {
            PartyManager.bossBar.setVisible(false);
            PartyManager.bossBar.removeAll();
        }
        WinCondition.diamondBlockBreaked = 0;
        WinCondition.reddead = 0;
        WinCondition.bluedead = 0;

        Campsite.log.info("§7La manche " + PartyManager.partyNumber + " est terminée.");
        startParty();
    }


    private static Location getRandomUnusedLocation(ArrayList<Location> locations, HashSet<Location> usedLocations) {
        ArrayList<Location> availableLocations = new ArrayList<>(locations);
        availableLocations.removeAll(usedLocations);
        if (availableLocations.isEmpty()) {
            return null; // Tous les emplacements ont été utilisés
        }
        Random random = new Random();
        return availableLocations.get(random.nextInt(availableLocations.size()));
    }

    public static void initMap()
    {
        PlaceChest.place();
        PlacePressurePlate.place();
        DiamondReplaceAtStart.replaceBedrock();
        WinCondition.bluedead = 0;
        WinCondition.reddead = 0;
    }

    public static void initPlayer(Player player)
    {
        player.setHealth(player.getMaxHealth());
        EnhanceServer.addPlayerKillCount(player);
        player.setGameMode(GameMode.SURVIVAL);
    }

}
