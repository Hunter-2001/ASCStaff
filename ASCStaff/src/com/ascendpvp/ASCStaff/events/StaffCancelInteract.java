package com.ascendpvp.ASCStaff.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import com.ascendpvp.ASCStaff.ASCStaffMain;
import com.ascendpvp.ASCStaff.utils.Helpers;

public class StaffCancelInteract implements Listener {
	
	ASCStaffMain plugin;
	public StaffCancelInteract(ASCStaffMain plugin) {
		this.plugin = plugin;
	}
	Helpers help = new Helpers();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent e) { //Cancel block break
		if(e.getPlayer().hasPermission("ascstaff.interact")) return;
		if(!plugin.staffmode.contains(e.getPlayer().getUniqueId())) return;
		e.setCancelled(true);
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent e) { //Cancel block place
		if(e.getPlayer().hasPermission("ascstaff.interact")) return;
		if(!plugin.staffmode.contains(e.getPlayer().getUniqueId())) return;
		
		e.setCancelled(true);
	}
	@EventHandler
	public void onInventoryInteract(InventoryClickEvent e) { //Cancel inventory move
		if(e.getWhoClicked().hasPermission("ascstaff.interact")) return;
		if(!plugin.staffmode.contains(e.getWhoClicked().getUniqueId())) return;
		if(e.getClickedInventory() == null) return;
		if(e.getClickedInventory().getType() != InventoryType.PLAYER) return;

		e.setCancelled(true);
	}
}
