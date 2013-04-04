package com.java.tw.gol;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.java.tw.gol.exception.GolException;
import com.java.tw.gol.exception.GolInvalidCharacterException;
import com.java.tw.gol.model.Cell;
import com.java.tw.gol.model.CellPattern;
import com.java.tw.gol.model.GolPlayGameRequest;
import com.java.tw.gol.model.GolPlayGameResponse;
import com.java.tw.gol.rules.GolRuleChain;
import com.java.tw.gol.rules.GolRulesContext;
import com.java.tw.gol.utils.GolConstants;
import com.java.tw.gol.utils.GolUtils;

public class GameOfLifeImpl implements GameOfLife
{
	private static Logger logger = Logger.getLogger("GameOfLifeImpl");
	
	/**********************************************************
	 * Method  : constructSeedPattern
	 * Purpose : Constructs a seed pattern from the input file.
	 * Argument: File source.
	 ***********************************************************/
	@Override
	public CellPattern constructSeedPattern(String seedSource) throws GolException
	{
		int rowIndex = 0, columnIndex=0;
		CellPattern seedPattern =  null;
		boolean isValidPattern = false;
		String line = null;
		BufferedReader bufferedReader = null;
		StringTokenizer st = null;
		boolean isAlive = false;
		List<Cell> cellList = new ArrayList<Cell>();
		try 
		{
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(seedSource)));
			Pattern dataPattern = Pattern.compile(GolConstants.GOL_CELL_PATTERN);
			while((line=bufferedReader.readLine()) != null)
			{
				if(line.trim().equals(GolConstants.GOL_EMPTY_STRING))
				{
					continue;
				}
				columnIndex =0;
				isValidPattern = dataPattern.matcher(line).matches();
				if(!isValidPattern)
				{
					throw new GolInvalidCharacterException("invalid characters");
				}
				st = new StringTokenizer(line);
				while(st.hasMoreTokens())
				{
					String cellValue = st.nextToken();
					if(cellValue.equalsIgnoreCase(GolConstants.GOL_EMPTY_STRING))
					{
						continue;
					}
					if(cellValue.equalsIgnoreCase(GolConstants.GOL_LIVE_CELL))
					{
						isAlive = true;
					}
					else
					{
						isAlive = false;
					}
					Cell cell = new Cell(rowIndex, columnIndex, isAlive, cellValue);
					cellList.add(cell);
					columnIndex++;
				}
				rowIndex++;
			}
			seedPattern = new CellPattern();
			seedPattern.setCells(cellList);
			seedPattern.setRows(rowIndex);
			seedPattern.setColumns(columnIndex);
		}
		catch (FileNotFoundException e)
		{
			logger.log(Level.SEVERE, "File not found exception occurred ", e);
			throw new GolException(e.getMessage());
		}
		catch(IOException ioe)
		{
			logger.log(Level.SEVERE, "IOException occurred ", ioe);
			throw new GolException(ioe.getMessage());	
		}
		catch(GolInvalidCharacterException ie)
		{
			logger.log(Level.SEVERE, "InvalidCharacterException occurred ", ie);
			throw new GolException(ie.getMessage());
		}
		catch(Exception e)
		{
			logger.log(Level.SEVERE, "Exception occurred ", e);
			throw new GolException(e.getMessage());
		}
		finally
		{
			try 
			{
				if(null!=bufferedReader)
					bufferedReader.close();
			} 
			catch (IOException e) 
			{
				logger.log(Level.SEVERE, "IOException occurred ", e);
			}
		}
		return seedPattern;
	}
	
	/****************************************************************************
	 * Method  : playGame
	 * Purpose : Plays the game depending on the rules.
	 * Argument: Cell[][] cellArray, int initRowCount, 
	 * 			 int initColCount,int maxRowCount, int maxColCount.
	 * Return  : 2D array of next generation cells.
	 ****************************************************************************/
	@Override
	public Cell[][] playGame(Cell[][] cellArray, int initRowCount, int initColCount,int maxRowCount, int maxColCount)
	{
		return getNextGeneration(cellArray,initRowCount,initColCount,maxRowCount,maxColCount);
	}
	
	/***************************************************************************
	 * Method  : getNextGeneration
	 * Purpose : Generates next generation cells
	 * 
	 * @param cellArray
	 * @param initRowCount
	 * @param initColCount
	 * @param maxRowCount
	 * @param maxColCount
	 * @return
	 *****************************************************************************/
	private Cell[][] getNextGeneration(Cell[][] cellArray, int initRowCount, int initColCount,int maxRowCount, int maxColCount) 
	{
		GolPlayGameResponse response = new GolPlayGameResponse();
		GolPlayGameRequest request = null;
		GolRuleChain rule = null;
		int ruleCount = 1;
		
		// Construct Request
		request = new GolPlayGameRequest();
		request.setInputCellArray(cellArray);
		request.setInitRowCount(initRowCount);
		request.setInitColCount(initColCount);
		request.setMaxRowCount(maxRowCount);
		request.setMaxColCount(maxColCount);
		
		// Construct Request and Response
		Cell[][] newGeneration = GolUtils.getNewGeneration(maxRowCount,maxColCount);
		response.setOutputCellArray(newGeneration);
		
		// Get Applicable rules for Game of Life
		Map<String, GolRuleChain> applicableRules = GolRulesContext.getInstance().getRulesMap();

		// Apply rules Sequentially.
		if(null!=applicableRules)
		{
			for(Map.Entry<String, GolRuleChain> entry : applicableRules.entrySet())
			{
				rule = entry.getValue();
				logger.log(Level.INFO,"applying rule " + entry.getKey() + " Rule No: " + ruleCount);
				rule.doAction(request, response);
				ruleCount ++;
			}
		}
		return response.getOutputCellArray();
	}
	
	
}
