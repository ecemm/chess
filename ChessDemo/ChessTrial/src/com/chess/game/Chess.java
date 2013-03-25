package com.chess.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.chess.game.model.Bishop;
import com.chess.game.model.BoardSquare;
import com.chess.game.model.Coordinate;
import com.chess.game.model.King;
import com.chess.game.model.Knight;
import com.chess.game.model.Pawn;
import com.chess.game.model.Piece;
import com.chess.game.model.PieceColor;
import com.chess.game.model.Queen;
import com.chess.game.model.Rook;

public class Chess implements ApplicationListener{
	
	private static final int BOARD_SQUARE_WIDTH=8;
	private static final int BOARD_SQUARE_HEIGHT=8;
	
	private static final int SQUARE_WIDTH=80;
	private static final int SQUARE_HEIGHT=80;
	
	private Coordinate sourceCoordinate;
	
	private int touchCount=0;
	
	private Texture rookBlack;
	private Texture rookWhite;
	private Texture knightBlack;
	private Texture knightWhite;
	private Texture bishopBlack;
	private Texture bishopWhite;
	private Texture queenBlack;
	private Texture queenWhite;
	private Texture kingBlack;
	private Texture kingWhite;
	private Texture pawnBlack;
	private Texture pawnWhite;
	
	
	private Rectangle recRookBlackRight;
	private Rectangle recRookBlackLeft;
	private Rectangle recRookWhiteRight;
	private Rectangle recRookWhiteLeft;
	private Rectangle recKnightBlackRight;
	private Rectangle recKnightBlackLeft;
	private Rectangle recKnightWhiteRight;
	private Rectangle recKnightWhiteLeft;
	private Rectangle recBishopBlackRight;
	private Rectangle recBishopBlackLeft;
	private Rectangle recBishopWhiteRight;
	private Rectangle recBishopWhiteLeft;
	private Rectangle recQueenBlack;
	private Rectangle recQueenWhite;
	private Rectangle recKingBlack;
	private Rectangle recKingWhite;
	private Array<Rectangle> recPawnBlacks;
	private Array<Rectangle> recPawnWhites;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Rectangle chessSquare;
	private ShapeRenderer shapeRenderer;
	private BoardSquare[][] boardSquareMatrix;
	
	private Skin skin;
	private BitmapFont font;
	private boolean isActionValid=true;
	
	@Override
	public void create() {		
		
		boardSquareMatrix=new BoardSquare[BOARD_SQUARE_WIDTH][BOARD_SQUARE_HEIGHT];
		
		for (int i = 0; i < BOARD_SQUARE_WIDTH; i++) {
			for(int j=0;j<BOARD_SQUARE_HEIGHT;j++){
				boardSquareMatrix[i][j]=new BoardSquare();
			}
		}
		
		rookBlack=new Texture(Gdx.files.internal("kale_siyah.png"));
		rookWhite=new Texture(Gdx.files.internal("kale_beyaz.png"));
		knightBlack=new Texture(Gdx.files.internal("at_siyah.png"));
		knightWhite=new Texture(Gdx.files.internal("at_beyaz.png"));
		bishopBlack=new Texture(Gdx.files.internal("fil_siyah.png"));
		bishopWhite=new Texture(Gdx.files.internal("fil_beyaz.png"));
		queenBlack=new Texture(Gdx.files.internal("vezir_siyah.png"));
		queenWhite=new Texture(Gdx.files.internal("vezir_beyaz.png"));
		kingBlack=new Texture(Gdx.files.internal("sah_siyah.png"));
		kingWhite=new Texture(Gdx.files.internal("sah_beyaz.png"));
		pawnBlack=new Texture(Gdx.files.internal("piyon_siyah.png"));
		pawnWhite=new Texture(Gdx.files.internal("piyon_beyaz.png"));
		
		skin=new Skin(Gdx.files.internal("uiskin.json"));
		font = skin.getFont("default-font");
		font.setColor(Color.RED);
		
		recRookBlackRight=new Rectangle();
		recRookBlackLeft=new Rectangle();
		
		recRookWhiteLeft=new Rectangle();
		recRookWhiteRight=new Rectangle();
		
		recKnightBlackRight=new Rectangle();
		recKnightBlackLeft=new Rectangle();
		
		recKnightWhiteRight=new Rectangle();
		recKnightWhiteLeft=new Rectangle();
		
		recBishopBlackRight=new Rectangle();
		recBishopBlackLeft=new Rectangle();
		
		recBishopWhiteRight=new Rectangle();
		recBishopWhiteLeft=new Rectangle();
		
		recQueenBlack=new Rectangle();
		recQueenWhite=new Rectangle();
		
		recKingBlack=new Rectangle();
		recKingWhite=new Rectangle();
		
		recPawnBlacks=new Array<Rectangle>();
		recPawnWhites=new Array<Rectangle>();
		
		camera = new OrthographicCamera();
	    camera.setToOrtho(false, 750, 750);
	    batch=new SpriteBatch();
	    
	    shapeRenderer=new ShapeRenderer();
	    
	    chessSquare=new Rectangle();
	    
	    sourceCoordinate=new Coordinate();
	    
	    initCoordinates();

		
		batch.setProjectionMatrix(camera.combined);
	   
		drawBoardSquares();//draw square board, and set board square coordinates
	    
		
		//set rectangle for each board square
	    recRookBlackRight.x=boardSquareMatrix[7][7].getX();
		recRookBlackRight.y=boardSquareMatrix[7][7].getY();
		recRookBlackRight.width=SQUARE_WIDTH;
		recRookBlackRight.height=SQUARE_HEIGHT;
		
		boardSquareMatrix[7][7].setRectangle(recRookBlackRight);
		
		
		recRookBlackLeft.x = boardSquareMatrix[7][0].getX();
		recRookBlackLeft.y = boardSquareMatrix[7][0].getY();
		recRookBlackLeft.height=SQUARE_HEIGHT;
		recRookBlackLeft.width=SQUARE_WIDTH;
		
		boardSquareMatrix[7][0].setRectangle(recRookBlackLeft);
		
		
		recRookWhiteRight.x=boardSquareMatrix[0][7].getX();
		recRookWhiteRight.y=boardSquareMatrix[0][7].getY();
		recRookWhiteRight.width=SQUARE_WIDTH;
		recRookWhiteRight.height=SQUARE_HEIGHT;
		
		boardSquareMatrix[0][7].setRectangle(recRookWhiteRight);
		
		recRookWhiteLeft.x=boardSquareMatrix[0][0].getX();
		recRookWhiteLeft.y=boardSquareMatrix[0][0].getY();
		recRookWhiteLeft.height=SQUARE_HEIGHT;
		recRookWhiteLeft.width=SQUARE_WIDTH;
		
		boardSquareMatrix[0][0].setRectangle(recRookWhiteLeft);
		
		recKnightBlackRight.x=boardSquareMatrix[7][6].getX();
		recKnightBlackRight.y=boardSquareMatrix[7][6].getY();
		recKnightBlackRight.width=SQUARE_WIDTH;
		recKnightBlackRight.height=SQUARE_HEIGHT;
		
		boardSquareMatrix[7][6].setRectangle(recKnightBlackRight);
		
		
		recKnightBlackLeft.x=boardSquareMatrix[7][1].getX();
		recKnightBlackLeft.y=boardSquareMatrix[7][1].getY();
		recKnightBlackLeft.width=SQUARE_WIDTH;
		recKnightBlackLeft.height=SQUARE_HEIGHT;
		
		boardSquareMatrix[7][1].setRectangle(recKnightBlackLeft);
		
		
		recKnightWhiteRight.x=boardSquareMatrix[0][6].getX();
		recKnightWhiteRight.y=boardSquareMatrix[0][6].getY();
		recKnightWhiteRight.width=SQUARE_WIDTH;
		recKnightWhiteRight.height=SQUARE_HEIGHT;
		
		boardSquareMatrix[0][6].setRectangle(recKnightWhiteRight);
		
		
		recKnightWhiteLeft.x=boardSquareMatrix[0][1].getX();
		recKnightWhiteLeft.y=boardSquareMatrix[0][1].getY();
		recKnightWhiteLeft.width=SQUARE_WIDTH;
		recKnightWhiteLeft.height=SQUARE_HEIGHT;
		
		boardSquareMatrix[0][1].setRectangle(recKnightWhiteLeft);
		
		
		recBishopBlackRight.x=boardSquareMatrix[7][5].getX();
		recBishopBlackRight.y=boardSquareMatrix[7][5].getY();
		recBishopBlackRight.width=SQUARE_WIDTH;
		recBishopBlackRight.height=SQUARE_HEIGHT;
		
		boardSquareMatrix[7][5].setRectangle(recBishopBlackRight);
		
		
		recBishopBlackLeft.x=boardSquareMatrix[7][2].getX();
		recBishopBlackLeft.y=boardSquareMatrix[7][2].getY();
		recBishopBlackLeft.width=SQUARE_WIDTH;
		recBishopBlackLeft.height=SQUARE_HEIGHT;
		
		boardSquareMatrix[7][2].setRectangle(recBishopBlackLeft);
		
		
		recBishopWhiteRight.x=boardSquareMatrix[0][5].getX();
		recBishopWhiteRight.y=boardSquareMatrix[0][5].getY();
		recBishopWhiteRight.width=SQUARE_WIDTH;
		recBishopWhiteRight.width=SQUARE_HEIGHT;
		
		boardSquareMatrix[0][5].setRectangle(recBishopWhiteRight);
		
		recBishopWhiteLeft.x=boardSquareMatrix[0][2].getX();
		recBishopWhiteLeft.y=boardSquareMatrix[0][2].getY();
		recBishopWhiteLeft.width=SQUARE_WIDTH;
		recBishopWhiteLeft.height=SQUARE_HEIGHT;
		
		boardSquareMatrix[0][2].setRectangle(recBishopWhiteLeft);
		
		recQueenBlack.x=boardSquareMatrix[7][3].getX();
		recQueenBlack.y=boardSquareMatrix[7][3].getY();
		recQueenBlack.width=SQUARE_WIDTH;
		recQueenBlack.height=SQUARE_HEIGHT;
		
		boardSquareMatrix[7][3].setRectangle(recQueenBlack);
		
		recQueenWhite.x=boardSquareMatrix[0][3].getX();
		recQueenWhite.y=boardSquareMatrix[0][3].getY();
		recQueenWhite.width=SQUARE_WIDTH;
		recQueenWhite.height=SQUARE_HEIGHT;
		
		boardSquareMatrix[0][3].setRectangle(recQueenWhite);
		
		recKingBlack.x=boardSquareMatrix[7][4].getX();
		recKingBlack.y=boardSquareMatrix[7][4].getY();
		recKingBlack.width=SQUARE_WIDTH;
		recKingBlack.height=SQUARE_HEIGHT;
		
		boardSquareMatrix[7][4].setRectangle(recKingBlack);
		
		
		recKingWhite.x=boardSquareMatrix[0][4].getX();
		recKingWhite.y=boardSquareMatrix[0][4].getY();
		recKingWhite.width=SQUARE_WIDTH;
		recKingWhite.height=SQUARE_HEIGHT;
		
		boardSquareMatrix[0][4].setRectangle(recKingWhite);
		
		
	    //set piece and texture for each board square 
	    boardSquareMatrix[7][0].setPiece(new Rook(PieceColor.BLACK));
		boardSquareMatrix[7][7].setPiece(new Rook(PieceColor.BLACK));
		
		boardSquareMatrix[7][0].setTexture(rookBlack);
		boardSquareMatrix[7][7].setTexture(rookBlack);
		
		boardSquareMatrix[0][0].setPiece(new Rook(PieceColor.WHITE));
		boardSquareMatrix[0][7].setPiece(new Rook(PieceColor.WHITE));
		
		boardSquareMatrix[0][0].setTexture(rookWhite);
		boardSquareMatrix[0][7].setTexture(rookWhite);
		
		boardSquareMatrix[7][1].setPiece(new Knight(PieceColor.BLACK));
		boardSquareMatrix[7][6].setPiece(new Knight(PieceColor.BLACK));
		
		boardSquareMatrix[7][1].setTexture(knightBlack);
		boardSquareMatrix[7][6].setTexture(knightBlack);

		boardSquareMatrix[0][1].setPiece(new Knight(PieceColor.WHITE));
		boardSquareMatrix[0][6].setPiece(new Knight(PieceColor.WHITE));
		
		boardSquareMatrix[0][1].setTexture(knightWhite);
		boardSquareMatrix[0][6].setTexture(knightWhite);

		boardSquareMatrix[7][2].setPiece(new Bishop(PieceColor.BLACK));
		boardSquareMatrix[7][5].setPiece(new Bishop(PieceColor.BLACK));
		
		boardSquareMatrix[7][2].setTexture(bishopBlack);
		boardSquareMatrix[7][5].setTexture(bishopBlack);

		boardSquareMatrix[0][2].setPiece(new Bishop(PieceColor.WHITE));
		boardSquareMatrix[0][5].setPiece(new Bishop(PieceColor.WHITE));
		
		boardSquareMatrix[0][2].setTexture(bishopWhite);
		boardSquareMatrix[0][5].setTexture(bishopWhite);

		boardSquareMatrix[7][3].setPiece(new Queen(PieceColor.BLACK));
		boardSquareMatrix[0][3].setPiece(new Queen(PieceColor.WHITE));

		boardSquareMatrix[7][3].setTexture(queenBlack);
		boardSquareMatrix[0][3].setTexture(queenWhite);
		
		boardSquareMatrix[7][4].setPiece(new King(PieceColor.BLACK));
		boardSquareMatrix[0][4].setPiece(new King(PieceColor.WHITE));
		
		boardSquareMatrix[7][4].setTexture(kingBlack);
		boardSquareMatrix[0][4].setTexture(kingWhite);
		
		for(int j=0;j<8;j++){
			boardSquareMatrix[1][j].setPiece(new Pawn(PieceColor.WHITE));
			boardSquareMatrix[6][j].setPiece(new Pawn(PieceColor.BLACK));
			
			boardSquareMatrix[1][j].setTexture(pawnWhite);
			boardSquareMatrix[6][j].setTexture(pawnBlack);
		}
	    
		for(int j=0;j<8;j++){
			recPawnWhites.add(new Rectangle(boardSquareMatrix[1][j].getX(), boardSquareMatrix[1][j].getY(), SQUARE_WIDTH, SQUARE_HEIGHT));
			boardSquareMatrix[1][j].setRectangle(recPawnWhites.get(j));
			recPawnBlacks.add(new Rectangle(boardSquareMatrix[6][j].getX(), boardSquareMatrix[6][j].getY(), SQUARE_WIDTH, SQUARE_HEIGHT));
			boardSquareMatrix[6][j].setRectangle(recPawnBlacks.get(j));
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
		skin.dispose();
		font.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		Gdx.gl.glEnable(GL10.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	    
	    initCoordinates();//we need to refresh the origin
		
	    camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		
		drawBoardSquares();
		
		placePiecesOnBoard();
		
		//if action is still not valid, keep the error message
		if(!isActionValid)
			showErrorMessage();
		
		if(Gdx.input.isTouched()) {
			
			touchCount++;
			

			Vector3 pos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(pos);
			
			float x = pos.x;
			float y = pos.y;
		
			//when user touches a square for the first time, we keep the coordinates in order to move it
			if(touchCount==1){
				isActionValid=true;
				sourceCoordinate.setX(x);
				sourceCoordinate.setY(y);
	        }

	        //move the piece if action is valid
	        if(touchCount==2){
		        boolean isValid = checkMotion(x,y,sourceCoordinate.getX(),sourceCoordinate.getY());
		        
		        if(isValid){
		        	isActionValid=true;
		        	Map<Integer, Integer> sourceIndice = getSourceIndice();//get the first touched board square indice from sourceCoordinate
		        	int sourceI = 0,sourceJ = 0,destI=0,destJ=0;
		        	if(sourceIndice!=null && sourceIndice.size()>0){
		        		Iterator<Entry<Integer, Integer>> iterator = sourceIndice.entrySet().iterator();
		        		while(iterator.hasNext()){
		        			Entry<Integer, Integer> next = iterator.next();
							sourceI=next.getKey();
							sourceJ=next.getValue();
		        		}
		        	}
		        	
		        	Map<Integer, Integer> destinationIndice = getDestinationIndice(x, y);//get the destination indice
		        	
		        	if(destinationIndice!=null){
						Iterator<Entry<Integer, Integer>> iterator = destinationIndice.entrySet().iterator();
						while(iterator.hasNext()){
							Entry<Integer, Integer> next = iterator.next();
							destI=next.getKey();
							destJ=next.getValue();
						}
		        	}
		        	
		        	
		        	//move piece to destination point, and clear the source cell
		        	Rectangle sourceRectangle = boardSquareMatrix[sourceI][sourceJ].getRectangle();
		        	sourceRectangle.x=boardSquareMatrix[destI][destJ].getX();
		        	sourceRectangle.y=boardSquareMatrix[destI][destJ].getY();
		        	
		        	boardSquareMatrix[sourceI][sourceJ].setX(boardSquareMatrix[destI][destJ].getX());
		        	boardSquareMatrix[sourceI][sourceJ].setY(boardSquareMatrix[destI][destJ].getY());
		        	
		        	boardSquareMatrix[sourceI][sourceJ].setRectangle(null);
		        	boardSquareMatrix[sourceI][sourceJ].setTexture(null);
		        	boardSquareMatrix[destI][destJ].setRectangle(sourceRectangle);
		        	boardSquareMatrix[destI][destJ].setPiece(boardSquareMatrix[sourceI][sourceJ].getPiece());
		        	boardSquareMatrix[sourceI][sourceJ].setPiece(null);
		        	
		        }
		        //if move action is not valid, then show error message 
		        else{
		        	isActionValid=false;
		        	showErrorMessage();

		        }
		        
		        //refresh the touchCount and sourceCoordinate, and user is able to start over
		        touchCount=0;
		        sourceCoordinate=new Coordinate();
	        }
			shapeRenderer.begin(ShapeType.Rectangle);
			shapeRenderer.setColor(Color.CYAN);
			
			Map<Integer, Integer> sourceIndiceDraw = getSourceIndice();
        	int sourceDrawI = 0,sourceDrawJ = 0;
        	if(sourceIndiceDraw!=null && sourceIndiceDraw.size()>0){
        		Iterator<Entry<Integer, Integer>> iterator = sourceIndiceDraw.entrySet().iterator();
        		while(iterator.hasNext()){
        			Entry<Integer, Integer> next = iterator.next();
					sourceDrawI=next.getKey();
					sourceDrawJ=next.getValue();
        		}
        	}
			
			shapeRenderer.rect(boardSquareMatrix[sourceDrawI][sourceDrawJ].getX(), boardSquareMatrix[sourceDrawI][sourceDrawJ].getY(), SQUARE_WIDTH, SQUARE_HEIGHT);
			shapeRenderer.end();
	      }
	}

	private void showErrorMessage() {
		float skinx = 60, skiny = 720;

		batch.begin();
		font.draw(batch, "Gecersiz Hamle", skinx, skiny);
		batch.end();
	}

	private Map<Integer,Integer> getSourceIndice() {

		Map<Integer,Integer> resultMap=new HashMap<Integer, Integer>();
		outer:
		for(int i=0;i<8;i++){
			for (int j=0;j<8;j++){
				float boardX = boardSquareMatrix[i][j].getX();
				float boardY = boardSquareMatrix[i][j].getY();
				float sourceX = sourceCoordinate.getX();
				float sourceY = sourceCoordinate.getY();
				if(sourceX>=boardX && sourceX<=boardX+SQUARE_WIDTH && sourceY>=boardY && sourceY<=boardY+SQUARE_HEIGHT){
					resultMap.put(i, j);
					break outer;
				}
			}
		}
		
		return resultMap;
	}

	private void drawBoardSquares() {
		shapeRenderer.setProjectionMatrix(camera.combined);

		shapeRenderer.begin(ShapeType.FilledRectangle);
		for(int i=0;i<BOARD_SQUARE_HEIGHT;i++){
			if(i>=1){
				refreshCoordinateY();
			}
			for(int j=0;j<BOARD_SQUARE_WIDTH;j++){
				if(j%2==0){
					if(i%2==0){
						shapeRenderer.filledRect(chessSquare.x, chessSquare.y, SQUARE_WIDTH, SQUARE_HEIGHT, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY);
						setBoardMatrix(i, j);
					}
					else{
						shapeRenderer.filledRect(chessSquare.x, chessSquare.y, SQUARE_WIDTH, SQUARE_HEIGHT, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
						setBoardMatrix(i, j);
					}
				}
				else{
					if(i%2==0){
						shapeRenderer.filledRect(chessSquare.x, chessSquare.y, SQUARE_WIDTH, SQUARE_HEIGHT, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE);
						setBoardMatrix(i, j);
					}
					else{
						shapeRenderer.filledRect(chessSquare.x, chessSquare.y, SQUARE_WIDTH, SQUARE_HEIGHT, Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY);
						setBoardMatrix(i, j);
					}

				}
				if(j<7)
					refreshCoordinateX();
					
			}
		}
		shapeRenderer.end();
	}

	private void placePiecesOnBoard() {
		batch.begin();
		
		batch.draw(rookBlack,recRookBlackLeft.x, recRookBlackLeft.y,SQUARE_WIDTH,SQUARE_HEIGHT);
		batch.draw(rookBlack,recRookBlackRight.x, recRookBlackRight.y,SQUARE_WIDTH,SQUARE_HEIGHT);
		
		
		batch.draw(rookWhite,recRookWhiteLeft.x, recRookWhiteLeft.y,SQUARE_WIDTH,SQUARE_HEIGHT);
		batch.draw(rookWhite,recRookWhiteRight.x, recRookWhiteRight.y,SQUARE_WIDTH,SQUARE_HEIGHT);
		
		batch.draw(knightBlack,recKnightBlackLeft.x, recKnightBlackLeft.y,SQUARE_WIDTH,SQUARE_HEIGHT);
		batch.draw(knightBlack,recKnightBlackRight.x, recKnightBlackRight.y,SQUARE_WIDTH,SQUARE_HEIGHT);

		batch.draw(knightWhite,recKnightWhiteLeft.x, recKnightWhiteLeft.y,SQUARE_WIDTH,SQUARE_HEIGHT);
		batch.draw(knightWhite,recKnightWhiteRight.x, recKnightWhiteRight.y,SQUARE_WIDTH,SQUARE_HEIGHT);

		batch.draw(bishopBlack,recBishopBlackLeft.x, recBishopBlackLeft.y,SQUARE_WIDTH,SQUARE_HEIGHT);
		batch.draw(bishopBlack,recBishopBlackRight.x, recBishopBlackRight.y,SQUARE_WIDTH,SQUARE_HEIGHT);

		batch.draw(bishopWhite,recBishopWhiteLeft.x, recBishopWhiteLeft.y,SQUARE_WIDTH,SQUARE_HEIGHT);
		batch.draw(bishopWhite,recBishopWhiteRight.x, recBishopWhiteRight.y,SQUARE_WIDTH,SQUARE_HEIGHT);

		batch.draw(queenBlack,recQueenBlack.x, recQueenBlack.y,SQUARE_WIDTH,SQUARE_HEIGHT);
		batch.draw(queenWhite,recQueenWhite.x, recQueenWhite.y,SQUARE_WIDTH,SQUARE_HEIGHT);

		batch.draw(kingBlack,recKingBlack.x, recKingBlack.y,SQUARE_WIDTH,SQUARE_HEIGHT);
		batch.draw(kingWhite,recKingWhite.x, recKingWhite.y,SQUARE_WIDTH,SQUARE_HEIGHT);
		
		for(int j=0;j<8;j++){
			batch.draw(pawnWhite,recPawnWhites.get(j).getX(), recPawnWhites.get(j).getY(), SQUARE_WIDTH, SQUARE_HEIGHT);
			batch.draw(pawnBlack,recPawnBlacks.get(j).getX(), recPawnBlacks.get(j).getY(), SQUARE_WIDTH, SQUARE_HEIGHT);
		}
		
		
		batch.end();
		
	}

	private void setBoardMatrix(int i, int j) {
		boardSquareMatrix[i][j].setX(chessSquare.x);
		boardSquareMatrix[i][j].setY(chessSquare.y);
		boardSquareMatrix[i][j].setWidth(SQUARE_WIDTH);
		boardSquareMatrix[i][j].setHeight(SQUARE_HEIGHT);
	}

	private void initCoordinates() {
		chessSquare.x=60;
	    chessSquare.y=60;
	}

	private void refreshCoordinateY() {
		chessSquare.y+=SQUARE_HEIGHT;
		chessSquare.x=60;

	}

	private void refreshCoordinateX() {
		chessSquare.x+=SQUARE_WIDTH;
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}


	private boolean checkMotion(float touchedX, float touchedY, float startX, float startY) {
		boolean retVal=false;
		
		outer:
		for(int i=0;i<8;i++){
			for (int j=0;j<8;j++){
				float boardX = boardSquareMatrix[i][j].getX();
				float boardY = boardSquareMatrix[i][j].getY();
				
				if(startX>=boardX && startX<=boardX+SQUARE_WIDTH && startY>=boardY && startY<=boardY+SQUARE_HEIGHT){
					Piece piece = boardSquareMatrix[i][j].getPiece();//find the piece to move
					if(piece==null){
						retVal=false;
					}
					int destI = 0,destJ = 0;
					
					Map<Integer, Integer> destIndice = getDestinationIndice(touchedX,touchedY);

					if(destIndice!=null && destIndice.size()>0){
						Iterator<Entry<Integer, Integer>> iterator = destIndice.entrySet().iterator();
						while(iterator.hasNext()){
							Entry<Integer, Integer> next = iterator.next();
							destI=next.getKey();
							destJ=next.getValue();
						}
						
						//check piece type (and color for knight and pawn) and decide whether piece moves or not
						if(piece instanceof Rook){
							retVal=false;
							break outer;
						}
						
						else if(piece instanceof Knight){
							
							if(piece.getColor()==PieceColor.WHITE){
								if(destI==i+2 && (destJ==j+1 || destJ==j-1)){
									
									retVal=true;
								}
								else
									retVal=false;
							}else if(piece.getColor()==PieceColor.BLACK){
								if(destI==i-2 && (destJ==j+1 || destJ==j-1)){
									
									retVal=true;
								}
							}
							else
								retVal=false;

							break outer;
							
						}
						
						else if (piece instanceof Bishop){
							retVal=false;
							break outer;
						}
						
						else if(piece instanceof Queen){
							retVal=false;
							break outer;
							
						}
						else if(piece instanceof King){
							retVal=false;
							break outer;
						}
						
						else if (piece instanceof Pawn){
							
							if(piece.getColor()==PieceColor.WHITE){
								if(j==destJ && (destI==i+1 || destI==i+2)){
									
									retVal= true;
								}
								else
									retVal=false;
							}else if(piece.getColor()==PieceColor.BLACK){
								if(j==destJ && (destI==i-1 || destI==i-2)){
									
									retVal= true;
								}
								else
									retVal=false;
								
							}else{
								retVal=false;
							}
							break outer;
						}
						else{
							retVal=false;
							break outer;
						}
					}
				}
			}
		}
		
		return retVal;

	}

	private Map<Integer,Integer> getDestinationIndice(float touchedX, float touchedY) {
		Map<Integer,Integer> resultMap=new HashMap<Integer, Integer>();
		
		outer:
		for(int i=0;i<8;i++){
			for (int j=0;j<8;j++){
				float boardX = boardSquareMatrix[i][j].getX();
				float boardY = boardSquareMatrix[i][j].getY();
				if(touchedX>=boardX && touchedX<=boardX+SQUARE_WIDTH && touchedY>=boardY && touchedY<=boardY+SQUARE_HEIGHT){
					resultMap.put(i, j);
					break outer;
				}
			}
		}
		
		return resultMap;
		
	}

}
