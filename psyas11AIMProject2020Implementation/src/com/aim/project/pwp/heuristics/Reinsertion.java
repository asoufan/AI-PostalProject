package com.aim.project.pwp.heuristics;

import java.util.Random;

import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;


public class Reinsertion extends HeuristicOperators implements HeuristicInterface {

	private final Random oRandom;
	
	public Reinsertion(Random oRandom) {

		super();
		
		this.oRandom = oRandom;
	}

	@SuppressWarnings("null")
	@Override
	public double apply(PWPSolutionInterface solution, double depthOfSearch, double intensityOfMutation) {

		int randomIndex = oRandom.nextInt(solution.getSolutionRepresentation().getNumberOfLocations()); //get index to be reinserted
		int chosenDeliveryLocation = oRandom.nextInt(solution.getSolutionRepresentation().getNumberOfLocations()); //get location to be reinserted at
		
		int[] locations = solution.getSolutionRepresentation().getSolutionRepresentation(); //get the array of locations
		int[] newArray = reInsert(locations, chosenDeliveryLocation, randomIndex); //reinsert using the method devised below
		
		solution.getSolutionRepresentation().setSolutionRepresentation(newArray); //set the new solution representation to be the new array
		
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
			
			for (int i=0 ; i <=3 ; i++) {
				apply(solution, depthOfSearch,intensityOfMutation);
			}
			return solution.getObjectiveFunctionValue();
			
		}
		else if (intensityOfMutation > 0.6 && intensityOfMutation < 0.8 ) {
			
			for (int i=0 ; i <=4 ; i++) {
				apply(solution, depthOfSearch,intensityOfMutation);
			}
			return solution.getObjectiveFunctionValue();
			
		}
		else if (intensityOfMutation > 0.8 && intensityOfMutation < 1.0 ) {
			
			for (int i=0 ; i <=5; i++) {
				apply(solution, depthOfSearch,intensityOfMutation);
			}
			return solution.getObjectiveFunctionValue();
			
		}
		else if (intensityOfMutation == 1.0) {
			
			for (int i=0 ; i <=6 ; i++) {
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
	
	public int[] reInsert(int[] arr, int indexInsert, int indexChosen) {
		
		int[] newArray = new int[arr.length]; //create a new array of same length
		int newArrayCount = 0; //counting number of elements in new array
		
		for (int i = 0 ; i <= newArray.length ; i++) { //loop through until you reach the index (indexInsert) at which you want to insert the indexChosen element
			
			if (i == indexChosen) { //if you reach the chosen index you want to reinsert, don't add it to the new array
				
				break;
			}
			else if(i == indexInsert) { //if you reach the index you want to insert at, add the element that was already there and then add the element you want to insert
				newArray[newArrayCount] = arr[i];
				newArrayCount++;
				newArray[newArrayCount] = arr[indexChosen];
				newArrayCount++;
			}
			
			else { //otherwise just copy the previous contents of the array into the new array
			newArray[newArrayCount] = arr[i];
			newArrayCount++;
			}
		} //note: new array count helps keep track of the elements in the new array because when we reach the indexChosen, the count falls behind since we don't add the element at indexChosen until we reach indexInsert
		
		return newArray;
	}

}
