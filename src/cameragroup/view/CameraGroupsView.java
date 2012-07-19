package cameragroup.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import cameragroup.model.CameraGroupMgr;
import cameragroup.model.GroupData;
import table.model.MediaTableModel;


public class CameraGroupsView implements ActionListener
{
	public CameraGroupsView(JPanel inPanel, MediaTableModel _model, JTable _jtable)
	{
		parentPanel = inPanel;
		parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
		
		JButton button = new JButton("Add Group");
		button.addActionListener(this);
		parentPanel.add(button);
		
		groupsPanel = new JPanel();
		groupsPanel.setLayout(new BoxLayout(groupsPanel, BoxLayout.Y_AXIS));
		parentPanel.add(groupsPanel);
		
		model = _model;
		jtable = _jtable;
	}
	
	public void resetGroups()
	{
		groupsPanel.removeAll();
		Iterator<Entry<String, GroupData>> iter = CameraGroupMgr.getInstance().getGroups().entrySet().iterator();
		while(iter.hasNext())
		{
			Entry<String, GroupData> entry = iter.next();
			groupsPanel.add(new CameraGroupView(entry.getValue(), model, jtable));
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		String groupName = new String("Group " + Integer.toString(groupCount++));
		GroupData gd = new GroupData(groupName);
		
		CameraGroupMgr.getInstance().addCameraGroup(groupName);
		groupsPanel.add(new CameraGroupView(gd, model, jtable));
	}

	private JPanel parentPanel;
	private JPanel groupsPanel;
	private MediaTableModel model;
	private JTable jtable;
	private static int groupCount = 1;
}
