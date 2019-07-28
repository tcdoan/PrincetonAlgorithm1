import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;


/**
 The JPieceTest component draws all the rotations of a tetris piece.
 JPieceTest.main()  creates a frame  with one JPieceTest for each
 of the 7 standard tetris pieces.
 
 This is the starter file version -- 
 The outer shell is done. You need to complete paintComponent()
 and drawPiece().
*/
class JPieceTest extends JComponent {
	protected Piece root;	

	public JPieceTest(Piece piece, int width, int height) {
		super();		
		setPreferredSize(new Dimension(width, height));
		root = piece;
	}

	/**
	 Draws the rotations from left to right.
	 Each piece goes in its own little box.
	*/
	public final int MAX_ROTATIONS = 4;
	public void paintComponent(Graphics g) {
		int w = this.getWidth() / 4;
		int h = this.getHeight();
		Rectangle r = new Rectangle(0, 0, w, h);
		drawPiece(g, root, r);

		int i = 0;
		for (Piece x = root.nextRotation(); x != root; x = x.nextRotation())
		{
			r = new Rectangle(++i*w, 0, w, h);
			drawPiece(g, x, r);
		}
	}
	
	/**
	 Draw the piece inside the given rectangle.
	*/
	private void drawPiece(Graphics g, Piece piece, Rectangle r) {		

		int w = r.height/4 < r.width/4 ? r.height/4 : r.width/4;
		Point dxy[][] = new Point[MAX_ROTATIONS][MAX_ROTATIONS];
		for (int x = 0; x < MAX_ROTATIONS; x++)
		{
			for (int y = 0; y < MAX_ROTATIONS; y++)
			{
				Point p = new Point(r.x + x * w, r.y + y * w);
				dxy[x][y] = p;
			}
		}

		for (Point p : piece.getBody())
		{
			int x = p.x;
			int y = 3 - p.y;
			Point dp = dxy[x][y];
			g.setColor(Color.BLACK);
			// g.fillRect(dp.x, dp.y, w, w);
			g.drawRect(dp.x, dp.y, w, w);
		}
	}	


	/**
	 Draws all the pieces by creating a JPieceTest for
	 each piece, and putting them all in a frame.
	*/
	static public void main(String[] args)
	
	{
		JFrame frame = new JFrame("Piece Tester");
		JComponent container = (JComponent)frame.getContentPane();		
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		
		Piece[] pieces = Piece.getPieces();
		for (int i=0; i<pieces.length; i++) {
			JPieceTest test = new JPieceTest(pieces[i], 375, 75);
			container.add(test);
		}
		
		// Size the window and show it on screen
		frame.pack();
		frame.setVisible(true);
		
		// Quit on window close
		frame.addWindowListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			}
		);
	}
}
