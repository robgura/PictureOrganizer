import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JProgressBar;
import javax.swing.table.AbstractTableModel;


@SuppressWarnings("serial")
public class MediaTableModel extends AbstractTableModel implements Observer
{

	public static final int PATH_COLUMN = 0;
	public static final int MODEL_COLUMN = 3;
	public static final int DATE_COLUMN = 4;
	public static final int IMAGE_COLUMN = 1;
	
	private ArrayList<MediaData> mediaDatas;
	
	private JProgressBar progressBar;
	
	MediaTableModel(JProgressBar _progressBar)
	{
		progressBar = _progressBar;
		CurrentDirectoryMgr.Get().addObserver(this);
	}
	
	@Override
	public int getColumnCount()
	{
		return 6;
	}

	@Override
	public String getColumnName(int column)
	{
		if(column == PATH_COLUMN)
		{
			return "Source Path";
		}
		
		if(column == MODEL_COLUMN)
		{
			return "Model";
		}
			
		if(column == DATE_COLUMN)
		{
			return "Date";
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
		
		if(column == PATH_COLUMN)
		{
			return data.getPath();
		}
		if(column == IMAGE_COLUMN)
		{
			return new ImageIcon(data.getImage());
		}
		
		if(column == 2)
		{
			return data.getExt();
		}
		
		if(column == MODEL_COLUMN)
		{
			return data.getCameraModel();
		}
		
		if(column == DATE_COLUMN)
		{
			return new Date(data.getCreationDate().getTimeInMillis());
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
		if(columnIndex == IMAGE_COLUMN)
		{
			return ImageIcon.class;
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
		HackFileNameExtensionFilter filter = new HackFileNameExtensionFilter("Media", "jpg", "jpeg", "avi", "mts", "mpg", "mpeg");
		File[] mediaFiles = directory.listFiles(filter);
		
		progressBar.setValue(0);
		progressBar.setMinimum(0);
		progressBar.setMaximum(mediaFiles.length);
		progressBar.setStringPainted(true);
		
		mediaDatas = new ArrayList<MediaData>(mediaFiles.length);
		
		for(int i = 0; i < mediaFiles.length; ++i)
		{
			try
			{
				mediaDatas.add(new MediaData(mediaFiles[i]));
			}
			catch (NotMedia e)
			{
				System.out.println("Found non media type " + mediaFiles[i].getAbsolutePath());
			}
			progressBar.setValue(i);
			System.out.println(progressBar);
		}
	}

}
