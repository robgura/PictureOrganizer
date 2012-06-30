import java.io.File;
import java.util.Observable;


public class CurrentDirectoryMgr extends Observable
{
	public static CurrentDirectoryMgr Get()
	{
		if(single == null)
		{
			single = new CurrentDirectoryMgr();
		}
		return single;
	}

	public File getFile()
	{
		return file;
	}

	public void setFile(File f)
	{
		
		file = f;
		this.setChanged();
		notifyObservers(f);
	}

	private File file;
	
	private static CurrentDirectoryMgr single = null;

	private CurrentDirectoryMgr()
	{
	}

}
