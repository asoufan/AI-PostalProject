package com.aim.project.pwp.instance.reader;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;

import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.instance.PWPInstance;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.PWPInstanceReaderInterface;


public class PWPInstanceReader implements PWPInstanceReaderInterface {

	@SuppressWarnings("null")
	@Override
	public PWPInstanceInterface readPWPInstance(Path path, Random random) {

		BufferedReader bfr1;
		BufferedReader bfr2;
		
		Location[] allLocations;
		Location postaldepotLocation = null;
		Location workerLocation= null;
		int lines =0;
		
		try {
			bfr1 = Files.newBufferedReader(path);
			
			while(bfr1.readLine() != null) { //calculate number of lines
				
				lines++;
			}
			
			bfr1.close(); //close the first reader
			
			allLocations = new Location[lines-6]; //define the array of locations (not including home and depot, to be total lines-6
			
			bfr2 = Files.newBufferedReader(path); //open a new reader
			String postalCoordinates;
			int i = 0; //keeps track of iterations
			int locations=0; //keeps track of number of locations in the allLocations array
			
			while ((postalCoordinates = bfr2.readLine()) != null) {
				i++;
				if (i > 7 && i < lines-6) { //if on the 7th line, start adding locations for the postal addresses that always come after line 7
				
					String[] coordinatesString = postalCoordinates.split("	"); //store coordinates as string, separated by "	"
					double[] coordinatesDouble = new double[2]; 
					coordinatesDouble[0] = Double.parseDouble(coordinatesString[0]); //convert to double
					coordinatesDouble[1] = Double.parseDouble(coordinatesString[1]);
					
					allLocations[locations] = new Location(coordinatesDouble[0], coordinatesDouble[1]); //create new location
					locations++; //increment location counter
					
				}
				
				if (i == 4) { //line 4 is always postal depot location
					String[] coordinatesString = postalCoordinates.split("	");
					double[] coordinatesDouble = new double[2];
					coordinatesDouble[0] = Double.parseDouble(coordinatesString[0]);
					coordinatesDouble[1] = Double.parseDouble(coordinatesString[1]);
					
					postaldepotLocation = new Location(coordinatesDouble[0], coordinatesDouble[1]);
					
				}
				
				if (i == 6) { //line 6 is always worker address
					
					String[] coordinatesString = postalCoordinates.split("	");
					double[] coordinatesDouble = new double[2];
					coordinatesDouble[0] = Double.parseDouble(coordinatesString[0]);
					coordinatesDouble[1] = Double.parseDouble(coordinatesString[1]);
					
					workerLocation = new Location(coordinatesDouble[0], coordinatesDouble[1]);
				}
				
			}
				
			
			
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}
		return new PWPInstance(lines - 6, allLocations, postaldepotLocation, workerLocation, random); //the number of locations is always lines -6 since name, comment, titles (postal, home, and delivery), and eof are not included
	}
}
