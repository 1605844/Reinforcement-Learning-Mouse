import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
Runs the learning algorithm, and illustrates the path that the final mouse will take.
Red squares are mouse traps, green squares show the path that the mouse takes, from the top left to the bottom right.
Each generation includes 2500 mice, with a mutation rate specified in the brain class.
-> The population size was chosen in conjunction with the mutation rate to decrease both the number of generations needed, and the
	computation time between generations
Each mouse will continue moving until it goes out of the grid, hits a mouse trap or reaches the target.
Once all of the mice are dead, the best mouse (according to the calculateFitness method in the Mouse class) is kept, and a new population of 1000
	mice will be created, with the best of the previous mice being the first entry, using parent mice from the previous generation (being 
	chosen using the selectParent method in the Population class), which are then mutated according the mutateBrain method.
This process continues until a mouse reaches the target square in the optimal number of steps (20)
*/

public class LetsLearn extends JPanel {
	
	//creates a JPanel which displays the final route that the optimal mouse takes
	public void drawMap(Mouse m) {
		JFrame f = new JFrame("Final Route");
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(566,589);
		f.setFocusable(true);
		
		Sketch s = new Sketch(m);
		//s.drawTiles();
		f.add(s);
		
			
	}
	
	//----------------------------------------------------------------------------------------------------------------------	
	//Main method which runs the learning algorithm and then draws the final route
	
	public static void main (String[] args) {
		
		Population test = new Population(2500);
		
		while (test.getMaxSteps() > 20) {
			if(test.allMiceDead()) {
				test.calcAllFitnesses();
				test.newGen();
				test.mutatePopulation();
			} else {
				test.updatePop();
			}
		}
		//System.out.println(test.getMaxSteps() + "\n");
		
		
		Mouse M = test.getMouse(0).baby();
		LetsLearn L = new LetsLearn();
		L.drawMap(M);
	}
	
	
}