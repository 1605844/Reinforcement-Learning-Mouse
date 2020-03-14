

public class Mouse {
	
	
	private Brain brain;
	private Vector pos, vel;
	private boolean alive, reachedGoal;
	private double fitness;
	
	Vector target = new Vector(10,10); //the target square is (10,10)
	
	//----------------------------------------------------------------------------------------------------------------------
	//constructor

	public Mouse() {
		this.brain = new Brain(50); //each mouse is given a brain with 50 directions
		this.pos = new Vector(0,0); //mouse starts at (1,1)
		this.vel = this.brain.getDirection(0); //first step is the first vector in the brain
		this.alive = true; //mice start alive
		this.reachedGoal = false; //mice don't start at the goal
		this.fitness = 0; //got to earn that fitness baby
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//getters
	
	public Brain getBrain() {
		return this.brain;
	}
	
	public int stepNumber() {
		return this.brain.getStep();
	}
	
	public Vector getPos() {
		return this.pos;
	}
	
	public Vector getVel() {
		return this.vel;
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	public boolean atTarget() {
		return this.reachedGoal;
	}
	
	public double getFitness() {
		return this.fitness;
	}
	
	public Vector getPreviousPos() {
		if (stepNumber() == 0) 
			return new Vector();
			
		Vector v = getPos();
		Vector V = v.subtract(getBrain().getDirection(stepNumber()));
		return V;
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//setters
	
	public void setPos(Vector v) {
		this.pos = v;
	}
	
	public void setVel(Vector v) {
		this.vel = v;
	}
	
	public void setFitness(double f) {
		this.fitness = f;
	}
	
	public void setBrain(Brain b){
		for (int i = 0; i < this.brain.getBrainSize(); i++) {
			this.brain.setDirection(i, b.getDirection(i));
		}
	}
	
	public void setAlive(boolean a) {
		this.alive = a;
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//Sets the information of the mouse to be like it was before it moved
	
	public void resetMouse() {
		this.pos = new Vector(0,0); 
		this.vel = this.brain.getDirection(0); 
		this.alive = true; 
		this.reachedGoal = false; 
		this.fitness = 0; 
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//this method calculates the fitness of the mice when they have either died or reached the target
	
	//If the mouse has reached the target square, its fitness is inversely related to the number of steps taken (plus one so that
	// 	its fitness will be better than that of a mouse who hasn't reached the target), and if it hasn't reached the target, its
	//	fitness is related to the inverse of its distance to the target (wrt the distance method in Vector)
	public void calculateFitness() {
		if (atTarget()) {
			double sN = stepNumber();
			double N = 100/sN;
			double f = 1 + N;
			this.fitness = f;
		} else {
			double u = (getPos().distance(target));
			double f = 1/u;
			this.fitness = f;
		}
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//produces a perfect clone of this mouse
	
	public Mouse baby() {
		Mouse m = new Mouse();
		Brain b = getBrain().cloneBrain();
		m.setBrain(b);
		m.setVel(b.getDirection(0));
		
		return m;
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//if this mouse has more steps to take, and isn't dead/at the goal, this method moves it
	
	public void move() {
		if (getBrain().getBrainSize() - 1 > stepNumber()) { //if the mouse has more steps it can take, it moves
			setPos(getPos().add(getVel()));
			getBrain().nextStep();
			int s = getBrain().getStep();
			setVel(getBrain().getDirection(s));
		} else {
			this.alive = false; //if it doesn't have any more steps in its brain, it dies
		}
	}
		
	//----------------------------------------------------------------------------------------------------------------------	
	//this method returns whether the mouse has gone out of bounds (or on a mouse trap)
	
	public boolean outOfBounds() {
		int x = getPos().getX();
		int y = getPos().getY();
		
		if (x < 0 || x > 10 || y < 0 || y > 10 ) { //the grid is an 11x11 square, where the mouse starts at (0,0)
			return true;
		} else if (onTrap()) {
			return true;
		} else {
			return false;
		}
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//subsidary to the out of bounds function: there are mouse traps placed on the grid, if it goes to any of these squares,
	//	it dies
	
	public boolean onTrap() {
		int x = getPos().getX();
		int y = getPos().getY();
		
		if (x == 0 && (y == 1 || y == 9)) {
			return true;
		} else if(x == 2 && (y == 2 || y == 8)) {
			return true;
		} else if (x == 3 && (y == 5 || y == 9)) {
			return true;
		} else if (x == 4 && y == 6) {
			return true;
		} else if (x == 5 && (y == 3 || y == 6 || y == 7)) {
			return true;
		} else if (x == 6 && y == 1) {
			return true;
		} else if (x == 7 && (y == 4 || y == 10)) {
			return true;
		} else if (x == 8 && y == 8) {
			return true;
		} else if (x == 9 && (y == 4 || y == 9)) {
			return true;
		} else if (x == 10 && y == 9) {
			return true;
		}
		
		return false;
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//this method updates the mouse for each step it attempts to take
	
	public void update() {
		if (isAlive() && !atTarget()) { //the mouse can only moved if it's not dead or at the target
			move();
			if (outOfBounds()) {
				this.alive = false; //if it goes out of bounds after it moves, it dies
			} else if (getPos().distance(target) == 0) {
				this.reachedGoal = true; //if it has reached the target square, it no longer needs to move
			}
		}
		calculateFitness(); //updates its fitness after every step it takes
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//main method for testing
	
	public static void main(String[] args) {
		
		Mouse m = new Mouse();
		System.out.println("First move = " + m.getVel().toString() + "\n");
		
		while(m.isAlive()) {
			m.update();
			
			//System.out.println("Pos = " + m.getPos().toString());
			//System.out.println("Vel = " + m.getVel().toString());
			//System.out.println(m.isAlive() + "\n");
		}
		
		System.out.println("\n");
		System.out.println("Final Position = " + m.getPos().toString());
		System.out.println("Final Move = " + m.getVel().toString());
		System.out.println("At Target: " + m.atTarget());
		System.out.println("Alive: " + m.isAlive());
		System.out.println("Fitness = " + m.getFitness());
		System.out.println("Steps = " + m.stepNumber());
		
		Mouse b = m.baby();
		System.out.println("\n");
		System.out.println("New Position = " + b.getPos().toString());
		System.out.println("First Move = " + b.getVel().toString());
		System.out.println("At Target: " + b.atTarget());
		System.out.println("Alive: " + b.isAlive());
		System.out.println("Fitness = " + b.getFitness() + "\n");
		
		while(b.isAlive()) {
			b.update();
			
			//System.out.println("Pos = " + b.getPos().toString());
			//System.out.println("Vel = " + b.getVel().toString());
			//System.out.println(b.isAlive() + "\n");
		}
		
		System.out.println("Fitness = " + b.getFitness() + "\n");
		System.out.println("Final Position = " + b.getPos().toString());
		System.out.println("Steps = " + b.stepNumber());
		
	}
	
	
}

















