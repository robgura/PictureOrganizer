import java.util.Iterator;
import java.util.Map.Entry;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


public class CameraGroups
{

	public CameraGroups(JPanel inPanel)
	{
		panel = inPanel;
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	}
	
	private JPanel panel;

	public void resetGroups()
	{
		panel.removeAll();
		Iterator<Entry<String, GroupData>> iter = CameraGroupMgr.getInstance().getGroups().entrySet().iterator();
		while(iter.hasNext())
		{
			Entry<String, GroupData> entry = iter.next();
			panel.add(new CameraGroupInfo(entry.getKey(), entry.getValue()));
		}
	}
}
