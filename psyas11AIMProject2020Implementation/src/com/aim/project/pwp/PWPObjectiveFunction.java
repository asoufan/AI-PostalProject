package com.aim.project.pwp;

import com.aim.project.pwp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.SolutionRepresentationInterface;
import com.aim.project.pwp.instance.Location;

public class PWPObjectiveFunction implements ObjectiveFunctionInterface {
	
	private final PWPInstanceInterface oInstance;
	
	public PWPObjectiveFunction(PWPInstanceInterface oInstance) {
		
		this.oInstance = oInstance;
	}

	@Override
	public double getObjectiveFunctionValue(SolutionRepresentationInterface oSolution) {
		
		int[] solution = new int[oSolution.getNumberOfLocations()];
		solution = oSolution.getSolutionRepresentation(); //get the current solution which includes the delivery addresses
		
		double totalcost = 0;
		
		for (int i= 0; i < solution.length ; i++) { //loop through the solution
			
			if (i ==0) { //calculate the euclidian distance between the depot and the first delivery address
				totalcost = totalcost + getCostBetweenDepotAnd(solution[i]);
			}
			else if (i >= 0 && i < solution.length -1) { //calculate the euclidian distance between the delivery addresses, including the first and until the last
			
			totalcost = totalcost + getCost(solution[i], solution[i+1]); 
			}
			else { //when i=solution.length-1 that means we are at the last solution and we calculate the distance between that and the home adddress
				totalcost = totalcost + getCostBetweenHomeAnd(solution[i]);
			}
		}
		
		return totalcost;
		
	}
	
	@Override
	public double getCost(int iLocationA, int iLocationB) {
		
		Location locationA = new Location(oInstance.getLocationForDelivery(iLocationA).getX(), oInstance.getLocationForDelivery(iLocationA).getY()) ; //get the location of the instance A
		Location locationB = new Location(oInstance.getLocationForDelivery(iLocationB).getX(), oInstance.getLocationForDelivery(iLocationB).getY()) ; //get the location of the instance B
		double cost =Math.hypot(locationA.getX() - locationB.getX(), locationA.getY() - locationB.getY()); //calculate the euclidian distance
		
		return cost;
	}

	@Override
	public double getCostBetweenDepotAnd(int iLocation) {
		
		Location location = new Location(oInstance.getLocationForDelivery(iLocation).getX(), oInstance.getLocationForDelivery(iLocation).getY()) ; //get the location of the instance
		Location depotLocation = new Location(oInstance.getPostalDepot().getX(), oInstance.getPostalDepot().getY()); //get the location of the post office
		double cost = Math.hypot(depotLocation.getX() - depotLocation.getX(), location.getY() - location.getY()); //calculate the euclidian distance
		
		return cost;
	}

	@Override
	public double getCostBetweenHomeAnd(int iLocation) {
		
		Location location = new Location(oInstance.getLocationForDelivery(iLocation).getX(), oInstance.getLocationForDelivery(iLocation).getY()) ; //get the location of instance 
		Location homeLocation = new Location(oInstance.getHomeAddress().getX(), oInstance.getHomeAddress().getY()); //get the location of the home address
		double cost = Math.hypot(homeLocation.getX() - location.getX(), homeLocation.getY() - location.getY()); //calculate the euclidian distance
		
		return cost;
		
	}
}
