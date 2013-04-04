package com.java.tw.gol.rules.handlers;

import com.java.tw.gol.model.Cell;
import com.java.tw.gol.model.GolPlayGameRequest;
import com.java.tw.gol.model.GolPlayGameResponse;
import com.java.tw.gol.rules.GolRuleChain;
import com.java.tw.gol.utils.GolConstants;
import com.java.tw.gol.utils.GolUtils;

public class LonelyOverCrowdedUnchangedRuleHandler implements GolRuleChain {

	/**************************************************************************************************
	 * Method        : doAction
	 * Applied Rules : 1.Any live cell with fewer than two live neighbors dies, as if by loneliness.
					   2.Any live cell with more than three live neighbors dies, as if by overcrowding.
					   3.Any live cell with two or three live neighbors lives, 
					     unchanged, to the next generation. 
	 * Purpose       : Applies the rule on the input data. Updates the output cell array
	 * Arguments     : GolPlayGameRequest request 
	 * 			       GolPlayGameResponse response
	 ***************************************************************************************************/
	@Override
	public void doAction(GolPlayGameRequest request,GolPlayGameResponse response) 
	{
		Cell cell = null;
		int maxRowCount = request.getMaxRowCount();
		int maxColCount = request.getMaxColCount();
		Cell[][] inputCellArray = request.getInputCellArray();
		Cell[][] outputCellArray = response.getOutputCellArray();
		for (int rowId = 0; rowId < maxRowCount; rowId++)
		{
			for (int columnId = 0; columnId < maxColCount; columnId++) 
			{
				int neighbouringLiveCellCount = GolUtils.getNeighbouringLiveCellCount(inputCellArray, rowId, columnId, maxRowCount, maxColCount);
				cell = inputCellArray[rowId][columnId];
				if(null!=cell)
				{
					if(cell.isAlive())
					{
						//1.Any live cell with fewer than two live neighbours dies, as if by loneliness.
						//2.Any live cell with more than three live neighbours dies, as if by overcrowding.
						if(neighbouringLiveCellCount < 2 || neighbouringLiveCellCount > 3)
						{
							cell = new Cell(rowId, columnId, false, GolConstants.GOL_DEAD_CELL);
							outputCellArray[rowId][columnId] = cell;
						}
						else
						{
							//3.Any live cell with two or three live neighbours lives, 
							//unchanged, to the next generation.
							outputCellArray[rowId][columnId] = inputCellArray[rowId][columnId];
						}
				  }
			  }
		}
		}
	}
}
