package org.morey.shoot.shoot.team;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;
import org.morey.shoot.shoot.Campsite;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.morey.shoot.shoot.Campsite.plugin;
import static org.morey.shoot.shoot.utils.EnhanceServer.setColoredNameForAll;

public class ReworkTeam {

    public static ChatColor redColor = ChatColor.of(new Color(255, 111, 111));
    public static ChatColor blueColor = ChatColor.of(new Color(111, 183, 255));

    // TODO: Partout où il y a écrit "bleu" ou "rouge" remplacer par deux strings pour modifier les couleurs des teams plus tard


    public static void initPlayersConfig(List<String> pseudos, String team) {
        plugin.getConfig().set("team." + team + ".players", pseudos);
        plugin.saveConfig();
    }

    /**
     * Permet d'ajouter un joueur à une équipe
     *
     * @param pseudo Pseudo du joueur à ajouter
     * @param team Équipe où ajouter le joueur
     */
    public static boolean addPlayer(String pseudo, String team) {

        List<String> pseudos = plugin.getConfig().getStringList("team." + team + ".players");
        if (pseudos.contains(pseudo)) {
            return false;
        }

        pseudos.add(pseudo.trim());
        initPlayersConfig(pseudos, team);
        setColoredNameForAll();
        return true;
    }

    /**
     * Permet de retirer un joueur d'une équipe.
     *
     * @param pseudo Le pseudo du joueur à retirer
     * @param team L'équipe du joueur à retirer
     */
    public static boolean removePlayer(String pseudo, String team) {

        List<String> pseudos = plugin.getConfig().getStringList("team." + team + ".players");
        if (!pseudos.contains(pseudo)) {
            return false;
        }

        pseudos.remove(pseudo.trim());
        initPlayersConfig(pseudos, team);
        setColoredNameForAll();
        return true;
    }

    /**
     * Permet de récupérer les joueurs d'une équipe.
     *
     * @param team L'équipe où on veut récupérer les joueurs
     */
    public static String getPlayersInTeam(String team)
    {
        List<String> pseudos = plugin.getConfig().getStringList("team." + team + ".players");
        return pseudos.toString();
    }

    /**
     * Permet de récupérer l'équipe d'un joueur.
     *
     * @param player Le joueur où l'on veut récupérer l'équipe
     */
    public static String getTeamPlayer(Player player)
    {
        if(getPlayersInTeam("bleu").contains(player.getName())) {
            return "bleu";
        } else if(getPlayersInTeam("rouge").contains(player.getName())) {
            return "rouge";
        }
        return null;
    }

    /**
     * Unregister le joueur si jamais il a été expulsé de l'équipe par une déconnexion ou autre événement non prévu.
     *
     * @param player Le joueur à unregister
     */
    public static void unregister(Player player)
    {
        removePlayer(player.getName(), getTeamPlayer(player));
        Campsite.log.info("Unregistering player: " + player.getName());
    }

    public static void unregisterAll()
    {
        for(Player players : Bukkit.getOnlinePlayers())
        {
            unregister(players);
        }
    }

    public static int getTeamSize(String team) {
        FileConfiguration config = plugin.getConfig();
        List<String> players = config.getStringList("team." + team + ".players");
        return players.size();
    }
}
