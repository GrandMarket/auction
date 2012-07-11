package com.github.grandmarket.auction;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import static org.bukkit.ChatColor.*;

public class Auction extends JavaPlugin {
	public void onEnable() {
		getLogger().info("Auction plugin has been enabled");
	}
	public void onDisable() {
		getLogger().info("Auction plugin has been disabled");
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("auction")) {
			if(args.length < 1) {
				sender.sendMessage("The auction plugin is under developemnt");
				return true;
			}
			else if(args[1] == "create") {
				if(sender.hasPermission("Auction.create")) {
					if(args.length > 1) {
						if(getConfig().contains("auctions."+args[2])) {
							sender.sendMessage("The auction "+args[2]+" already exists.");
							return true;
						}
						else {
							getConfig().set("auctions."+args[2]+".name", args[2]);
							if(args.length > 2) {
								getConfig().set("auctions."+args[2]+".owner", args[3]);
								sender.sendMessage(GRAY+"Auction " + args[2] + "created with owner "+args[3]);
								return true;
							}
							else if(sender.hasPermission("Auction.run")) {
								getConfig().set("auctions."+args[2]+".owner", sender.getName());
								sender.sendMessage(GRAY+"Auction "+args[2]+" created with owner " + sender.getName());
								return true;
							}
							else {
								sender.sendMessage("No auction owner specified, and you don't have permission to run an auction.");
								return true;
							}
						}
					}
					else {
						sender.sendMessage("Usage: /"+commandLabel+" create <name> [owner]");
						return true;
					}
				}
				else {
					sender.sendMessage("You don't have permssion to create an auction");
					return true;
				}
			}
			else if(args[1] == "setspawn") {
				if(args.length > 1) {
					if(!getConfig().contains("auctions."+args[2])) {
						sender.sendMessage("The specified auction does not exist.");
						return true;
					}
					Player sendingPlayer = (Player) sender;
					Vector spawnLocation = sendingPlayer.getLocation().toVector();
					getConfig().set("auctions."+args[2]+".spawnLocation", spawnLocation);
					sender.sendMessage(GRAY+"Auction spawn set");
					return true;
				}
				else {
					sender.sendMessage("Usage: /auction setspawn <auction name>");
					return true;
				}
			}
			else if(args[1] == "help" || args[1] == "?") {
				sender.sendMessage("Usage:");
				sender.sendMessage("/auction create <auction name> [auction owner]");
				sender.sendMessage("/auction setspawn <auction name>");
			}
		}
		return false;
	}
}
