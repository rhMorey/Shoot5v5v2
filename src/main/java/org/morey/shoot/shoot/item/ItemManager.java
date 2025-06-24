package org.morey.shoot.shoot.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    public static ItemStack Item(Material mat, String name, String lore1) {

        ItemStack I = new ItemStack(mat);
        ItemMeta IM = I.getItemMeta();
        List<String> ILore = new ArrayList<>();
        ILore.add(String.valueOf(lore1));
        IM.setLore(ILore);
        IM.setDisplayName(name);
        IM.setUnbreakable(true);
        I.setItemMeta(IM);
        return I;
    }

}
