package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
	
	//ASSOCIA��O ENTRE OS OBJETOS
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
			//PE�O VINERAVEL AO #EN PASSANT #WHITE - S� � POSS�VEL SE ESTIVER NA LINHA 5
			if(position.getRow() == 3) { //TESTAR SE PEM UM PE�O ADVERS�RIO DO LADO DELA DO MEU PE�O
				
				//TESTAR SE TEM UMA PE�A ADVERS�RIA DO LADO ESQUERDO
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				/*
				 * [getBoard().positionExists(left)] - TESTA SE A POSI��O EXISTE
				 * [IsThereOpponentPiece(left)] - SE A PE�A � UM OPONENTE
				 * [getBoard().piece(left) == chessMetch.getEnPassantVulnerable()] - E SE ESSA PE�A EST� VUNER�VEL AO [EN PASSANT]
				 * # ESD ESSE FOR O CASO VOU INFORMAR QUE O PE�O PODE CAPTURAR A PE�A NA POSI��O #LEFT
				*/
				if(getBoard().positionExists(left) && IsThereOpponentPiece(left) && getBoard().piece(left) == chessMetch.getEnPassantVulnerable()) {
					mat[left.getRow() - 1][left.getColumn()] = true;//VOU DEFINIR COMO UMA POSS�VEL POSI��O PARA O MEU PE�O
				}
				
				//#RIGHT EN PASSANT
				//TESTAR SE TEM UMA PE�A ADVERS�RIA DO LADO DIREITO
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				/*
				 * [getBoard().positionExists(right)] - TESTA SE A POSI��O #right EXISTE
				 * [IsThereOpponentPiece(right)] - SE A PE�A � UM OPONENTE
				 * [getBoard().piece(right) == chessMetch.getEnPassantVulnerable()] - E SE ESSA PE�A EST� VUNER�VEL AO [EN PASSANT]
				 * # ESD ESSE FOR O CASO VOU INFORMAR QUE O PE�O PODE CAPTURAR A PE�A NA POSI��O #right
				*/
				if(getBoard().positionExists(right) && IsThereOpponentPiece(right) && getBoard().piece(right) == chessMetch.getEnPassantVulnerable()) {
					mat[right.getRow() - 1][right.getColumn()] = true;//VOU DEFINIR COMO UMA POSS�VEL POSI��O PARA O MEU PE�O
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
			//PE�O VINERAVEL AO #EN PASSANT #BLACK - S� � POSS�VEL SE ESTIVER NA LINHA 4
			if(position.getRow() == 4) { //TESTAR SE PEM UM PE�O ADVERS�RIO DO LADO DELA DO MEU PE�O
				
				//TESTAR SE TEM UMA PE�A ADVERS�RIA DO LADO ESQUERDO
				Position left = new Position(position.getRow(), position.getColumn() - 1);
				/*
				 * [getBoard().positionExists(left)] - TESTA SE A POSI��O EXISTE
				 * [IsThereOpponentPiece(left)] - SE A PE�A � UM OPONENTE
				 * [getBoard().piece(left) == chessMetch.getEnPassantVulnerable()] - E SE ESSA PE�A EST� VUNER�VEL AO [EN PASSANT]
				 * # ESD ESSE FOR O CASO VOU INFORMAR QUE O PE�O PODE CAPTURAR A PE�A NA POSI��O #LEFT
				*/
				if(getBoard().positionExists(left) && IsThereOpponentPiece(left) && getBoard().piece(left) == chessMetch.getEnPassantVulnerable()) {
					mat[left.getRow() + 1][left.getColumn()] = true;//VOU DEFINIR COMO UMA POSS�VEL POSI��O PARA O MEU PE�O
				}
				
				//#RIGHT EN PASSANT
				//TESTAR SE TEM UMA PE�A ADVERS�RIA DO LADO DIREITO
				Position right = new Position(position.getRow(), position.getColumn() + 1);
				/*
				 * [getBoard().positionExists(right)] - TESTA SE A POSI��O #right EXISTE
				 * [IsThereOpponentPiece(right)] - SE A PE�A � UM OPONENTE
				 * [getBoard().piece(right) == chessMetch.getEnPassantVulnerable()] - E SE ESSA PE�A EST� VUNER�VEL AO [EN PASSANT]
				 * # ESD ESSE FOR O CASO VOU INFORMAR QUE O PE�O PODE CAPTURAR A PE�A NA POSI��O #right
				*/
				if(getBoard().positionExists(right) && IsThereOpponentPiece(right) && getBoard().piece(right) == chessMetch.getEnPassantVulnerable()) {
					mat[right.getRow() + 1][right.getColumn()] = true;//VOU DEFINIR COMO UMA POSS�VEL POSI��O PARA O MEU PE�O
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
