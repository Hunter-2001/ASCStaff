package com.ascendpvp.ASCStaff.events.staffitems;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.ascendpvp.ASCStaff.ASCStaffMain;
import com.ascendpvp.ASCStaff.utils.Helpers;

public class CPSTestItem implements Listener {

	ASCStaffMain plugin;
	public CPSTestItem(ASCStaffMain plugin) {
		this.plugin = plugin;
	}
	Helpers help = new Helpers();

	public Map<Player, Integer> testedPlayers = new HashMap<Player, Integer>();
	
	@EventHandler
	public void onCPSItemUse(PlayerInteractEntityEvent e) {

		//Basic checks
		if(!plugin.staffmode.contains(e.getPlayer().getUniqueId())) return;
		if(e.getRightClicked().getType() != EntityType.PLAYER) return;
		Player p = e.getPlayer();
		Player target = (Player) e.getRightClicked();
		if(p.getItemInHand() == null || p.getItemInHand().getItemMeta() == null) return;
		if(p.getItemInHand().getItemMeta().getDisplayName() == null) return;
		if(!p.getItemInHand().getItemMeta().getDisplayName().equals(help.cc("&bCPS &7Test"))) return;
		e.setCancelled(true);
		if(testedPlayers.containsKey(target)) {
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.cps_test_already_running")));
			return;
		}
		
		testedPlayers.put(target, Integer.valueOf(0));
		p.sendMessage(help.cc(plugin.getConfig().getString("messages.cps_test_started").replace("#target#", target.getName())));
		
		//Create runnable to remove target from HashMap after a certian amount of time
		new BukkitRunnable() {
			public void run() {
				int totalClicks = testedPlayers.get(target);
				Double testLength = plugin.getConfig().getDouble("cps_test_length");
				Double cps = totalClicks / testLength;
				p.sendMessage(help.cc(plugin.getConfig().getString("messages.cps_test_finished").replace("#cps#", String.valueOf(cps)).replace("#target#", target.getName())));
				testedPlayers.remove(target);
			}
		}.runTaskLater(plugin, plugin.getConfig().getInt("cps_test_length") * 20);

	}
	//Event to handle adding amount of clicks the target performs to HashMap
	@EventHandler 
	public void onTestedClick(PlayerInteractEvent e) {

		if(!testedPlayers.containsKey(e.getPlayer())) return;
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			testedPlayers.put(e.getPlayer(), testedPlayers.get(e.getPlayer()) + 1);
		}
	}
}
