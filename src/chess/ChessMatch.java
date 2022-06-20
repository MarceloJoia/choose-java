package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;

	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];

		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j); // DownCasting
			}
		}
		return mat;
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPisition();
		Position target = targetPosition.toPisition();
		validateSourcePosition(source);
		ValidateTargetPosition(source, target);
		Piece capturePiece = makeMove(source, target);

		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturePiece);
			throw new ChessException("You can't put yourself in check");
		}

		check = (testCheck(opponent(currentPlayer))) ? true : false;
		
		if(testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		} else {
			nextTurn();
		}
		
		return (ChessPiece) capturePiece;
	}

	public boolean[][] PossibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPisition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturePiece = board.removePiece(target);
		board.placePiece(p, target);

		if (capturePiece != null) {
			piecesOnTheBoard.remove(capturePiece);
			capturedPieces.add(capturePiece);
		}
		return capturePiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece p = board.removePiece(target);
		board.placePiece(p, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsApiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}

	private void ValidateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("There is no " + color + "king on the board");
	}

	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPisition();
		List<Piece> opponentPiece = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPiece) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getCollumn()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for(Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for(int i=0; i<board.getRows(); i++) {
				for(int j=0; j<board.getColumns(); j++) {
					if(mat[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPisition();
						Position terget = new Position(i, j);
						Piece capturedPiece = makeMove(source, terget);
						boolean testCheck = testCheck(color);
						undoMove(source, terget, capturedPiece);
						if(!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPisition());
		piecesOnTheBoard.add(piece);
	}

	private void initialSetup() {
		placeNewPiece('h', 7, new Rook(board, Color.WHITE));
		placeNewPiece('d', 1, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE));

		placeNewPiece('b', 8, new Rook(board, Color.BLACK));
		placeNewPiece('a', 8, new King(board, Color.BLACK));

		/*
		 * placeNewPiece('a', 1, new Rook(board, Color.WHITE)); placeNewPiece('b', 1,
		 * new Knight(board, Color.WHITE)); placeNewPiece('c', 1, new Bishop(board,
		 * Color.WHITE)); placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		 * placeNewPiece('e', 1, new King(board, Color.WHITE, this)); placeNewPiece('f',
		 * 1, new Bishop(board, Color.WHITE)); placeNewPiece('g', 1, new Knight(board,
		 * Color.WHITE)); placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		 * placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this)); placeNewPiece('b',
		 * 2, new Pawn(board, Color.WHITE, this)); placeNewPiece('c', 2, new Pawn(board,
		 * Color.WHITE, this)); placeNewPiece('d', 2, new Pawn(board, Color.WHITE,
		 * this)); placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
		 * placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this)); placeNewPiece('g',
		 * 2, new Pawn(board, Color.WHITE, this)); placeNewPiece('h', 2, new Pawn(board,
		 * Color.WHITE, this));
		 * 
		 * placeNewPiece('a', 8, new Rook(board, Color.BLACK)); placeNewPiece('b', 8,
		 * new Knight(board, Color.BLACK)); placeNewPiece('c', 8, new Bishop(board,
		 * Color.BLACK)); placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		 * placeNewPiece('e', 8, new King(board, Color.BLACK, this)); placeNewPiece('f',
		 * 8, new Bishop(board, Color.BLACK)); placeNewPiece('g', 8, new Knight(board,
		 * Color.BLACK)); placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		 * placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this)); placeNewPiece('b',
		 * 7, new Pawn(board, Color.BLACK, this)); placeNewPiece('c', 7, new Pawn(board,
		 * Color.BLACK, this)); placeNewPiece('d', 7, new Pawn(board, Color.BLACK,
		 * this)); placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
		 * placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this)); placeNewPiece('g',
		 * 7, new Pawn(board, Color.BLACK, this)); placeNewPiece('h', 7, new Pawn(board,
		 * Color.BLACK, this));
		 */
	}

}
