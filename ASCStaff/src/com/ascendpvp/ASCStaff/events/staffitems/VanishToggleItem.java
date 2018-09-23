package com.ascendpvp.ASCStaff.events.staffitems;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.ascendpvp.ASCStaff.ASCStaffMain;
import com.ascendpvp.ASCStaff.utils.Helpers;

public class VanishToggleItem implements Listener {
	
	ASCStaffMain plugin;
	public VanishToggleItem(ASCStaffMain plugin) {
		this.plugin = plugin;
	}
	Helpers help = new Helpers();

	@EventHandler
	public void toggleVanishEvent(PlayerInteractEvent e) {

		//Basic checks
		if(e.getPlayer().getItemInHand() == null || e.getPlayer().getItemInHand().getItemMeta() == null) return;
		if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName() == null) return;
		if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(help.cc("&bToggle &7Vanish"))) {
			e.setCancelled(true);
			
			//Method to toggle staff vanish
			if(plugin.vanished.contains(e.getPlayer().getUniqueId())) {
				plugin.vanished.remove(e.getPlayer().getUniqueId());
				e.getPlayer().sendMessage(help.cc(plugin.getConfig().getString("messages.vanish_toggled_off")));
				
				for(Player onlinePlayers : Bukkit.getOnlinePlayers()) {
					if(!onlinePlayers.hasPermission("ascstaff.seevanish")) {
						onlinePlayers.showPlayer(e.getPlayer());
					}
				}
			} else {
				plugin.vanished.add(e.getPlayer().getUniqueId());
				e.getPlayer().sendMessage(help.cc(plugin.getConfig().getString("messages.vanish_toggled_on")));
				
				for(Player onlinePlayers : Bukkit.getOnlinePlayers()) {
					if(!onlinePlayers.hasPermission("ascstaff.seevanish")) {
						onlinePlayers.hidePlayer(e.getPlayer());
					}
				}
			}
		}
	}
}
