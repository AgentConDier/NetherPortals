package net.hampoelz.NetherPortals.Commands;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import net.hampoelz.NetherPortals.main.Config;

public class NetherPortals implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			if (sender instanceof ConsoleCommandSender) {
				if (args[0].equalsIgnoreCase("reload")) {
					try {
						Config.load();
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getSuccess()));
					} catch (IOException | InvalidConfigurationException e) {
						e.printStackTrace();
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getFail()));
					}
				} else{
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getUsage()));
					return false;
				}
		    }
			if (sender instanceof Player){
				Player p = (Player) sender;
				
				if (args[0].equalsIgnoreCase("reload")) {
					if (p.hasPermission("NetherPortals.reload")) {
						try {
							Config.load();
							
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getSuccess()));
						} catch (IOException | InvalidConfigurationException e) {
							e.printStackTrace();
							
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getFail()));
						}
					} else {
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getNoPermissions()));
					}
				} else {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getUsage()));
					return false;
				}
			}
		} else {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getArguments()).replace("%n%", ""+args.length));
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getUsage()));
			return false;
		}
		return true;
	}
}
