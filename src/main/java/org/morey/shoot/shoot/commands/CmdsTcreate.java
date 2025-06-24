package org.morey.shoot.shoot.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.morey.shoot.shoot.Campsite;

public class CmdsTcreate implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command c, @NotNull String s, @NotNull String[] args) {

        if (s.equalsIgnoreCase("tcreate")) {

            sender.sendMessage(Campsite.prefix + Campsite.getCampsiteColor + "Cette commande est désactivée.");
            /*

            red = Objects.requireNonNull(sender.getServer().getScoreboardManager()).getMainScoreboard().getTeam("rouge");
            blue = sender.getServer().getScoreboardManager().getMainScoreboard().getTeam("bleu");
            if(blue == null) {
                blue = Objects.requireNonNull(sender.getServer().getScoreboardManager()).getMainScoreboard().registerNewTeam("bleu");
                sender.sendMessage(Campsite.prefix + "L'équipe bleu a été créée.");
            }
            if(red == null) {
                red = Objects.requireNonNull(sender.getServer().getScoreboardManager()).getMainScoreboard().registerNewTeam("rouge");
                sender.sendMessage(Campsite.prefix + "L'équipe rouge a été créée.");
            }
            setTeamColor(blue, red);
            //sender.sendMessage("§7Équipes configurées.");*/
        }
        return false;
    }
}
