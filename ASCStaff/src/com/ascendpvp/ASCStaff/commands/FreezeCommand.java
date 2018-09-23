package com.ascendpvp.ASCStaff.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.ascendpvp.ASCStaff.ASCStaffMain;
import com.ascendpvp.ASCStaff.utils.Helpers;

public class FreezeCommand implements CommandExecutor {

	ASCStaffMain plugin;
	Inventory frozenInv;
	public FreezeCommand(ASCStaffMain plugin) {
		this.plugin = plugin;
		frozenInv = Bukkit.createInventory(null, 9, help.cc(plugin.getConfig().getString("messages.frozen_gui_name")));
	}
	Helpers help = new Helpers();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		//Basic checks
		if(!cmd.getName().equalsIgnoreCase("freeze")) return false;
		if(!(sender instanceof Player)) {
			sender.sendMessage("Console can't use Freeze silly!");
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
		if(!p.hasPermission("ascstaff.freeze")) {
			p.sendMessage(help.cc(plugin.getConfig().getString("messages.staff_no_permission")));
			return false;
		}
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
		return false;
	}
}
