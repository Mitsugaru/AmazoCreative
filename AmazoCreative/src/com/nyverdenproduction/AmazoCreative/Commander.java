package com.nyverdenproduction.AmazoCreative;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.nyverdenproduction.AmazoCreative.permissions.PermCheck;
import com.nyverdenproduction.AmazoCreative.permissions.PermissionNode;

public class Commander implements CommandExecutor
{
	// Class variables
	private final AmazoCreative plugin;
	private final PermCheck perm;
	private final static String bar = "======================";
	private long time = 0;

	public Commander(AmazoCreative plugin)
	{
		this.plugin = plugin;
		this.perm = plugin.getPermissionsHandler();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args)
	{
		if (plugin.getConfigHandler().getRootConfig().debugTime)
		{
			time = System.nanoTime();
		}
		// See if any arguments were given
		if (args.length == 0)
		{
			// Check if they have "karma" permission
			this.displayHelp(sender);
		}
		else
		{
			final String com = args[0].toLowerCase();
			if (com.equals("version") || com.equals("ver"))
			{
				// Version and author
				return showVersion(sender, args);
			}
			else if (com.equals("?") || com.equals("help"))
			{
				return displayHelp(sender);
			}
			else if (com.equals("reload"))
			{
				if (!perm.checkPermission(sender, PermissionNode.ADMIN_RELOAD))
				{
					sender.sendMessage(ChatColor.RED + AmazoCreative.TAG
							+ " Lack permission: "
							+ PermissionNode.ADMIN_RELOAD);
					return true;
				}
				plugin.getConfigHandler().reload();
				sender.sendMessage(ChatColor.GREEN + AmazoCreative.TAG
						+ " Reloaded config");
				return true;
			}
			else if (com.equals("reset"))
			{
				return resetPlayer(sender, args);
			}
			else
			{
				sender.sendMessage(ChatColor.RED + AmazoCreative.TAG
						+ " Unknown command '" + ChatColor.AQUA + com
						+ ChatColor.RED + "'");
			}
		}
		if (plugin.getConfigHandler().getRootConfig().debugTime)
		{
			debugTime(sender, time);
		}
		return false;
	}

	private boolean resetPlayer(CommandSender sender, String[] args)
	{
		if (perm.checkPermission(sender, PermissionNode.ADMIN_RESET))
		{
			try
			{
				final String playerName = args[1];
				// TODO reset player limits
				plugin.getConfigHandler().getStorageConfig()
						.resetPlayer(playerName);
				sender.sendMessage(ChatColor.GREEN + AmazoCreative.TAG
						+ " Player '" + ChatColor.GOLD + playerName
						+ ChatColor.GREEN + "' limits reset.");
			}
			catch (ArrayIndexOutOfBoundsException aioob)
			{
				sender.sendMessage(ChatColor.RED + AmazoCreative.TAG
						+ " Market name not given.");
			}
		}
		else
		{
			sender.sendMessage(ChatColor.RED + AmazoCreative.TAG
					+ " Lack Permission: "
					+ PermissionNode.ADMIN_RESET.getNode());
		}
		return true;
	}

	private boolean showVersion(CommandSender sender, String[] args)
	{
		sender.sendMessage(ChatColor.BLUE + bar + "=====");
		sender.sendMessage(ChatColor.GREEN + "AmazoCreative v"
				+ plugin.getDescription().getVersion());
		sender.sendMessage(ChatColor.GREEN + "Coded by Mitsugaru");
		sender.sendMessage(ChatColor.BLUE + "===========" + ChatColor.GRAY
				+ "Config" + ChatColor.BLUE + "===========");
		if (plugin.getConfigHandler().getRootConfig().debugTime)
			sender.sendMessage(ChatColor.GRAY + "Debug time: "
					+ plugin.getConfigHandler().getRootConfig().debugTime);
		return true;
	}

	/**
	 * Show the help menu, with commands and description
	 * 
	 * @param sender
	 *            to display to
	 */
	private boolean displayHelp(CommandSender sender)
	{
		sender.sendMessage(ChatColor.GREEN + "==========" + ChatColor.WHITE
				+ "AmazoCreative" + ChatColor.GREEN + "==========");
		if (perm.checkPermission(sender, PermissionNode.ADMIN_RESET))
		{
			sender.sendMessage(ChatColor.BLUE + "/ac reset"
					+ ChatColor.LIGHT_PURPLE + " [player]" + ChatColor.WHITE
					+ " : Reloads configs");
		}
		if (perm.checkPermission(sender, PermissionNode.ADMIN_RELOAD))
		{
			sender.sendMessage(ChatColor.BLUE + "/ac reload" + ChatColor.WHITE
					+ " : Reloads configs");
		}
		sender.sendMessage(ChatColor.BLUE + "/ac help" + ChatColor.WHITE
				+ " : Show help menu");
		sender.sendMessage(ChatColor.BLUE + "/ac version" + ChatColor.WHITE
				+ " : Show version and about");
		return true;
	}

	private void debugTime(CommandSender sender, long time)
	{
		time = System.nanoTime() - time;
		sender.sendMessage("[Debug]" + AmazoCreative.TAG + "Process time: "
				+ time);
	}

}
