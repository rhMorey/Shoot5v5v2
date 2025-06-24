package org.morey.shoot.shoot.mode;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.morey.shoot.shoot.Campsite;
import org.morey.shoot.shoot.team.ReworkTeam;
import org.morey.shoot.shoot.utils.EnhanceServer;
import org.morey.shoot.shoot.mode.option.PlaceChest;

import static org.apache.logging.log4j.LogManager.getLogger;

public class Timer {

    public static int prepa = 30; //30
    public static int secondsPassed = 0;
    public static int gameTime = 180; // 240
    public static org.bukkit.boss.BossBar bossBar;
    public static org.bukkit.boss.BossBar bossBarEnd;
    public static int endGameTimer = 15;

    public static void startTimer() {
        bossBar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);
        bossBar.setProgress(1.0);
        bossBar.setVisible(true);
        Bukkit.getScheduler().cancelTasks(Campsite.getInstance());

        new BukkitRunnable() {

            @Override
            public void run() {

                String timerFormat = "§7[" + Campsite.getCampsiteColor + secondsPassed + "§7/§f" + gameTime + "§7]";
                if(secondsPassed <= (prepa - 1))
                {
                    if(secondsPassed == 1)
                    {
                        for (Player ap : Bukkit.getOnlinePlayers())
                        {
                            if(ReworkTeam.getTeamPlayer(ap).equals("bleu"))
                            {
                                ap.sendMessage("§fLes attaquants se préparent," + Campsite.getCampsiteColor + " préparez vos défenses§f afin de protéger le cube en diamant.");
                            }
                            if(ReworkTeam.getTeamPlayer(ap).equals("rouge"))
                            {
                                ap.sendMessage("§fPréparez-vous à attaquer dans " + Campsite.getCampsiteColor + "30 secondes§f ...");
                            }
                        }
                    }
                    bossBar.setColor(BarColor.WHITE);
                    bossBar.setTitle(ReworkTeam.blueColor + "Préparation de l'équipe bleu " + timerFormat);
                    for (Player ap : Bukkit.getOnlinePlayers())
                    {
                        if(ReworkTeam.getTeamPlayer(ap).equals("rouge"))
                        {
                            ap.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 30, 8, false, false));
                            ap.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 40, 8, false, false));
                            ap.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 8, false, false));
                            ap.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 30, 250, false, false));
                        }
                    }
                }
                if(secondsPassed == 30)
                {
                    for (Player ap : Bukkit.getOnlinePlayers())
                    {
                        ap.getWorld().playSound(ap, Sound.ITEM_GOAT_HORN_SOUND_0, 1, 1);
                        ap.sendMessage(Campsite.getCampsiteColor + "Les attaquants arrivent !");
                    }
                }
                if(secondsPassed == 80)
                {
                    PlaceChest.place();
                }
                if(secondsPassed >= prepa && secondsPassed < gameTime)
                {
                    bossBar.setColor(BarColor.PURPLE);
                    bossBar.setTitle(Campsite.getCampsiteColor + "Bataille en cours " + timerFormat);
                }
                if (secondsPassed >= gameTime) {
                    cancel(); // arrête le timer
                    bossBar.removeAll();
                    bossBar.setTitle(secondsPassed + " §fsecondes restantes");
                    getLogger().info("Le timer est terminé.");
                    WinCondition.win("blue", false);
                    return;
                }
                bossBar.setProgress(1.0 - ((double) secondsPassed / gameTime));

                // Mettre à jour la BossBar pour tous les joueurs en ligne
                for (Player player : Bukkit.getOnlinePlayers()) {
                    bossBar.addPlayer(player);
                }

                // Effectuez des actions à chaque seconde
                secondsPassed++;
            }
        }.runTaskTimer(Campsite.getInstance(), 20L, 20L); // Démarrer le timer, 20L = 1 seconde
    }

    public static void endGame()
    {
        bossBarEnd = Bukkit.createBossBar("§fFin de la manche.", BarColor.WHITE, BarStyle.SOLID);
        bossBarEnd.setProgress(1.0);
        bossBarEnd.setVisible(true);
        secondsPassed = 0;
        Bukkit.getScheduler().cancelTasks(Campsite.getInstance());

        new BukkitRunnable() {

            @Override
            public void run() {
                if (secondsPassed >= endGameTimer) { // Si 6 minutes se sont écoulées (6 minutes * 60 secondes)
                    cancel(); // Arrêtez le timer
                    bossBarEnd.removeAll();
                    getLogger().info("Manche terminé.");
                    secondsPassed = 0;
                    for(Player ap : Bukkit.getOnlinePlayers())
                    {
                        EnhanceServer.spawnTeleport(ap);
                    }
                    Bukkit.getScheduler().cancelTasks(Campsite.getInstance());
                    return;
                }
                bossBarEnd.setProgress(1.0 - ((double) secondsPassed / endGameTimer));

                // Mettre à jour la BossBar pour tous les joueurs en ligne
                for (Player player : Bukkit.getOnlinePlayers()) {
                    bossBarEnd.addPlayer(player);
                }

                // Effectuez des actions à chaque seconde
                secondsPassed++;
            }
        }.runTaskTimer(Campsite.getInstance(), 20L, 20L);
    }
}
