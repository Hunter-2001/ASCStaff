package com.ascendpvp.ASCStaff;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.ascendpvp.ASCStaff.commands.CPSCommand;
import com.ascendpvp.ASCStaff.commands.FreezeCommand;
import com.ascendpvp.ASCStaff.commands.StaffChat;
import com.ascendpvp.ASCStaff.commands.StaffChatToggle;
import com.ascendpvp.ASCStaff.commands.StaffToggle;
import com.ascendpvp.ASCStaff.events.FrozenCancelInteract;
import com.ascendpvp.ASCStaff.events.StaffCancelInteract;
import com.ascendpvp.ASCStaff.events.VanishOnConnect;
import com.ascendpvp.ASCStaff.events.staffitems.CPSTestItem;
import com.ascendpvp.ASCStaff.events.staffitems.FreezePlayerItem;
import com.ascendpvp.ASCStaff.events.staffitems.RandomTpItem;
import com.ascendpvp.ASCStaff.events.staffitems.StaffChatToggleItem;
import com.ascendpvp.ASCStaff.events.staffitems.VanishToggleItem;


public class ASCStaffMain extends JavaPlugin {
	
	public List<UUID> staffmode = new ArrayList<UUID>();
	public List<UUID> vanished = new ArrayList<UUID>();
	public List<UUID> staffchat = new ArrayList<UUID>();
	public List<UUID> frozen = new ArrayList<UUID>();
	
	public void onEnable() {
		CPSCommand CPSCommandClass = new CPSCommand(this);
		
		getCommand("staff").setExecutor(new StaffToggle(this));
		Bukkit.getPluginManager().registerEvents(new VanishOnConnect(this), this);
		Bukkit.getPluginManager().registerEvents(new StaffChatToggleItem(this), this);
		Bukkit.getPluginManager().registerEvents(new StaffCancelInteract(this), this);
		Bukkit.getPluginManager().registerEvents(new CPSTestItem(this), this);
		Bukkit.getPluginManager().registerEvents(new RandomTpItem(this), this);
		Bukkit.getPluginManager().registerEvents(CPSCommandClass, this);
		Bukkit.getPluginManager().registerEvents(new FrozenCancelInteract(this), this);
		Bukkit.getPluginManager().registerEvents(new FreezePlayerItem(this), this);
		Bukkit.getPluginManager().registerEvents(new VanishToggleItem(this), this);
		getCommand("sc").setExecutor(new StaffChat(this));
		getCommand("sctoggle").setExecutor(new StaffChatToggle(this));
		getCommand("freeze").setExecutor(new FreezeCommand(this));
		getCommand("cps").setExecutor(CPSCommandClass);
		saveDefaultConfig();
	}
}
