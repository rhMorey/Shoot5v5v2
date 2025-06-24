package org.morey.shoot.shoot.team.inventory;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class InvManager implements Listener {

    public static void redInv(Player player) {

        ItemStack swordy = new ItemStack(Material.STONE_SWORD);
        ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE);
        ItemStack crossbow = new ItemStack(Material.CROSSBOW);
        crossbow.addEnchantment(Enchantment.PIERCING, 2);
        ItemStack arrow = new ItemStack(Material.ARROW, 16);
        player.getInventory().addItem(swordy, pickaxe, crossbow, arrow);
        player.getInventory().setHelmet(createEquipment(new ItemStack(Material.LEATHER_HELMET), Color.RED));
        player.getInventory().setChestplate(createEquipment(new ItemStack(Material.LEATHER_CHESTPLATE), Color.RED));
        player.getInventory().setLeggings(createEquipment(new ItemStack(Material.LEATHER_LEGGINGS), Color.RED));
        player.getInventory().setBoots(createEquipment(new ItemStack(Material.LEATHER_BOOTS), Color.RED));
    }

    public static void blueInv(Player player) {
        ItemStack swordp = new ItemStack(Material.STONE_SWORD);
        ItemStack bow = new ItemStack(Material.BOW, 1);
        ItemStack arrow = new ItemStack(Material.ARROW, 14);
        player.getInventory().addItem(swordp, bow, arrow);
        player.getInventory().setHelmet(createEquipment(new ItemStack(Material.LEATHER_HELMET), Color.BLUE));
        player.getInventory().setChestplate(createEquipment(new ItemStack(Material.LEATHER_CHESTPLATE), Color.BLUE));
        player.getInventory().setLeggings(createEquipment(new ItemStack(Material.LEATHER_LEGGINGS), Color.BLUE));
        player.getInventory().setBoots(createEquipment(new ItemStack(Material.LEATHER_BOOTS), Color.BLUE));
    }

    public static ItemStack createEquipment(ItemStack leather, Color color)
    {
        LeatherArmorMeta meta = (LeatherArmorMeta) leather.getItemMeta();
        assert meta != null;
        meta.setColor(color);
        leather.setItemMeta(meta);
        leather.addEnchantment(Enchantment.PROTECTION, 3);
        return leather;
    }

}
