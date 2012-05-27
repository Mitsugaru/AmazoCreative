package com.nyverdenproduction.AmazoCreative.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.nyverdenproduction.AmazoCreative.AmazoCreative;
import com.nyverdenproduction.AmazoCreative.Item;
import com.sk89q.worldguard.protection.ApplicableRegionSet;

public class ACBlockListener implements Listener
{
	private AmazoCreative plugin;

	public ACBlockListener(AmazoCreative plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlace(final BlockPlaceEvent event)
	{
		if (event.isCancelled() || event.getPlayer() == null || event.getBlockPlaced() == null)
		{
			return;
		}
		final Player player = event.getPlayer();
		final Item item = new Item(event.getBlock().getTypeId(), event.getBlock().getData(), (short) 0);
		// check if in applicable world
		if (!plugin.getConfigHandler().getRootConfig().worlds.contains(player
				.getWorld().getName()))
		{
			return;
		}
		/**
		 * Thanks to rmb938 for the following example on dealing with wg regions
		 * http
		 * ://forums.bukkit.org/threads/check-if-player-is-in-worldguard-region
		 * -error.73598/
		 */
		ApplicableRegionSet regionSet = plugin.getWorldGuard()
				.getRegionManager(player.getWorld())
				.getApplicableRegions(player.getLocation());
		plugin.getLogger().info("RegionSet size: " + regionSet.size());
		//Check if block is in config item list
		if(!plugin.getConfigHandler().getValuesConfig().items.containsKey(item))
		{
			return;
		}
		//TODO Check the player's current limit for the block
		//TODO deny if player is at / above limit
		//TODO increment player value if event is not denied
	}
}
