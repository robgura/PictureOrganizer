import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import com.drew.imaging.ImageProcessingException;


@SuppressWarnings("serial")
public class MediaTableModel extends AbstractTableModel implements Observer
{

	private int rowCount;
	private ArrayList<MediaData> mediaDatas;
	
	MediaTableModel()
	{
		CurrentDirectoryMgr.Get().addObserver(this);
		rowCount = 10;
	}
	
	@Override
	public int getColumnCount()
	{
		return 5;
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
		
		if(column == 0)
		{
			return data.getPath();
		}
		if(column == 1)
		{
			return data.getFileNameNoExt();
		}
		
		if(column == 2)
		{
			return data.getExt();
		}
		
		if(column == 3)
		{
			return data.getCameraModel();
		}
		
		if(column == 4)
		{
			return data.getCreationDate();
		}
		
		if(column == 5)
		{
			return data.getTimeSource();
		}
		
		return Integer.toString(row + 1) + " - " + Integer.toString(column + 1);
	}
	

	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		if(columnIndex == 5)
		{
			return GregorianCalendar.class;
		}
		
		return super.getColumnClass(columnIndex);
	}

	@Override
	public void update(Observable arg0, Object objNewDir)
	{
		File newDir = (File) objNewDir;
		loadDirectoryInfo(newDir);
		fireTableDataChanged();
	}
	
	private void loadDirectoryInfo(File directory)
	{
		HackFileNameExtensionFilter filter = new HackFileNameExtensionFilter("Media", "jpg", "avi", "mts");
		File[] mediaFiles = directory.listFiles(filter);
		
		mediaDatas = new ArrayList<MediaData>(mediaFiles.length);
		
		for(int i = 0; i < mediaFiles.length; ++i)
		{
			try
			{
				mediaDatas.add(new MediaData(mediaFiles[i]));
			}
			catch (NotMedia e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
