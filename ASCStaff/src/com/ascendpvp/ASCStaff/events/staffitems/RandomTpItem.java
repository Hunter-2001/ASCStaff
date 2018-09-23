package com.ascendpvp.ASCStaff.events.staffitems;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.ascendpvp.ASCStaff.ASCStaffMain;
import com.ascendpvp.ASCStaff.utils.Helpers;

public class RandomTpItem implements Listener {

	ASCStaffMain plugin;
	public RandomTpItem(ASCStaffMain plugin) {
		this.plugin = plugin;
	}
	Helpers help = new Helpers();

	@EventHandler
	public void randomTpEvent(PlayerInteractEvent e) {

		//Basic checks
		if(e.getPlayer().getItemInHand() == null || e.getPlayer().getItemInHand().getItemMeta() == null) return;
		if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName() == null) return;
		if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(help.cc("&bRandom &7Teleporter"))) {
			
			//Method to randomly teleport to a player on the server
			e.setCancelled(true);
			Player p = e.getPlayer();
			int random = new Random().nextInt(Bukkit.getOnlinePlayers().size());
			Player target = (Player)Bukkit.getOnlinePlayers().toArray()[random];

			if (p.equals(target)) {
				random = new Random().nextInt(Bukkit.getOnlinePlayers().size());
				target = (Player)Bukkit.getOnlinePlayers().toArray()[random];
			}
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.random_tp_message").replace("#randomPlayer#", target.getName())));
			p.teleport(target);
		}
	}
}
