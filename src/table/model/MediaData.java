package table.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;
import cameragroup.model.GroupData;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;

public class MediaData
{
	private static final int PICTURE_HEIGHT = 100;
	
	public enum TimeSource
	{
		FILE_NAME,
		EXIF,
		FILE_MTIME,
		UNKOWN
	}
	public MediaData(File _file) throws NotMedia
	{
		file = _file;

		initFileInfo();
		
		initExifInfo();
		
		initThumbnail();
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

	public BufferedImage getImage()
	{
		if(image == null)
		{
			if(isMovie()) return defaultMovieImage;
			return defaultImage;
		}
		
		return image;
	}

	public Calendar getCreationDate()
	{
		Calendar val = null;
		TimeSource t = getTimeSource();
		if(t == TimeSource.FILE_MTIME) val = mtime;
		if(t == TimeSource.FILE_NAME) val = fileNameTime;
		if(t == TimeSource.EXIF) val = exifDate;
		
		Calendar newGuy = null;
		if(val != null)
		{
			newGuy = Calendar.getInstance();
			newGuy.setTimeInMillis(val.getTimeInMillis() + (groupData.adjSeconds * 1000));
		}
		return newGuy;
	}

	public String getNewFileName()
	{
		Calendar date = getCreationDate();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd__HH-mm-ss");
		
		String form = sdf.format(date.getTime());
		
		return form;
	}

	public String getCameraModel()
	{
		if(ext.compareTo("mts") == 0)
		{
			return "Canon VIXIA HF200";
		}
		else if(ext.compareTo("mov") == 0)
		{
			return "NIKON D7000";
		}
		return cameraModel;
	}
	
	public void setGroupData(GroupData _groupData)
	{
		groupData = _groupData;
	}
	
	public GroupData getGroupData()
	{
		return groupData;
	}

	public File getFile()
	{
		return file;
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
						        || ext.compareTo("mts") == 0
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
	
	public void createThumbnail()
	{
		if(isImage())
		{
			try
			{
				BufferedImage imageFromFile;
				imageFromFile = ImageIO.read(file);
				double scale = (double) PICTURE_HEIGHT / (double) imageFromFile.getHeight();
				int newH = PICTURE_HEIGHT;
				int newW = (int) (imageFromFile.getWidth() * scale);
	
				image = new BufferedImage(newW, newH, imageFromFile.getType());
				Graphics2D g = image.createGraphics();
				g.drawImage(imageFromFile, 0, 0, newW, newH, null);
				g.dispose();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void initExifInfo()
	{
		if(isJpg())
		{
			try
			{
				//ClassNotFoundException indicates xmpcore is not in classpath
				// jar can be found in metadata-extrator zip file
				Metadata metadata = ImageMetadataReader.readMetadata(file);
				Directory dateDir = metadata.getDirectory(ExifSubIFDDirectory.class);
				if(dateDir != null)
				{
					Date date = dateDir.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);

					if(date != null)
					{
						exifDate = Calendar.getInstance();
						exifDate.setTime(date);
					}
				}

				Directory modelDir = metadata.getDirectory(ExifIFD0Directory.class);
				if(modelDir != null)
				{
					cameraModel = modelDir.getString(ExifIFD0Directory.TAG_MODEL);
				}
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

	private void initFileInfo() throws NotMedia
	{
		ext = FilenameUtils.getExtension(file.getAbsolutePath()).toLowerCase();
		
		if(! isMedia())
		{
			throw new NotMedia();
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
		String fileNameNoExt = FilenameUtils.getBaseName(file.getAbsolutePath());
		Pattern p = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}__[0-9]{2}-[0-9]{2}-[0-9]{2}.*");
		Matcher m = p.matcher(fileNameNoExt);
		if(m.matches())
		{
			String[] pieces = fileNameNoExt.split("[-|_| ]");
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
					System.err.println(e.getMessage());
					System.err.println(fileNameNoExt);
				}
			}
		}
	}
	
	private void initThumbnail()
	{
		if(defaultImage == null)
		{
			int newH = PICTURE_HEIGHT;
			int newW = PICTURE_HEIGHT;
			
			defaultImage = new BufferedImage(newW, newH, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = defaultImage.createGraphics();
			g.setColor(Color.BLUE);
			g.drawLine(newW, 0, 0, newH);
			g.setColor(Color.RED);
			g.drawLine(newW, newH, 0, 0);
			g.dispose();
		}
		
		if(defaultMovieImage == null)
		{
			int newH = PICTURE_HEIGHT;
			int newW = PICTURE_HEIGHT;
			
			defaultMovieImage = new BufferedImage(newW, newH, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = defaultMovieImage.createGraphics();
			g.setColor(Color.BLUE);
			g.drawLine(0, newH / 3, newW, newH / 3);
			g.setColor(Color.RED);
			g.drawLine(0, newH * 2 / 3, newW, newH * 2 / 3);
			g.setColor(Color.GREEN);
			g.drawString("MOVIE", 0, PICTURE_HEIGHT / 2);
			g.dispose();
		}
	}
	
	private File file;
	private String ext;
	private Calendar exifDate;
	private Calendar mtime;
	private Calendar fileNameTime;
	private String cameraModel;
	private BufferedImage image;
	private GroupData groupData;
	private static BufferedImage defaultImage;
	private static BufferedImage defaultMovieImage;


}
