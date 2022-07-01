package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	
	//ASSOCIAÇÃO ENTRE OS OBJETOS
	private ChessMatch chessMetch;

	public Pawn(Board board, Color color, ChessMatch chessMetch) {
		super(board, color);
		this.chessMetch = chessMetch;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0);

		if (getColor() == Color.WHITE) {
			p.setValues(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsApiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() - 2, position.getColumn());
			Position p2 = new Position(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsApiece(p) && getBoard().positionExists(p2)
					&& !getBoard().thereIsApiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && IsThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && IsThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//#LEFT EN PASSANT
			//PEÃO VINERAVEL AO #EN PASSANT #WHITE - SÓ É POSSÍVEL SE ESTIVER NA LINHA 5
			if(position.getRow() == 3) { //TESTAR SE PEM UM PEÃO ADVERSÁRIO DO LADO DELA DO MEU PEÃO
				
				//TESTAR SE TEM UMA PEÇA ADVERSÁRIA DO LADO ESQUERDO
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				/*
				 * [getBoard().positionExists(left)] - TESTA SE A POSIÇÃO EXISTE
				 * [IsThereOpponentPiece(left)] - SE A PEÇA É UM OPONENTE
				 * [getBoard().piece(left) == chessMetch.getEnPassantVulnerable()] - E SE ESSA PEÇA ESTÁ VUNERÁVEL AO [EN PASSANT]
				 * # ESD ESSE FOR O CASO VOU INFORMAR QUE O PEÃO PODE CAPTURAR A PEÇA NA POSIÇÃO #LEFT
				*/
				if(getBoard().positionExists(left) && IsThereOpponentPiece(left) && getBoard().piece(left) == chessMetch.getEnPassantVulnerable()) {
					mat[left.getRow() - 1][left.getColumn()] = true;//VOU DEFINIR COMO UMA POSSÍVEL POSIÇÃO PARA O MEU PEÃO
				}
				
				//#RIGHT EN PASSANT
				//TESTAR SE TEM UMA PEÇA ADVERSÁRIA DO LADO DIREITO
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				/*
				 * [getBoard().positionExists(right)] - TESTA SE A POSIÇÃO #right EXISTE
				 * [IsThereOpponentPiece(right)] - SE A PEÇA É UM OPONENTE
				 * [getBoard().piece(right) == chessMetch.getEnPassantVulnerable()] - E SE ESSA PEÇA ESTÁ VUNERÁVEL AO [EN PASSANT]
				 * # ESD ESSE FOR O CASO VOU INFORMAR QUE O PEÃO PODE CAPTURAR A PEÇA NA POSIÇÃO #right
				*/
				if(getBoard().positionExists(right) && IsThereOpponentPiece(right) && getBoard().piece(right) == chessMetch.getEnPassantVulnerable()) {
					mat[right.getRow() - 1][right.getColumn()] = true;//VOU DEFINIR COMO UMA POSSÍVEL POSIÇÃO PARA O MEU PEÃO
				}
			}
			
		} 
		else {
			p.setValues(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsApiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() + 2, position.getColumn());
			Position p2 = new Position(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsApiece(p) && getBoard().positionExists(p2)
					&& !getBoard().thereIsApiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && IsThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}

			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && IsThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//#RIGHT EN PASSANT
			//PEÃO VINERAVEL AO #EN PASSANT #BLACK - SÓ É POSSÍVEL SE ESTIVER NA LINHA 4
			if(position.getRow() == 4) { //TESTAR SE PEM UM PEÃO ADVERSÁRIO DO LADO DELA DO MEU PEÃO
				
				//TESTAR SE TEM UMA PEÇA ADVERSÁRIA DO LADO ESQUERDO
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				/*
				 * [getBoard().positionExists(left)] - TESTA SE A POSIÇÃO EXISTE
				 * [IsThereOpponentPiece(left)] - SE A PEÇA É UM OPONENTE
				 * [getBoard().piece(left) == chessMetch.getEnPassantVulnerable()] - E SE ESSA PEÇA ESTÁ VUNERÁVEL AO [EN PASSANT]
				 * # ESD ESSE FOR O CASO VOU INFORMAR QUE O PEÃO PODE CAPTURAR A PEÇA NA POSIÇÃO #LEFT
				*/
				if(getBoard().positionExists(left) && IsThereOpponentPiece(left) && getBoard().piece(left) == chessMetch.getEnPassantVulnerable()) {
					mat[left.getRow() + 1][left.getColumn()] = true;//VOU DEFINIR COMO UMA POSSÍVEL POSIÇÃO PARA O MEU PEÃO
				}
				
				//#RIGHT EN PASSANT
				//TESTAR SE TEM UMA PEÇA ADVERSÁRIA DO LADO DIREITO
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				/*
				 * [getBoard().positionExists(right)] - TESTA SE A POSIÇÃO #right EXISTE
				 * [IsThereOpponentPiece(right)] - SE A PEÇA É UM OPONENTE
				 * [getBoard().piece(right) == chessMetch.getEnPassantVulnerable()] - E SE ESSA PEÇA ESTÁ VUNERÁVEL AO [EN PASSANT]
				 * # ESD ESSE FOR O CASO VOU INFORMAR QUE O PEÃO PODE CAPTURAR A PEÇA NA POSIÇÃO #right
				*/
				if(getBoard().positionExists(right) && IsThereOpponentPiece(right) && getBoard().piece(right) == chessMetch.getEnPassantVulnerable()) {
					mat[right.getRow() + 1][right.getColumn()] = true;//VOU DEFINIR COMO UMA POSSÍVEL POSIÇÃO PARA O MEU PEÃO
				}
			}
			
		}

		return mat;
	}
	
	@Override
	public String toString() {
		return "p";
	}

}
