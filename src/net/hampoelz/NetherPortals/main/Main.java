package net.hampoelz.NetherPortals.main;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.hampoelz.NetherPortals.Commands.NetherPortals;
import net.hampoelz.NetherPortals.Events.Events;

public class Main extends JavaPlugin
{
	public static Boolean debug = false;
	
	@Override
	public void onEnable()
	{
		if (!Bukkit.getPluginManager().isPluginEnabled("AsyncWorldManager"))
		{
            Bukkit.getLogger().warning("[NetherPortals] Could not find AsyncWorldManager!! Plugin will likely not work without it!");
        }
		
		Bukkit.getPluginManager().registerEvents(new Events(), this);
		
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
