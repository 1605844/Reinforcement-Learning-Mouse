import java.lang.*;

//A standard class to create the basic properties of a vector
//The mouse will be walking along an integer grid, so the vectors are integer valued

public class Vector {
	
	private int x,y;
	
	//----------------------------------------------------------------------------------------------------------------------
	//constructors
	
	//creates a zero vector
	public Vector() {
		this.x = 0;
		this.y = 0;
	}
	
	//creates an integer vector
	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	//creates a unit vector along the basis directions, with any real number as input
	public Vector(double angle) {
		double h = Math.cos(angle);
		double v = Math.sin(angle);
		
		if (v > 0) {
			if (h < 0) {
				this.x = -1;
				this.y = 0;
			} else {
				this.x = 1;
				this.y = 0;
			}
		} else {
			if (h < 0) {
				this.x = 0;
				this.y = -1;
			} else {
				this.x = 0;
				this.y = 1;
			}
		}
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//getters
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	
	//----------------------------------------------------------------------------------------------------------------------
	//method to calculate this vectors distance to an input vector using the maximum metric
	
	public int distance(Vector v) {
		int xDist = Math.abs(getX() - v.getX());
		int yDist = Math.abs(getY() - v.getY());
		
		int dist = xDist + yDist;
		return dist;
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//method to add this vector to an input vector, and returns the results
	
	public Vector add(Vector v) {
		int xVal = getX() + v.getX();
		int yVal = getY() + v.getY();
		
		return new Vector(xVal,yVal);
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//method to subtract this vector to an input vector, and returns the results
	
	public Vector subtract(Vector v) {
		int xVal = getX() - v.getX();
		int yVal = getY() - v.getY();
		
		return new Vector(xVal,yVal);
	}	
	
	//----------------------------------------------------------------------------------------------------------------------
	//Turns the vector into a string
	
	public String toString() {
		String s = "(" + getX() + "," + getY() + ")";
		return s;
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//Main method to test
	
	public static void main(String[] args) {
		

		
	}
	
	
	
	
	
}















