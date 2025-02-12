package com.squishyslime.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.squishyslime.Main;

public class VanishTab implements TabCompleter {
	public Main plugin;
	public VanishTab(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
			if(sender.hasPermission("vanish.exec")) {
			List<String> tab = new ArrayList<>();
			Bukkit.getOnlinePlayers().forEach(pO -> {
				tab.add(pO.getName());
			});
			tab.add("pu");
			tab.add("pickup");
			tab.add("ver");
			tab.add("version");
			return tab;
			}
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(plugin.vanishedPlayers.contains(p.getUniqueId())) {
					List<String> tab = new ArrayList<>();
					tab.add("pu");
					tab.add("pickup");
					tab.add("ver");
					tab.add("version");
					return tab;
				}
			}
		return null;
	}

}
