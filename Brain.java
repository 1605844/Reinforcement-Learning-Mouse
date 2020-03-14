

public class Brain{ 
	
	private Vector[] directions; //the brain consists of a set of directions that the mouse will take
	private int step; //the brain will keep track of which step it is on
	
	//----------------------------------------------------------------------------------------------------------------------
	//constructors
	
	public Brain(int size) {
		this.directions = new Vector[size]; //the brain contains an array of random unit vectors which the mouse will follow
		this.step = 0; //the brain also contains information about which step it should take next
		randomize(); 
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//getters
	
	public int getStep() {
		return this.step;
	}
	
	public Vector getDirection(int i) {
		return this.directions[i];
	}
	
	public int getBrainSize() {
		return this.directions.length;
	}
	
	
	//----------------------------------------------------------------------------------------------------------------------
	//setters
	
	public void setDirection(int i, Vector v) {
		this.directions[i] = v;
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//tells the brain that it has moved a step
	
	public void nextStep() {
		this.step++;
	}
	
	
	//----------------------------------------------------------------------------------------------------------------------
	//fills the brain with random directions
	
	public void randomize() {
		for(int i = 0;i < getBrainSize();i++) {
			double rand = 2 * Math.PI * Math.random(); //each step is chosen uniformly at random
			Vector v = new Vector(rand);
			setDirection(i,v);
		}
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//method that creates a perfect clone of this brain
	
	public Brain cloneBrain() {
		Brain clone = new Brain(getBrainSize());
		for (int i = 0;i < getBrainSize();i++) {
			clone.setDirection(i,getDirection(i));
		}
		return clone;
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//method that mutates random entries in this brain
	
	public void mutateBrain() {
		double mutationRate = 0.05; //the probability that any single entry in the brain will be changed, from a uniform distribution
		for (int i = 0;i < getBrainSize();i++) {
			double rand = Math.random();
			if (rand < mutationRate) {
				double angle = 2 * Math.PI * Math.random(); //the new entry is chosen at random, from a uniform distribution
				Vector v = new Vector(angle);
				setDirection(i,v);
			}
		}
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//turns the brain into a string of vectors
	
	public String toString() {
		String s = "";
		for (int i = 0; i < getBrainSize(); i++) {
			s += " " + this.directions[i].toString();
		}
		
		return s;
	}
	
	
	
	
	
}















