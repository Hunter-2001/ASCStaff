package com.ascendpvp.ASCStaff.events;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.ascendpvp.ASCStaff.ASCStaffMain;
import com.ascendpvp.ASCStaff.utils.Helpers;

public class FrozenCancelInteract implements Listener {

	ASCStaffMain plugin;
	public FrozenCancelInteract(ASCStaffMain plugin) {
		this.plugin = plugin;
	}
	Helpers help = new Helpers();
	
	/*
	 * ---------------------------------------------------------------------
	 * This class is essentially to disalow all actions from a Frozen player
	 * ---------------------------------------------------------------------
	 */
	
	@EventHandler //PlayerInteract
	public void onFrozenInteract(PlayerInteractEvent e) {
		if(!plugin.frozen.contains(e.getPlayer().getUniqueId())) return;
		e.setCancelled(true);
	}
	@EventHandler //Player Drop Item
	public void onFrozenItemDrop(PlayerDropItemEvent e) {
		if(!plugin.frozen.contains(e.getPlayer().getUniqueId())) return;
		e.setCancelled(true);
	}
	@EventHandler //Player Interact Entity
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
		if(!plugin.frozen.contains(e.getPlayer().getUniqueId())) return;
		e.setCancelled(true);
	}
	@EventHandler //Player Item Consume
	public void onFrozenConsume(PlayerItemConsumeEvent e) {
		if(!plugin.frozen.contains(e.getPlayer().getUniqueId())) return;
		e.setCancelled(true);
	}
	@EventHandler //Player Pickup Item
	public void onFrozenPickupItem(PlayerPickupItemEvent e) {
		if(!plugin.frozen.contains(e.getPlayer().getUniqueId())) return;
		e.setCancelled(true);
	}
	@EventHandler //Player Damage Entity
	public void onFrozenDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity().getType() != EntityType.PLAYER) return;
		if(!plugin.frozen.contains(e.getEntity().getUniqueId())) return;
		e.getDamager().sendMessage(help.cc(plugin.getConfig().getString("messages.frozen_player_damage")));
		e.setCancelled(true);
	}
	@EventHandler //Player Move
	public void onFrozenMove(PlayerMoveEvent e) {
		if(!plugin.frozen.contains(e.getPlayer().getUniqueId())) return;
		e.getPlayer().teleport(e.getPlayer());
	}
	@EventHandler //Player Inventory Interact
	public void onFrozenInvInteract(InventoryClickEvent e) {
		if(!plugin.frozen.contains(e.getWhoClicked().getUniqueId())) return;
		e.setCancelled(true);
	}
}
