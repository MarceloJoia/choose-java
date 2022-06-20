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
		// Pego uma posi��o p
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		/*
		 * [p != null] Vejo se a posi��o est� vazia?
		 * [p instanceof Rook] Se � uma torre?
		 * [p.getColor() == getColor()] Se a cor � a mesma?
		 * [p.getMoveCount() == 0] Se a torre ainda n�o movimentou-se?
		 */
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// Above
		p.setValues(position.getRow() - 1, position.getCollumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}

		// Below
		p.setValues(position.getRow() + 1, position.getCollumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}

		// Left
		p.setValues(position.getRow(), position.getCollumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}

		// Right
		p.setValues(position.getRow(), position.getCollumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}

		// NW
		p.setValues(position.getRow() - 1, position.getCollumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}

		// NE
		p.setValues(position.getRow() - 1, position.getCollumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}

		// SW
		p.setValues(position.getRow() + 1, position.getCollumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}

		// SE
		p.setValues(position.getRow() + 1, position.getCollumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}
		
		// #Movimento especial - Torre
		/*
		 * [getMoveCount()== 0] - Ver se o REI foi movimentado.
		 * [!chessMatch.getCheck()] - Ver se o REI est� em Cheke.
		 */
		if(getMoveCount()== 0 && !chessMatch.getCheck()) {
			/* #Roque pequeno
			 * [Position(position.getRow(), position.getCollumn() + 3)] - Pego a posi��o onde deve est�r a torre do REI.
			*/
			Position posT1 = new Position(position.getRow(), position.getCollumn() + 3);
			/*
			 * [testRookCastling(posT1)] - Testar se existe uma TORRE apta para ROQUE.
			 */
			if(testRookCastling(posT1)) {
				/* Testar se as 2 casar est�o vazias
				 * [Position(position.getRow(), position.getCollumn() + 1)] - Ver se casa da DIREITA do REI est� vazia?
				 * p2 v� se a segunda casa da direita est� vazia.
				 */
				Position p1 = new Position(position.getRow(), position.getCollumn() + 1);
				Position p2 = new Position(position.getRow(), position.getCollumn() + 2);
				/*
				 * #[getBoard().piece(p2) == null] - Ver se a PRIMEIRA posi��o est� vazia
				 * #[getBoard().piece(p2) == null] - Ver se a SEGUNDA posi��o est� vazia
				 */
				if(getBoard().piece(p2) == null && getBoard().piece(p2) == null) {
					/* Incluir na matriz como poss�vel movimento.
					 * [ [position.getRow()] ] - Linha do REI
					 * [ [position.getCollumn() + 2] ] - Coluna do REI
					 */
					mat[position.getRow()][position.getCollumn() + 2] = true;
				}
			}
			
			// ROQUE Grande
			Position posT2 = new Position(position.getRow(), position.getCollumn() - 4);

			if(testRookCastling(posT2)) {
				Position p1 = new Position(position.getRow(), position.getCollumn() - 1);
				Position p2 = new Position(position.getRow(), position.getCollumn() - 2);
				Position p3 = new Position(position.getRow(), position.getCollumn() - 3);
				if(getBoard().piece(p2) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[position.getRow()][position.getCollumn() - 2] = true;
				}
			}
		}
		
		return mat;
	}
}
