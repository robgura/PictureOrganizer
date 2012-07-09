import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;


@SuppressWarnings("serial")
public class MediaTableModel extends AbstractTableModel
{
	public static final int FILE_NAME_COLUMN = 0;
	public static final int GROUP_COLUMN = 1;
	public static final int IMAGE_COLUMN = 2;
	public static final int MODEL_COLUMN = 3;
	public static final int DATE_COLUMN = 4;
	
	public static final int COLUMN_COUNT = 5;
	
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
			return data.getFileName();
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
			//return new Date(data.getCreationDate().getTimeInMillis());
			//return data.getCreationDate().getTime();
			return data.getCreationDate();
		}
		
		if(column == 5)
		{
			//return data.getTimeSource();
			return new Long(data.getCreationDate().getTimeInMillis());
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
				//this.fireTableCellUpdated(i, DATE_COLUMN);
				this.fireTableRowsUpdated(i,  i);
			}
			++i;
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
				GroupData groupData = CameraGroupMgr.getInstance().addCameraGroup(mediaData.getCameraModel());
				mediaData.setGroupData(groupData);
			}
			catch (NotMedia e)
			{
				System.err.println("Found non media type " + mediaFiles[i].getAbsolutePath());
			}
		}
	}

	private ArrayList<MediaData> mediaDatas;

}
