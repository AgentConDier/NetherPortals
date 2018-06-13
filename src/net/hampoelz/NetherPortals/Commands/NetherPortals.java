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
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (sender instanceof ConsoleCommandSender)
		{
			if (args[1].equalsIgnoreCase("reload"))
			{
				try
				{
					Config.load();
					
					sender.sendMessage("The config was successfully reloaded");
				}
				catch (IOException | InvalidConfigurationException e)
				{
					e.printStackTrace();
					
					sender.sendMessage("An error occurred while reloading the configuration");
				}
			}
			else
			{
				sender.sendMessage("This command can only be executed as a player");
			}
	    }
		
		if (sender instanceof Player)
		{
			Player p = (Player) sender;
			
			if (args.length == 1)
			{
				if (p.hasPermission("NetherPortals.reload"))
				{
					if (args[0].equalsIgnoreCase("reload"))
					{
						try
						{
							Config.load();
							
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6NetherPortals&8] &6The config was successfully reloaded"));
						}
						catch (IOException | InvalidConfigurationException e)
						{
							e.printStackTrace();
							
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6NetherPortals&8] &cAn error occurred while reloading the configuration"));
						}
					}
					else
					{
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getUsage()));
					}
				}
				else
				{
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getNoPermissions()));
				}
			}
			else
			{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getUsage()));
			}
		}
		
		return true;
	}
}
