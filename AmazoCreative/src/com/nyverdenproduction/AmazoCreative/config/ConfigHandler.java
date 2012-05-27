package com.nyverdenproduction.AmazoCreative.config;

import com.nyverdenproduction.AmazoCreative.AmazoCreative;

public class ConfigHandler
{
	private RootConfig root;
	private StorageConfig storage;
	private ValuesConfig values;
	
	public ConfigHandler(AmazoCreative plugin)
	{
		root = new RootConfig(plugin);
		storage = new StorageConfig(plugin);
		values = new ValuesConfig(plugin);
	}
	
	public RootConfig getRootConfig()
	{
		return root;
	}
	
	public StorageConfig getStorageConfig()
	{
		return storage;
	}
	
	public ValuesConfig getValuesConfig()
	{
		return values;
	}
}
