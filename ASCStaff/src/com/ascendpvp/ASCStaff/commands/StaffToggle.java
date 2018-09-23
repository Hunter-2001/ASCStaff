package com.ascendpvp.ASCStaff.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ascendpvp.ASCStaff.ASCStaffMain;
import com.ascendpvp.ASCStaff.utils.Helpers;



public class StaffToggle implements CommandExecutor {

	ASCStaffMain plugin;
	public StaffToggle(ASCStaffMain plugin) {
		this.plugin = plugin;
	}
	Helpers help = new Helpers();

	public Map<UUID, ItemStack[]> staffInv = new HashMap<UUID, ItemStack[]>();
	public Map<UUID, ItemStack[]> staffInvArmor = new HashMap<UUID, ItemStack[]>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		//Basic checks
		if(!cmd.getName().equalsIgnoreCase("staff")) return false;
		if(!(sender instanceof Player)) {
			sender.sendMessage("Console can't use ASCStaff, silly!");
			return false;
		}
		Player p = (Player) sender;
		if(args.length > 0) {
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.staff_incorrect_usage")));
			return false;
		}
		if(!p.hasPermission("ascstaff.use")) {
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.staff_no_permission")));
			return false;
		}

		//If player disables staff mode
		if(plugin.staffmode.contains(p.getUniqueId())) {
			if(staffInv.containsKey(p.getUniqueId()) && staffInvArmor.containsKey(p.getUniqueId())) {

				p.setAllowFlight(false);
				plugin.vanished.remove(p.getUniqueId());
				plugin.staffmode.remove(p.getUniqueId());
				p.getPlayer().getInventory().clear();
				p.getInventory().setContents(staffInv.get(p.getUniqueId()));
				p.getInventory().setArmorContents(staffInvArmor.get(p.getUniqueId()));
				p.sendMessage(help.cc(plugin.getConfig().getString("messages.staff_off")));

				for(Player onlinePlayers : Bukkit.getOnlinePlayers()) {
					if(!onlinePlayers.hasPermission("ascstaff.seevanish")) {
						onlinePlayers.showPlayer(p.getPlayer());
					}
				}
			}
			return false;
		}

		//Adding data to Lists/Maps
		if(!plugin.staffchat.contains(p.getUniqueId())) {
			plugin.staffchat.add(p.getUniqueId());
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.staff_chat_on")));
		}
		p.setAllowFlight(true);
		plugin.vanished.add(p.getUniqueId());
		plugin.staffmode.add(p.getUniqueId());
		staffInv.put(p.getUniqueId(), p.getInventory().getContents());
		staffInvArmor.put(p.getUniqueId(), p.getInventory().getArmorContents());
		p.getPlayer().getInventory().clear();
		p.getPlayer().getInventory().setArmorContents(null);
		p.sendMessage(help.cc(plugin.getConfig().getString("messages.staff_on")));

		//Adding staff items
		ItemStack randomTeleportItem = new ItemStack(Material.BLAZE_ROD);
		ItemStack playerFreezeItem = new ItemStack(Material.ICE);
		ItemStack worldEditItem = new ItemStack(Material.WOOD_AXE);
		ItemStack cpsTestItem = new ItemStack(Material.CARROT_STICK);
		ItemStack vanishItem = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemStack staffChatItem = new ItemStack(Material.BARRIER);
		p.getPlayer().getInventory().setItem(0, worldEditItem);
		p.getPlayer().getInventory().setItem(2, help.nameItemLore(cpsTestItem, help.cc("&bCPS &7Test"), help.cc("&7- Right click a player to test their CPS")));
		p.getPlayer().getInventory().setItem(3, help.nameItemLore(randomTeleportItem, help.cc("&bRandom &7Teleporter"), help.cc("&7- Right click to teleport to a random player")));
		p.getPlayer().getInventory().setItem(5, help.nameItemLore(playerFreezeItem, help.cc("&bFreeze &7Player"), help.cc("&7- Right Click a player to freeze them")));
		p.getPlayer().getInventory().setItem(6, help.nameItemLore(staffChatItem, help.cc("&bToggle &7Staff Chat"), help.cc("&7- Toggle staff messages")));
		p.getPlayer().getInventory().setItem(8, help.nameItemLore(vanishItem, help.cc("&bToggle &7Vanish"), help.cc("&7- Toggle your player visibility")));

		for(Player onlinePlayers : Bukkit.getOnlinePlayers()) {
			if(!onlinePlayers.hasPermission("ascstaff.seevanish")) {
				onlinePlayers.hidePlayer(p.getPlayer());
			}
		}
		return false;
	}
}