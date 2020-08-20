package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;


public class AdjacentSwap extends HeuristicOperators implements HeuristicInterface {

	private final Random oRandom;
	
	public AdjacentSwap(Random oRandom) {

		super();
		
		this.oRandom = oRandom;
	}

	@SuppressWarnings("null")
	@Override
	public double apply(PWPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {
		
		
		int randomIndex = oRandom.nextInt(solution.getSolutionRepresentation().getNumberOfLocations()); //pick a random location, assign it to randomIndex
		int nextIndex = randomIndex + 1 ; //assign nextIndex to the next location
	
		
		if (randomIndex == solution.getSolutionRepresentation().getNumberOfLocations()) { //if the index chosen is the last index
			
			swapLocations(solution, randomIndex, solution.getSolutionRepresentation().getSolutionRepresentation()[0]); //swap with the first location
			
		}
		else {
		
			swapLocations(solution, randomIndex, nextIndex); //otherwise swap with next index
		}
			
			
			if (intensityOfMutation > 0.0 && intensityOfMutation < 0.2 ) { //intensity of mutation
				
				for (int i=0 ; i <=1 ; i++) {
					apply(solution, depthOfSearch,intensityOfMutation);
				}
				
				return solution.getObjectiveFunctionValue();
				
			}
			else if (intensityOfMutation > 0.2 && intensityOfMutation < 0.4 ) {
				
				for (int i=0 ; i <=2 ; i++) {
					apply(solution, depthOfSearch,intensityOfMutation);
				}
				return solution.getObjectiveFunctionValue();
				
			}
			else if (intensityOfMutation > 0.4 && intensityOfMutation < 0.6 ) {
				
				for (int i=0 ; i <=4 ; i++) {
					apply(solution, depthOfSearch,intensityOfMutation);
				}
				return solution.getObjectiveFunctionValue();
				
			}
			else if (intensityOfMutation > 0.6 && intensityOfMutation < 0.8 ) {
				
				for (int i=0 ; i <=8 ; i++) {
					apply(solution, depthOfSearch,intensityOfMutation);
				}
				return solution.getObjectiveFunctionValue();
				
			}
			else if (intensityOfMutation > 0.8 && intensityOfMutation < 1.0 ) {
				
				for (int i=0 ; i <=16; i++) {
					apply(solution, depthOfSearch,intensityOfMutation);
				}
				return solution.getObjectiveFunctionValue();
				
			}
			else if (intensityOfMutation == 1.0) {
				
				for (int i=0 ; i <=32 ; i++) {
					apply(solution, depthOfSearch,intensityOfMutation);
				}
				return solution.getObjectiveFunctionValue();
			}
			else {
				return (Double) null;
			}
		
	}

	@Override
	public boolean isCrossover() {

		return false;
	}

	@Override
	public boolean usesIntensityOfMutation() {

		return true;
	}

	@Override
	public boolean usesDepthOfSearch() {

		return false;
	}

}

