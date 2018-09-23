package com.ascendpvp.ASCStaff.events;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.ascendpvp.ASCStaff.ASCStaffMain;
import com.ascendpvp.ASCStaff.utils.Helpers;

public class VanishOnConnect implements Listener {

	ASCStaffMain plugin;
	public VanishOnConnect(ASCStaffMain plugin) {
		this.plugin = plugin;
	}
	Helpers help = new Helpers();

	//Event to hide staff members from players as they join
	@EventHandler
	public void onDefaultPlayerJoin(PlayerJoinEvent e) {
		if(plugin.vanished.size() <= 0) return;
		for(UUID vanishedPlayers : plugin.vanished) {
			if(!e.getPlayer().hasPermission("ascstaff.seevanish")) {
				Player p = Bukkit.getPlayer(vanishedPlayers);
				e.getPlayer().hidePlayer(p);
			}
		}
	}
}
