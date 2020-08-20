package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.XOHeuristicInterface;


public class OX extends HeuristicOperators implements XOHeuristicInterface {
	
	private final Random oRandom;
	
	private ObjectiveFunctionInterface oObjectiveFunction;

	public OX(Random oRandom) {
		
		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {

		return oSolution.getObjectiveFunctionValue();
	}

	@SuppressWarnings("null")
	@Override
	public double apply(PWPSolutionInterface p1, PWPSolutionInterface p2,
			PWPSolutionInterface c, double depthOfSearch, double intensityOfMutation) {
		
		int[] parent1 = p1.getSolutionRepresentation().getSolutionRepresentation();
		int[] parent2 = p2.getSolutionRepresentation().getSolutionRepresentation();
		int tourSize = 6;
		
		double objValue;
		
		int child1[] = new int[parent1.length]; //create child1
		int child2[] = new int[parent1.length]; //create child2
		
		int startPoint = oRandom.nextInt(parent1.length); //pick start point
		int endPoint =  startPoint + tourSize;//pick end point after it
		
		while (endPoint == parent1.length) { //if end point is last location then pick another start or end point
			
			startPoint = oRandom.nextInt(parent1.length);
			endPoint =  startPoint + tourSize;
		}
		
		
		for (int i = startPoint ; i < endPoint ; i++) { //from start to end point, copy the locations and place them in the children arrays
	
			child1[i] = parent1[i]; //do this for both child 1 and 2
			child2[i] = parent2[i];

		}
		
		child1 = fillChildren(child1, parent2, tourSize, endPoint); //use the defined method below to complete the crossover and fill the children arrays
		child2 = fillChildren(child2, parent1, tourSize, endPoint);
		
		double pickChild = getRandomDoubleBetween(0,1); //pick a random child
		
		if (pickChild > 0.5) {
			
			c.getSolutionRepresentation().setSolutionRepresentation(child1); //set the solution representation of c to be of child1
		}
		else {
			
			c.getSolutionRepresentation().setSolutionRepresentation(child2); //set the solution of c to be of child2
		}
		
		p1.getSolutionRepresentation().setSolutionRepresentation(child1);//set the children to be the new parents before starting the next crossover based on intensity of mutation
		p2.getSolutionRepresentation().setSolutionRepresentation(child2);
		
		
		
		if (intensityOfMutation > 0.0 && intensityOfMutation < 0.2 ) { //intensity of mutation
			
			for (int i=0 ; i <=1 ; i++) {
				apply(p1, p2, c, depthOfSearch, intensityOfMutation);
			}
			
			return c.getObjectiveFunctionValue();
			
		}
		else if (intensityOfMutation > 0.2 && intensityOfMutation < 0.4 ) {
			
			for (int i=0 ; i <=2 ; i++) {
				apply(p1, p2, c, depthOfSearch, intensityOfMutation);
			}
			return c.getObjectiveFunctionValue();
			
		}
		else if (intensityOfMutation > 0.4 && intensityOfMutation < 0.6 ) {
			
			for (int i=0 ; i <=4 ; i++) {
				apply(p1, p2, c, depthOfSearch, intensityOfMutation);
			}
			return c.getObjectiveFunctionValue();
			
		}
		else if (intensityOfMutation > 0.6 && intensityOfMutation < 0.8 ) {
			
			for (int i=0 ; i <=8 ; i++) {
				apply(p1, p2, c, depthOfSearch, intensityOfMutation);
			}
			return c.getObjectiveFunctionValue();
			
		}
		else if (intensityOfMutation > 0.8 && intensityOfMutation < 1.0 ) {
			
			for (int i=0 ; i <=16; i++) {
				apply(p1, p2, c, depthOfSearch, intensityOfMutation);
			}
			return c.getObjectiveFunctionValue();
			
		}
		else if (intensityOfMutation == 1.0) {
			
			for (int i=0 ; i <=32 ; i++) {
				apply(p1, p2, c, depthOfSearch, intensityOfMutation);
			}
			return c.getObjectiveFunctionValue();
		}
		else {
			return (Double) null;
		}
	
		
		
		
	}

	@Override
	public boolean isCrossover() {

		return true;
	}

	@Override
	public boolean usesIntensityOfMutation() {

		return true;
	}

	@Override
	public boolean usesDepthOfSearch() {

		return false;
	}


	@Override
	public void setObjectiveFunction(ObjectiveFunctionInterface f) {
		
		this.oObjectiveFunction = f;
	}
	
	
	public int[] fillChildren(int[] child, int[] parent, int tourSize, int endPoint) { //method for filling child1 with the locations from parent2 after they've been filled with the locations from the original parent1 (that was used to create child1)
		
		int locationInParent = endPoint; //keeps track of which location we are inserting from the parent
		int locationInChild = endPoint; //keeps track of where in the child array we are
		//the logic behind having 2 counters is because the locationInChild is only incremented when a location is added, this happens in all cases except when there is a duplicate location
		
		for (int j = 0 ; j < parent.length - tourSize; j++) { //fill the children array with the rest of the locations using OX principles until it reaches the starting point (length- tour size steps away from the end point)
			
			//1.make sure you are not putting in an index that is already in the array
			for (int c =0 ; c < child.length ; c++) { //go through the child array and make sure that the element to be inserted is not already there
				
				if (child[locationInChild] == child[c]) { //if it is then move on to next location
					locationInParent++; //move on to the next location in the parent, don't increment the locationInChild counter because nothing has been added
				}
			}
			
			//2.check if the location is the last element so you can start from the beginning of the parent for the next iteration
			if ( locationInParent == child.length) { //if the location to be inserted is the last element, then 
				
				child[locationInChild] = parent[locationInParent]; //insert and then reset to beginning of the array
				locationInParent = 0;
				locationInChild =0;
			}
			
			//3.if both tests pass then insert the element from the parent into the child
			child[locationInChild] = parent[locationInParent];
			locationInParent++;
			locationInChild++;

		}
		
		return child;
	}
	
	
	
	
}
