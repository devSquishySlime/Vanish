package com.squishyslime.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import com.squishyslime.Main;

public class VanishCommand implements CommandExecutor {
    public Main plugin;

    public VanishCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(IridiumColorAPI.process("<GRADIENT:d6152c>You need to be a player or add one to the command!</GRADIENT:bf0b20>"));
                return true;
            }
            if (sender.hasPermission("vanish.exec")) {
                Player p = (Player) sender;
                if (plugin.vanishedPlayers.contains(p.getUniqueId())) {
                    plugin.vanishedPlayers.remove(p.getUniqueId());
                    Bukkit.getOnlinePlayers().forEach(pO -> {
                        if (!pO.hasPermission("vanish.see")) {
                            pO.showPlayer(plugin, p);
                            pO.sendMessage(ChatColor.YELLOW + p.getName() + " joined the game");
                        }
                    });
                    p.sendMessage(IridiumColorAPI.process("<GRADIENT:2fe4f5>[Vanish] You are now visible!</GRADIENT:057a85>"));
                } else {
                    plugin.vanishedPlayers.add(p.getUniqueId());
                    Bukkit.getOnlinePlayers().forEach(pO -> {
                        if (!pO.hasPermission("vanish.see")) {
                            pO.hidePlayer(plugin, p);
                            pO.sendMessage(ChatColor.YELLOW + p.getName() + " left the game");
                        }
                    });
                    p.sendMessage(IridiumColorAPI.process("<GRADIENT:2fe4f5>[Vanish] You are now hidden!</GRADIENT:057a85>"));
                }
            } else {
                sender.sendMessage(IridiumColorAPI.process("<GRADIENT:d6152c>You cannot execute this command2!</GRADIENT:bf0b20>"));
                return true;
            }
        } else if (args.length == 1) {
            if (args[0].equals("pu") || args[0].equals("pickup")) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (sender.hasPermission("vanish.exec") || plugin.vanishedPlayers.contains(p.getUniqueId())) {
                        if (plugin.pickupOn.contains(p.getUniqueId())) {
                            plugin.pickupOn.remove(p.getUniqueId());
                            p.sendMessage(IridiumColorAPI.process("<GRADIENT:2fe4f5>[Vanish] You can't pickup items now!</GRADIENT:057a85>"));
                            return true;
                        } else {
                            plugin.pickupOn.add(p.getUniqueId());
                            p.sendMessage(IridiumColorAPI.process("<GRADIENT:2fe4f5>[Vanish] You are now able to pickup items!</GRADIENT:057a85>"));
                            return true;
                        }
                    }
                } else {
                    sender.sendMessage(IridiumColorAPI.process("<GRADIENT:d6152c>You need to be a player to execute this command!</GRADIENT:bf0b20>"));
                    return true;
                }
            } else if (args[0].equals("ver") || args[0].equals("version")) {
                sender.sendMessage(IridiumColorAPI.process("<GRADIENT:2fe4f5>[Vanish] Version: " + plugin.currentVersion + "</GRADIENT:057a85>"));
                return true;
            } else {
                if (!sender.hasPermission("vanish.exec")) {
                    sender.sendMessage(IridiumColorAPI.process("<GRADIENT:d6152c>You cannot execute this command!1</GRADIENT:bf0b20>"));
                    return true;
                }
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target == null) {
                    sender.sendMessage("<GRADIENT:d6152c>That player isn't online!</GRADIENT:bf0b20>");
                    return true;
                }
                if(!plugin.vanishedPlayers.contains(target.getUniqueId())) {
                plugin.vanishedPlayers.add(target.getUniqueId());
                Bukkit.getOnlinePlayers().forEach(pO -> {
                    pO.hidePlayer(plugin, target);
                });
                target.sendMessage(IridiumColorAPI.process("<GRADIENT:2fe4f5>[Vanish] You are now hidden!</GRADIENT:057a85>"));
                sender.sendMessage(IridiumColorAPI.process("<GRADIENT:2fe4f5>[Vanish] " + target.getName() + " is now hidden!</GRADIENT:057a85>"));
                }
                else {
                    plugin.vanishedPlayers.remove(target.getUniqueId());
                    Bukkit.getOnlinePlayers().forEach(pO -> {
                        pO.showPlayer(plugin, target);
                    });
                    target.sendMessage(IridiumColorAPI.process("<GRADIENT:2fe4f5>[Vanish] You are now shown!</GRADIENT:057a85>"));
                    sender.sendMessage(IridiumColorAPI.process("<GRADIENT:2fe4f5>[Vanish] " + target.getName() + " is now shown!</GRADIENT:057a85>"));
                    }
                }
        } else {
            sender.sendMessage(IridiumColorAPI.process("<GRADIENT:d6152c>Usage: /vanish <pu/pickup/ver/version></GRADIENT:bf0b20>"));
        }
        return true;
    }
}
