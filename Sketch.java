import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Sketch extends JPanel {
	
	private int[][] s = new int[11][11]; //the JPanel will be filled with 11 squares of varying colour
	/*
	0 = unvisited
	1 = visited
	2 = trap
	*/
	
	//----------------------------------------------------------------------------------------------------------------------
	//constructor
	
	//The constructor is given the final mouse, and sends it through the grid, changing the colour of the squares as it goes along
	public Sketch(Mouse m) {
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				this.s[i][j] = 0;
			}
		}
		
		this.s[10][10] = 1;
		
		//mousetraps
		this.s[0][1] = 2;
		this.s[0][9] = 2;
		this.s[2][2] = 2;
		this.s[2][8] = 2;
		this.s[3][5] = 2;
		this.s[3][9] = 2;
		this.s[4][6] = 2;
		this.s[5][3] = 2;
		this.s[5][6] = 2;
		this.s[5][7] = 2;
		this.s[6][1] = 2;
		this.s[7][4] = 2;
		this.s[7][10] = 2;
		this.s[8][0] = 2;
		this.s[9][4] = 2;
		this.s[9][9] = 2;
		this.s[10][9] = 2;
		
		do {
			Vector v = m.getPos();
			//System.out.println(v.toString());
			this.s[v.getX()][v.getY()] = 1;
			m.update();
			
		} while (!m.atTarget() && m.isAlive());
	
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	
	public void drawTiles() {
		
		String Board = new String();
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				Board = Board + "	" +getVal(i,j);
			}
			Board+= "\n";
		}
		
		System.out.println(Board);
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	
	private int getVal(int i, int j) {
		return this.s[i][j];
	}
	
	//----------------------------------------------------------------------------------------------------------------------
	//creates the graphic of the grid to add to the end JPanel
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.ORANGE);
		
		//-------------------------------------------------------------------------------
		
		class SquareColours {
			
			private int x,y,z;
			
			//---------------------------------------------------------------------------
			
			SquareColours(int x, int y, int z) {
				this.x = x;
				this.y = y;
				this.z = z;
			}
			
			//---------------------------------------------------------------------------
			
			public void chooseSquareColours() {
				int k = this.z;
				
				switch(k) {
					case 0:
						g.setColor(new Color(0,0,255)); //draws unvisited squares as blue
						break;
					case 1:
						g.setColor(new Color(0,255,0)); //draws squares on the mouse's path as green
						break;
					case 2: 
						g.setColor(new Color(255,0,0)); //draws the mouse trap squares as red
						break;
					case 3: 
						g.setColor(Color.BLACK);
						break;
					default: g.setColor(new Color(100,100,100));
				}
				
				
			}
			
			//---------------------------------------------------------------------------
			
			public void drawSquares(int i, int j) {
					g.fillRect(i*50,j*50,49,49);
			}
			
		}
		
		//-------------------------------------------------------------------------------
		
		for (int i = 0;i < 11;i++) {
			for (int j = 0;j < 11; j++) {
				SquareColours A = new SquareColours(i,j,getVal(i,j));
				A.chooseSquareColours();
				A.drawSquares(i,j);
			}
		}
		
		/*SquareColours A = new SquareColours(0,0,getVal(0,0));
		A.chooseSquareColours();
		A.drawSquares(0,0);
		
		SquareColours B = new SquareColours(1,2,getVal(1,2));
		B.chooseSquareColours();
		B.drawSquares(1,2);*/
		
	}
	
	
	
}




