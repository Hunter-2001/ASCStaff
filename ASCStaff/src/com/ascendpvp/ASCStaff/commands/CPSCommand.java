package com.ascendpvp.ASCStaff.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.ascendpvp.ASCStaff.ASCStaffMain;
import com.ascendpvp.ASCStaff.utils.Helpers;

public class CPSCommand implements CommandExecutor, Listener {
	
	ASCStaffMain plugin;
	public CPSCommand(ASCStaffMain plugin) {
		this.plugin = plugin;
	}
	Helpers help = new Helpers();
	
	public Map<Player, Integer> testedPlayers = new HashMap<Player, Integer>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		//Basic checks
		if(!cmd.getName().equalsIgnoreCase("cps")) return false;
		if(!(sender instanceof Player)) {
			sender.sendMessage("Console can't use CPSTest silly!");
			return false;
		}
		Player p = (Player) sender;
		if(Bukkit.getPlayer(args[0]) == null) {
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.invalid_target")));
			return false;
		}
		Player target = Bukkit.getPlayer(args[0]);
		if(args.length > 1) {
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.cps_test_incorrect_usage")));
			return false;
		}
		if(!p.hasPermission("ascstaff.cps")) {
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.staff_no_permission")));
			return false;
		}
		if(testedPlayers.containsKey(target)) {
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.cps_test_already_running")));
			return false;
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
		return false;
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
