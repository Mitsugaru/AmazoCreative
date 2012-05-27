package com.nyverdenproduction.AmazoCreative;

import org.bukkit.plugin.java.JavaPlugin;

import com.nyverdenproduction.AmazoCreative.config.ConfigHandler;
import com.nyverdenproduction.AmazoCreative.permissions.PermCheck;

public class AmazoCreative extends JavaPlugin
{
	private PermCheck perm;
	private ConfigHandler configHandler;
	@Override
	public void onEnable()
	{
		//Initialize permissions
		perm = new PermCheck(this);
		//Initialize config handler
		configHandler = new ConfigHandler(this);
		//Initialize listener
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
