import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



@SuppressWarnings("serial")
public class CameraGroupView extends javax.swing.JPanel implements ChangeListener, ActionListener
{

	public CameraGroupView(GroupData _groupData, MediaTableModel _model, JTable _jtable)
	{
		groupData = _groupData;
		tableModel = _model;
		jtable = _jtable;
		
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
		spinnerPanel.add(minuteSpinner);
		spinnerPanel.add(secondSpinner);
		
		add(spinnerPanel);
		
		JButton button = new JButton("Add Selection");
		button.addActionListener(this);
		add(button);
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
	private JTable jtable;
	
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		int[] rows = jtable.getSelectedRows();
		for(int i = 0; i < rows.length; ++i)
		{
			int row = jtable.convertRowIndexToModel(rows[i]);
			tableModel.changeGroup(row, groupData);
		}
	}

}
