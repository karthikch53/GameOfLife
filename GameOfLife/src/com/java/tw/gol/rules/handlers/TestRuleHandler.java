package com.java.tw.gol.rules.handlers;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.java.tw.gol.model.GolPlayGameRequest;
import com.java.tw.gol.model.GolPlayGameResponse;
import com.java.tw.gol.rules.GolRuleChain;

public class TestRuleHandler implements GolRuleChain {
	
	private static final Logger logger = Logger.getLogger("TestRuleHandler");

	@Override
	public void doAction(GolPlayGameRequest request,GolPlayGameResponse response)
	{
		logger.log(Level.INFO, ".......Applying dummy rule...." );
	}
}
