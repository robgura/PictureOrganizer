import java.util.TreeMap;


public class CameraGroupMgr
{
	
	public GroupData addCameraGroup(String groupName)
	{
		if(groupName == null)
		{
			groupName = "UNKOWN";
		}
		
		GroupData foundGroup = groups.get(groupName);
		
		if(foundGroup == null)
		{
			foundGroup = new GroupData(groupName);
			foundGroup.adjSeconds = 0;
			foundGroup.mediaCount = 1;
			groups.put(groupName,  foundGroup);
		}
		else
		{
			foundGroup.mediaCount++;
		}
		
		return foundGroup;
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
