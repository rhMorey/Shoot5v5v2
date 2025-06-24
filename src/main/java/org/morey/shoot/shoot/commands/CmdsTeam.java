package org.morey.shoot.shoot.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.morey.shoot.shoot.Campsite;
import org.morey.shoot.shoot.team.ReworkTeam;

public class CmdsTeam implements CommandExecutor {

    String needName = Campsite.error + "Veuillez indiquer un joueur à ajouter";
    String needTeam = Campsite.error + "Veuillez indiquer une équipe valide.";
    String syntaxeError = Campsite.error + "Syntaxe:§7 /csteam <add|remove|get> <joueur> <rouge|bleu>";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command c, @NotNull String s, @NotNull String[] args) {

        if (s.equalsIgnoreCase("csteam")) {

            if(!(sender instanceof Player)) {
                sender.sendMessage(Campsite.error + "Cette commande doit être exécutée par un joueur.");
                return true;
            }

            if(args.length <= 0) {
                sender.sendMessage(syntaxeError);
                return true;
            }

            String action = args[0].toLowerCase();

            // AJOUTER/ENLEVER UN JOUEUR A L'ÉQUIPE
            if (action.equals("add") || action.equals("remove")) {

                if (args.length < 3) {
                    sender.sendMessage(needName);
                    return true;
                }

                String team = args[2].toLowerCase();
                Player targetPlayer = Bukkit.getPlayer(args[1]);

                if (targetPlayer == null || !targetPlayer.isOnline()) {
                    sender.sendMessage(Campsite.error + "Le joueur spécifié n'est pas en ligne.");
                    return true;
                }

                if (!team.equals("rouge") && !team.equals("bleu")) {
                    sender.sendMessage(needTeam);
                    return true;
                }

                if (action.equals("add")) {

                    if (addPlayerToTeam(targetPlayer.getName(), team)) {
                        sender.sendMessage(Campsite.prefix + "Le joueur " + targetPlayer.getName() + " a été ajouté à l'équipe " + team + ".");
                    } else {
                        sender.sendMessage(Campsite.error + "Le joueur " + targetPlayer.getName() + " est déjà dans cette équipe.");
                    }
                } else if (action.equals("remove")) {

                    if (removePlayerFromTeam(targetPlayer.getName(), team)) {
                        sender.sendMessage(Campsite.prefix + "Le joueur " + targetPlayer.getName() + " a été retiré de l'équipe " + team + ".");
                    } else {
                        sender.sendMessage(Campsite.error + "Le joueur " + targetPlayer.getName() + " n'est pas dans cette équipe.");
                    }
                }
                return true;
            }

            if(args[0].equalsIgnoreCase("get")) {
                if(args.length < 2) {
                    sender.sendMessage(needTeam);
                    return true;
                }

                String team = args[1].toLowerCase();

                if(!team.equals("rouge") && !team.equals("bleu")) {
                    sender.sendMessage(needTeam);
                    return true;
                }
                sender.sendMessage(Campsite.prefix + "Équipe " + team + ": §7" + ReworkTeam.getPlayersInTeam(team));
                return true;
            }
            sender.sendMessage(Campsite.error + "Une erreur s'est produite, vérifiez la syntaxe. Syntaxe:§7 /csteam <add|remove|get> <joueur> <rouge|bleu>");
        }
        return false;
    }

    private boolean addPlayerToTeam(String playerName, String team) {
        return ReworkTeam.addPlayer(playerName, team);
    }

    private boolean removePlayerFromTeam(String playerName, String team) {
        return ReworkTeam.removePlayer(playerName, team);
    }

}
