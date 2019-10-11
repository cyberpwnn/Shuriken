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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.MouseInputAdapter;

import ninja.bytecode.shuriken.Shuriken;
import ninja.bytecode.shuriken.bench.Profiler;
import ninja.bytecode.shuriken.format.F;
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
				.fractureWith(new CNG(rng, 1, 1), 100)
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
		private static final long serialVersionUID = 1L;

		@Override
		public Dimension getPreferredSize()
		{
			return new Dimension(900, 900);
		}

		@Override
		protected void paintComponent(Graphics gg)
		{
			Graphics2D g = (Graphics2D) gg;
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			render(g);
			double rr = Math.random();
			rgg = rr;
			frame.setTitle("Frame Time: " + F.duration(Shuriken.profiler.getResult("render").getAverage(), 2) + " MS (Accuracy: " + F.pc(1D / a, 0) + ")");
			a--;
			render(g);
			
			
			if(Shuriken.profiler.getResult("render").getAverage() > 20D)
			{
				a++;
			}
			
			else if(Shuriken.profiler.getResult("render").getAverage() < 16D)
			{
				a--;
			}
			
			a = a < 1 ? 1 : a;
		}

		private double render(Graphics2D g)
		{
			Shuriken.profiler.start("render");
			
			for(double i = 0; i < width(); i += a)
			{
				for(double j = 0; j < height(); j += a)
				{
					float n = (float) c.noise((i + cx + cxx) / z, (j + cy + cyy) / z, M.ms() / 10000D);
					g.setColor(Color.getHSBColor(n, n, n));
					g.setStroke(new BasicStroke(a));
					g.drawRect((int) i, (int) j, 1, 1);
				}
			}
			
			Shuriken.profiler.stop("render");
			return Shuriken.profiler.getResult("render").getAverage();
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
