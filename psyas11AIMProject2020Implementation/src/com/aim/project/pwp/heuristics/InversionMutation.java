package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;


public class InversionMutation extends HeuristicOperators implements HeuristicInterface {
	
	private final Random oRandom;
	
	public InversionMutation(Random oRandom) {
	
		super();
		
		this.oRandom = oRandom;
	}

	@SuppressWarnings("null")
	@Override
	public double apply(PWPSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {

		int randomIndex1 = oRandom.nextInt(oSolution.getSolutionRepresentation().getNumberOfLocations()); //get 2 random indices
		int randomIndex2 = oRandom.nextInt(oSolution.getSolutionRepresentation().getNumberOfLocations());
		
		while (randomIndex1 >= randomIndex2) { //make sure than 2nd index is greater than first if not keep randomizing
			
			randomIndex1 = oRandom.nextInt(oSolution.getSolutionRepresentation().getNumberOfLocations());
			randomIndex2 = oRandom.nextInt(oSolution.getSolutionRepresentation().getNumberOfLocations());
		}
		
		int midpoint= randomIndex1 + ((randomIndex2 + 1) - randomIndex1) / 2; //take the midpoint of all the indices between the 2 random indices chosen
		int correspondingSwap = randomIndex2;
		
		for (int i = randomIndex1 ; i < midpoint ; i++) { //go from the first index, until the midpoint
			swapLocations(oSolution, oSolution.getSolutionRepresentation().getSolutionRepresentation()[i], oSolution.getSolutionRepresentation().getSolutionRepresentation()[correspondingSwap]); //swap the chosen index with the corresponding chosen index after the midpoint
			correspondingSwap--; //decrement the count so that the next iteration will start from the index after randomIndex1 and swap it with the index before randomIndex2
		} //repeat the process until all indices between the two chosen start & end points are swapped
		
		if (dIntensityOfMutation > 0.0 && dIntensityOfMutation < 0.2 ) { //intensity of mutation
			
			for (int i=0 ; i <=1 ; i++) {
				apply(oSolution, dDepthOfSearch,dIntensityOfMutation);
			}
			
			return oSolution.getObjectiveFunctionValue();
			
		}
		else if (dIntensityOfMutation > 0.2 && dIntensityOfMutation < 0.4 ) {
			
			for (int i=0 ; i <=2 ; i++) {
				apply(oSolution, dDepthOfSearch,dIntensityOfMutation);
			}
			return oSolution.getObjectiveFunctionValue();
			
		}
		else if (dIntensityOfMutation > 0.4 && dIntensityOfMutation < 0.6 ) {
			
			for (int i=0 ; i <=3 ; i++) {
				apply(oSolution, dDepthOfSearch,dIntensityOfMutation);
			}
			return oSolution.getObjectiveFunctionValue();
			
		}
		else if (dIntensityOfMutation > 0.6 && dIntensityOfMutation < 0.8 ) {
			
			for (int i=0 ; i <=4 ; i++) {
				apply(oSolution, dDepthOfSearch,dIntensityOfMutation);
			}
			return oSolution.getObjectiveFunctionValue();
			
		}
		else if (dIntensityOfMutation > 0.8 && dIntensityOfMutation < 1.0 ) {
			
			for (int i=0 ; i <=5; i++) {
				apply(oSolution, dDepthOfSearch,dIntensityOfMutation);
			}
			return oSolution.getObjectiveFunctionValue();
			
		}
		else if (dIntensityOfMutation == 1.0) {
			
			for (int i=0 ; i <=6 ; i++) {
				apply(oSolution, dDepthOfSearch,dIntensityOfMutation);
			}
			return oSolution.getObjectiveFunctionValue();
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
