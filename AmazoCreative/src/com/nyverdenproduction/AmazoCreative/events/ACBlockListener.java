package com.nyverdenproduction.AmazoCreative.events;

import org.bukkit.ChatColor;
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
		//Check if they're in an existing region. If so, ignore.
		if(regionSet.size() > 0)
		{
			return;
		}
		//Check if block is in config item list
		if(!plugin.getConfigHandler().getValuesConfig().items.containsKey(item))
		{
			return;
		}
		//Check the player's current limit for the block
		int limit = plugin.getConfigHandler().getStorageConfig().getPlayerLimit(player.getName(), item);
		if(limit >= plugin.getConfigHandler().getValuesConfig().items.get(item).limit)
		{
			//deny if player is at / above limit
			event.setCancelled(true);
			//TODO send message to player
			player.sendMessage(ChatColor.RED + AmazoCreative.TAG + " Hit limit for item!");
		}
		else
		{
			// increment player value if event is not denied
			plugin.getConfigHandler().getStorageConfig().setPlayerLimit(player.getName(), item, ++limit);
		}
		
	}
}
