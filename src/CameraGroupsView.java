import java.util.Iterator;
import java.util.Map.Entry;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


public class CameraGroupsView
{

	public CameraGroupsView(JPanel inPanel, MediaTableModel _model)
	{
		panel = inPanel;
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		model = _model;
	}
	
	public void resetGroups()
	{
		panel.removeAll();
		Iterator<Entry<String, GroupData>> iter = CameraGroupMgr.getInstance().getGroups().entrySet().iterator();
		while(iter.hasNext())
		{
			Entry<String, GroupData> entry = iter.next();
			panel.add(new CameraGroupView(entry.getValue(), model));
		}
	}

	private JPanel panel;
	private MediaTableModel model;
}
