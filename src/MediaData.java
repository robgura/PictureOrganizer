import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;

public class MediaData
{
	public enum TimeSource
	{
		FILE_NAME,
		EXIF,
		FILE_MTIME,
		UNKOWN
	}
	public MediaData(File file) throws NotMedia
	{
		initFileInfo(file);
		
		initExifInfo(file);
	}

	public String getExt()
	{
		return ext;
	}

	public TimeSource getTimeSource()
	{
		if(fileNameTime != null) return TimeSource.FILE_NAME;
		if(exifDate != null) return TimeSource.EXIF;
		if(mtime != null) return TimeSource.FILE_MTIME;
		return TimeSource.UNKOWN;
	}

	public Calendar getCreationDate()
	{
		TimeSource t = getTimeSource();
		if(t == TimeSource.FILE_MTIME) return mtime;
		if(t == TimeSource.FILE_NAME) return fileNameTime;
		if(t == TimeSource.EXIF) return exifDate;
		return null;
	}

	public String getCameraModel()
	{
		return cameraModel;
	}

	public String getPath()
	{
		return path;
	}

	public boolean isMedia()
	{
		return isMovie() || isImage();
	}

	public boolean isMovie()
	{
		return ext != null && (    ext.compareTo("mpg") == 0 
						        || ext.compareTo("avi") == 0
						        || ext.compareTo("mpeg") == 0
						        || ext.compareTo("mov") == 0
						      );
	}

	public boolean isImage()
	{
		return isJpg();
	}

	public boolean isJpg()
	{
		return ext != null && (ext.compareTo("jpg") == 0 || ext.compareTo("jpeg") == 0);
	}
	
	public String getFileNameNoExt()
	{
		return fileNameNoExt;
	}
	
	private void initExifInfo(File jpgFile)
	{
		if(isJpg())
		{
			try
			{
				//ClassNotFoundException indicates xmpcore is not in classpath
				// jar can be found in metadata-extrator zip file
				Metadata metadata = ImageMetadataReader.readMetadata(jpgFile);
				Directory dateDir = metadata.getDirectory(ExifSubIFDDirectory.class);
				Date date = dateDir.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
				
				if(date != null)
				{
					exifDate = Calendar.getInstance();
					exifDate.setTime(date);
				}
				
				Directory modelDir = metadata.getDirectory(ExifIFD0Directory.class);
				cameraModel = modelDir.getString(ExifIFD0Directory.TAG_MODEL);
			}
			catch (ImageProcessingException e)
			{
				System.err.println("1 ImageProcessingException");
				e.printStackTrace();
			}
			catch (IOException e)
			{
				System.err.println("2 IOException");
				e.printStackTrace();
			}
		}
	}

	private void initFileInfo(File file) throws NotMedia
	{
		path = file.getAbsolutePath();
		int dot = path.lastIndexOf(".");
		if(dot != -1)
		{
			ext = path.substring(dot + 1, path.length()).toLowerCase();
			
			if(! isMedia())
			{
				throw new NotMedia();
			}
	
			int slash = path.lastIndexOf(File.separator);
			if(slash != -1)
			{
				fileNameNoExt = path.substring(slash + 1, dot);
			}
		}
		
		getDateFromModifiedTime(file);
		
		getDateFromFileName();
	}

	private void getDateFromModifiedTime(File file)
	{
		mtime = Calendar.getInstance();
		mtime.setTimeInMillis(file.lastModified());
	}

	private void getDateFromFileName()
	{
		Pattern p = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}__[0-9]{2}-[0-9]{2}-[0-9]{2}.*");
		Matcher m = p.matcher(fileNameNoExt);
		if(m.matches())
		{
			String[] pieces = fileNameNoExt.split("[-|_]");
			if(pieces.length >= 7)
			{
				try{
				fileNameTime = Calendar.getInstance();
				fileNameTime.set( Integer.parseInt(pieces[0])		// year
								, Integer.parseInt(pieces[1]) - 1	// month
								, Integer.parseInt(pieces[2])       // day
								, Integer.parseInt(pieces[4])       // hour (skip one since there are two underscores
								, Integer.parseInt(pieces[5])       // minute
								, Integer.parseInt(pieces[6])       // second
							    );
				}
				catch (NumberFormatException e)
				{
					System.out.println(e.getMessage());
					System.out.println(fileNameNoExt);
				}
			}
		}
	}

	private String path;
	private String fileNameNoExt;
	private String ext;
	private Calendar exifDate;
	private Calendar mtime;
	private Calendar fileNameTime;
	private String cameraModel;


}
