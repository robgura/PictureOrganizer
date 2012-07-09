import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTable;


public class JTableDoubleClickListener implements MouseListener
{

	@Override
	public void mouseClicked(MouseEvent event)
	{
		if(event.getClickCount() == 2)
		{
			JTable jtable = (JTable) event.getSource();
			int row = jtable.convertRowIndexToModel(jtable.getSelectedRow());
			//int column = jtable.convertColumnIndexToModel(jtable.getSelectedColumn());
			MediaTableModel model = (MediaTableModel) jtable.getModel();
			model.showThumbnail(row);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}
}
