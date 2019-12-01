package ninja.bytecode.shuriken.visualizer;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.MouseInputAdapter;

import ninja.bytecode.shuriken.Shuriken;
import ninja.bytecode.shuriken.logging.L;
import ninja.bytecode.shuriken.math.CNG;
import ninja.bytecode.shuriken.math.M;
import ninja.bytecode.shuriken.math.RNG;

public class Visualizer2D
{
	private boolean ctrl;
	private double z;
	private int a;
	private JFrame frame;
	private CV cv;
	private boolean dragging;
	private int lastX;
	private int lastY;
	private int cx;
	private int cy;
	private int cxx;
	private int cyy;
	private double rgg;
	private Timer t;
	private boolean done = false;
	private CNG c;

	public static void main(String[] a)
	{
		RNG rng = new RNG("Hello World");
		//@builder
		CNG c = new CNG(rng, 1, 1)
				.fractureWith(new CNG(rng, 1, 1).scale(0.08), 10)
				;
		//@done
		new Visualizer2D(c);
	}

	public Visualizer2D(CNG c)
	{
		Shuriken.profiler.start("render");
		Shuriken.profiler.stop("render");
		lastX = -1;
		lastY = -1;
		cx = 0;
		cy = 0;
		cxx = 0;
		cyy = 0;
		ctrl = false;
		this.c = c;
		z = 50;
		dragging = false;
		a = 4;
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}

				catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
				{

				}

				frame = new JFrame("Shuriken Visualizer");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new BorderLayout());
				cv = new CV();
				frame.setFocusable(true);
				frame.requestFocus();
				frame.addKeyListener(new KeyListener()
				{
					@Override
					public void keyTyped(KeyEvent l)
					{

					}

					@Override
					public void keyReleased(KeyEvent l)
					{
						if(l.getKeyCode() == KeyEvent.VK_SHIFT)
						{
							ctrl = false;
						}
					}

					@Override
					public void keyPressed(KeyEvent l)
					{
						if(l.getKeyCode() == KeyEvent.VK_SHIFT)
						{
							ctrl = true;
						}
					}
				});

				cv.addMouseMotionListener(new MouseInputAdapter()
				{

					@Override
					public void mouseDragged(MouseEvent e)
					{
						if(lastX == -1 && lastY == -1)
						{
							L.i("RSET");
							lastX = e.getX();
							lastY = e.getY();
						}

						cx += lastX - e.getX();
						cy += lastY - e.getY();
						lastX = e.getX();
						lastY = e.getY();
						a = 15;
						cv.repaint();
					}

					@Override
					public void mouseMoved(MouseEvent e)
					{
						lastX = e.getX();
						lastY = e.getY();
					}
				});
				cv.addMouseWheelListener(new MouseWheelListener()
				{
					@Override
					public void mouseWheelMoved(MouseWheelEvent e)
					{
						if(ctrl)
						{
							a += e.getWheelRotation();
							a = a < 1 ? 1 : a;
							a = a > 40 ? 40 : a;
						}

						else
						{
							z -= (e.getPreciseWheelRotation() * (z / 10D)) * 0.25;
							z = z < 1D ? 1D : z;
						}

						a = 15;
						cv.repaint();
					}
				});
				frame.add(cv);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				t = new Timer((int) (16 + Shuriken.profiler.getResult("render").getAverage()), new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent var1)
					{
						tick();
					}
				});
				t.start();
			}
		});
	}

	public void tick()
	{
		cv.repaint();
		t.restart();
	}

	public void redraw()
	{
		frame.repaint();
	}

	public void destroy()
	{
		frame.setVisible(false);
		frame.dispose();
	}

	public class CV extends JPanel
	{
		private boolean done = false;
		private boolean init = false;
		private FileReader fr;
		private static final long serialVersionUID = 1L;
		private int res = 660;
		private int zoom = 4;
		private int[][] mtdm = new int[res][];

		@Override
		public Dimension getPreferredSize()
		{
			return new Dimension(res * zoom, res * zoom);
		}

		@Override
		protected void paintComponent(Graphics gg)
		{
			if(!init)
			{
				try
				{
					init = true;
					File f = new File("C:/Users/cyberpwn/Desktop/wormholes.txt");
					fr = new FileReader(f);

					for(int i = 0; i < mtdm.length; i++)
					{
						mtdm[i] = new int[mtdm.length];
					}
				}
				catch(Throwable e)
				{

				}
			}

			Graphics2D g = (Graphics2D) gg;
			super.paintComponent(g);
			g.setColor(Color.BLACK);

			try
			{
				g.setColor(Color.BLACK);

				g.clearRect(0, 0, width(), height());
				if(done)
				{
					for(int i = 0; i < mtdm.length; i++)
					{
						for(int j = 0; j < mtdm.length; j++)
						{
							g.setColor(new Color(mtdm[i][j]));
							g.setStroke(new BasicStroke(a));
							g.drawRect(i * zoom, j * zoom, zoom / 2, zoom / 2);
						}
					}
				}

				else
				{
					int fff = 0;

					looping: while(true)
					{
						char[] slow = new char[1];
						for(int i = 0; i < mtdm.length; i++)
						{
							System.out.println("");

							for(int j = 0; j < mtdm.length; j++)
							{
								int r = fr.read(slow);
								System.out.print(slow[0]);

								if(r == -1)
								{
									done = true;
									fr.close();
									break looping;
								}

								mtdm[i][j] += fff += slow[0] * slow[0];
							}
						}
					}
				}
			}

			catch(Throwable e)
			{

			}

		}
	}

	public int width()
	{
		return cv.getWidth();
	}

	public int height()
	{
		return cv.getHeight();
	}
}
