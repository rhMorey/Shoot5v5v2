package org.morey.shoot.shoot.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

import static org.morey.shoot.shoot.team.team.blue;
import static org.morey.shoot.shoot.team.team.red;
public class tcreateCmds implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command c, @NotNull String s, @NotNull String[] args) {

        if (s.equalsIgnoreCase("tcreate")) {
            red = Objects.requireNonNull(sender.getServer().getScoreboardManager()).getMainScoreboard().getTeam("rouge");
            blue = sender.getServer().getScoreboardManager().getMainScoreboard().getTeam("bleu");
            if(blue == null) {
                blue = Objects.requireNonNull(sender.getServer().getScoreboardManager()).getMainScoreboard().registerNewTeam("bleu");
                //sender.sendMessage("§7L'équipe bleu n'existait pas, elle a été créée.");
            }
            if(red == null) {
                red = Objects.requireNonNull(sender.getServer().getScoreboardManager()).getMainScoreboard().registerNewTeam("rouge");
                //sender.sendMessage("§7L'équipe rouge n'existait pas, elle a été créée.");
            }
            blue.setDisplayName("Bleu");
            blue.setColor(org.bukkit.ChatColor.BLUE);
            red.setDisplayName("Rouge");
            red.setColor(ChatColor.RED);
            //sender.sendMessage("§7Équipes configurées.");
        }
        return false;
    }
}
