package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		// Above
		p.setValues(position.getRow() - 1, position.getCollumn());// Verificar a acima da minha torre
		while (getBoard().positionExistis(p) && !getBoard().thereIsApiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
			p.setRow(p.getRow() - 1);
		}
		if (getBoard().positionExistis(p) && IsThereOpponentPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}

		// Left
		p.setValues(position.getRow(), position.getCollumn() - 1);// Verificar a acima da minha torre
		while (getBoard().positionExistis(p) && !getBoard().thereIsApiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
			p.setCollumn(p.getCollumn() - 1);
		}
		if (getBoard().positionExistis(p) && IsThereOpponentPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}

		// Right
		p.setValues(position.getRow(), position.getCollumn() + 1);// Verificar a acima da minha torre
		while (getBoard().positionExistis(p) && !getBoard().thereIsApiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
			p.setCollumn(p.getCollumn() + 1);
		}
		if (getBoard().positionExistis(p) && IsThereOpponentPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}

		// Below
		p.setValues(position.getRow() + 1, position.getCollumn());// Verificar a acima da minha torre
		while (getBoard().positionExistis(p) && !getBoard().thereIsApiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
			p.setRow(p.getRow() + 1);
		}
		if (getBoard().positionExistis(p) && IsThereOpponentPiece(p)) {
			mat[p.getRow()][p.getCollumn()] = true;
		}

		return mat;
	}
}
