import java.util.TreeMap;


public class CameraGroupMgr
{
	
	public void addCameraGroup(String groupName)
	{
		if(groupName == null) return;
		
		GroupData foundGroup = groups.get(groupName);
		
		if(foundGroup == null)
		{
			foundGroup = new GroupData();
			foundGroup.adjSeconds = 0;
			foundGroup.mediaCount = 1;
			groups.put(groupName,  foundGroup);
		}
		else
		{
			foundGroup.mediaCount++;
		}
	}
	
	public static CameraGroupMgr getInstance()
	{
		if(instance == null)
		{
			instance = new CameraGroupMgr();
		}
		return instance;
	}
	
	public void reset()
	{
		groups = new TreeMap<String, GroupData>();
	}
	
	public void updateTime(String groupName, Integer value) throws CameraGroupNotFound
	{
		GroupData group = groups.get(groupName);
		if(group == null)
		{
			throw new CameraGroupNotFound(groupName);
		}
		
		group.adjSeconds = value.intValue();
	}
	
	public TreeMap<String, GroupData> getGroups()
	{
		return groups;
	}

	private CameraGroupMgr()
	{
		groups = new TreeMap<String, GroupData>();
	}
	
	private static CameraGroupMgr instance;
	
	private TreeMap<String, GroupData> groups;

	
}
