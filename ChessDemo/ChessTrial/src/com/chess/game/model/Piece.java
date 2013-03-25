package com.chess.game.model;

public abstract class Piece{

	private PieceColor color;
	
	
	public Piece() {
		super();
	}


	public Piece(PieceColor color) {
		super();
		this.color = color;
	}

	public PieceColor getColor() {
		return color;
	}


	public void setColor(PieceColor color) {
		this.color = color;
	}
	
	
}
