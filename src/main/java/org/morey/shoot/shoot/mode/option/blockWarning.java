package org.morey.shoot.shoot.mode.option;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.morey.shoot.shoot.mainEvent;
import org.morey.shoot.shoot.team.team;

import java.awt.*;
import java.util.UUID;

public class blockWarning implements Listener {

    @EventHandler @SuppressWarnings("deprecated")
    public void advertBlock(BlockBreakEvent event)
    {
        Material block = event.getBlock().getType();
        World w = event.getBlock().getWorld();
        Location loc = event.getBlock().getLocation();
        Player breaker = event.getPlayer();
        if(block.equals(mainEvent.blueblock))
        {
            for (Player ap : Bukkit.getOnlinePlayers())
            {
                if(team.blue.getEntries().contains(ap.getName()) && team.red.getEntries().contains(breaker.getName()))
                {
                    w.playSound(loc, Sound.ITEM_SHIELD_BLOCK, 3, 0);
                    ap.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§cUn attaquant a détruit un bloc de défense!"));
                }
            }
        }
    }

}
