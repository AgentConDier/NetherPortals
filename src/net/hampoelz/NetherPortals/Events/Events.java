package net.hampoelz.NetherPortals.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.PortalType;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.hampoelz.NetherPortals.main.Config;
import net.hampoelz.NetherPortals.main.Main;

public class Events  implements Listener
{
	@EventHandler
	public void onQuit(PlayerQuitEvent event)
	{
		if (Main.useStayPut)
		{
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "wm tp " + event.getPlayer().getName() + " " + Config.getDefaultWorld());
		}
	}
	
	@EventHandler
    public void onPortal(PlayerPortalEvent event)
    {
		Player p = event.getPlayer();
		String w = p.getWorld().getName();
		
		PortalType type = PortalType.ENDER;
		
		if (event.getFrom().getBlock().getType() == Material.PORTAL)
		{
            type = PortalType.NETHER;
            event.useTravelAgent(true);
        }
			
		if (type == PortalType.NETHER)
		{
			ConfigurationSection section = Config.getWorlds();
			for (String from : section.getKeys(false))
			{
				if (w.equalsIgnoreCase(from))
				{
					String to = Config.getWorldsNETHER(from);
					
					if (to != null)
					{
						if (Bukkit.getWorld(to) != null)
						{
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "wm tp " + event.getPlayer().getName() + " " + to);
						}
						else
						{
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe specified world does not exist!"));
						}
						
						World world = p.getLocation().getWorld();
						double X = p.getLocation().getX();
						double Y = p.getLocation().getY();
						double Z = p.getLocation().getZ();
						
						Location location = new Location(world, X + 2, Y, Z + 2);
						p.teleport(location);
						
					}
				}
			}
		}
		
		if(type == PortalType.ENDER)
		{
			ConfigurationSection section = Config.getWorlds();
			for (String from : section.getKeys(false))
			{
				if (w.equalsIgnoreCase(from))
				{
					String to = Config.getWorldsEND(from);
					
					if (to != null)
					{		
						if (Bukkit.getWorld(to) != null)
						{
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "wm tp " + event.getPlayer().getName() + " " + to);
						}
						else
						{
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe specified world does not exist!"));
						}
						
						World world = p.getLocation().getWorld();
						double X = p.getLocation().getX();
						double Y = p.getLocation().getY();
						double Z = p.getLocation().getZ();
						
						Location location = new Location(world, X + 5, Y, Z + 5);
						p.teleport(location);
					}
				}
			}
		}
    }
}
