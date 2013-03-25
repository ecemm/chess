package com.chess.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class BoardSquare {
	private float x;
	private float y;
	private int width;
	private int height;
	private Piece piece;
	private Texture texture;
	private Rectangle rectangle;
	
	public BoardSquare() {

	}
	

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}


	public Texture getTexture() {
		return texture;
	}


	public void setTexture(Texture texture) {
		this.texture = texture;
	}


	public Rectangle getRectangle() {
		return rectangle;
	}


	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	
	
	
}
