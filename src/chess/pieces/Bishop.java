package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

	public Bishop(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// NW
		p.setValues(position.getRow() - 1, position.getCollumn() - 1);
		while (getBoard().positionExistis(p) && !getBoard().thereIsApiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
			p.setValues(p.getRow() - 1, p.getCollumn() - 1);
		}
		if (getBoard().positionExistis(p) && IsThereOpponentPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}

		// NE
		p.setValues(position.getRow() - 1, position.getCollumn() + 1);
		while (getBoard().positionExistis(p) && !getBoard().thereIsApiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
			p.setValues(p.getRow() - 1, p.getCollumn() + 1);
		}
		if (getBoard().positionExistis(p) && IsThereOpponentPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}

		// SE
		p.setValues(position.getRow() + 1, position.getCollumn() + 1);
		while (getBoard().positionExistis(p) && !getBoard().thereIsApiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
			p.setValues(p.getRow() + 1, p.getCollumn() + 1);
		}
		if (getBoard().positionExistis(p) && IsThereOpponentPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}

		// SW
		p.setValues(position.getRow() + 1, position.getCollumn() - 1);
		while (getBoard().positionExistis(p) && !getBoard().thereIsApiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
			p.setValues(p.getRow() + 1, p.getCollumn() - 1);
		}
		if (getBoard().positionExistis(p) && IsThereOpponentPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}

		return mat;
	}
}
