package com.java.tw.gol.model;

public class GolPlayGameRequest
{
	private Cell[][] inputCellArray;
	private int initRowCount;
	private int initColCount; 
	private int maxRowCount;
	private int maxColCount;

	public Cell[][] getInputCellArray() {
		return inputCellArray;
	}

	public void setInputCellArray(Cell[][] inputCellArray) {
		this.inputCellArray = inputCellArray;
	}

	public int getInitRowCount() {
		return initRowCount;
	}

	public void setInitRowCount(int initRowCount) {
		this.initRowCount = initRowCount;
	}

	public int getInitColCount() {
		return initColCount;
	}

	public void setInitColCount(int initColCount) {
		this.initColCount = initColCount;
	}

	public int getMaxRowCount() {
		return maxRowCount;
	}

	public void setMaxRowCount(int maxRowCount) {
		this.maxRowCount = maxRowCount;
	}

	public int getMaxColCount() {
		return maxColCount;
	}

	public void setMaxColCount(int maxColCount) {
		this.maxColCount = maxColCount;
	}
	
}
