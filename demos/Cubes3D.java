package demos;


import graph3D.Point3D;
import graph3D.Polygon3D;
import graph3D.Rect3D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Cube 3D demo
 * 
 * @author kenny cason http://www.ken-soft.com 
 * @date 2009 June
 */
public class Cubes3D extends JFrame {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -514363092944040524L;

	
	/*
	 * for fast processing 
	 */
	private LinkedList<Polygon3D> polygons;
	/* 
	 * for more spatial control, try playing with a 3D array
	 */
	//private Polygon3D[][][] polygons;
	
	private Rect3D border;
	private Draw draw;
	
	private int xOrig = 350;
	private int yOrig = 300;
	private int zOrig = 0;
	
	/*
	 * settings for cube faces
	 */
	private Color top = new Color(0xFFFFFF);
	private Color bottom = new Color(0xFDFF00);
	private Color left = new Color(0xFF7200);
	private Color right = new Color(0xFF1313);
	private Color front = new Color(0x20B814);
	private Color back = new Color(0x0033FF);
	
	
	/*
	 * only repaint when you need to
	 */
	private boolean repaint = true;
	
	private boolean exit = false;
	private boolean reset = false;

	/*
	 * puzzle settings
	 */
	private int puzzleDim = 7;
	private int pieceSize = 45;
	private int spacing = 50;
	private boolean solid = false;
	private boolean randomRotation = false;
	private int puzzleColorScheme = 1;
	
	/*
	 * global contral vars
	 */
	private boolean rotating = false;
	private long rotateCurrentAngle = 0;
	private double rotateSpeed = 20;
	
	/*
	 * defined, but not used in this example
	 */
	private int rotateColumn = -1;
	private int rotateRow = -1;
	private int rotateDir = 1;
	
	
	
	public static void main (String[] args) {
		new Cubes3D();
	}  
 
	public Cubes3D() {
		super("Cubes3D");
		
		
		/* dim, width, spacing */
		polygons = makePuzzle(puzzleDim, pieceSize, spacing, solid);
		setColors(puzzleColorScheme);
		
		
		draw = new Draw();

		
		
		

		
		// set up GUI
		// can't have this in an applet
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(draw); 
		setSize(700,700);
		setLocation(100,100);
		validate();
		setVisible(true);
		exit = false;
		
		Keyboard keyboard = new Keyboard();
		GUIMouse mouse = new GUIMouse();
		draw.addMouseMotionListener(mouse);
		draw.addMouseListener(mouse);
		draw.addKeyListener(keyboard);
		
		
		getContentPane().add(draw);

	    long time = System.currentTimeMillis();
		while (!exit) {
			
			if(rotating) {
				if(System.currentTimeMillis() - time > rotateSpeed) {
					time = System.currentTimeMillis();
					rotateCurrentAngle += 5;
					if(rotateCurrentAngle >= 90) {
						rotating = false;
					}
					if(rotateColumn != -1) {
						rotateColumn(rotateColumn, rotateDir*5);
					}
					if(rotateRow != -1) {
						rotateRow(rotateRow, rotateDir*5);
					} 
					repaint = true;

				}
			}
			if(randomRotation) {
				if(System.currentTimeMillis() - time > rotateSpeed) {
					time = System.currentTimeMillis();
					randomRotate();
					repaint = true;
				}
			}
			if(repaint) {
				repaint();
				repaint = false;
			}
			if(reset) {
				polygons = makePuzzle(puzzleDim, pieceSize, spacing, solid);
				setColors(puzzleColorScheme);
				
				reset = false;
				repaint = true;
			}

		}
	}
	
	/**
	 * TODO not yet implemented
	 * @param c
	 * @param theta
	 */
	public void rotateColumn(int c, int theta) {
		for(Polygon3D polygon : polygons) {
			if(((Rect3D)polygon).x == c) {
				//polygon.rotateXAroundAxis(theta);
			}
		}
	}
	
	public void rotateRow(int r, int theta) {
		for(Polygon3D polygon : polygons) {
			if(((Rect3D)polygon).y == r) {
				//polygon.rotateYAroundAxis(theta);
			}
		}	
	}
	
	/**
	 * randomly rotate all cubes
	 *
	 */
	public void randomRotate() {
		Random r = new Random();
		for(Polygon3D polygon : polygons) {
			
			if(r.nextInt(3) > 1) {
				polygon.rotateXAroundAxis(r.nextInt(5)-r.nextInt(4));
				polygon.rotateX(r.nextInt(5)-r.nextInt(4));
			}
			
			if(r.nextInt(3) > 1) {
				polygon.rotateYAroundAxis(r.nextInt(5)-r.nextInt(4));
				polygon.rotateY(r.nextInt(5)-r.nextInt(4));
			}
			if(r.nextInt(3) > 1) {
				polygon.rotateZAroundAxis(r.nextInt(5)-r.nextInt(4));
				polygon.rotateZ(r.nextInt(5)-r.nextInt(4));
			}
		}
	}
	
	/**
	 * create a puzzle centered at the origin
	 * @param dim
	 * @param width
	 * @param space
	 * @return
	 */
	public LinkedList<Polygon3D> makePuzzle(int dim, int width, int space, boolean solid) {
		LinkedList<Polygon3D> polygons = new LinkedList<Polygon3D>();
		
	
		int startPos;
		startPos = -(((dim - 1)*space)/2); 

		for (int z = 0, zOff = startPos; z < dim; z++, zOff += space) {
			for (int y = 0, yOff = startPos; y < dim; y++, yOff += space) {
				for (int x = 0, xOff = startPos; x < dim; x++, xOff += space) {
					if (solid) {
						polygons.add(new Rect3D(width, new Point3D(xOff, yOff, zOff)));
					} else {
						if (dim > 2) {
							if (x == 0 || y == 0 || z == 0 || x == dim - 1
									|| y == dim - 1 || z == dim - 1) {
								Rect3D rect = new Rect3D(width, new Point3D(xOff, yOff, zOff));
								rect.x = (short) x;
								rect.y = (short) y;
								rect.z = (short) z;
								polygons.add(rect);
							}
						}
					}
				}
			}


		}
		
		border = new Rect3D(Math.abs(2*startPos)+width, new Point3D(0,0,0)); 
		
		for(Polygon3D polygon : polygons) {
			polygon.rotateXAroundAxis(-12);
			polygon.rotateYAroundAxis(15);
		}
		/*
		 * don't forget to rotate the border with the cube!
		 */
		border.rotateXAroundAxis(-12);
		border.rotateYAroundAxis(15);
		
		
		return polygons;
	}
	
	public void setColors(int i) {
		if(i == 1) { // normal
			top = new Color(0xFFFFFF);
			bottom = new Color(0xFDFF00);
			left = new Color(0xFF7200);
			right = new Color(0xFF1313);
			front = new Color(0x20B814);
			back = new Color(0x0033FF);
		} else if(i == 2) { // dark
			top = new Color(0xFFFFFF);
			bottom = new Color(0xCCCCCC);
			left = new Color(0x888888);
			right = new Color(0xAAAAAA);
			front = new Color(0x444444);
			back = new Color(0x000000);
		} else if(i == 3) { // Reds
			top = new Color(0xFF0000);
			bottom = new Color(0xDD0000);
			left = new Color(0xBB0000);
			right = new Color(0x990000);
			front = new Color(0x770000);
			back = new Color(0x550000);
		} else if(i == 4) { // Greens
			top = new Color(0x00FF00);
			bottom = new Color(0x00DD00);
			left = new Color(0x00BB00);
			right = new Color(0x009900);
			front = new Color(0x007700);
			back = new Color(0x005500);
		} else if(i == 5) { // blues
			top = new Color(0x0000FF);
			bottom = new Color(0x0000DD);
			left = new Color(0x0000BB);
			right = new Color(0x000099);
			front = new Color(0x000077);
			back = new Color(0x000055);
		} else if(i == 6) { // white
			top = new Color(0xFFFFFF);
			bottom = new Color(0xFFFFFF);
			left = new Color(0xFFFFFF);
			right = new Color(0xFFFFFF);
			front = new Color(0xFFFFFF);
			back = new Color(0xFFFFFF);
		}
		for(Polygon3D polygon : polygons) {
			polygon.setFaceColors(new Color[] {top,left,front,right,back,bottom});
		}
		
	}
	
	/**
	 * 
	 * @author kenny
	 *
	 */
	private class Draw extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2832523728498871378L;

	
		
		public Draw() {
		}

		
 
		/**
		 * 
		 */
		public void paintComponent(Graphics _g) {
			Graphics2D g = (Graphics2D) _g;
 
		/*	for(Polygon3D polygon : polygons) {
				polygon.renderWired(g, new Point3D(xOrig, yOrig, zOrig));
			}*/
			
			/*
			 * paint them in order of increading z-index
			 */
			int[] ordered = orderPolygonsByZDepth();
			for(int i = ordered.length - 1; i >= 0; i--) {
					polygons.get(ordered[i]).renderColored(g, new Point3D(xOrig, yOrig, zOrig));
			}
			
			// this is the out border used to determin if the mouse is over the cube
		//	border.renderWired(g, new Point3D(xOrig, yOrig, zOrig));

		} 
		
		
		/**
		 * 
		 * @return
		 */
		public int[] orderPolygonsByZDepth() {
			int[] ordered = new int[polygons.size()];
			for(int i = 0; i < ordered.length; i++) {
				ordered[i] = i;
			}
			for(int c = 1; c < ordered.length+1; c++) {
				for(int i = 0; i < ordered.length - c; i++) {
					if(polygons.get(ordered[i]).getZAxisOffset() < polygons.get(ordered[i+1]).getZAxisOffset()) {
						int temp = ordered[i];
						ordered[i] = ordered[i+1];
						ordered[i+1] = temp;
					}
				}
			}
			return ordered;
		}
		

		
	} 
	
	
	/**
	 * 
	 * @author kenny
	 *
	 */
	private class Keyboard implements KeyListener {


		public Keyboard() {
			addKeyListener(this);
		}
		

		public void keyTyped(KeyEvent e) {

		}

		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				//System.out.println("Shifting");
				for(Polygon3D p : polygons) {
					 p.getAxisOffset().setY( p.getAxisOffset().getY() + 10);
				}
				border.setYAxisOffset(border.getYAxisOffset() + 10);
				repaint = true;
			}  if (e.getKeyCode() == KeyEvent.VK_UP) {
				for(Polygon3D p : polygons) {
					 p.getAxisOffset().setY( p.getAxisOffset().getY() - 10);
				}
				border.setYAxisOffset(border.getYAxisOffset() - 10);
				repaint = true;
			} 
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				for(Polygon3D p : polygons) {
					 p.getAxisOffset().setX( p.getAxisOffset().getX() + 10);
				}
				border.setXAxisOffset(border.getXAxisOffset() + 10);
				repaint = true;
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				for(Polygon3D p : polygons) {
					 p.getAxisOffset().setX( p.getAxisOffset().getX() - 10);
				}
				border.setXAxisOffset(border.getXAxisOffset() - 10);
				repaint = true;
			} else if (e.getKeyCode() == KeyEvent.VK_A) { // reset
				reset = true;
			} else if (e.getKeyCode() == KeyEvent.VK_F) { // random rotate toggle
				if(!randomRotation) {
				randomRotation = true;
				} else {
					randomRotation = false;
				}
			} else if (e.getKeyCode() == KeyEvent.VK_S) { // Toggle solid
				if(!solid) {
					solid = true;
				} else {
					solid = false;
				}
				reset = true;
			} else if (e.getKeyCode() == KeyEvent.VK_Q) { // spacing--
				if(spacing > 10) {
					spacing -= 5;
					reset = true;
				}
			} else if (e.getKeyCode() == KeyEvent.VK_W) { // spacing++
				spacing += 5;
				reset = true;
			} else if (e.getKeyCode() == KeyEvent.VK_E) { // width--
				if(pieceSize >=10) {
					pieceSize -= 5;
					reset = true;
				}
			} else if (e.getKeyCode() == KeyEvent.VK_R) { // width++
				if(pieceSize + 5 <= spacing) {
					pieceSize += 5;	
					reset = true;
				}
			} else if (e.getKeyCode() == KeyEvent.VK_T) { // dim--
				if(puzzleDim > 1) {
					puzzleDim -= 1;
					reset = true;
				}
			} else if (e.getKeyCode() == KeyEvent.VK_Y) { // dim++
				puzzleDim += 1;
				reset = true;
			}  else if (e.getKeyCode() == KeyEvent.VK_D) { // random face
				Random r = new Random();
				int i = r.nextInt(6)+1;
				System.out.println(i);
				setColors(i);
				repaint = true;
			} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // exit
				exit = true;
			}
			
			
		}

		public void keyReleased(KeyEvent e) {

		}
		
	}

	/**
	 * 
	 * 
	 */
	private class GUIMouse implements MouseMotionListener, MouseListener {

		int mX, mY;

		public GUIMouse() {
		}

		public void mouseDragged(MouseEvent e) {
	//		System.out.println("DRAGGING");
			updateRotate(e);
			repaint = true;
		}

		void updateRotate(MouseEvent e) {

			int x = e.getX();
			int y = e.getY();
			if (x - mX > 1) {
				for (Polygon3D p : polygons) {
					p.rotateYAroundAxis(10);
				}
				border.rotateYAroundAxis(10);
			} else if (mX - x > 1) {
				for (Polygon3D p : polygons) {
					p.rotateYAroundAxis(-10);
				}
				border.rotateYAroundAxis(-10);
			}
			if (y - mY > 1) {
				for (Polygon3D p : polygons) {
					p.rotateXAroundAxis(-10);
				}
				border.rotateXAroundAxis(-10);
			} else if (mY - y > 1) {
				for (Polygon3D p : polygons) {
					p.rotateXAroundAxis(10);
				}
				border.rotateXAroundAxis(10);
			}
			mX = x;
			mY = y;

		}

		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseClicked(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mousePressed(MouseEvent e) {
		//	System.out.println("PRESSED!");

			
		}

		public void mouseReleased(MouseEvent e) {
		//	System.out.println("RELEASED!");
		
		}
		
		/**
		 * Determine whether the mouse, a point, is in the Cube.
		 * the projection of the Cube (or anyother shape), when projected into 2D forms a polygon,
		 * therefore, the sum of all the angles formed between 2 connecting points and the point, should some to 360
		 * else, it is not in the cube
		 * @param mX
		 * @param mY
		 * @return
		 */
		public boolean isMouseInPolygonProjection(int mX, int mY) {
			mX -= xOrig;
			mY -= yOrig;
			LinkedList<Point3D> extrema = getExtremePoints();
			double theta = 0;
			
			/*
			 * TODO define this function. this function will start at (mX,mY)
			 * and calculate the angle formed from by (mX,mY) and each
			 * consequtive extrema point calculated above. The resulting some of
			 * the angles will be 360 if the mouse is inside the rectangle
			 * 
			 * Later, go through and find the Cube (Rect3D) objects that
			 * returned true, and find the one with the HIGHEST Z coord. That
			 * will be the one u clicked
			 * 
			 * This algorithm can also be applied to determining if the Mouse is inside the Border, (the whole cube)
			 */
		    
			
			if(theta == 360) {
				System.out.println("DEG!");
				return true;
			} 
			return false;
		}
		
		/**
		 * returns the highest and lowest integers for each dimension. (i.e. returns the outermost points of the Cube!) 
		 * (i.e. lowest/highest X, lowest/highest Y
		 * @return
		 */
		public LinkedList<Point3D> getExtremePoints() {
			LinkedList<Point3D> extrema = new LinkedList<Point3D>();
			double lX = Double.MAX_VALUE, lY = Double.MAX_VALUE;
			double hX = Double.MIN_VALUE, hY = Double.MIN_VALUE;
			
			for(int i = 0; i < border.getFaces().length; i++) {
				for(int j = 0; j < border.getFaces()[i].getPoints().length; j++) {
					double x = border.getFaces()[i].getPoints()[j].getX();
					double y = border.getFaces()[i].getPoints()[j].getY();
					double z = border.getFaces()[i].getPoints()[j].getZ();
					if(x < lX) { // if lower than lowest X
						lX = x;
						if(!extrema.contains(border.getFaces()[i].getPoints()[j])) {
							extrema.add(border.getFaces()[i].getPoints()[j]);
						}
					}
					if(x > hX) { // higher
						hX = x;
						if(!extrema.contains(border.getFaces()[i].getPoints()[j])) {
							extrema.add(border.getFaces()[i].getPoints()[j]);
						}
					}
					if(y < lY) { // if lower than lowest Y
						lY = y;
						if(!extrema.contains(border.getFaces()[i].getPoints()[j])) {
							extrema.add(border.getFaces()[i].getPoints()[j]);
						}
					}
					if(y > hY) { // higher
						hY = y;
						if(!extrema.contains(border.getFaces()[i].getPoints()[j])) {
							extrema.add(border.getFaces()[i].getPoints()[j]);
						}
					}
				}
			}
			
			return extrema;
		}
	}
	
	
	
}  

