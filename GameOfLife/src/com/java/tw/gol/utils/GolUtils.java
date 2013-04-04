package com.java.tw.gol.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.java.tw.gol.exception.GolException;
import com.java.tw.gol.model.Cell;
import com.java.tw.gol.model.CellPattern;
import com.java.tw.gol.rules.GolRuleChain;
import com.java.tw.gol.rules.GolRulesContext;

public class GolUtils 
{
	
	private static final Logger logger = Logger.getLogger("GolUtils");
	/************************************************
	 * Method : printCellPattern
	 * Purpose: Prints the 2D Cell array in console.
	 * @param cellPattern
	 ***********************************************/
	public static void printCellPattern(Cell[][] cellPattern)
	{
		for(int i=0;i<cellPattern.length;i++)
		{
			if(i>0)
				System.out.println("\n");
			Cell[] temp = cellPattern[i];
			for(int j=0;j<temp.length;j++)
			{
				Cell cell = temp[j];
				if(null!=cell)
				{
					System.out.print(" "+ cell.getCellValue());
				}
			}
		}
		System.out.println("\n");
	}
	/**********************************************************************
	 * Method : convertCellPatternTo2DArrayWithBuffer
	 * Purpose: Converts the cell pattern formed by reading the input to a 
	 * 			2D array of Cell Objects.
	 * 			Adding a border to allow the universe to expand.	 
	 * @param pattern
	 * @param maxRowCount
	 * @param maxColCount
	 * @return 2D Cell Array
	 **********************************************************************/
	
	public static Cell[][] convertCellPatternTo2DArrayWithBuffer(CellPattern pattern,int maxRowCount, int maxColCount)
	{
		Cell[][] cellArray = GolUtils.getNewGeneration(maxRowCount, maxColCount);
		for (Cell c : pattern.getCells()) 
		{
			if (c != null) 
			{				
				cellArray[1+c.getxPos()][1+c.getyPos()] = c;
			}
		}
		return cellArray;
	}
	/******************************************************************************
	 * Method : getNewGeneration
	 * Purpose: Returns a new generation of the universe.
	 * @param maxRowCount
	 * @param maxColCount
	 * @return A new generation of cells in a cell array
	 *****************************************************************************/
	
	public static Cell[][] getNewGeneration(int maxRowCount, int maxColCount)
	{
		Cell[][] cellArray = new Cell[maxRowCount][maxColCount];
		for(int row=0;row<maxRowCount;row++){
			cellArray[row] = new Cell[maxColCount];
			for(int column=0;column<maxColCount;column++){
				cellArray[row][column] = new Cell(row, column, false, GolConstants.GOL_DEAD_CELL);
			}
		}
		return cellArray;
	}
	/****************************************************************************
	 * Method : getNeighbouringLiveCellCount 			
	 * Purpose: Returns the no. of live cells surrounding the cell.
	 * 			Checks North west, West, South west, North, South, North east,
	 * 			east and south east cells. 
	 * @param cellArray
	 * @param xIndex
	 * @param yIndex
	 * @param initRowCount
	 * @param initColCount
	 * @return No. of Live Cells.
	 *************************************************************/
	
	
	public static int getNeighbouringLiveCellCount(Cell[][] cellArray,int xIndex, int yIndex, int initRowCount, int initColCount) 
	{
		int liveCellCount = 0;
		//north west neighbor
		if(checkIfCellIsAlive(cellArray,xIndex-1,yIndex-1,initRowCount,initColCount)) liveCellCount+=1;
		//west neighbor
		if(checkIfCellIsAlive(cellArray,xIndex-1,yIndex,initRowCount,initColCount)) liveCellCount+=1;
		//south west neighbor
		if(checkIfCellIsAlive(cellArray,xIndex-1,yIndex+1,initRowCount,initColCount)) liveCellCount+=1;
		//north neighbor
		if(checkIfCellIsAlive(cellArray,xIndex,yIndex-1,initRowCount,initColCount)) liveCellCount+=1;
		//south neighbor
		if(checkIfCellIsAlive(cellArray,xIndex,yIndex+1,initRowCount,initColCount)) liveCellCount+=1;
		//north east neighbor
		if(checkIfCellIsAlive(cellArray,xIndex+1,yIndex-1,initRowCount,initColCount)) liveCellCount+=1;
		//east neighbor
		if(checkIfCellIsAlive(cellArray,xIndex+1,yIndex,initRowCount,initColCount)) liveCellCount+=1;
		//south east neighbor
		if(checkIfCellIsAlive(cellArray,xIndex+1,yIndex+1,initRowCount,initColCount)) liveCellCount+=1;
		return liveCellCount;
	}
	
	/**************************************************************
	 * Method : checkIfCellIsAlive
	 * Purpose: Checks if a cell in the given coordinates x and y
	 * 			is alive.
	 * @param cellArray
	 * @param x
	 * @param y
	 * @param initRowCount
	 * @param initColCount
	 * @return boolean (Alive/Dead)
	 *****************************************************************/
	private static boolean checkIfCellIsAlive(Cell[][] cellArray,int x, int y,int initRowCount, int initColCount)
	{
		Cell cell = null;
		boolean isAlive = false;
		if(x >= 0 && y >= 0 && x < initRowCount && y < initColCount)
		{
			cell = cellArray[x][y];
			if(null!=cell && cell.isAlive())
			{
				isAlive = true;
			}
		}
		return isAlive;
	}
	
	/************************************************************
	 * Method : getApplicableRules
	 * Purpose: Returns the rules that are applicable for Game of
	 * 			Life. 
	 * Return : A map of rules 
	 *************************************************************/
	public static void loadRules() throws GolException
	{
		Map<String, GolRuleChain> ruleMap = new LinkedHashMap<String, GolRuleChain>();
		String key = null;
		String className = null;
		Properties properties = null;
		try
		{
			properties = loadRulesFromPropertiesFile();
			Set<String> propertyNames = properties.stringPropertyNames();
			Iterator<String> propertyNameIterator = propertyNames.iterator();
			while(propertyNameIterator.hasNext())
			{
				key = propertyNameIterator.next();
				className = properties.getProperty(key);
				logger.log(Level.INFO, "instantiating class - " + className);
				if(Class.forName(className).newInstance() instanceof GolRuleChain)
				{
					ruleMap.put(key, (GolRuleChain)Class.forName(className).newInstance());
				}
				else
				{
					throw new GolException("Class cast Exception. Rule implementation class should implement GolRuleChain Interface. ");
				}
				
			}
	   }
		catch (InstantiationException e)
		{
			logger.log(Level.SEVERE,"InstantiationException occurred while populating rules. ");
			throw new GolException(e.getMessage());
		} 
		catch (IllegalAccessException e)
		{
			logger.log(Level.SEVERE,"IllegalAccessException occurred while populating rules. ");
			throw new GolException(e.getMessage());
		}
		catch (ClassNotFoundException e)
		{
			logger.log(Level.SEVERE,"ClassNotFoundException occurred while instantiating rules implementation. Class name - " + className);
			throw new GolException(e.getMessage());
		}
		if(null!=properties && properties.size() < 1)
		{
			throw new GolException("Please provide a valid property file. Property file is empty/Unable to load properties file.");
		}
		GolRulesContext.getInstance().setRulesMap(ruleMap);
	}
	
	private static Properties loadRulesFromPropertiesFile()
	{
		InputStream inputStream = null;
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null)
        {
            classLoader = GolUtils.class.getClass().getClassLoader();
        }
        inputStream = classLoader.getResourceAsStream(GolConstants.RULES_PROPERTIES_FILE);
        if (inputStream != null)
        {
        	try
        	{
        		properties.load(inputStream);
        	}
        	catch (IOException e)
        	{
        		logger.log(Level.SEVERE, "unable to load rules. please check the property file path.");
        	}
        	finally
        	{
        		try
        		{
        			if(null!=inputStream)
        				inputStream.close();
        		}
        		catch (IOException e)
        		{
        			logger.log(Level.SEVERE, "unable to close input stream");
        		}
        	}
        }
        return properties;
	}
}
