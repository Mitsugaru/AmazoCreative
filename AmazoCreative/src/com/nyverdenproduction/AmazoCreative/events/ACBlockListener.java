package com.nyverdenproduction.AmazoCreative.events;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.nyverdenproduction.AmazoCreative.AmazoCreative;
import com.nyverdenproduction.AmazoCreative.Item;
import com.nyverdenproduction.AmazoCreative.permissions.PermissionNode;
import com.sk89q.worldguard.protection.ApplicableRegionSet;

public class ACBlockListener implements Listener
{
	private AmazoCreative plugin;

	public ACBlockListener(AmazoCreative plugin)
	{
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockDestroy(final BlockBreakEvent event)
	{
		if (event.isCancelled() || event.getPlayer() == null
				|| event.getBlock() == null)
		{
			return;
		}
		final Player player = event.getPlayer();
		// Check for ignore permission node
		if (plugin.getPermissionsHandler().checkPermission(player,
				PermissionNode.IGNORE))
		{
			return;
		}
		// check if in applicable world
		if (!plugin.getConfigHandler().getRootConfig().worlds.contains(player
				.getWorld().getName()))
		{
			return;
		}
		if (plugin.getConfigHandler().getRootConfig().debugEvents)
		{
			plugin.getLogger().info(
					"valid world: " + player.getWorld().getName());
		}
		final ApplicableRegionSet regionSet = plugin.getWorldGuard()
				.getRegionManager(event.getBlock().getWorld())
				.getApplicableRegions(event.getBlock().getLocation());
		if (plugin.getConfigHandler().getRootConfig().debugEvents)
		{
			plugin.getLogger().info("regionSet size = " + regionSet.size());
		}
		// Check if its not in a region
		if (regionSet.size() <= 0)
		{
			// Deny
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED + AmazoCreative.TAG
					+ " Cannot destroy outside of a region.");
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlace(final BlockPlaceEvent event)
	{
		if (event.isCancelled() || event.getPlayer() == null
				|| event.getBlockPlaced() == null)
		{
			return;
		}
		final Player player = event.getPlayer();
		// Check for ignore permission node
		if (plugin.getPermissionsHandler().checkPermission(player,
				PermissionNode.IGNORE))
		{
			return;
		}
		final Item item = new Item(event.getBlock().getTypeId(), event
				.getBlock().getData(), (short) 0);
		// check if in applicable world
		if (!plugin.getConfigHandler().getRootConfig().worlds.contains(player
				.getWorld().getName()))
		{
			return;
		}
		if (plugin.getConfigHandler().getRootConfig().debugEvents)
		{
			plugin.getLogger().info(
					"valid world: " + player.getWorld().getName());
		}
		/**
		 * Thanks to rmb938 for the following example on dealing with wg regions
		 * http ://forums.bukkit.org/threads/check-if-player-is-in-worldguard-
		 * region -error.73598/
		 */
		final Block placed = event.getBlockPlaced();
		final ApplicableRegionSet regionSet = plugin.getWorldGuard()
				.getRegionManager(placed.getWorld())
				.getApplicableRegions(placed.getLocation());
		if (plugin.getConfigHandler().getRootConfig().debugEvents)
		{
			plugin.getLogger().info("regionSet size = " + regionSet.size());
		}
		// Check if its not in a region
		if (regionSet.size() <= 0)
		{
			// Check if block is in config item list
			if (!plugin.getConfigHandler().getValuesConfig().items
					.containsKey(item))
			{
				// Deny
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + AmazoCreative.TAG
						+ " Must build inside a region.");
				return;
			}
			else
			{
				// Check the player's current limit for the block
				int limit = plugin.getConfigHandler().getStorageConfig()
						.getPlayerLimit(player.getName(), item);
				if (limit >= plugin.getConfigHandler().getValuesConfig().items
						.get(item).limit)
				{
					// deny if player is at / above limit
					event.setCancelled(true);
					// send message to player
					player.sendMessage(ChatColor.RED + AmazoCreative.TAG
							+ " Hit limit for item!");
					if (plugin.getConfigHandler().getRootConfig().debugEvents)
					{
						plugin.getLogger().info(
								player.getName() + " hit limit: "
										+ item.getItemType().name() + " : "
										+ item.getData());
					}
				}
				else
				{
					// increment player value if event is not denied
					plugin.getConfigHandler().getStorageConfig()
							.setPlayerLimit(player.getName(), item, ++limit);
				}
			}
		}
	}
}
