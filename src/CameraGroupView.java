import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
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
		
		JPanel spinnerPanel = new JPanel();
		spinnerPanel.setLayout(new BoxLayout(spinnerPanel, BoxLayout.X_AXIS));
		
		secondSpinner = new JSpinner();
		secondSpinner.addChangeListener(this);
		secondSpinner.setMaximumSize(new Dimension(9000, 20));
		
		minuteSpinner = new JSpinner();
		minuteSpinner.addChangeListener(this);
		minuteSpinner.setMaximumSize(new Dimension(9000, 20));
		
		hourSpinner = new JSpinner();
		hourSpinner.addChangeListener(this);
		hourSpinner.setMaximumSize(new Dimension(9000, 20));
		
		daySpinner = new JSpinner();
		daySpinner.addChangeListener(this);
		daySpinner.setMaximumSize(new Dimension(9000, 20));
		
		spinnerPanel.add(daySpinner);
		spinnerPanel.add(hourSpinner);
		spinnerPanel.add(secondSpinner);
		
		add(spinnerPanel);
		add(new JLabel("Useless Information"));
	}

	@Override
	public void stateChanged(ChangeEvent event)
	{
		if(event.getSource() == secondSpinner || event.getSource() == minuteSpinner || event.getSource() == hourSpinner || event.getSource() == daySpinner)
		{
			SpinnerNumberModel secondsModel = (SpinnerNumberModel) secondSpinner.getModel();
			long seconds = secondsModel.getNumber().longValue();
			
			SpinnerNumberModel minutesModel = (SpinnerNumberModel) minuteSpinner.getModel();
			long minutes = minutesModel.getNumber().longValue();
			
			SpinnerNumberModel hourModel = (SpinnerNumberModel) hourSpinner.getModel();
			long hours = hourModel.getNumber().longValue();
			
			SpinnerNumberModel dayModel = (SpinnerNumberModel) daySpinner.getModel();
			long days = dayModel.getNumber().longValue();
			
			groupData.adjSeconds = seconds + minutes * 60 + hours * 60 * 60 + days * 60 * 60 * 24;
			
			tableModel.updateGroup(groupData.getName());
		}
	}
	
	private GroupData groupData;
	private JSpinner secondSpinner;
	private JSpinner minuteSpinner;
	private JSpinner hourSpinner;
	private JSpinner daySpinner;
	private MediaTableModel tableModel;

}
