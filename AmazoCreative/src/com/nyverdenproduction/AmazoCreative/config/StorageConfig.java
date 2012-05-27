package com.nyverdenproduction.AmazoCreative.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.nyverdenproduction.AmazoCreative.AmazoCreative;
import com.nyverdenproduction.AmazoCreative.Item;
import com.nyverdenproduction.AmazoCreative.config.ValuesConfig.ACInfo;

public class StorageConfig
{
	private AmazoCreative plugin;
	private File file;
	private YamlConfiguration config;

	public StorageConfig(AmazoCreative plugin)
	{
		this.plugin = plugin;
		try
		{
			file = new File(plugin.getDataFolder().getAbsolutePath()
					+ "/storage.yml");
			if (!file.exists())
			{
				file.createNewFile();
			}
			config = YamlConfiguration.loadConfiguration(file);
		}
		catch (SecurityException s)
		{
			plugin.getLogger().warning("Cannot access storage file!");
			s.printStackTrace();
		}
		catch (IOException io)
		{
			plugin.getLogger().warning("Cannot create new storage file!");
			io.printStackTrace();
		}
	}

	public void save()
	{
		try
		{
			config.save(file);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void reload()
	{
		try
		{
			config.load(file);
		}
		catch (FileNotFoundException fnf)
		{
			fnf.printStackTrace();
		}
		catch (IOException io)
		{
			io.printStackTrace();
		}
		catch (InvalidConfigurationException ic)
		{
			ic.printStackTrace();
		}
	}
	
	public void resetPlayer(String name)
	{
		config.set(name, null);
		save();
	}

	public int getPlayerLimit(String name, Item item)
	{
		// Get path
		final ACInfo info = plugin.getConfigHandler().getValuesConfig().items
				.get(item);
		int limit = config.getInt(name + "." + info.path, 0);
		return limit;
	}

	public void setPlayerLimit(String name, Item item, int limit)
	{
		// Get path
		final ACInfo info = plugin.getConfigHandler().getValuesConfig().items
				.get(item);
		// Set limit
		config.set(name + "." + info.path, limit);
		// Save changes
		save();
	}
}
