import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
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

@SuppressWarnings("serial")
public class App extends JFrame
{

	//private JFrame frame;
	private JTable table;

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

		initChooseDirectory(this);

		initTable();
	}

	private void initTable()
	{
		table = new JTable(new MediaTableModel());
		table.setFillsViewportHeight(true);
		table.setPreferredSize(new Dimension(800, 600));

		JPanel centerPanel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(table);
		centerPanel.add(scrollPane);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
	}

	private void initFrame()
	{
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
	}

	private static void initChooseDirectory(JFrame frame)
	{
		JPanel northPanel = new JPanel();
		frame.getContentPane().add(northPanel, BorderLayout.NORTH);

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
