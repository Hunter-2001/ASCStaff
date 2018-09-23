package com.ascendpvp.ASCStaff.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ascendpvp.ASCStaff.ASCStaffMain;
import com.ascendpvp.ASCStaff.utils.Helpers;

public class StaffChatToggle implements CommandExecutor {

	ASCStaffMain plugin;
	public StaffChatToggle(ASCStaffMain plugin) {
		this.plugin = plugin;
	}
	Helpers help = new Helpers();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		//Basic checks
		if(!cmd.getName().equalsIgnoreCase("sctoggle")) return false;
		if(!(sender instanceof Player)) {
			sender.sendMessage("Console can't use StaffChat silly!");
			return false;
		}
		Player p = (Player) sender;
		if(args.length > 0) {
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.staff_chat_toggle_incorrect_usage")));
			return false;
		}
		if(!p.hasPermission("ascstaff.chat")) {
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.staff_no_permission")));
			return false;
		}
		
		//Toggle StaffChat on/off
		if(plugin.staffchat.contains(p.getUniqueId())) {
			plugin.staffchat.remove(p.getUniqueId());
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.staff_chat_off")));
		} else {
			plugin.staffchat.add(p.getUniqueId());
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.staff_chat_on")));
		}
		return false;
	}
}
