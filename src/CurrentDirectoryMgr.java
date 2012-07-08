import java.io.File;
import javax.swing.JLabel;


public class CurrentDirectoryMgr
{
	public void setNewFile(File f)
	{
		file = f;
		
		CameraGroupMgr.getInstance().reset();
		
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

	public void setCameraGroups(CameraGroups cameraGroups)
	{
		this.cameraGroups = cameraGroups;
	}

	public void setjLabel(JLabel jLabel)
	{
		this.jLabel = jLabel;
	}

	private File file;
	
	private MediaTableModel mediaTableModel;
	
	private CameraGroups cameraGroups;

	private JLabel jLabel;


}
