package com.java.tw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.java.tw.gol.GameOfLife;
import com.java.tw.gol.GameOfLifeImpl;
import com.java.tw.gol.exception.GolException;
import com.java.tw.gol.model.Cell;
import com.java.tw.gol.model.CellPattern;
import com.java.tw.gol.utils.GolUtils;

public class PlayGameOfLife {
	
	private static Logger logger = Logger.getLogger("PlayGameOfLife");
	public static void main(String[] args)
	{
		String filePath = null;
		int initRowCount = 0;
		int initColumnCount = 0;
		int maxRowCount = 0;
		int maxColCount = 0;
		try
		{
			GameOfLife gameOfLife = new GameOfLifeImpl();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			System.out.println(" ***** Enter the absolute path of file *****");
			while(((filePath = bufferedReader.readLine()) == null || filePath.trim().equals("")))
			{
				System.out.println("Please enter a valid path");
				break;
			}
			// Load Rules
			GolUtils.loadRules();
			
			// Construct Input pattern from file.
			CellPattern cellPattern = gameOfLife.constructSeedPattern(filePath);
			if(null!=cellPattern && cellPattern.getCells().isEmpty())
			{
				throw new GolException("File is empty. Please provide a valid input.");
			}
			initRowCount = cellPattern.getRows();
			initColumnCount = cellPattern.getColumns();
			maxRowCount = initRowCount + 2;
			maxColCount = initColumnCount + 2;
			Cell[][] cellArray = GolUtils.convertCellPatternTo2DArrayWithBuffer(cellPattern,maxRowCount,maxColCount);
			System.out.println("************ Initial Cell Pattern **************");
			GolUtils.printCellPattern(cellArray);
			// Play Game.
			Cell[][] newGeneration = gameOfLife.playGame(cellArray, initRowCount,initColumnCount,maxRowCount,maxColCount);
			System.out.println("************ Final Cell Pattern **************");
			GolUtils.printCellPattern(newGeneration);
			
		}
		catch(GolException e)
		{
			logger.log(Level.SEVERE, "GolException occurred while playing game of life.. Stack trace - ", e)	;
		}
		catch(Exception e)
		{
			logger.log(Level.SEVERE, "Exception occurred while playing game of life.. Stack trace - ", e)	;
		}
		
	}

}
