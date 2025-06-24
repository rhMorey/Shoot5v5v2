package org.morey.shoot.shoot.mode.option;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.morey.shoot.shoot.Campsite;
import org.morey.shoot.shoot.team.ReworkTeam;
import org.morey.shoot.shoot.utils.EnhanceServer;

public class BlockWarning implements Listener {

    @EventHandler @SuppressWarnings("deprecated")
    public void advertBlock(BlockBreakEvent event)
    {
        Material block = event.getBlock().getType();
        World w = event.getBlock().getWorld();
        Location loc = event.getBlock().getLocation();
        Player breaker = event.getPlayer();
        if(block.equals(EnhanceServer.blueblock))
        {
            for (Player ap : Bukkit.getOnlinePlayers())
            {
                if(ReworkTeam.getTeamPlayer(ap).equals("rouge") && ReworkTeam.getTeamPlayer(breaker).equals("rouge"))
                {
                    w.playSound(loc, Sound.ITEM_SHIELD_BLOCK, 3, 0);
                    ap.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Campsite.getCampsiteColor + "Un attaquant a détruit un bloc de défense!"));
                }
            }
        }
    }

}
