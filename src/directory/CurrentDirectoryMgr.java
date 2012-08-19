package directory;

import java.io.File;
import javax.swing.JLabel;
import cameragroup.model.CameraGroupMgr;
import cameragroup.view.CameraGroupsView;
import table.model.MediaTableModel;


public class CurrentDirectoryMgr
{
	public void setNewFile(File f)
	{
		file = f;
		
		cameraGroupMgr.reset();
		
		mediaTableModel.readDirectory(f);
		
		cameraGroups.resetGroups();
		
		jLabel.setText(f.getAbsolutePath());
	}

	public File getFile()
	{
		return file;
	}

	public void setMediaTableModel(MediaTableModel mediaTableModel)
	{
		this.mediaTableModel = mediaTableModel;
	}

	public void setCameraGroups(CameraGroupsView cameraGroups)
	{
		this.cameraGroups = cameraGroups;
	}

	public void setjLabel(JLabel jLabel)
	{
		this.jLabel = jLabel;
	}

	public void setCameraGroupMgr(CameraGroupMgr cameraGroupMgr)
	{
		this.cameraGroupMgr = cameraGroupMgr;
	}

	private File file;
	
	private MediaTableModel mediaTableModel;
	
	private CameraGroupsView cameraGroups;
	
	private CameraGroupMgr cameraGroupMgr;

	private JLabel jLabel;


}
