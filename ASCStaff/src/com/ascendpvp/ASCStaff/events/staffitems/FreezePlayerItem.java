package com.ascendpvp.ASCStaff.events.staffitems;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.ascendpvp.ASCStaff.ASCStaffMain;
import com.ascendpvp.ASCStaff.utils.Helpers;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class FreezePlayerItem implements Listener {

	ASCStaffMain plugin;
	Inventory frozenInv;
	public FreezePlayerItem(ASCStaffMain plugin) {
		this.plugin = plugin;
		frozenInv = Bukkit.createInventory(null, 9, help.cc(plugin.getConfig().getString("messages.frozen_gui_name")));
	}
	Helpers help = new Helpers();

	@EventHandler
	public void onFreezeItemUse(PlayerInteractEntityEvent e) {

		//Basic checks
		if(!plugin.staffmode.contains(e.getPlayer().getUniqueId())) return;
		if(e.getRightClicked().getType() != EntityType.PLAYER) return;
		Player p = e.getPlayer();
		Player target = (Player) e.getRightClicked();
		if(p.getItemInHand() == null || p.getItemInHand().getItemMeta() == null) return;
		if(p.getItemInHand().getItemMeta().getDisplayName() == null) return;
		if(!p.getItemInHand().getItemMeta().getDisplayName().equals(help.cc("&bFreeze &7Player"))) return;
		e.setCancelled(true);

		if(plugin.frozen.contains(target.getUniqueId())) { //Remove Frozen Player
			plugin.frozen.remove(target.getUniqueId());
			target.closeInventory();
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.player_unfrozen").replace("#target#", target.getName())));
		} else { //Add Frozen Player + Open Frozen Gui
			plugin.frozen.add(target.getUniqueId());

			//Creation of GUI
			ItemStack glass = new ItemStack(Material.STAINED_GLASS, 1, (short)plugin.getConfig().getInt("frozen_gui_glass_color"));
			ItemStack admit = new ItemStack(Material.BARRIER);
			ItemStack moreTime = new ItemStack(Material.WATCH);
			ItemStack joiningDiscord = new ItemStack(Material.ENCHANTED_BOOK);

			frozenInv.setItem(0, help.nameItemLore(glass, help.cc("&bYou have been &3FROZEN"), help.cc(plugin.getConfig().getString("messages.frozen_discord_link"))));
			frozenInv.setItem(1, help.nameItemLore(glass, help.cc("&bYou have been &3FROZEN"), help.cc(plugin.getConfig().getString("messages.frozen_discord_link"))));
			frozenInv.setItem(2, help.nameItemLore(admit, help.cc("&bAdmit to Cheating"), help.cc(plugin.getConfig().getString("messages.frozen_admit_to_cheating").replace("#banLength#", String.valueOf(plugin.getConfig().getInt("admit_ban_length"))))));
			frozenInv.setItem(3, help.nameItemLore(glass, help.cc("&bYou have been &3FROZEN"), help.cc(plugin.getConfig().getString("messages.frozen_discord_link"))));
			frozenInv.setItem(4, help.nameItemLore(moreTime, help.cc("&bNeed more time"), help.cc(plugin.getConfig().getString("messages.frozen_needs_more_time"))));
			frozenInv.setItem(5, help.nameItemLore(glass, help.cc("&bYou have been &3FROZEN"), help.cc(plugin.getConfig().getString("messages.frozen_discord_link"))));
			frozenInv.setItem(6, help.nameItemLore(joiningDiscord, help.cc("&bCurrently joining Discord"), help.cc(plugin.getConfig().getString("messages.frozen_joining_discord"))));
			frozenInv.setItem(7, help.nameItemLore(glass, help.cc("&bYou have been &3FROZEN"), help.cc(plugin.getConfig().getString("messages.frozen_discord_link"))));
			frozenInv.setItem(8, help.nameItemLore(glass, help.cc("&bYou have been &3FROZEN"), help.cc(plugin.getConfig().getString("messages.frozen_discord_link"))));

			target.openInventory(frozenInv);
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.player_frozen").replace("#target#", target.getName())));
		}
	}
	//Event to prevent Frozen player inventory close
	@EventHandler 
	public void onFrozenCloseInv(InventoryCloseEvent e) {
		new BukkitRunnable() {
			public void run() {
				if(!plugin.frozen.contains(e.getPlayer().getUniqueId())) return;
				e.getPlayer().openInventory(frozenInv);
			}
		}.runTaskLater(plugin, 1);
	}
	
	//---Frozen Gui Options---
	@EventHandler
	public void onItemClick(InventoryClickEvent e) {
		if(!plugin.frozen.contains(e.getWhoClicked().getUniqueId())) return;
		if(e.getClickedInventory() == null) return;
		if(!e.getClickedInventory().getName().equalsIgnoreCase(help.cc(plugin.getConfig().getString("messages.frozen_gui_name")))) return;
		Player player = (Player) e.getWhoClicked();
		
		//If player clicks on slot 2 (Admit to cheating)
		if(e.getSlot() == 2 && e.getClickedInventory().getType() != InventoryType.PLAYER) {
			player.closeInventory();
			plugin.frozen.remove(player.getUniqueId());
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), plugin.getConfig().getString("admit_ban_command").replace("#frozenPlayerIP#", player.getAddress().getAddress().getHostAddress()).replace("#reason#", plugin.getConfig().getString("messages.frozen_admit_ban_reason")).replace("#banLength#", String.valueOf(plugin.getConfig().getInt("admit_ban_length"))));
			//Send staff ban message
			for(Player onlinePlayers : Bukkit.getOnlinePlayers()) {
				if(onlinePlayers.hasPermission("ascstaff.frozen.messages")) {
					onlinePlayers.sendMessage(help.cc(plugin.getConfig().getString("messages.frozen_admit").replace("#frozenPlayer#", player.getName())));
				}
			}
			
			//If player clicks on slot 4 (Requesting more time)
		} else if(e.getSlot() == 4 && e.getClickedInventory().getType() != InventoryType.PLAYER) {
			for(Player onlinePlayers : Bukkit.getOnlinePlayers()) {
				if(onlinePlayers.hasPermission("ascstaff.frozen.messages")) {
					onlinePlayers.sendMessage(help.cc(plugin.getConfig().getString("messages.message_frozen_needs_time").replace("#frozenPlayer#", player.getName())));
				}
			}
			player.sendMessage(help.cc(plugin.getConfig().getString("messages.frozen_time_requested")));

			//If player clicks on slot 6 (Player joining discord shortly)
		} else if(e.getSlot() == 6 && e.getClickedInventory().getType() != InventoryType.PLAYER) {
			for(Player onlinePlayers : Bukkit.getOnlinePlayers()) {
				if(onlinePlayers.hasPermission("ascstaff.frozen.messages")) {
					onlinePlayers.sendMessage(help.cc(plugin.getConfig().getString("messages.message_frozen_joining_discord").replace("#frozenPlayer#", player.getName())));
				}
			}
			player.sendMessage(help.cc(plugin.getConfig().getString("messages.frozen_discord_notified")));
		}
	}
	//---Frozen Gui Options---
	
	//---Frozen Logout---
	@EventHandler
	public void onFrozenLogout(PlayerQuitEvent e) {
		if(!plugin.frozen.contains(e.getPlayer().getUniqueId())) return;
		for(Player onlinePlayers : Bukkit.getOnlinePlayers()) {
			if(onlinePlayers.hasPermission("ascstaff.frozen.messages")) {
				TextComponent banMessage = new TextComponent(help.cc(plugin.getConfig().getString("messages.frozen_logged_out").replace("#frozenPlayer#", e.getPlayer().getName())));
				banMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, plugin.getConfig().getString("logged_ban_command").replace("#frozenPlayerIP#", e.getPlayer().getAddress().getAddress().getHostAddress()).replace("#reason#", plugin.getConfig().getString("messages.frozen_logout_ban_reason"))));
				onlinePlayers.spigot().sendMessage(banMessage);	
			}
		}
	}
	//---Frozen Logout---
}
