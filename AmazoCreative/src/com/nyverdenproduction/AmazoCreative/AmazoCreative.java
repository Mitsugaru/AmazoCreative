package com.nyverdenproduction.AmazoCreative;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.nyverdenproduction.AmazoCreative.config.ConfigHandler;
import com.nyverdenproduction.AmazoCreative.events.ACBlockListener;
import com.nyverdenproduction.AmazoCreative.permissions.PermCheck;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class AmazoCreative extends JavaPlugin
{
	public static final String TAG = "[AmazoCreative]";
	private PermCheck perm;
	private ConfigHandler configHandler;
	private WorldGuardPlugin wg;
	@Override
	public void onEnable()
	{
		//Initialize permissions
		perm = new PermCheck(this);
		//Initialize config handler
		configHandler = new ConfigHandler(this);
		//Grab WorldGuard
		try
		{
			Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
			if(plugin == null || !(plugin instanceof WorldGuardPlugin))
			{
				getLogger().warning("Could not attach to WorldGuard!");
				getServer().getPluginManager().disablePlugin(this);
			}
			else
			{
				wg = (WorldGuardPlugin) plugin;
			}
			
		}
		catch (Exception e)
		{
			getLogger().warning("Could not attach to WorldGuard!");
			getServer().getPluginManager().disablePlugin(this);
		}
		//Initialize listener
		getServer().getPluginManager().registerEvents(new ACBlockListener(this), this);
	}
	
	public WorldGuardPlugin getWorldGuard() {
	    return wg;
	}

	public PermCheck getPermissionHandler()
	{
		return perm;
	}
	
	public ConfigHandler getConfigHandler()
	{
		return configHandler;
	}
}
