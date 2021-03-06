package table.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;
import cameragroup.model.GroupData;


@SuppressWarnings("serial")
public class MediaTableModel extends AbstractTableModel
{
	public static final int FILE_NAME_COLUMN = 0;
	public static final int GROUP_COLUMN = 1;
	public static final int IMAGE_COLUMN = 2;
	public static final int MODEL_COLUMN = 3;
	public static final int DATE_COLUMN = 4;
	
	public static final int COLUMN_COUNT = 5;
	
	public MediaTableModel()
	{
		groupings = new ArrayList<IGrouping>();
	}
	
	public void addGrouping(IGrouping grouping)
	{
		groupings.add(grouping);
	}

	@Override
	public int getColumnCount()
	{
		return COLUMN_COUNT;
	}

	@Override
	public String getColumnName(int column)
	{
		if(column == FILE_NAME_COLUMN)
		{
			return "File Name";
		}
		
		if(column == GROUP_COLUMN)
		{
			return "Group";
		}
		
		if(column == MODEL_COLUMN)
		{
			return "Model";
		}
			
		if(column == DATE_COLUMN)
		{
			return "Date";
		}
		
		if(column == IMAGE_COLUMN)
		{
			return "Image";
		}
		
		return super.getColumnName(column);
	}

	@Override
	public int getRowCount()
	{
		if(mediaDatas == null)
		{
			return 0;
		}
		return mediaDatas.size();
	}

	@Override
	public Object getValueAt(int row, int column)
	{
		MediaData data = mediaDatas.get(row);
		
		if(column == FILE_NAME_COLUMN)
		{
			return data.getFile().getAbsolutePath();
		}
		
		if(column == IMAGE_COLUMN)
		{
			return new ImageIcon(data.getImage());
		}
		
		if(column == GROUP_COLUMN)
		{
			return data.getGroupData().getName();
		}
		
		if(column == MODEL_COLUMN)
		{
			return data.getCameraModel();
		}
		
		if(column == DATE_COLUMN)
		{
			return data.getCreationDate();
		}
		
		if(column == 5)
		{
			return data.getNewFileName();
		}
		
		return Integer.toString(row + 1) + " - " + Integer.toString(column + 1);
	}
	

	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		if(columnIndex == IMAGE_COLUMN)
		{
			return ImageIcon.class;
		}
		
		if(columnIndex == DATE_COLUMN)
		{
			return Calendar.class;
		}
		
		return super.getColumnClass(columnIndex);
	}

	public void readDirectory(File newDir)
	{
		loadDirectoryInfo(newDir);
		fireTableDataChanged();
	}
	
	public void updateGroup(String name)
	{
		int i = 0;
		for(MediaData mediaData :  mediaDatas)
		{
			if(mediaData.getGroupData().getName().compareTo(name) == 0)
			{
				this.fireTableRowsUpdated(i,  i);
			}
			++i;
		}
	}

	public void showThumbnail(int row)
	{
		MediaData mediaData = mediaDatas.get(row);
		mediaData.createThumbnail();
		this.fireTableCellUpdated(row, IMAGE_COLUMN);
	}

	public void changeGroup(int row, GroupData groupData)
	{
		MediaData mediaData = mediaDatas.get(row);
		if(mediaData.getGroupData() != groupData)
		{
			mediaData.setGroupData(groupData);
			this.fireTableRowsUpdated(row,  row);
		}
	}

	public ArrayList<MediaData> getMediaDatas()
	{
		return mediaDatas;
	}
	
	private void addToGroups(MediaData mediaData)
	{
		for(IGrouping g : groupings)
		{
			g.AddNewMediaDataToGroup(mediaData);
		}
	}

	private void loadDirectoryInfo(File directory)
	{
		HackFileNameExtensionFilter filter = new HackFileNameExtensionFilter("Media", "jpg", "jpeg", "avi", "mts", "mpg", "mpeg", "mov");
		File[] mediaFiles = directory.listFiles(filter);
		
		mediaDatas = new ArrayList<MediaData>(mediaFiles.length);
		
		for(int i = 0; i < mediaFiles.length; ++i)
		{
			try
			{
				MediaData mediaData = new MediaData(mediaFiles[i]);
				mediaDatas.add(mediaData);
				addToGroups(mediaData);
			}
			catch (NotMedia e)
			{
				System.err.println("Found non media type " + mediaFiles[i].getAbsolutePath());
			}
		}
	}

	private ArrayList<MediaData> mediaDatas;
	//private CameraGroupMgr cameraGroupMgr;
	private ArrayList<IGrouping> groupings;

}
