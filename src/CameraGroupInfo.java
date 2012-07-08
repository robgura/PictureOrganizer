import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



@SuppressWarnings("serial")
public class CameraGroupInfo extends javax.swing.JPanel implements ChangeListener
{

	public CameraGroupInfo(String _groupName, GroupData groupData)
	{
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		groupName = _groupName;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(new JLabel(groupName + "(" + Integer.toString(groupData.mediaCount) + ")"));
		
		spinner = new JSpinner();
		spinner.addChangeListener(this);
		add(spinner);
		
		spinner.setMaximumSize(new Dimension(9000, 20));
		
		add(new JLabel("Useless Information"));
	}

	@Override
	public void stateChanged(ChangeEvent event)
	{
		if(event.getSource() == spinner)
		{
			try
			{
				SpinnerModel model = spinner.getModel();
				CameraGroupMgr.getInstance().updateTime(groupName, (Integer) model.getValue());
			}
			catch (CameraGroupNotFound e)
			{
				System.err.println("Could not find group name" + e.groupName);
				e.printStackTrace();
			}
		}
	}
	
	private String groupName;
	private JSpinner spinner;

}
