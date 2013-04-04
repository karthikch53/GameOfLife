package com.java.tw.gol.rules;

import com.java.tw.gol.model.GolPlayGameRequest;
import com.java.tw.gol.model.GolPlayGameResponse;

public interface GolRuleChain 
{
	/****************************************************************************
	 * Method :   doAction
	 * Purpose:   Applies the rule on the input data.
	 * Arguments: GolPlayGameRequest request 
	 * 			  GolPlayGameResponse response
	 ****************************************************************************/
	public void doAction(GolPlayGameRequest request, GolPlayGameResponse response);
}
