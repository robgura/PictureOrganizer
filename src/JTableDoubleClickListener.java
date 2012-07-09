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
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}
}
