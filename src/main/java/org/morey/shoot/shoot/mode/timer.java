package org.morey.shoot.shoot.mode;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.morey.shoot.shoot.Shoot;
import org.morey.shoot.shoot.mainEvent;
import org.morey.shoot.shoot.mode.option.placeChest;
import org.morey.shoot.shoot.team.team;

import static org.apache.logging.log4j.LogManager.getLogger;

public class timer {

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
        Bukkit.getScheduler().cancelTasks(Shoot.getInstance());

        new BukkitRunnable() {

            @Override
            public void run() {

                if(secondsPassed <= (prepa - 1))
                {
                    if(secondsPassed == 1)
                    {
                        for (Player ap : Bukkit.getOnlinePlayers())
                        {
                            if(team.blue.getEntries().contains(ap.getName()))
                            {
                                ap.sendMessage("§7Les attaquants se préparent, §epréparez vos défenses§7 afin de protéger le cube en diamant.");
                            }
                            if(team.red.getEntries().contains(ap.getName()))
                            {
                                ap.sendMessage("§7Préparez-vous à attaquer dans §e30 secondes§7 ...");
                            }
                        }
                    }
                    bossBar.setColor(BarColor.YELLOW);
                    bossBar.setTitle("§ePréparation de l'équipe bleu §7[§f" + secondsPassed + "/§6" + gameTime + "§7]");
                    for (Player ap : Bukkit.getOnlinePlayers())
                    {
                        if(team.red.getEntries().contains(ap.getName()))
                        {
                            ap.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 8, false, false));
                            ap.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 40, 8, false, false));
                            ap.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 8, false, false));
                            ap.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 30, 250, false, false));
                        }
                    }
                }
                if(secondsPassed == 30)
                {
                    for (Player ap : Bukkit.getOnlinePlayers())
                    {
                        ap.getWorld().playSound(ap, Sound.ITEM_GOAT_HORN_SOUND_0, 1, 1);
                        ap.sendMessage("§cLes attaquants arrivent !");
                    }
                }
                if(secondsPassed == 80)
                {
                    placeChest.place();
                }
                if(secondsPassed >= prepa && secondsPassed < gameTime)
                {
                    bossBar.setColor(BarColor.RED);
                    bossBar.setTitle("§cBataille en cours §7[§f" + secondsPassed + "/§6" + gameTime + "§7]");
                }
                if (secondsPassed >= gameTime) { // Si 6 minutes se sont écoulées (6 minutes * 60 secondes)
                    cancel(); // Arrêtez le timer
                    bossBar.removeAll();
                    bossBar.setTitle(secondsPassed + " secondes restantes");
                    getLogger().info("Le timer est terminé.");
                    winCondition.win("blue", false);
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
        }.runTaskTimer(Shoot.getInstance(), 20L, 20L); // Démarrer le timer, 20L = 1 seconde
    }

    public static void endGame()
    {
        bossBarEnd = Bukkit.createBossBar("§eFin de la manche.", BarColor.YELLOW, BarStyle.SOLID);
        bossBarEnd.setProgress(1.0);
        bossBarEnd.setVisible(true);
        secondsPassed = 0;
        Bukkit.getScheduler().cancelTasks(Shoot.getInstance());

        new BukkitRunnable() {

            @Override
            public void run() {
                if (secondsPassed >= endGameTimer) { // Si 6 minutes se sont écoulées (6 minutes * 60 secondes)
                    cancel(); // Arrêtez le timer
                    bossBarEnd.removeAll();
                    getLogger().info("Le EndGame timer est terminé");
                    secondsPassed = 0;
                    for(Player ap : Bukkit.getOnlinePlayers())
                    {
                        mainEvent.spawnTeleport(ap);
                    }
                    Bukkit.getScheduler().cancelTasks(Shoot.getInstance());
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
        }.runTaskTimer(Shoot.getInstance(), 20L, 20L);
    }
}
