package com.aim.project.pwp.solution;

import com.aim.project.pwp.interfaces.SolutionRepresentationInterface;

/**
 * 
 * @author Warren G. Jackson
 * 
 *
 */
public class SolutionRepresentation implements SolutionRepresentationInterface {

	private int[] aiSolutionRepresentation;

	public SolutionRepresentation(int[] aiRepresentation) {

		this.aiSolutionRepresentation = aiRepresentation;
	}

	@Override
	public int[] getSolutionRepresentation() {

		return aiSolutionRepresentation;
	}

	@Override
	public void setSolutionRepresentation(int[] aiSolutionRepresentation) {

		this.aiSolutionRepresentation = aiSolutionRepresentation;
	}

	@Override
	public int getNumberOfLocations() {

		// TODO return the total number of locations in this instance (includes DEPOT and HOME).
		int count=0;
		
		for (int i=0; i < aiSolutionRepresentation.length ; i++) { //go through the solution and count the number of ints
			count++;
		}
		
		return count + 2; //return the count + 2 to include depot and home in the count as I have assumed that they are not part of the solution representation int array
		
	}

	@SuppressWarnings("null")
	@Override
	public SolutionRepresentationInterface clone() {

		// TODO perform a DEEP clone of the solution representation!
		int[] clonedSolution = null;
		for (int i =0 ; i < aiSolutionRepresentation.length ; i++) {
			clonedSolution[i] = aiSolutionRepresentation[i];
		}
		
		return new SolutionRepresentation(clonedSolution) ;
		
		
	}

}
