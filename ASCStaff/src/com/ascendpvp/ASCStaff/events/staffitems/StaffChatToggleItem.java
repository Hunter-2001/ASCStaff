package com.ascendpvp.ASCStaff.events.staffitems;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.ascendpvp.ASCStaff.ASCStaffMain;
import com.ascendpvp.ASCStaff.utils.Helpers;

public class StaffChatToggleItem implements Listener {

	ASCStaffMain plugin;
	public StaffChatToggleItem(ASCStaffMain plugin) {
		this.plugin = plugin;
	}
	Helpers help = new Helpers();
	
	@EventHandler
	public void toggleStaffChatEvent(PlayerInteractEvent e) {
		
		//Basic checks
		if(e.getPlayer().getItemInHand() == null || e.getPlayer().getItemInHand().getItemMeta() == null) return;
		if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName() == null) return;
		if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(help.cc("&bToggle &7Staff Chat"))) {
			Player p = e.getPlayer();
			e.setCancelled(true);
			
			//Toggle StaffChat on/off
			if(plugin.staffchat.contains(p.getUniqueId())) {
				plugin.staffchat.remove(p.getUniqueId());
				p.sendMessage(help.cc(plugin.getConfig().getString("messages.staff_chat_off")));
			} else {
				plugin.staffchat.add(p.getUniqueId());
				p.sendMessage(help.cc(plugin.getConfig().getString("messages.staff_chat_on")));
			}
		}
	}
}
