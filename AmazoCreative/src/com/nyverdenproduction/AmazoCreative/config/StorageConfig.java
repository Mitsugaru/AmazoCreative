package com.nyverdenproduction.AmazoCreative.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.nyverdenproduction.AmazoCreative.AmazoCreative;

public class StorageConfig
{
	private File file;
	private YamlConfiguration config;

	public StorageConfig(AmazoCreative plugin)
	{
		try
		{
			file = new File(plugin.getDataFolder().getAbsolutePath()
					+ "/storage.yml");
			if (!file.exists())
			{
				file.createNewFile();
			}
			config = YamlConfiguration.loadConfiguration(file);
			loadDefaults();
			loadSettings();
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
		loadDefaults();
		loadSettings();
	}

	private void loadDefaults()
	{
		// Defaults
		final Map<String, Object> defaults = new LinkedHashMap<String, Object>();
		// TODO defaults
		// Insert defaults into config file if they're not present
		for (final Entry<String, Object> e : defaults.entrySet())
		{
			if (!config.contains(e.getKey()))
			{
				config.set(e.getKey(), e.getValue());
			}
		}
		save();
	}

	private void loadSettings()
	{

	}
}
