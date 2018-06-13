package net.hampoelz.NetherPortals.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config
{
	public static File ConfigFile = new File("plugins/NetherPortals", "config.yml");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);
	
	//----------------------------------------------------------------------------------------------------------------------\\
	
	public static void save() throws IOException
	{
		Config.save(ConfigFile);
	}
	
	public static void load() throws FileNotFoundException, IOException, InvalidConfigurationException
	{
		Config.load(ConfigFile);
	}
	
	public static void setConfig() throws IOException
	{
		int ConfigVersion = 1;
		
		int Version = Config.getInt("config.ConfigVersion");
		
		if(!ConfigFile.exists())
		{		
			Config.set("config.ConfigVersion", ConfigVersion);
			
			Config.set("config.General.Messages.No Permissions", "&8[&6NetherPortals&8] &cYou not have permission to perform this command.");
			Config.set("config.General.Messages.Usage", "&8[&6NetherPortals&8] &8Usage: &7/NetherPortals [reload]");
			
			Config.set("config.DefaultWorld", "Hub");
			
			Config.set("worlds.Survival.portalgoesto.NETHER", "Survival_Nether");
			Config.set("worlds.Survival.portalgoesto.END", "Survival_End");
			
			Config.set("worlds.Survival_Nether.portalgoesto.Nether", "Survival");

			Config.set("worlds.Survival_End.portalgoesto.END", "Survival");
			
			save();
		}
		else if (Version < ConfigVersion || Version > ConfigVersion)
		{	
			File ConfigFileBackup = new File("plugins/NetherPortals", "old config [v" + Version + "].yml");
			
			FileUtils.copyFile(ConfigFile, ConfigFileBackup);
			
			ConfigFile.delete();
			
			setConfig();
		}
	}
	
	//----------------------------------------------------------------------------------------------------------------------\\
	
	public static String getNoPermissions()
	{
		return Config.getString("config.General.Messages.No Permissions");
	}
	
	public static String getUsage()
	{
		return Config.getString("config.General.Messages.Usage");
	}
	
	public static String getDefaultWorld()
	{
		return Config.getString("config.DefaultWorld");
	}
	
	public static ConfigurationSection getWorlds()
	{
		return Config.getConfigurationSection("worlds");
	}
	
	public static String getWorldsNETHER(String from)
	{
		return Config.getString("worlds." + from + ".portalgoesto.NETHER");
	}
	
	public static String getWorldsEND(String from)
	{
		return Config.getString("worlds." + from + ".portalgoesto.END");
	}
}
