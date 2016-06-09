/*
 * Paint the Picture - Java Applet
 * 
 * An applet in Java for painting with basic tools,
 * opening an image to paint on and saving the drawing
 * to a png image file.
 * 
 * By:
 * Tarasha Khurana (370/COE/14)
 * Computer Engineering Division
 * Netaji Subhas Institute of Technology, New Delhi
 * 
 * Copyright &copy; 2016 Tarasha Khurana
 */

package drawing;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.applet.*;
import java.awt.event.*;
import java.util.*;

public class drawimp extends Applet {
	
	// variables declaration
	private BufferedImage toDrawOn = new BufferedImage(600, 630, BufferedImage.TYPE_3BYTE_BGR);
	
	private int r, g, b, x, y, xco, startx, starty, endx, endy, widthx, heighty, thickness = 5, whichTool = 0;
	private boolean colorbutton = false, ended = false;
	private Color colorbuttoncolor;
	private String filepath;
	
	// constructor
	public drawimp() {
		initializer();
	}
	
	// init function for applet
	public void init() {
		Graphics g = toDrawOn.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 600, 630);
		setSize(600, 800);
	}
	
	// function called by constructor; initializes the applet GUI with components
	// and associates with them respective event handlers
	public void initializer() {
		
		setLayout(null);
		
		JButton buttonPen = new JButton("Pen");
		JButton buttonOval = new JButton("Oval");
		JButton buttonRect = new JButton("Rectangle");
		JButton buttonLine = new JButton("Line");
		JButton openit = new JButton("Open");
		JButton clearall = new JButton("Clear All");
		JButton saveit = new JButton("Save");
		JLabel imagelabel = new JLabel(new ImageIcon(toDrawOn));
		JLabel tlabel = new JLabel("Thickness");
		JSlider thick = new JSlider(JSlider.HORIZONTAL, 1, 50, 5);
		
		// setting imagelabel properties
		imagelabel.setBounds(0, 85, 600, 630);
		imagelabel.addMouseListener(new MouseAdapter () {
			public void mousePressed(MouseEvent e) {
				ended = false;
				startx = e.getX();
				starty = e.getY();
			}

			public void mouseReleased(MouseEvent e) {
				ended = true;
				endx = e.getX();
				endy = e.getY();
				updateimage();
			}
			
			public void mouseEntered(MouseEvent e) {
				showStatus("Mouse Entered Applet Area\n");
			}
		
			public void mouseExited(MouseEvent e) {
				showStatus("Mouse Exited Applet Area\n");
			}
			
		});
		imagelabel.addMouseMotionListener(new MouseMotionAdapter(){
			
			public void mouseDragged(MouseEvent e) {
				x = e.getX();
				y = e.getY();
				updateimage();
			}
			
		});
		add(imagelabel);
		
		// setting tlabel's properties
		tlabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
		tlabel.setBounds(45, 50, 90, 25);
		add(tlabel);
		
		// setting thick's properties
		thick.setBounds(170, 50, 410, 25);
		thick.addChangeListener(new ChangeListener() { 
			
			@Override
			public void stateChanged (ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					thickness = (int)source.getValue();
				}
			}
			
		});
		add(thick);
		
		// setting buttonLine's properties
		buttonLine.setFont(new Font("Monospaced", Font.PLAIN, 16));
		buttonLine.setBounds(25, 10, 130, 30);
		buttonLine.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				whichTool = 1;
			}
			
		});
		add(buttonLine);
		
		// setting buttonOval's properties
		buttonOval.setFont(new Font("Monospaced", Font.PLAIN, 16));
		buttonOval.setBounds(165, 10, 130, 30);
		buttonOval.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				whichTool = 2;
			}
			
		});
		add(buttonOval);
		
		// setting buttonRect's properties
		buttonRect.setFont(new Font("Monospaced", Font.PLAIN, 16));
		buttonRect.setBounds(305, 10, 130, 30);
		buttonRect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				whichTool = 3;
			}
			
		});
		add(buttonRect);
		
		// setting buttonPen's properties
		buttonPen.setFont(new Font("Monospaced", Font.PLAIN, 16));
		buttonPen.setBounds(445, 10, 130, 30);
		buttonPen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				whichTool = 4;
			}
			
		});
		add(buttonPen);
		
		// setting openit's properties
		openit.setFont(new Font("Monospaced", Font.PLAIN, 16));
		openit.setBounds(15, 760, 180, 30);
		openit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser openpane = new JFileChooser();
				int val = openpane.showOpenDialog(drawimp.this);
				if (val == JFileChooser.APPROVE_OPTION) {
					filepath = (openpane.getCurrentDirectory().toString()) + "\\" + (openpane.getSelectedFile().getName());
					//System.out.println(filepath);
					openAsImage();
				}
			}
			
		});
		add(openit);
		
		// setting clearall's properties
		clearall.setFont(new Font("Monospaced", Font.PLAIN, 16));
		clearall.setBounds(210, 760, 180, 30);
		clearall.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Graphics g = toDrawOn.getGraphics();
				g.setColor(Color.WHITE);
				g.fillRect(0,  0, 600, 630);
				repaint();
			}
			
		});
		add(clearall);
		
		// setting saveit's properties
		saveit.setFont(new Font("Monospaced", Font.PLAIN, 16));
		saveit.setBounds(405, 760, 180, 30);
		saveit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser savepane = new JFileChooser();
				int val = savepane.showSaveDialog(drawimp.this);
				if (val == JFileChooser.APPROVE_OPTION) {
					filepath = (savepane.getCurrentDirectory().toString()) + "\\" + (savepane.getSelectedFile().getName()+ ".png");
					//System.out.println(filepath);
					saveAsImage();
				}
			}
			
		});
		add(saveit);
		
		// creating an ArrayList of color buttons for collectively setting properties
		xco = 10;
		ArrayList <JButton> colorss = new ArrayList<>();
		
		JButton cwhite = new JButton("   ");
		JButton c1 = new JButton("   ");
		JButton c2 = new JButton("   "); 
		JButton c3 = new JButton("   ");
		JButton c4 = new JButton("   ");
		JButton c5 = new JButton("   ");
		JButton c6 = new JButton("   ");
		JButton c7 = new JButton("   ");
		JButton c8 = new JButton("   ");
		JButton c9 = new JButton("   ");
		JButton c10 = new JButton("   ");
		JButton c11 = new JButton("   ");
		
		colorss.add(c1);
		colorss.add(c2);
		colorss.add(c3);
		colorss.add(c4);
		colorss.add(c5);
		colorss.add(c6);
		colorss.add(c7);
		colorss.add(c8);
		colorss.add(c9);
		colorss.add(c10);
		colorss.add(c11);
		
		// setting  white color permanently for erasing
		cwhite.setBackground(Color.WHITE);
		cwhite.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton source = (JButton)e.getSource();
				colorbutton = true;
				colorbuttoncolor = source.getBackground();
			}
			
		});
		cwhite.setBounds(9, 730, 44, 20);
		add(cwhite);
		
		xco = 58;
		for (JButton colors : colorss) {
			r = (int)(Math.random()*256);
			g = (int)(Math.random()*256);
			b = (int)(Math.random()*256);
			colors.setBackground(new Color(r, g, b));
			colors.setBounds(xco, 730, 44, 20);
			xco += 49;
			colors.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JButton source = (JButton)e.getSource();
					colorbutton = true;
					colorbuttoncolor = source.getBackground();
				}
				
			});
			add(colors);
		}
		
	}
	
	// saving an image as png
	public void saveAsImage() {
		try {
			ImageIO.write(toDrawOn, "png", new File(filepath));
		} catch (IOException e) {
			System.out.println(e.toString() + "%" + filepath);
		}
	}
	
	// opening the chosen image and drawing on the bufferedimage
	public void openAsImage() {
		try {
			BufferedImage openedimage = null;
			openedimage = ImageIO.read(new File(filepath));
			
			Graphics g = toDrawOn.getGraphics();
			float openedimageheight = openedimage.getHeight();
			float openedimagewidth = openedimage.getWidth();
			float newx = 0, newy = 0;
			float newwidth = 0, newheight = 0;
			if (openedimageheight > 630 || openedimagewidth > 600) {
				if (openedimageheight > openedimagewidth) {
					newheight = 630;
					newwidth = openedimagewidth * (600/openedimageheight);
					newx = (600 - newwidth)/2;
				}
				else {
					newheight = openedimageheight * (630/openedimagewidth);
					newwidth = 600;
					newy = (630 - newheight)/2;
				}
			}
			else if (openedimageheight < 630 && openedimagewidth < 600) {
				if (openedimageheight > openedimagewidth) {
					newheight = 630;
					newwidth = openedimagewidth * (openedimageheight/600);
					newx = (600 - newwidth)/2;
				}
				else {
					newheight = openedimageheight * (openedimagewidth/630);
					newwidth = 600;
					newy = (630 - newheight)/2;
				}
			}
			else {
				newwidth = openedimagewidth;
				newheight = openedimageheight;
			}
			g.drawImage(openedimage, (int)newx, (int)newy, (int)newwidth, (int)newheight, null);
			repaint();
			
		} catch (IOException e) {
			System.out.println(e.toString() + "%" + filepath);
		}
	}
	
	// updating the bufferedimage according to user
	public void updateimage() {

		Graphics2D g = toDrawOn.createGraphics();
		Graphics2D g2d = (Graphics2D)g;
		
		int startxx = 0, startyy = 0;
		
		if (startx < endx && starty < endy) {
			startxx = startx;
			startyy = starty;
			widthx = endx - startx;
			heighty = endy - starty;
		}
		else if (startx < endx && starty > endy) {
			startxx = startx;
			startyy = endy;
			widthx = endx - startx;
			heighty = starty - endy;
		}
		else if (startx > endx && starty < endy) {
			startxx = endx;
			startyy = starty;
			widthx = startx - endx;
			heighty = endy - starty;
		}
		else if (startx > endx && starty > endy) {
			startxx = endx;
			startyy = endy;
			widthx = startx - endx;
			heighty = starty - endy;
		}
		
		if (colorbutton) {
			g.setColor(colorbuttoncolor);
		}
		if (whichTool == 2) {
			if (ended) {
				g.fillOval(startxx, startyy, widthx, heighty);
				ended = false;
			}
		}
		if (whichTool == 3) {
			if (ended) {
				g.fillRect(startxx, startyy, widthx, heighty);
				ended = false;
			}
		}
		if (whichTool == 1) {
			if (ended) {
				g2d.setStroke(new BasicStroke(thickness));
				g2d.drawLine(startx, starty, endx, endy);
				ended = false;
			}		
		}
		if (whichTool == 4 && x != 0 && y != 0) {
			g.fillOval(x, y, thickness, thickness);
		}
		g.dispose();
		repaint();
	}
	
	// overridden update function for just painting over again
	public void update(Graphics g) {
		paint(g);
	}
	
	/*public static void main (String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new drawimp().setVisible(true);
			}
		});
	}
	*/
}