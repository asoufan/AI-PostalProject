package com.aim.project.pwp.heuristics;


import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;


/**
 * 
 * @author Warren G. Jackson
 * Performs adjacent swap, returning the first solution with strict improvement
 *
 */
public class NextDescent extends HeuristicOperators implements HeuristicInterface {
	
	private final Random oRandom;
	
	public NextDescent(Random oRandom) {
	
		super();
		
		this.oRandom = oRandom;
	}

	@Override
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {

		int randomIndex = oRandom.nextInt(oSolution.getSolutionRepresentation().getNumberOfLocations()); //pick a random starting point
		
		double bestObjFunctionValue = oSolution.getObjectiveFunctionValue(); //get the current objective function value & set it to the best
		
		int numberOfAcceptances = determineDepth(dDepthOfSearch); //the number of improvements accepted
		int currentlyAccepted=0; //to keep track of how many solutions were accepted
		
		for (int i = 0 ; i <= oSolution.getSolutionRepresentation().getNumberOfLocations() ; i++) { //loop through the locations in the solution
			
			if (randomIndex == oSolution.getSolutionRepresentation().getNumberOfLocations()) { //if the last index is chosen/reached, then swap with the last to loop circularly and go through all locations 
				
				swapLocations(oSolution, randomIndex, oSolution.getSolutionRepresentation().getSolutionRepresentation()[0]); //swap last & first
			}
			
			swapLocations(oSolution, randomIndex, randomIndex+1); //use adjacent swap to swap the random index with the following one
			
			
			if (oSolution.getObjectiveFunctionValue() > bestObjFunctionValue) { //if the new value of the objective function is better than the previous
				
				bestObjFunctionValue = oSolution.getObjectiveFunctionValue(); //update the best objective function value
				currentlyAccepted++; //increment number of improvements
				
			}
			else { //if the new value of the objective function is not an improvement then swap back 
				swapLocations(oSolution, randomIndex +1, randomIndex);	
			}
			
			randomIndex++; //move onto the next location
			
			if (currentlyAccepted >= numberOfAcceptances) { //if the number of improvements exceeds the number of permitted improvements determined by depth of search
				break; //exit the for loop
			}

		}
		
		return bestObjFunctionValue;
		
	}

	@Override
	public boolean isCrossover() {

		return false;
	}

	@Override
	public boolean usesIntensityOfMutation() {

		return false;
	}

	@Override
	public boolean usesDepthOfSearch() {

		return true;
	}
	

}
