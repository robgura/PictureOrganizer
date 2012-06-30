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

public class App
{

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					App window = new App();
					window.frame.pack();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
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

		initialize();
	}

	private void initialize()
	{
		initFrame();
		initMenuBar();

		// chose directory section
		initChooseDirectory(frame);

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
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
	}

	private void initFrame()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
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
		frame.setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem fileOpenMenu = new JMenuItem("Open");
		fileOpenMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		fileMenu.add(fileOpenMenu);

	}

}
