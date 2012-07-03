import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileFilter;


public class HackFileNameExtensionFilter implements FileFilter
{
	private FileNameExtensionFilter realFilter;

	public HackFileNameExtensionFilter(String desc, String... extensions)
	{
		realFilter = new FileNameExtensionFilter(desc, extensions);
	}

	@Override
	public boolean accept(File file)
	{
		if(file.isFile())
		{
			return realFilter.accept(file);
		}
		return false;
	}

}
