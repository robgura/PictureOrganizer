
package cameragroup.model;

public class GroupData
{
	public long adjSeconds = 0;
	public int mediaCount = 0;
	private String name;
	public String getName()
	{
		return name;
	}
	
	public GroupData(String name)
	{
		this.name = name;
	}
}
