package com.aim.project.pwp.heuristics;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;

public class HeuristicOperators {

	private ObjectiveFunctionInterface oObjectiveFunction;

	public HeuristicOperators() {

	}

	public void setObjectiveFunction(ObjectiveFunctionInterface f) {

		this.oObjectiveFunction = f;
	}
	
	public void swapLocations(PWPSolutionInterface solution, int index1, int index2) {
		
		int temp = solution.getSolutionRepresentation().getSolutionRepresentation()[index1];
		solution.getSolutionRepresentation().getSolutionRepresentation()[index1] = solution.getSolutionRepresentation().getSolutionRepresentation()[index2];
		solution.getSolutionRepresentation().getSolutionRepresentation()[index2] = temp;
		
	}
	
	@SuppressWarnings("null")
	public int determineDepth(double depthOfSearch) {
		
		if (depthOfSearch > 0.0 && depthOfSearch < 0.2) {
			return 1;
		}
		else if (depthOfSearch > 0.2 && depthOfSearch < 0.4) {
			return 2;
		}
		else if (depthOfSearch > 0.4 && depthOfSearch < 0.6) {
			return 3;
		}
		else if (depthOfSearch > 0.6 && depthOfSearch < 0.8) {
			return 4;
		}
		else if (depthOfSearch > 0.8 && depthOfSearch < 1.0) {
			return 5;
		}
		else if (depthOfSearch == 1.0) {
			return 6;
		}
		else {
			return (Integer) null;
		}
		
	}
	
	public double getRandomDoubleBetween(double min, double max){
	    double x = (Math.random()*((max-min)+1))+min;
	    return x;
	}

	/**
	 * TODO implement any common functionality here so that your
	 * 			heuristics can reuse them!
	 * E.g.  you may want to implement the swapping of two delivery locations here!
	 */
}
