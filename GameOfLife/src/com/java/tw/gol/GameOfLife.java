package com.java.tw.gol;

import com.java.tw.gol.exception.GolException;
import com.java.tw.gol.model.Cell;
import com.java.tw.gol.model.CellPattern;


public interface GameOfLife
{
	/**********************************************************
	 * Method  : constructSeedPattern
	 * Purpose : Constructs a seed pattern from the input file.
	 * Argument: File source.
	 ***********************************************************/
	CellPattern constructSeedPattern(String source) throws GolException;
	
	/********************************************************************
	 * Method  : playGame
	 * Purpose : Plays the game depending on the rules.
	 * Argument: Cell[][] cellArray, int initRowCount, 
	 * 			 int initColCount,int maxRowCount, int maxColCount.
	 * Return  : 2D array of next generation cells.
	 ********************************************************************/
	Cell[][] playGame(Cell[][] cellArray, int patternRowCount, int patternColCount, int maxRowCount, int maxColCount);
}
