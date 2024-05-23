package org.morey.shoot.shoot.team.inventory;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class invManager implements Listener {

    public static void redInv(Player player) {

        ItemStack swordy = new ItemStack(Material.STONE_SWORD);
        ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE);
        ItemStack crossbow = new ItemStack(Material.CROSSBOW);
        crossbow.addEnchantment(Enchantment.PIERCING, 2);
        ItemStack arrow = new ItemStack(Material.ARROW, 16);
        player.getInventory().addItem(createEquipment(new ItemStack(Material.LEATHER_HELMET), Color.RED),
                createEquipment(new ItemStack(Material.LEATHER_CHESTPLATE), Color.RED),
                createEquipment(new ItemStack(Material.LEATHER_LEGGINGS), Color.RED),
                createEquipment(new ItemStack(Material.LEATHER_BOOTS), Color.RED),
                swordy, pickaxe, crossbow, arrow);

    }

    public static void blueInv(Player player) {
        ItemStack swordp = new ItemStack(Material.STONE_SWORD);
        ItemStack bow = new ItemStack(Material.BOW, 1);
        ItemStack arrow = new ItemStack(Material.ARROW, 14);
        player.getInventory().addItem(createEquipment(new ItemStack(Material.LEATHER_HELMET), Color.BLUE),
                createEquipment(new ItemStack(Material.LEATHER_CHESTPLATE), Color.BLUE),
                createEquipment(new ItemStack(Material.LEATHER_LEGGINGS), Color.BLUE),
                createEquipment(new ItemStack(Material.LEATHER_BOOTS), Color.BLUE),
                swordp, bow, arrow);

    }

    public static ItemStack createEquipment(ItemStack leather, Color color)
    {
        LeatherArmorMeta meta = (LeatherArmorMeta) leather.getItemMeta();
        assert meta != null;
        meta.setColor(color);
        leather.setItemMeta(meta);
        leather.addEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION_ENVIRONMENTAL, 3);
        return leather;
    }

}
