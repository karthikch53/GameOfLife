package com.java.tw.gol.rules.handlers;

import com.java.tw.gol.model.Cell;
import com.java.tw.gol.model.GolPlayGameRequest;
import com.java.tw.gol.model.GolPlayGameResponse;
import com.java.tw.gol.rules.GolRuleChain;
import com.java.tw.gol.utils.GolConstants;
import com.java.tw.gol.utils.GolUtils;

public class BringDeadCellToLifeRuleHandler implements GolRuleChain {

	/***********************************************************************************
	 * Method        : doAction 
	 * Applied Rules : Any dead cell with exactly three live neighbors comes to life. 
	 * Purpose       : Applies the rule on the input data. Updates the output cell array
	 * Arguments     : GolPlayGameRequest request 
	 * 			       GolPlayGameResponse response
	 ************************************************************************************/
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
					//4. Any dead cell with exactly three live neighbors comes to life.
					if(neighbouringLiveCellCount == 3)
					{
						cell = new Cell(rowId, columnId, false, GolConstants.GOL_LIVE_CELL);
						outputCellArray[rowId][columnId] = cell;
					}
			    }
		    }
		}
	}
}
