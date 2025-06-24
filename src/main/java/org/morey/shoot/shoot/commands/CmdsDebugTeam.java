package org.morey.shoot.shoot.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.morey.shoot.shoot.team.TeamBuilder;
import net.md_5.bungee.api.chat.TextComponent;

import java.awt.*;

public class CmdsDebugTeam implements CommandExecutor {

    TeamBuilder builder = new TeamBuilder();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(s.equalsIgnoreCase("createTeam")) {
            Player player = (Player) sender;
            TeamBuilder team = builder
                    .setTeamName("rouge")
                    .setTeamColorHex(new Color(255, 182, 193));
            //255, 182, 193
            //TextComponent component = new TextComponent(teamName);
            //component.setColor(net.md_5.bungee.api.ChatColor.of(teamColorHex));  // Utilisation de la couleur hexadécimale
            TextComponent component = new TextComponent(
                    "§rPrefix d'équipe » " + team.getTeamPrefix(player) + "\n" +
                    "§rNom d'équipe » " + team.getTeamName(player) + "\n" +
                    "§rCouleur d'équipe » " + team.getTeamColorHex(player) + " couleur" + "\n" +
                    "§rSize d'équipe » " + team.getTeamSize(team.getTeamName(player)));
            component.setColor(net.md_5.bungee.api.ChatColor.of(new Color(255, 182, 193)));
            sender.sendMessage(component.toLegacyText());
            return true;
        }
        return false;
    }
}
