package com.ascendpvp.ASCStaff.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ascendpvp.ASCStaff.ASCStaffMain;
import com.ascendpvp.ASCStaff.utils.Helpers;

public class StaffChat implements CommandExecutor {

	ASCStaffMain plugin;
	public StaffChat(ASCStaffMain plugin) {
		this.plugin = plugin;
	}
	Helpers help = new Helpers();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		//Basic Checks
		if(!cmd.getName().equalsIgnoreCase("sc")) return false;
		if(!(sender instanceof Player)) {
			sender.sendMessage("Console can't use StaffChat silly!");
			return false;
		}
		Player p = (Player) sender;
		if(args.length < 1) {
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.staff_chat_incorrect_usage")));
			return false;
		}
		if(!p.hasPermission("ascstaff.chat")) {
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.staff_no_permission")));
			return false;
		}
		if(!plugin.staffchat.contains(p.getUniqueId())) {
			plugin.staffchat.add(p.getUniqueId());
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.staff_chat_on")));
		}
		
		//Iterate through online players and send message to players with StaffChat enabled
		for(Player onlinePlayers : Bukkit.getOnlinePlayers()) {
			if(plugin.staffchat.contains(onlinePlayers.getUniqueId())) {
				onlinePlayers.sendMessage(help.cc(plugin.getConfig().getString("messages.staff_chat").replace("#message#", String.join(" ", args)).replace("#name#", p.getName())));
			}
		}

		return false;
	}
}
