


public class Population {
	
	private Mouse[] mice;
	//The generation of the population starts at 1, and the maximum number of steps each mouse can take is the size of their brains
	private int gen = 1, bestMouse = 0, maxSteps = 50, size;
	
	//----------------------------------------------------------------------------------------------------------------------
	//constructor
	
	public Population(int size) {
		this.mice = new Mouse[size];
		for (int i = 0; i < size; i++) {
			this.mice[i] = new Mouse(); //creates a population of random mice, of a chosen size
		}
		this.size = size;
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//getters
	
	public Mouse getMouse(int i) {
		return this.mice[i];
	}
	
	public int getGen() {
		return gen;
	}
	
	public int getMaxSteps() {
		return maxSteps;
	}
	
	public int getBestMouse() {
		return bestMouse;
	}
	
	public int popSize() {
		return this.size;
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//setters
	
	public void setMouse(int i, Mouse m) {
		this.mice[i] = m;
	}
	
	public void nextGen() {
		gen++;
	}
	
	public void setMaxSteps(int s) {
		maxSteps = s;
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//updates all of the mice - similar to update method in Mouse
	
	public void updatePop() {
		for (int i = 0; i < popSize(); i++) {
			if (getMouse(i).stepNumber() > getMaxSteps()) {
				getMouse(i).setAlive(false);
			} else {
				getMouse(i).update();
				//System.out.println(getMouse(i).getPos());
			}
		}
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//says whether all of the mice in the population are dead or not
	
	public boolean allMiceDead() {
		for (int i = 0; i < popSize(); i++) {
			Mouse m = getMouse(i);
			if (m.isAlive() && !m.atTarget()) {
				return false; //if any are alive and not at the target, then not all of them are dead
			}
		}
		return true;
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//calculates all of the fitnesses of all the mice
	
	public void calcAllFitnesses() {
		for (int i = 0; i < popSize(); i++) {
			getMouse(i).calculateFitness();
		}
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//returns sum of all of the fitnesses
	
	public double sumOfFitnesses() {
		calcAllFitnesses();
		double fitSum = 0;
		for (int i = 0; i < popSize(); i++) {
			fitSum += getMouse(i).getFitness();
			//System.out.println(getMouse(i).getFitness());
		}
		
		return fitSum;
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//mutates the next generation of mice
	
	public void mutatePopulation() {
		for (int i = 1; i < popSize(); i++) {
			getMouse(i).getBrain().mutateBrain();
		}
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//finds the best scoring mouse
	
	public void setBestMouse() {
		
		calcAllFitnesses();
		double max = 0;
		int maxIndex = 0;
		
		for (int i = 0; i < popSize(); i++) {
			if(getMouse(i).getFitness() > max) {
				max = getMouse(i).getFitness(); //if this mouse has a higher fitness than the previous highest, it becomes the best
				maxIndex = i;
			}
		}
		
		//if there is a mouse that has completed the course in fewer steps, all futre mice can take a maximum of the same amount of steps
		//If they take more, their fitness cannot be any higher and therefore shouldn't be considered
		if (getMouse(maxIndex).getFitness() > 1)
			setMaxSteps(getMouse(maxIndex).stepNumber());
		
		bestMouse = maxIndex;
		
		//System.out.println("Best mouse is: " + maxIndex);
		//System.out.println("Pos of best mouse: " + getMouse(maxIndex).getPos().toString());
		//System.out.println("Best score = " +max);
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//selects a parent for each mouse in the next generation
	
	
	public Mouse selectParent() {
		double sum = sumOfFitnesses();
		//System.out.println(sum);
		double rand = sum * Math.random();
		
		//Each mouse is weighted by their fitness. The higher their fitness, the more likely they are to be picked uniformly at
		//	random to be the parent for a mouse in the next generation
		//This ensure the better performing mice are tweaked more often than than the bad ones
		double runSum = 0;
		for (int i = 0; i < popSize(); i++) {
			runSum += getMouse(i).getFitness();
			if (runSum > rand)
				return getMouse(i);
		}
		
		//It should never get here
		System.out.println("returning null");
		return null;
		
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//creates the next generation of mice, by creating a new population, and filling it with the mutated babies of the
	//	previous population
	
	public void newGen() {
		setBestMouse();
		
		Population newMice = new Population(popSize());
		//System.out.println("I am putting mouse " + getBestMouse() + " in the first entry");
		Brain b = getMouse(getBestMouse()).getBrain();
		newMice.getMouse(0).setBrain(b);
		newMice.getMouse(0).resetMouse();
		
		for (int i = 1; i < popSize(); i++) {
			Mouse parent = selectParent();
			Brain B = parent.getBrain();
			newMice.getMouse(i).setBrain(B);
			newMice.getMouse(i).resetMouse();
		}
		
		
		for (int i = 0; i < popSize(); i++) {
			setMouse(i, newMice.getMouse(i));
		}
		
		nextGen();
		System.out.println(this.gen); //shows which generation the program is on
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//main method for testing
	
	public static void main(String[] args) {
		
		Population test = new Population(1000);
		
		while (test.getMaxSteps() > 30) {
			if(test.allMiceDead()) {
				//System.out.println("After all dead: " +test.getMouse(0).getFitness());
				test.calcAllFitnesses();
				test.newGen();
				test.mutatePopulation();
			} else {
				test.updatePop();
				//System.out.println("step");
			}
		}
		System.out.println(test.getMaxSteps());
		//test.setBestMouse();
		//String s = test.getMouse(test.getBestMouse()).getBrain().toString();
		//System.out.println(s);
		
	}
	
}










