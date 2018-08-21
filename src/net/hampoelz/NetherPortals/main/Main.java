package net.hampoelz.NetherPortals.main;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.hampoelz.NetherPortals.Commands.NetherPortals;
import net.hampoelz.NetherPortals.Events.Events;

public class Main extends JavaPlugin
{

	public static Boolean useStayPut = false;
	public static Boolean debug = false;
	
	@Override
	public void onEnable()
	{
		if (Bukkit.getPluginManager().isPluginEnabled("AsyncWorldManager"))
		{
			if (Bukkit.getPluginManager().isPluginEnabled("StayPut"))
			{
				useStayPut = true;
			}
			
			Bukkit.getPluginManager().registerEvents(new Events(), this);
        }
		else
        {
            throw new RuntimeException("Could not find AsyncWorldManager!! Plugin can not work without it!");
        }
		
		getCommands();
		
		try
		{
			Config.setConfig();
			Config.save();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("[NetherPortals] v" + getDescription().getVersion() + " enabled!");
	}

	@Override
	public void onDisable()
	{
		System.out.println("[NetherPortals] v" + getDescription().getVersion() + " disabled!");
	}
	
	private void getCommands()
	{
		getCommand("netherportals").setExecutor(new NetherPortals());
		getCommand("np").setExecutor(new NetherPortals());
	}
}
