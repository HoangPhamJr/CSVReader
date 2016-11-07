Synopsis

The purpose of this project is to import a CSV file into java without the use of any external libraries or dependencies and only use java.io, jcava.util, and java.lang

Motivation

I ran accross an issue where I could not read and parse a CSV in JAVA easily without installing packages and other files so I wrote this CSV reader in order to parse csv files without using any 

Installation

This is a simple java script that does not require any installation. It can be run by importing the CSVReader.java file into any IDE and executing it after replacing the empty quotes in line 37 with the location of the CSV. 
line 37: ArrayList<String[]> fullData = readCSV("");

API Reference

public static ArrayList<String[]> readCSV(String CSVLocation) - This method takes in a string containing the full path of the CSV file

Tests

Describe and show how to run the tests with code examples.
Enter in the full path of the included tests.csv located in /tests and run the code as directed in the Installation section

Contributors

Hoang Pham

License

Open Source - Feel free to use or modify any of this code for personal or commercial use