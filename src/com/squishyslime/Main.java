package com.squishyslime;

import java.util.HashSet;
import java.util.UUID;

import com.squishyslime.api.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import com.squishyslime.commands.VanishCommand;
import com.squishyslime.listener.VanishListener;
import com.squishyslime.commands.VanishTab;
public class Main extends JavaPlugin {
	public HashSet<UUID> vanishedPlayers = new HashSet<>();
	public HashSet<UUID> pickupOn = new HashSet<>();
	public String currentVersion = "1.0.1";
	@Override
	public void onEnable() {
		new Metrics(this,24761);
		getCommand("vanish").setExecutor(new VanishCommand(this));
		getCommand("vanish").setTabCompleter(new VanishTab(this));
		getServer().getPluginManager().registerEvents(new VanishListener(this),this);
	}

}
