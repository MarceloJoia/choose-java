package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	private boolean testRookCastling(Position position) {
		// Pego uma posição p
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		/*
		 * [p != null] Vejo se a posição está vazia?
		 * [p instanceof Rook] Se é uma torre?
		 * [p.getColor() == getColor()] Se a cor é a mesma?
		 * [p.getMoveCount() == 0] Se a torre ainda não movimentou-se?
		 */
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// Above
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Below
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Left
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// Right
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// NW
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// NE
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// SW
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// SE
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		// #Movimento especial - Torre
		/*
		 * [getMoveCount()== 0] - Ver se o REI foi movimentado.
		 * [!chessMatch.getCheck()] - Ver se o REI está em Cheke.
		 */
		if(getMoveCount()== 0 && !chessMatch.getCheck()) {
			/* #Roque pequeno
			 * [Position(position.getRow(), position.getColumn() + 3)] - Pego a posição onde deve estár a torre do REI.
			*/
			Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
			/*
			 * [testRookCastling(posT1)] - Testar se existe uma TORRE apta para ROQUE.
			 */
			if(testRookCastling(posT1)) {
				/* Testar se as 2 casar estão vazias
				 * [Position(position.getRow(), position.getColumn() + 1)] - Ver se casa da DIREITA do REI está vazia?
				 * p2 vê se a segunda casa da direita está vazia.
				 */
				Position p1 = new Position(position.getRow(), position.getColumn() + 1);
				Position p2 = new Position(position.getRow(), position.getColumn() + 2);
				/*
				 * #[getBoard().piece(p2) == null] - Ver se a PRIMEIRA posição está vazia
				 * #[getBoard().piece(p2) == null] - Ver se a SEGUNDA posição está vazia
				 */
				if(getBoard().piece(p2) == null && getBoard().piece(p2) == null) {
					/* Incluir na matriz como possível movimento.
					 * [ [position.getRow()] ] - Linha do REI
					 * [ [position.getColumn() + 2] ] - Coluna do REI
					 */
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			
			// ROQUE Grande
			Position posT2 = new Position(position.getRow(), position.getColumn() - 4);

			if(testRookCastling(posT2)) {
				Position p1 = new Position(position.getRow(), position.getColumn() - 1);
				Position p2 = new Position(position.getRow(), position.getColumn() - 2);
				Position p3 = new Position(position.getRow(), position.getColumn() - 3);
				if(getBoard().piece(p2) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}
		
		return mat;
	}
}
