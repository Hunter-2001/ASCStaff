package com.ascendpvp.ASCStaff.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Helpers {

	public String cc(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public ItemStack nameItem(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack nameItemLore(ItemStack item, String name, String lore) {
		ItemMeta meta = item.getItemMeta();
		List<String> lores = new ArrayList<String>();
		meta.setDisplayName(name);
		lores.add(lore);
		meta.setLore(lores);
		item.setItemMeta(meta);
		return item;
	}
}
