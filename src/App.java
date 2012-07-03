import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class App extends JFrame
{

	private JTable table;
	private JProgressBar progressBar;

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
	}

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

		initFrame();
		
		initMenuBar();

		initProgressBar();
		
		initChooseDirectory(getContentPane());
		
		initTable();
	}

	private void initTable()
	{
		AbstractTableModel tableModel = new MediaTableModel(progressBar);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table.setAutoCreateRowSorter(true);
		table.setRowHeight(90);
	}

	private void initFrame()
	{
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
	}
	
	private void initProgressBar()
	{
		progressBar = new JProgressBar(SwingConstants.HORIZONTAL);
		getContentPane().add(progressBar, BorderLayout.SOUTH);
	}

	private static void initChooseDirectory(Container contentPane)
	{
		JPanel northPanel = new JPanel();
		contentPane.add(northPanel, BorderLayout.NORTH);

		ChooseDirectoryButtonHandler chooseDirectoryButtonHandler = new ChooseDirectoryButtonHandler(northPanel);

		Box horizontalBox = Box.createHorizontalBox();
		northPanel.add(horizontalBox);

		JButton chooseDirectoryButton = new JButton("Choose Directory");

		chooseDirectoryButton.addActionListener(chooseDirectoryButtonHandler);
		horizontalBox.add(chooseDirectoryButton);

		CurrentDirectoryJLabel selectedDirectoryLabel = new CurrentDirectoryJLabel();
		CurrentDirectoryMgr.Get().addObserver(selectedDirectoryLabel);
		horizontalBox.add(selectedDirectoryLabel);
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

}
