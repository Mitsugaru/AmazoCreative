package com.nyverdenproduction.AmazoCreative.permissions;

public enum PermissionNode
{
	ADMIN(".admin"), IGNORE(".ignore");
	private static final String prefix = "AmazoCreative";
	private String node;

	private PermissionNode(String node)
	{
		this.node = prefix + node;
	}
	
	public String getNode()
	{
		return node;
	}

}
