package com.aim.project.pwp;


import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.aim.project.pwp.heuristics.AdjacentSwap;
import com.aim.project.pwp.heuristics.CX;
import com.aim.project.pwp.heuristics.DavissHillClimbing;
import com.aim.project.pwp.heuristics.InversionMutation;
import com.aim.project.pwp.heuristics.NextDescent;
import com.aim.project.pwp.heuristics.OX;
import com.aim.project.pwp.heuristics.Reinsertion;
import com.aim.project.pwp.instance.InitialisationMode;
import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.instance.reader.PWPInstanceReader;
import com.aim.project.pwp.interfaces.HeuristicInterface;
import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.interfaces.Visualisable;
import com.aim.project.pwp.interfaces.XOHeuristicInterface;
import AbstractClasses.ProblemDomain;

public class AIM_PWP extends ProblemDomain implements Visualisable {

	private String[] instanceFiles = {
		"square", "libraries-15", "carparks-40", "tramstops-85", "trafficsignals-446", "streetlights-35714"
	};
	
	private PWPSolutionInterface[] aoMemoryOfSolutions;
	
	public PWPSolutionInterface oBestSolution;
	
	public PWPInstanceInterface oInstance;
	
	private HeuristicInterface[] aoHeuristics;
	
	private ObjectiveFunctionInterface oObjectiveFunction;
	
	private final long seed;
		
	public AIM_PWP(long seed) {
		
		super(seed);
		this.seed = seed;

		// TODO - set default memory size and create the array of low-level heuristics
		Random oRandom = new Random();
		
		HeuristicInterface inversionMutation = new InversionMutation(oRandom);
		HeuristicInterface adjacentSwap = new AdjacentSwap(oRandom);
		HeuristicInterface reInsertion = new Reinsertion(oRandom);
		HeuristicInterface nextDescent = new NextDescent(oRandom);
		HeuristicInterface davissHillClimbing = new DavissHillClimbing(oRandom);
		HeuristicInterface oX = new OX(oRandom);
		HeuristicInterface cX = new CX(oRandom);
		
		aoHeuristics = new HeuristicInterface[getNumberOfHeuristics()];
		aoHeuristics[0] = inversionMutation;
		aoHeuristics[1] = adjacentSwap;
		aoHeuristics[2] = reInsertion;
		aoHeuristics[3] = nextDescent;
		aoHeuristics[4] = davissHillClimbing;
		aoHeuristics[5] = oX;
		aoHeuristics[6] = cX;
		
		setMemorySize(2);		
		
	}
	
	public PWPSolutionInterface getSolution(int index) {
		
		return aoMemoryOfSolutions[index]; 
	}
	
	public PWPSolutionInterface getBestSolution() {
		
		return oBestSolution;
	}

	@Override
	public double applyHeuristic(int hIndex, int currentIndex, int candidateIndex) {
		
		// TODO - apply heuristic and return the objective value of the candidate solution
		//			remembering to keep track/update the best solution
		
		int[] crossoverHeuristics = getHeuristicsOfType(HeuristicType.CROSSOVER);
		
		for(int i=0 ; i < crossoverHeuristics.length ; i++) {
			
			if (hIndex == crossoverHeuristics[i]) {
				double objValue =aoHeuristics[hIndex].apply(aoMemoryOfSolutions[candidateIndex], getDepthOfSearch(), getIntensityOfMutation());
				return objValue;
			}
		}
		
		initialiseSolution(currentIndex);
		
		copySolution(currentIndex,candidateIndex);
		
		double candidateObjValue = aoHeuristics[hIndex].apply(aoMemoryOfSolutions[candidateIndex], getDepthOfSearch(), getIntensityOfMutation()); //apply the heuristic hIndex at the candidate solution
		
		//aoMemoryOfSolutions[candidateIndex].setObjectiveFunctionValue(oObjectiveFunction.getObjectiveFunctionValue(aoMemoryOfSolutions[candidateIndex].getSolutionRepresentation()));
		
		if (candidateObjValue < getBestSolutionValue()) {
			
			oBestSolution= aoMemoryOfSolutions[candidateIndex];
		}
		
		
		return candidateObjValue;
		
		
	}

	@Override
	public double applyHeuristic(int hIndex, int parent1Index, int parent2Index, int candidateIndex) {
		
		// TODO - apply heuristic and return the objective value of the candidate solution
		//			remembering to keep track/update the best solution
		int[] depthHeuristics = getHeuristicsThatUseDepthOfSearch();
		int[] intensityHeuristics = getHeuristicsThatUseIntensityOfMutation();
		
		for(int i=0 ; i < depthHeuristics.length + intensityHeuristics.length ; i++) {
			
			if (hIndex == depthHeuristics[i] || hIndex == intensityHeuristics[i] ) {
				double objValue =aoHeuristics[hIndex].apply(aoMemoryOfSolutions[candidateIndex], getDepthOfSearch(), getIntensityOfMutation());
				return objValue;
			}
		}
		
		initialiseSolution(parent1Index);
		initialiseSolution(parent2Index);
		
		double candidateObjValue = ((XOHeuristicInterface)aoHeuristics[hIndex]).apply(aoMemoryOfSolutions[parent1Index],aoMemoryOfSolutions[parent2Index],
				aoMemoryOfSolutions[candidateIndex], getDepthOfSearch(), getIntensityOfMutation());
		
		
		if (candidateObjValue < getBestSolutionValue()) {
			
			oBestSolution= aoMemoryOfSolutions[candidateIndex];
		}
		
		
		return candidateObjValue;
	}

	@Override
	public String bestSolutionToString() {
		
		// TODO return the location IDs of the best solution including DEPOT and HOME locations
		//		e.g. "DEPOT -> 0 -> 2 -> 1 -> HOME"
		
		String bestSolutionString= "";
		int[] bestSolution =oBestSolution.getSolutionRepresentation().getSolutionRepresentation(); //get the solution in the form of int[]
		
		for (int k = 0 ; k < bestSolution.length ; k++) { //print out as a string
			if (k == 0) { //first solution prints after DEPOT
			bestSolutionString ="DEPOT ->" + bestSolution[k];
			}
			else if (k == bestSolution.length -1) { //last solution prints before HOME
				bestSolutionString = bestSolutionString + " " + bestSolution[k] + " " + "HOME";
			}
			else { //solutions in between add to the middle
				bestSolutionString = bestSolutionString + " " + bestSolution[k] + "-->";
			}
			
		}
		
		return bestSolutionString;
		
	}

	@Override
	public boolean compareSolutions(int iIndexA, int iIndexB) {

		// TODO return true if the objective values of the two solutions are the same, else false
		if (aoMemoryOfSolutions[iIndexA].getObjectiveFunctionValue() == aoMemoryOfSolutions[iIndexB].getObjectiveFunctionValue()) {
			return true;
		}
		else {
			return false;
		}
		
	}

	@Override
	public void copySolution(int iIndexA, int iIndexB) {

		// TODO - BEWARE this should copy the solution, not the reference to it!
		//			That is, that if we apply a heuristic to the solution in index 'b',
		//			then it does not modify the solution in index 'a' or vice-versa.
		
		PWPSolutionInterface sol = aoMemoryOfSolutions[iIndexA].clone(); //clone solution at index a
		int[] a = sol.getSolutionRepresentation().getSolutionRepresentation(); //get the solution representation at a
		aoMemoryOfSolutions[iIndexB].getSolutionRepresentation().setSolutionRepresentation(a); //set the solution representation at b, to be the cloned solution from a
			
	}

	@Override
	public double getBestSolutionValue() {

		// TODO
		return oBestSolution.getObjectiveFunctionValue();
	}
	
	@Override
	public double getFunctionValue(int index) {
		
		// TODO
		return aoMemoryOfSolutions[index].getObjectiveFunctionValue();
	}

	@Override
	public int[] getHeuristicsOfType(HeuristicType type) {
		
		// TODO return an array of heuristic IDs based on the heuristic's type.
		
		ArrayList<Integer> crossover = new ArrayList<>();
		ArrayList<Integer> mutation = new ArrayList<>();
		ArrayList<Integer> depth = new ArrayList<>();
		
		
		for (int i = 0 ; i < aoHeuristics.length ; i++) { //itreate through the heuristics
			
			if (aoHeuristics[i].isCrossover() == true){ //if crossover, add to crossover list
				
				crossover.add(i);
			}
			
			if (aoHeuristics[i].usesDepthOfSearch()== true){ //if depth of search, add to depth list
				
				depth.add(i);
			}
			
			if (aoHeuristics[i].usesIntensityOfMutation() == true){ // if intensity of mutation, add to mutation list
			
				mutation.add(i);
			}
		}
		
			if (type == HeuristicType.CROSSOVER) { //return corresponding array based on parameter type
				
				return crossover.stream().mapToInt(i -> i).toArray(); //convert the list to array
				
			}
			else if(type == HeuristicType.LOCAL_SEARCH) {
				
				return depth.stream().mapToInt(i -> i).toArray();
			}
			else if(type == HeuristicType.MUTATION) {
				
				return mutation.stream().mapToInt(i -> i).toArray();
			}
			else {
				return null;
			}
		
	}

	@Override
	public int[] getHeuristicsThatUseDepthOfSearch() {
		
		// TODO return the array of heuristic IDs that use depth of search.
		return getHeuristicsOfType(HeuristicType.LOCAL_SEARCH);
	}

	@Override
	public int[] getHeuristicsThatUseIntensityOfMutation() {
		
		// TODO return the array of heuristic IDs that use intensity of mutation.
		return getHeuristicsOfType(HeuristicType.MUTATION);
	}

	@Override
	public int getNumberOfHeuristics() {

		// TODO - has to be hard-coded due to the design of the HyFlex framework...
		return 7;
	}

	@Override
	public int getNumberOfInstances() {

		// TODO return the number of available instances
		return oInstance.getNumberOfLocations();
	}

	@Override
	public void initialiseSolution(int index) {
		
		// TODO - initialise a solution in index 'index' 
		// 		making sure that you also update the best solution!
		
		PWPSolutionInterface sol = getSolution(index); //get the solution at the index
		
		sol = getLoadedInstance().createSolution(InitialisationMode.RANDOM); //assign the solution to the created solution from the loaded instance
		
		sol.setObjectiveFunctionValue(oObjectiveFunction.getObjectiveFunctionValue(sol.getSolutionRepresentation())); //set the objective function value

		//sol.getObjectiveFunctionValue();
		
		aoMemoryOfSolutions[index] = sol;
		
		if (getBestSolution() !=  null) {
			
			for (int i= 0 ; i < aoMemoryOfSolutions.length ; i++) { //iterate through the solutions
				
				if (aoMemoryOfSolutions[i]!= null && getBestSolutionValue() < getFunctionValue(i)) { //make sure the best solution's function value is greater than the function value of each solution
					oBestSolution = aoMemoryOfSolutions[i]; 
				}
				
			}
			
		}
		else {
			oBestSolution = aoMemoryOfSolutions[index];
		}
		
		
	}

	// TODO implement the instance reader that this method uses
	//		to correctly read in the PWP instance, and set up the objective function.
	@Override
	public void loadInstance(int instanceId) {

		String SEP = FileSystems.getDefault().getSeparator();
		String instanceName = "instances" + SEP + "pwp" + SEP + instanceFiles[instanceId] + ".pwp";

		Path path = Paths.get(instanceName);
		Random random = new Random(seed);
		PWPInstanceReader oPwpReader = new PWPInstanceReader();
		oInstance = oPwpReader.readPWPInstance(path, random);

		oObjectiveFunction = oInstance.getPWPObjectiveFunction();
		
		for(HeuristicInterface h : aoHeuristics) {
			h.setObjectiveFunction(oObjectiveFunction);
		}
	}

	@Override
	public void setMemorySize(int size) {

		// TODO sets a new memory size
		// IF the memory size is INCREASED, then
		//		the existing solutions should be copied to the new memory at the same indices.
		// IF the memory size is DECREASED, then
		//		the first 'size' solutions are copied to the new memory.
		
		aoMemoryOfSolutions = new PWPSolutionInterface[2];
		
		if (size > aoMemoryOfSolutions.length) { //check if size is bigger
			
			PWPSolutionInterface[] biggerMemoryOfSolutions = new PWPSolutionInterface[size]; //create new array with bigger size
			
			for (int j=0 ; j < aoMemoryOfSolutions.length ; j++) { //copy all elements from old array
				
				biggerMemoryOfSolutions[j] = getSolution(j);
			}
		
		aoMemoryOfSolutions = biggerMemoryOfSolutions; //set new array to be the new memory of solutions
			
		}
		
		if (size < aoMemoryOfSolutions.length) {
			PWPSolutionInterface[] smallerMemoryOfSolutions = new PWPSolutionInterface[size]; //create new array with smaller size
			
			for (int j =0 ; j < smallerMemoryOfSolutions.length ; j++) {
				
				smallerMemoryOfSolutions[j] = getSolution(j);
			}
			
			aoMemoryOfSolutions = smallerMemoryOfSolutions;
		}
		
		if (size <=1) {
			return;
		}
		
	}

	@Override
	public String solutionToString(int index) {

		String solutionString= "";
		int[] solution =aoMemoryOfSolutions[index].getSolutionRepresentation().getSolutionRepresentation(); //get the solution in the form of int[]
		
		for (int k = 0 ; k < solution.length ; k++) { //print out as a string
			if (k == 0) { //first solution prints after DEPOT
				solutionString ="DEPOT ->" + solution[k];
			}
			else if (k == solution.length -1) { //last solution prints before HOME
				solutionString = solutionString + " " + solution[k] + " " + "HOME";
			}
			else { //solutions in between add to the middle
				solutionString = solutionString + " " + solution[k] + "-->";
			}
			
		}
		
		return solutionString;

	}

	@Override
	public String toString() {

		// TODO change 'AAA' to be your username
		return "psyas11's G52AIM PWP";
	}
	
	private void updateBestSolution(int index) {
		
		// TODO
		oBestSolution = aoMemoryOfSolutions[index];
		
	}
	
	@Override
	public PWPInstanceInterface getLoadedInstance() {

		return this.oInstance;
	}

	@Override
	public Location[] getRouteOrderedByLocations() {

		int[] city_ids = getBestSolution().getSolutionRepresentation().getSolutionRepresentation();
		Location[] route = Arrays.stream(city_ids).boxed().map(getLoadedInstance()::getLocationForDelivery).toArray(Location[]::new);
		return route;
	}
}
