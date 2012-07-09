import java.awt.Component;
import java.text.DateFormat;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


@SuppressWarnings("serial")
public class CalendarRenderer extends JLabel implements TableCellRenderer
{
	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5)
	{
		Calendar date = (Calendar) arg1;
		
		this.setText(DateFormat.getDateTimeInstance().format(date.getTime()));
		
		return this;
	}

}
