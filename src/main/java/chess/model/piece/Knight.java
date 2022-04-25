package chess.model.piece;

import chess.model.square.Direction;

import java.util.List;

public class Knight extends PointMovingPiece {

    private static final double POINT = 2.5;

    public Knight(Team team) {
        super(team);
    }

    public Knight(int id, Team team, int squareId) {
        super(id, team, squareId);
    }

    @Override
    public String name() {
        return PieceType.n.name();
    }

    @Override
    public boolean isNotEmpty() {
        return true;
    }

    @Override
    public boolean isPawn() {
        return false;
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public List<Direction> getDirection() {
        return List.of(
                Direction.NNE,
                Direction.NNW,
                Direction.SSE,
                Direction.SSW,
                Direction.EEN,
                Direction.EES,
                Direction.WWN,
                Direction.WWS);
    }

    @Override
    public double getPoint() {
        return POINT;
    }
}
