**NOT IN FINAL REPORT**

Premise:
A mouse wants to get from one corner of an 11x11 grid to the opposite corner. 
In its way are a series of mousetraps.
The mouse must find an optimal route (ie fewest number of possible steps) from the start to the target, without going out of bounds or hitting a mousetrap.
The algorithm creates a population of mice which are given a set of random directions to follow, for which their performance is scored.
The best mouse is stored, and a new generation is created, taking parents from the previous generation with slight modifications, to try again.
This is then continued until a mouse has been created which takes an optimal route (there are multiple).
The probability of picking an optimal path uniformly at random is 4.6x10^(-62):
-There are 94 possible optimal paths
-We assume that an average path length before dying is 20, you can travel in four different directions at each square, and you can revisit squares
-There are 18 squares you cannot visit/cannot move from (the end point and the mouse traps)



Classes:
Vector: The basic functionality of a vector with integer components
Brain: Where the path of the mouse is created, stored and manipulated
Mouse: The object that 'moves' through the grid, trying to get to a designated target square
Population: Where the learning algorithm methods are, and are implemented on a set of mice
Sketch: Where the components of the visulization occurs (not involved in actual learning)
LetsLearn: Where the learning algorithm happens, and creates the visualization of the end product
