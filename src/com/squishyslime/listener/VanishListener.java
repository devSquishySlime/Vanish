package com.squishyslime.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import com.squishyslime.Main;

public class VanishListener implements Listener {
	public Main plugin;
	public VanishListener(Main plugin) {
		this.plugin = plugin;
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		if(!(p.hasPermission("vanish.see"))) {
		plugin.vanishedPlayers.forEach(pHidden -> {
				if(p.getUniqueId() != pHidden) {
					p.hidePlayer(plugin, Bukkit.getPlayer(pHidden));
				}
		});
		}
		if(plugin.vanishedPlayers.contains(p.getUniqueId())) {
			event.setJoinMessage("");
			Bukkit.getOnlinePlayers().forEach(pO -> {
				pO.hidePlayer(plugin,p);
			});
			p.sendMessage(IridiumColorAPI.process("<GRADIENT:2fe4f5>[Vanish] You are still hidden!</GRADIENT:057a85>"));
		}
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		if(plugin.vanishedPlayers.contains(p.getUniqueId())) {
			event.setQuitMessage("");
		}
	}
	@EventHandler
	public void onPickup(PlayerPickupItemEvent event) {
	    Player p = event.getPlayer();
	    if (plugin.vanishedPlayers.contains(p.getUniqueId()) && !plugin.pickupOn.contains(p.getUniqueId())) {
	        event.setCancelled(true);
	    }
	}

}