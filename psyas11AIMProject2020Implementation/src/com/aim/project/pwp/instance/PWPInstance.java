package com.aim.project.pwp.instance;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.aim.project.pwp.PWPObjectiveFunction;
import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.solution.SolutionRepresentation;
import com.aim.project.pwp.solution.PWPSolution;


public class PWPInstance implements PWPInstanceInterface {
	
	private final Location[] aoLocations;
	
	private final Location oPostalDepotLocation;
	
	private final Location oHomeAddressLocation;
	
	private final int iNumberOfLocations;
	
	private final Random oRandom;
	
	private ObjectiveFunctionInterface oObjectiveFunction = null;
	
	/**
	 * 
	 * @param numberOfLocations The TOTAL number of locations (including DEPOT and HOME).
	 * @param aoLocations The delivery locations.
	 * @param oPostalDepotLocation The DEPOT location.
	 * @param oHomeAddressLocation The HOME location.
	 * @param random The random number generator to use.
	 */
	public PWPInstance(int numberOfLocations, Location[] aoLocations, Location oPostalDepotLocation, Location oHomeAddressLocation, Random random) {
		
		this.iNumberOfLocations = numberOfLocations;
		this.oRandom = random;
		this.aoLocations = aoLocations;
		this.oPostalDepotLocation = oPostalDepotLocation;
		this.oHomeAddressLocation = oHomeAddressLocation;
	}

	@SuppressWarnings("null")
	@Override
	public PWPSolution createSolution(InitialisationMode mode) {
		
		// TODO construct a new 'PWPSolution' using RANDOM initialisation
		
		if (mode == InitialisationMode.RANDOM) {
		
		int[] solutions = new int[getNumberOfLocations()];//array of solutions
		
		for (int i = 0 ; i <= getNumberOfLocations() -1 ; i++) { //enumerate the array, giving each location an integer value
			
			solutions[i]= i;
			
		}

		Collections.shuffle(Arrays.asList(solutions)); //shuffle the array
		
		SolutionRepresentation solution = new SolutionRepresentation(solutions); //create a new solution representation using the array of ints
		
		ObjectiveFunctionInterface objfunc = getPWPObjectiveFunction(); //get the objective function to be used 

		PWPSolution sol = new PWPSolution(solution, objfunc.getObjectiveFunctionValue(solution)); //create the new PWPSolution using the solution representation and the value of the objective function applied to the solution
		
		return sol;
		
		}
		
		else {
			return null;
		}
	}
	
	@Override
	public ObjectiveFunctionInterface getPWPObjectiveFunction() {
		
		if(oObjectiveFunction == null) {
			this.oObjectiveFunction = new PWPObjectiveFunction(this);
		}

		return oObjectiveFunction;
	}

	@Override
	public int getNumberOfLocations() {

		return iNumberOfLocations;
	}

	@Override
	public Location getLocationForDelivery(int deliveryId) {

		return this.aoLocations[deliveryId];
	}

	@Override
	public Location getPostalDepot() {
		
		return this.oPostalDepotLocation;
	}

	@Override
	public Location getHomeAddress() {
		
		return this.oHomeAddressLocation;
	}
	
	@Override
	public ArrayList<Location> getSolutionAsListOfLocations(PWPSolutionInterface oSolution) {
		
		// TODO return an 'ArrayList' of ALL LOCATIONS in the solution.

		ArrayList<Location> locationsList = new ArrayList<Location>();//create an arraylist of locations
		
		int[] solutions = oSolution.getSolutionRepresentation().getSolutionRepresentation(); //get the solutions as an array of integers, first from the PWPSolutionInterface and then from the SolutionRepresentationInterface
		
		for (int i= 0 ; i < solutions.length ; i++) { //go through the array of integers and add each one to the arraylist using the getLocationforDelivery method from the PWPInstanceInterface class
			locationsList.add(getLocationForDelivery(solutions[i]));
		}

		return locationsList;
		
	}

}
