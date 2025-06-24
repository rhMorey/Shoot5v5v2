package org.morey.shoot.shoot.team;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.morey.shoot.shoot.Campsite;

import java.awt.*;
import java.util.List;

import static org.morey.shoot.shoot.Campsite.plugin;

public class TeamBuilder {

    private String teamName;
    private Color teamColorHex;

    public TeamBuilder setTeamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public TeamBuilder setTeamColorHex(Color teamColorHex) {
        this.teamColorHex = teamColorHex;
        return this;
    }

    public String getTeamName(Player player) {
        return ReworkTeam.getTeamPlayer(player);
    }

    public static int getTeamSize(String team) {
        return ReworkTeam.getTeamSize(team);
    }

    public Color getTeamColorHex(Player player) {
        if(ReworkTeam.getTeamPlayer(player).equalsIgnoreCase("bleu")) {
            return ReworkTeam.blueColor.getColor(); // Bleu
        } else if(ReworkTeam.getTeamPlayer(player).equalsIgnoreCase("rouge")) {
            return ReworkTeam.redColor.getColor(); // Rouge
        }
        return null;
    }

    public String getTeamPrefix(Player player)
    {
        if(ReworkTeam.getPlayersInTeam("bleu").contains(player.getName())) {
            return ReworkTeam.blueColor + "Bleu";
        } else if(ReworkTeam.getPlayersInTeam("rouge").contains(player.getName())) {
            return ReworkTeam.redColor + "Rouge";
        }
        return "Erreur lors de l'exécution du script";
    }
}

