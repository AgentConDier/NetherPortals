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

import net.hampoelz.NetherPortals.main.Config;
import net.hampoelz.NetherPortals.main.Main;

public class Events  implements Listener
{
	@EventHandler
    public void onPortal(PlayerPortalEvent event)
    {
		Player p = event.getPlayer();
		String w = event.getFrom().getWorld().getName();
		
		if (Main.debug) {
			System.out.println("[NetherPortals] Portal Event Details:");
			System.out.println("[NetherPortals] From: "+w+" "+event.getFrom().getBlockX()+" "+event.getFrom().getBlockY()+" "+event.getFrom().getBlockZ());
			System.out.println("[NetherPortals] To: "+event.getTo());//+" "+event.getTo().getBlockX()+" "+event.getTo().getBlockY()+" "+event.getTo().getBlockZ());
		}
		
		PortalType type = null;
		
		int x = event.getFrom().getBlockX();
		int y = event.getFrom().getBlockZ();
		Location blockCheckLocation = event.getFrom();
		
		blockcheck:for (int i = -1; i < 2; i++) { // checks in a 3x3 space around the player for portal blocks to determine the portal type
			for (int j = -1; j < 2; j++) {
				blockCheckLocation.setX(x+i);
				blockCheckLocation.setZ(y+j);
				if (Main.debug) {
					System.out.println("[NetherPortals] Checking block "+((i+1)*3+(j+2))+"/9 at "+blockCheckLocation.getX()+" "+blockCheckLocation.getBlockY()+" "+blockCheckLocation.getZ()+": Is "+blockCheckLocation.getBlock().getType());
				}
				if (blockCheckLocation.getBlock().getType() == Material.NETHER_PORTAL) {
		            type = PortalType.NETHER;
		            break blockcheck;
		        } else if (blockCheckLocation.getBlock().getType() == Material.END_PORTAL) {
		        	type = PortalType.ENDER;
		            break blockcheck;
		        }
			}
		}
		
		if (Config.useTravelAgent()) {
			event.useTravelAgent(true);
		}
		
		if (Main.debug) {
			System.out.println("[NetherPortals] "+p.getName()+" used a "+blockCheckLocation.getBlock().getType()+" and will be teleported from "+w+" to the appropriate dimension");
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
							/* DEPRECATED
							String command = "wm tp " + to + " " + event.getPlayer().getName();
							if (Main.debug) {
								System.out.println("[NetherPortals] about to run teleport command: "+command);
							}
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
							*/
							if (Config.useTravelAgent()) {
								Location target = p.getLocation();
								target.setWorld(Bukkit.getWorld(to));
								if (Bukkit.getWorld(to).getEnvironment() == World.Environment.NETHER) {
									target.setX(Math.floor(target.getX()/8));
									target.setZ(Math.floor(target.getZ()/8));
								} else if (Bukkit.getWorld(to).getEnvironment() == World.Environment.NORMAL) {
									target.setX(Math.floor(target.getX()*8));
									target.setZ(Math.floor(target.getZ()*8));
								}
								event.setTo(target);
							} else {
								event.setTo(Bukkit.getWorld(to).getSpawnLocation());
							}
							if (Config.doAfterTeleport()) {
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getTeleport()).replace("%w%", to));
							}
						}
						else
						{
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getWorldError()));
						}
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
							/* DEPRECATED
							String command = "wm tp " + to + " " + event.getPlayer().getName();
							if (Main.debug) {
								System.out.println("[NetherPortals] about to run teleport command: "+command);
							}
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
							*/
							if (Config.useTravelAgent()) {
								if (Bukkit.getWorld(to).getEnvironment() == World.Environment.THE_END) {
									Location portalTarget = new Location(Bukkit.getWorld(to), 100, 48, 0);
									Location target = new Location(Bukkit.getWorld(to), 100, 48, 0); // that's not how the wiki says it works, but it works
									event.getPortalTravelAgent().createPortal(portalTarget); // creates obsidian platform
									p.teleport(target); // teleport the player onto the platform
								} else if (Bukkit.getWorld(to).getEnvironment() == World.Environment.NORMAL) {
									p.teleport(Bukkit.getWorld(to).getSpawnLocation());
								}
							} else {
								p.teleport(Bukkit.getWorld(to).getSpawnLocation());
							}
							if (Config.doAfterTeleport()) {
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getTeleport()).replace("%w%", to));
							}
							
						}
						else
						{
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.getWorldError()));
						}
					}
				}
			}
		}
    }
}
