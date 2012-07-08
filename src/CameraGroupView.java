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
public class CameraGroupView extends javax.swing.JPanel implements ChangeListener
{

	public CameraGroupView(GroupData _groupData, MediaTableModel _model)
	{
		groupData = _groupData;
		tableModel = _model;
		
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(new JLabel(groupData.getName() + " (" + Integer.toString(groupData.mediaCount) + ")"));
		
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
			SpinnerModel model = spinner.getModel();
			groupData.adjSeconds = ((Integer) model.getValue()).intValue();
			tableModel.updateGroup(groupData.getName());
		}
	}
	
	private GroupData groupData;
	private JSpinner spinner;
	private MediaTableModel tableModel;

}
