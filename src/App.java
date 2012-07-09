import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

@SuppressWarnings("serial")
public class App extends JFrame
{

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		JFrame frame;
		try
		{
			frame = new App();
			frame.pack();
			frame.setVisible(true);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
	}

	private MediaTableModel tableModel;

	/**
	 * Create the application.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public App() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		currentDirectoryMgr = new CurrentDirectoryMgr();
		
		initFrame();
		
		initMenuBar();

		initChooseDirectory(getContentPane());
		
		initTable();
		
		initModelInfo();
		
	}

	private void initModelInfo()
	{
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.WEST);
		CameraGroupsView cameraGroups = new CameraGroupsView(panel, tableModel);
		
		currentDirectoryMgr.setCameraGroups(cameraGroups);
	}
	
	private void initTable()
	{
		tableModel = new MediaTableModel();
		table = new JTable(tableModel);
		
		table.setAutoCreateRowSorter(true);
		table.setRowHeight(90);
		TableColumn tableColumn = table.getColumnModel().getColumn(MediaTableModel.DATE_COLUMN);
		tableColumn.setCellRenderer(new CalendarRenderer());
		
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		currentDirectoryMgr.setMediaTableModel(tableModel);
	}

	private void initFrame()
	{
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
	}
	
	private void initChooseDirectory(Container contentPane)
	{
		JPanel northPanel = new JPanel();
		contentPane.add(northPanel, BorderLayout.NORTH);

		ChooseDirectoryButtonHandler chooseDirectoryButtonHandler = new ChooseDirectoryButtonHandler(northPanel, currentDirectoryMgr);

		Box horizontalBox = Box.createHorizontalBox();
		northPanel.add(horizontalBox);

		JButton chooseDirectoryButton = new JButton("Choose Directory");

		chooseDirectoryButton.addActionListener(chooseDirectoryButtonHandler);
		horizontalBox.add(chooseDirectoryButton);

		JLabel selectedDirectoryLabel = new JLabel();
		horizontalBox.add(selectedDirectoryLabel);
		currentDirectoryMgr.setjLabel(selectedDirectoryLabel);
	}

	private void initMenuBar()
	{

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem fileOpenMenu = new JMenuItem("Open");
		fileOpenMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		fileMenu.add(fileOpenMenu);

	}
	
	private JTable table;
	private CurrentDirectoryMgr currentDirectoryMgr;

}
