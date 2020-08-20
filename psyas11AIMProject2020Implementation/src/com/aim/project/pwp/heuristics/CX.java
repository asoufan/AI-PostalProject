package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.XOHeuristicInterface;


public class CX extends HeuristicOperators implements XOHeuristicInterface {
	
	private final Random oRandom;
	
	private ObjectiveFunctionInterface oObjectiveFunction;

	public CX(Random oRandom) {
		
		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {

		return solution.getObjectiveFunctionValue();
	}

	@SuppressWarnings("null")
	@Override
	public double apply(PWPSolutionInterface p1, PWPSolutionInterface p2,
			PWPSolutionInterface c, double depthOfSearch, double intensityOfMutation) {
		
		int[] parent1 = p1.getSolutionRepresentation().getSolutionRepresentation();
		int[] parent2 = p2.getSolutionRepresentation().getSolutionRepresentation();
		

		int child1[] = new int[parent1.length]; //create child1
		int child2[] = new int[parent1.length]; //create child2
		
		int cyclePoint = oRandom.nextInt(parent1.length); //pick start point
		
		child1[cyclePoint] = parent1[cyclePoint];//set the start points to be the first locations added into the array
		child2[cyclePoint] = parent2[cyclePoint];
		
		for (int k =0 ; k < parent1.length -1 ; k++) { //exclude one iteration since already placed first elements in the child arrays above
			
			for(int i =0 ; i < parent1.length ; i++) 
			{ //cycle through array of parent1
				
				if (parent1[i] == parent2[cyclePoint]) 
				{ //find the value at index i in parent1 that corresponds to the value at the index in parent2
					child1[i] = parent1[i]; //set the value at that index in child1 to be the value of the index found 
					child2[i] = parent2[i];//set the value at that index in child2 to be the value of the same index in parent2
					cyclePoint = parent2[i]; //set the new start/cycle point to be the value of at index in parent2
				}
			}
		}
		
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
	public void setObjectiveFunction(ObjectiveFunctionInterface oObjectiveFunction) {
		
		this.oObjectiveFunction = oObjectiveFunction;
	}
	
	
}
