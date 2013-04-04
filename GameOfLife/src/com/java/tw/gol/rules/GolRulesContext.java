package com.java.tw.gol.rules;

import java.util.Collections;
import java.util.Map;

public final class GolRulesContext 
{
	private static GolRulesContext rulesContext = null;
	private Map<String, GolRuleChain> rulesMap;
	private GolRulesContext()
	{
		
	}
	public static GolRulesContext getInstance()
	{
		if(rulesContext == null)
		{
			rulesContext = new GolRulesContext();
		}
		return rulesContext;
	}
	public Map<String, GolRuleChain> getRulesMap() {
		return Collections.unmodifiableMap(rulesMap);
	}
	public void setRulesMap(Map<String, GolRuleChain> rulesMap) {
		this.rulesMap = rulesMap;
	}

}
