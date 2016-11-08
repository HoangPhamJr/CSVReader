package com.HP.module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class CSVReader {
	
	//CONFIGURATION
	//Set this to false if the file does not contain a header
	private static boolean isThereAHeader = true;
	
	//If there is no header then set the number of columns, if there is a header this will automatically be set
	private static int columns = 1;
	
	//If you want debugging to see what is being stored change to true
	private static boolean debug = true;//For basic logs
	private static boolean verboseDebug = false;//For more extended logs
	
	//If you want system.out.println set to true otherwise set to false for LOGGER.info outputs
	private static boolean systemOutPrintLn = true;
	
	
	//Do not modify these variables
	private static ArrayList<String> header = new ArrayList<String>();
	private static final Logger LOGGER = Logger.getLogger( Class.class.getName());
	private static final String className = MethodHandles.lookup().lookupClass().toString();
	
	public static void main(String[] args) {
		//This method is to run the CSV reader and test it
		ArrayList<String[]> fullData = readCSV("");
		
		//If debug output final arrayList
		if(debug){printArrayList(fullData);}
		
		//Sample for get element using the test file
		//System.out.println(getElement(fullData.get(0), "Last"));
		
	}

	public static ArrayList<String[]> readCSV(String CSVLocation){
		LOGGER.entering(className, LOGGER.getParent().toString());
		//ArrayList to store the csv file
		ArrayList<String[]> storedCSV = new ArrayList<String[]>();
		Scanner processLine = new Scanner(System.in);
		BufferedReader fileReader = null;
		//Try reading the file
		try {
			//Process each line
			fileReader = new BufferedReader(new FileReader(new File(CSVLocation)));
			String readLine = fileReader.readLine();
			
			//Process the data
			processLine = new Scanner(readLine).useDelimiter(",");
			String[] data = new String[columns];
			int row = 0;
			while(null!=readLine){//Process each row
				processLine = new Scanner(readLine).useDelimiter(",");
				int column = 0;
				data = new String[columns];
				while(processLine.hasNext()){//Process each element in the row
					String rowData = processLine.next().trim();
					//If the rowData starts with a quote then process till the end of the quote
					if(rowData.length()>1 && rowData.substring(0,1).equals("\"")){
						rowData = rowData.substring(1);
						while(!rowData.contains("\"") && processLine.hasNext()){
							rowData = rowData + processLine.next();
						}
						rowData = rowData.replace("\"", "");
					}
					//If this row is the header row then store in the header ArrayList
					if(isThereAHeader&&row==0){
						header.add(rowData);
					}
					else{//This is a data row so store the data
						if(column<data.length){
							data[column] = rowData;
						}
					}
					//If debugging is enabled then output to the system
					if(debug && verboseDebug){printLine("VerboseDebug Data row " + (row+1) + " - " + rowData,1);}
					column++;
					//System.out.println(row);
				}
				if(isThereAHeader&&row==0){//Set the number of columns to be correct
					columns = column;
				}else{
					storedCSV.add(data);
				}
				//Get the next line
				readLine = fileReader.readLine();
				row++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			printLine("The file was not found",2);
		} catch (IOException e) {
			e.printStackTrace();
			printLine("Line reading error in fileReader.readLine()",2);
		}finally{
			processLine.close();
			try {
				fileReader.close();
			} catch (IOException e) {//Not initialized
				e.printStackTrace();
			}
		}
		LOGGER.exiting(className, LOGGER.getParent().toString());
		return storedCSV;
	}
	
	public static String getElement(String[] list, String element){
		//This method allows you to retrieve elements by their header name allowing you to not have to hard code element positions and the header can be dynamic if more columns are added
		return list[getPosition(element)];
	}
	
	private static int getPosition(String element){
		int result = 0;
		int counter = 0;
		for(String tempElement:header){
			if(null!=tempElement && tempElement.length()>1 && tempElement.equals(element)){
				result = counter;
				break;
			}
			counter++;
		}
		return result;
	}
	
	private static void printArrayList(ArrayList<String[]> fullData){
		LOGGER.entering(className, "printArrayList");
		int row = 0;
		for(String[] element: fullData){//Iterate through all rows
			int column = 0;
			for(String data: element){//Iterate through all columns
				printLine("Debug: Row " + row + " - column " + column + ": " + data,1);
				column++;
			}
			row++;
		}
		LOGGER.exiting(className, "printArrayList");
	}
	
	private static void printLine(String line, int code){
		if(systemOutPrintLn){
			System.out.println(line);
		}else{
			if(code==1){//code 1=info, 2=severe
				LOGGER.info(line);
			}else{
				LOGGER.severe(line);
			}
		}
	}
}
