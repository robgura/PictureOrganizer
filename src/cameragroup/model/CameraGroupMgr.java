package cameragroup.model;

import java.util.TreeMap;
import table.model.IGrouping;
import table.model.MediaData;


public class CameraGroupMgr implements IGrouping
{
	public CameraGroupMgr()
	{
		groups = new TreeMap<String, GroupData>();
	}

	/**
	 * adds a new GroupData to the group name if it doesn't already exist, returns indexed one if it already does exist
	 * 
	 * @param groupName
	 * @return
	 */
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
			groups.put(groupName,  foundGroup);
		}
		
		return foundGroup;
	}
	
	public TreeMap<String, GroupData> getGroups()
	{
		return groups;
	}

	public void reset()
	{
		groups = new TreeMap<String, GroupData>();
	}
	
	private TreeMap<String, GroupData> groups;

	@Override
	public void AddNewMediaDataToGroup(MediaData media)
	{
		GroupData groupData = addCameraGroup(media.getCameraModel());
		media.setGroupData(groupData);
	}
}
