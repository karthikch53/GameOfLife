package com.java.tw.gol.model;

import java.util.List;

public class CellPattern 
{
	private List<Cell> cells;
	private int rows;
	private int columns;
	
	public List<Cell> getCells() {
		return cells;
	}
	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getColumns() {
		return columns;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}

}
