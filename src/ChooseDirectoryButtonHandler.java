import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;


public class ChooseDirectoryButtonHandler implements ActionListener
{

	private Component parent;

	public ChooseDirectoryButtonHandler(Component parent)
	{
		super();
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		JFileChooser fileChooser = new JFileChooser("Z:\\Pictures");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		
		int ret = fileChooser.showDialog(parent, "Open");
	
		if(ret == JFileChooser.APPROVE_OPTION)
		{
			CurrentDirectoryMgr.Get().setFile(fileChooser.getSelectedFile());
		}
	}

}
