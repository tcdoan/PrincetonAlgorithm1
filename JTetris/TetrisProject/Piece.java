import java.awt.*;
import java.util.*;

/**
 An immutable representation of a tetris piece in a particular rotation.
 Each piece is defined by the blocks that make up its body.

 The body is represented by the (x, y) coordinates of its blocks, 
 with the origin in the lower-left corner.

 So the body of this piece is defined by the (x,y) points: (0,0), (1,0), (1, 1), (2,1)

 Each piece responds to the messages like getWidth(), getHeight(), and getBody() 
 that allow the client to see the state of the piece.

 The getSkirt() message returns an array that shows the lowest y value for each x value across the piece 
 ({0, 0, 1} for the piece above).

 The skirt makes it easier to compute how a piece will come to land in the board. 

 To allow the client to see the various piece rotations that are available, the Piece class builds 
 an array of the standard tetris pieces internally -- available through the Piece.getPieces().
 This array contains the first rotation of each of the standard pieces.

 Starting with any piece, the nextRotation() message returns the "next" piece object that represents 
 the next counter-clockwise rotation. Enough calls to nextRotation() gets back to the original piece.

 This is the starter file version -- a few simple things are filled in already
 
 @author	Nick Parlante
 @version	1.0, Mar 1, 2001
*/
public final class Piece {
/*
 Implementation notes:
 -The starter code does out a few simple things for you
 -Store the body as a Point[] array
 -The ivars in the Point class are .x and .y
 -Do not assume there are 4 points in the body -- use array.length
 to keep the code general
*/
	private Point[] body;
	private int[] skirt;
	private int width;
	private int height;
	private Piece next;	// "next" rotation
	
	static private Piece[] pieces;	// singleton array of first rotations
	
	
	/**
	 Defines a new piece given the Points that make up its body.
	 Makes its own copy of the array and the Point inside it.
	 Does not set up the rotations.
	 
	 This constructor is PRIVATE -- if a client
	 wants a piece object, they must use Piece.getPieces().
	*/
	private Piece(Point[] points) {
		this.body = new Point[points.length];
		System.arraycopy(points, 0, this.body, 0, points.length);
		int w = 0;
		int h = 0;
		for (Point p : points)
		{
			w = p.x > w ? p.x : w;
			h = p.y > h ? p.y : h;
		}
		this.width = w+1;
		this.height = h + 1;

		this.skirt = new int[this.width];
		Arrays.fill(this.skirt, this.height);
		for (Point p : points)
		{
			this.skirt[p.x] = p.y < this.skirt[p.x] ? p.y : this.skirt[p.x];
		}		
	}	

	/**
	 Returns the width of the piece measured in blocks.
	*/
	public int getWidth() {
		return this.width;
	}
	
	/**
	 Returns the height of the piece measured in blocks.
	*/
	public int getHeight() {
		return this.height;
	}

	/**
	 Returns a pointer to the piece's body. The caller
	 should not modify this array.
	*/
	public Point[] getBody() {
		return this.body;
	}
	
	/**
	 Returns a pointer to the piece's skirt. For each x value
	 across the piece, the skirt gives the lowest y value in the body.
	 This useful for computing where the piece will land.
	 The caller should not modify this array.
	*/
	public int[] getSkirt() {
		return this.skirt;
	}


	/**
	 Returns a piece that is 90 degrees counter-clockwise
	 rotated from the receiver.
	 
	 <p>Implementation:
	 The Piece class pre-computes all the rotations once.
	 This method just hops from one pre-computed rotation
	 to the next in constant time.
	*/	
	public Piece nextRotation() {
		return next;
	}
	
	
	/**
	 Returns true if two pieces are the same --
	 their bodies contain the same points.
	 Interestingly, this is not the same as having exactly the
	 same body arrays, since the points may not be
	 in the same order in the bodies. Used internally to detect
	 if two rotations are effectively the same.
	*/
	public boolean equals(Piece other) {
		if (other == this)
		{
			return true;
		}

		for (Piece x = this.next; x != this; x = x.next)
		{
			if (x == other)
			{
				return true;
			} else {
				if (isEqual(x.getBody(), other.getBody()))
				{
					return true;
				}
			}
		}

		return false;
	}

	private static boolean isEqual(Point[] p1, Point[] p2)
	{
		if (p1 == null || p2 == null || p1.length != p2.length)
		{
			return false;
		}

		for (int i = 0; i < p1.length; i++ )
		{
			if (p1[i].x != p2[i].x || p1[i].y != p2[i].y)
			{
				return false;
			}
		}

		return true;
	}
	
	/**
	 Returns an array containing the first rotation of
	 each of the 7 standard tetris pieces.
	 The next (counterclockwise) rotation can be obtained
	 from each piece with the {@link #nextRotation()} message.
	 In this way, the client can iterate through all the rotations
	 until eventually getting back to the first rotation.
	*/
	public static Piece[] getPieces() {
	/*
	 Hint
	 
	 My code to produce the array of the pieces looks like the following.
	 -parsePoints computes the Point[] array
	 -The Piece constructor builds a single piece but not the rotations
	 -The helper function piecerRow() computes all the rotations of that piece
	 and connects them by their .next fields.
	 
	 Your pieces must be in the same 0..6 order to get the same output as
	 the sample solution. Only compute the array when it is first asked for.
	 Then just re-use that array for later requests.
	 
		pieces = new Piece[] {
			pieceRow(new Piece(parsePoints("0 0	0 1	0 2	0 3"))),	// 0
			pieceRow(new Piece(parsePoints("0 0	0 1	0 2	1 0"))),	// 1
			pieceRow(new Piece(parsePoints("0 0	1 0	1 1	1 2"))),	// 2
			pieceRow(new Piece(parsePoints("0 0	1 0	1 1	2 1"))),	// 3
			pieceRow(new Piece(parsePoints("0 1	1 1	1 0	2 0"))),	// 4
			pieceRow(new Piece(parsePoints("0 0	0 1	1 0	1 1"))),	// 5
			pieceRow(new Piece(parsePoints("0 0	1 0	1 1	2 0"))),	// 6
		};
	*/

		if (null == pieces || pieces.length == 0)
		{
			pieces = new Piece[] {
				pieceRow(new Piece(parsePoints("0 0	0 1	0 2	0 3"))),	// 0
				pieceRow(new Piece(parsePoints("0 0	0 1	0 2	1 0"))),	// 1
				pieceRow(new Piece(parsePoints("0 0	1 0	1 1	1 2"))),	// 2
				pieceRow(new Piece(parsePoints("0 0	1 0	1 1	2 1"))),	// 3
				pieceRow(new Piece(parsePoints("0 1	1 1	1 0	2 0"))),	// 4
				pieceRow(new Piece(parsePoints("0 0	0 1	1 0	1 1"))),	// 5
				pieceRow(new Piece(parsePoints("0 0	1 0	1 1	2 0"))),	// 6
			};
		}

		return pieces;
	}

	private static Piece pieceRow(Piece x) {
		if (isEqual(x.getBody(), parsePoints("0 0 0 1	0 2	0 3"))) {
			Piece pnext =  new Piece(parsePoints("0 0 1 0 2 0 3 0"));
			x.next = pnext;
			pnext.next = x;

		} else if (isEqual(x.getBody(), parsePoints("0 0 0 1	0 2	1 0"))) {
			Piece pnext1 =  new Piece(parsePoints("0 0 1 0 2 0 2 1"));
			x.next = pnext1;
			Piece pnext2 =  new Piece(parsePoints("0 2 1 0 1 1 1 2"));
			pnext1.next = pnext2;
			Piece pnext3 =  new Piece(parsePoints("0 0 0 1 1 1 2 1"));
			pnext2.next = pnext3;
			pnext3.next = x;

		} else if (isEqual(x.getBody(), parsePoints("0 0 1 0	1 1	1 2"))) {
			Piece pnext1 =  new Piece(parsePoints("0 1 1 1 2 0 2 1"));
			x.next = pnext1;
			Piece pnext2 =  new Piece(parsePoints("0 0 0 1 0 2 1 2"));
			pnext1.next = pnext2;
			Piece pnext3 =  new Piece(parsePoints("0 0 0 1 1 0 2 0"));
			pnext2.next = pnext3;
			pnext3.next = x;

		} else if (isEqual(x.getBody(), parsePoints("0 0 1 0	1 1	2 1"))) {
			Piece pnext =  new Piece(parsePoints("0 1 0 2 1 0 1 1"));
			x.next = pnext;
			pnext.next = x;

		} else if (isEqual(x.getBody(), parsePoints("0 1 1 1	1 0	2 0"))) {
			Piece pnext =  new Piece(parsePoints("0 0 0 1 1 1 1 2"));
			x.next = pnext;
			pnext.next = x;

		} else if (isEqual(x.getBody(), parsePoints("0 0 0 1	1 0	1 1"))) {
			x.next = x;
		} else if (isEqual(x.getBody(), parsePoints("0 0 1 0	1 1	2 0"))) {
			Piece pnext1 =  new Piece(parsePoints("0 1 1 0 1 1 1 2"));
			x.next = pnext1;
			Piece pnext2 =  new Piece(parsePoints("0 1 1 0 1 1 2 1 "));
			pnext1.next = pnext2;
			Piece pnext3 =  new Piece(parsePoints("0 0 0 1 0 2 1 1"));
			pnext2.next = pnext3;
			pnext3.next = x;

		} else {
			throw new RuntimeException("Unpexpected given piece.");
		}

		return x;
	}

	/**
	 Given a string of x,y pairs ("0 0	0 1	0 2	1 0"), parses
	 the points into a Point[] array.
	 (Provided code)
	*/
	private static Point[] parsePoints(String string) {
		// could use Arraylist here, but use vector so works on Java 1.1
		Vector points = new Vector();
		StringTokenizer tok = new StringTokenizer(string);
		try {
			while(tok.hasMoreTokens()) {
				int x = Integer.parseInt(tok.nextToken());
				int y = Integer.parseInt(tok.nextToken());
				
				points.addElement(new Point(x, y));
			}
		}
		catch (NumberFormatException e) {
			throw new RuntimeException("Could not parse x,y string:" + string);	// cheap way to do assert
		}
		
		// Make an array out of the Vector
		Point[] array = new Point[points.size()];
		points.copyInto(array);
		return(array);
	}
}
