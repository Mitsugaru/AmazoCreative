package com.nyverdenproduction.AmazoCreative.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.nyverdenproduction.AmazoCreative.AmazoCreative;
import com.nyverdenproduction.AmazoCreative.Item;

public class ValuesConfig
{
	private File file;
	private YamlConfiguration config;
	private Map<Item, ACInfo> items = new HashMap<Item, ACInfo>();

	public ValuesConfig(AmazoCreative plugin)
	{
		try
		{
			file = new File(plugin.getDataFolder().getAbsolutePath()
					+ "/values.yml");
			if (!file.exists())
			{
				file.createNewFile();
			}
			config = YamlConfiguration.loadConfiguration(file);
			loadDefaults();
			loadSettings();
			loadItems();
		}
		catch (SecurityException s)
		{
			plugin.getLogger().warning("Cannot access values file!");
			s.printStackTrace();
		}
		catch (IOException io)
		{
			plugin.getLogger().warning("Cannot create new values file!");
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
		loadItems();
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
	
	private void loadItems()
	{
		// load items from config
		for (final String entry : config.getKeys(false))
		{
			try
			{
				// Attempt to parse non data value nodes
				int key = Integer.parseInt(entry);
				if (key <= 0)
				{
					// plugin.getLogger().warning(
					// Karmiconomy.TAG
					// + " Zero or negative item id for entry: "
					// + entry);
				}
				else
				{
					items.put(new Item(key, Byte.parseByte("" + 0), (short) 0),
							parseInfo(entry));
				}
			}
			catch (final NumberFormatException ex)
			{
				// Potential data value entry
				if (entry.contains("&"))
				{
					try
					{
						final String[] split = entry.split("&");
						final int item = Integer.parseInt(split[0]);
						final int data = Integer.parseInt(split[1]);
						if (item <= 0)
						{
							// plugin.getLogger()
							// .warning(
							// Karmiconomy.TAG
							// + " Zero or negative item id for entry: "
							// + entry);
						}
						else
						{
							if (item != 373)
							{
								items.put(
										new Item(item, Byte
												.parseByte("" + data),
												(short) data), parseInfo(entry));
							}
							else
							{
								items.put(new Item(item,
										Byte.parseByte("" + 0), (short) data),
										parseInfo(entry));
							}
						}
					}
					catch (ArrayIndexOutOfBoundsException a)
					{
						// plugin.getLogger()
						// .warning(
						// "Wrong format for "
						// + entry
						// + ". Must follow '<itemid>&<datavalue>:' entry.");
					}
					catch (NumberFormatException exa)
					{
						// plugin.getLogger().warning(
						// "Non-integer number for " + entry);
					}
				}
				else
				{
					// plugin.getLogger().warning("Invalid entry for " + entry);
				}
			}
		}
	}
	
	private ACInfo parseInfo(final String path)
	{
		return new ACInfo();
	}
	
	private class ACInfo
	{
		
	}
}
