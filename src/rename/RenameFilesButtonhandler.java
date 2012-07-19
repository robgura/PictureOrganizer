package rename;

import org.apache.commons.io.FilenameUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;
import table.model.MediaData;
import table.model.MediaTableModel;

public class RenameFilesButtonhandler implements ActionListener
{
	public RenameFilesButtonhandler(MediaTableModel mediaModel)
	{
		this.mediaModel = mediaModel;
	}

	private class RenameInfo
	{
		public File orig;
		public File newName;
		public int index = 0;
		
		@Override
		
		public String toString()
		{
			if(orig == null) return "orig null";
			if(newName == null) return "newName null";
			
			return "\nORIG " + orig.getAbsolutePath() + "\nNEW  " + newName.getAbsolutePath() + " "  + index;
		}
	}
	
	private class FileCompares implements Comparator<RenameInfo>
	{

		@Override
		public int compare(RenameInfo left, RenameInfo rite)
		{
			int val = left.newName.compareTo(rite.newName);
			
			if(val == 0)
			{
				val = left.orig.compareTo(rite.orig);
			}
			
			return val;
		}
	}
	
	private class IndexCompares implements Comparator<RenameInfo>
	{

		@Override
		public int compare(RenameInfo left, RenameInfo rite)
		{
			int val = left.newName.compareTo(rite.newName);
			
			if(val == 0)
			{
				val = left.index - rite.index;
			}
			
			return val;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		ArrayList<MediaData> mediaDatas = mediaModel.getMediaDatas();
		if(mediaModel == null || mediaDatas == null) return;
		
		TreeSet<RenameInfo> renameInfo = new TreeSet<RenameInfo>(new FileCompares());
		
		for(MediaData media : mediaDatas)
		{
			RenameInfo ri = new RenameInfo();
			
			ri.orig = media.getFile();
			
			String path = FilenameUtils.getFullPath(media.getFile().getAbsolutePath());
			String ext = media.getExt();
			
			ri.newName = new File(path + media.getNewFileName() + "." + ext);
			
			renameInfo.add(ri);
		}
		
		TreeSet<RenameInfo> usedRI = new TreeSet<RenameInfo>(new IndexCompares());
		
		for(RenameInfo ri : renameInfo)
		{
			while(usedRI.contains(ri))
			{
				++ri.index;
			}
			
			usedRI.add(ri);
		}
		
		for(RenameInfo ri : usedRI)
		{
			String newName = ri.newName.getAbsolutePath();
			if(ri.index != 0)
			{
				String path = FilenameUtils.getFullPath(newName);
				String ext = FilenameUtils.getExtension(newName);
				String base = FilenameUtils.getBaseName(newName);
				
				newName = path + base + "-" + Integer.toHexString(ri.index) + "." + ext; 
			}
			
			ri.newName = new File(newName);
			
			boolean suc = ri.orig.renameTo(ri.newName);
			
			if(! suc)
			{
				System.err.println("Could not rename " + ri.orig.getAbsolutePath() + " to " + ri.newName.getAbsolutePath());
			}
		}
	}
	
	MediaTableModel mediaModel;

}
