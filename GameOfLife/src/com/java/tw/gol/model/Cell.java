package com.java.tw.gol.model;

public class Cell {
	
	private int xPos;
	private int yPos;
	private boolean isAlive;
	private String cellValue;
	
	public Cell(int x, int y, boolean cellState, String value)
	{
		this.xPos = x;
		this.yPos = y;
		this.isAlive = cellState;
		this.cellValue = value;
	}
	
	public Cell(Cell c)
	{
		this.xPos = c.xPos;
		this.yPos = c.yPos;
		this.isAlive = c.isAlive;
		this.cellValue = c.cellValue;
	}
	public int getxPos() {
		return xPos;
	}
	public void setxPos(int xPos) {
		this.xPos = xPos;
	}
	public int getyPos() {
		return yPos;
	}
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	public String getCellValue() {
		return cellValue;
	}
	public void setCellValue(String cellValue) {
		this.cellValue = cellValue;
	}
}
