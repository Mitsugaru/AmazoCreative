package com.nyverdenproduction.AmazoCreative.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.ConfigurationSection;

import com.nyverdenproduction.AmazoCreative.AmazoCreative;

public class RootConfig
{
	private AmazoCreative plugin;

	public RootConfig(AmazoCreative plugin)
	{
		this.plugin = plugin;
		// Grab config
		final ConfigurationSection config = plugin.getConfig();
		// Defaults
		final Map<String, Object> defaults = new LinkedHashMap<String, Object>();
		defaults.put("version", plugin.getDescription().getVersion());
		// Insert defaults into config file if they're not present
		for (final Entry<String, Object> e : defaults.entrySet())
		{
			if (!config.contains(e.getKey()))
			{
				config.set(e.getKey(), e.getValue());
			}
		}
		// Save config
		plugin.saveConfig();
		// load settings
		loadSettings(config);
	}

	public void reloadConfig()
	{
		// Initial relaod
		plugin.reloadConfig();
		// Grab config
		final ConfigurationSection config = plugin.getConfig();
		// Load settings
		loadSettings(config);
	}

	private void loadSettings(ConfigurationSection config)
	{

	}
}
