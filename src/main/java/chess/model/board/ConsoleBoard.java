package chess.model.board;

import chess.dto.ScoreResult;
import chess.model.piece.*;
import chess.model.square.File;
import chess.model.square.Rank;
import chess.model.square.Square;
import chess.model.status.End;
import chess.model.status.Ready;
import chess.model.status.Running;
import chess.model.status.Status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ConsoleBoard {

    private static final int LINE_RANGE = 8;
    private static final int KING_COUNT = 2;

    private final Map<Square, Piece> board;
    private Status status;

    public ConsoleBoard() {
        this.status = new Ready();
        this.board = new HashMap<>();
        final List<File> files = Arrays.asList(File.values());

        initMajorPieces(Team.WHITE, Rank.ONE, files);
        initMajorPieces(Team.BLACK, Rank.EIGHT, files);
        initPawns(Team.WHITE, Rank.TWO, files);
        initPawns(Team.BLACK, Rank.SEVEN, files);
        initEmpty();
    }

    public void move(String source, String target) {
        Square sourceSquare = Square.fromString(source);
        Square targetSquare = Square.fromString(target);
        Piece piece = board.get(sourceSquare);
        checkMovable(sourceSquare, targetSquare, piece);
        moveTo(sourceSquare, targetSquare, piece);
        status = checkAliveTwoKings();
    }

    private void moveTo(Square sourceSquare, Square targetSquare, Piece piece) {
        board.put(targetSquare, piece);
        board.put(sourceSquare, new Empty());
    }

    private void checkMovable(Square sourceSquare, Square targetSquare, Piece piece) {
        if (!piece.movable(this, sourceSquare, targetSquare)) {
            throw new IllegalArgumentException("해당 위치로 움직일 수 없습니다.");
        }
        if (!piece.canMoveWithoutObstacle(this, sourceSquare, targetSquare)) {
            throw new IllegalArgumentException("경로 중 기물이 있습니다.");
        }
    }

    public ScoreResult calculateScore() {
        return ScoreResult.from(board);
    }

    public Status checkAliveTwoKings() {
        if (board.values().stream()
                .filter(Piece::isKing)
                .count() != KING_COUNT) {
            status = new End();
        }
        return status;
    }

    private void initEmpty() {
        for (Rank rank : Rank.values()) {
            fillSquareByFile(rank);
        }
    }

    private void fillSquareByFile(Rank rank) {
        for (File file : File.values()) {
            Square square = Square.of(file, rank);
            checkEmpty(square);
        }
    }

    private void checkEmpty(Square square) {
        if (!board.containsKey(square)) {
            board.put(square, new Empty());
        }
    }

    private void initPawns(Team team, Rank rank, List<File> files) {
        for (int i = 0; i < LINE_RANGE; i++) {
            board.put(Square.of(files.get(i), rank), new Pawn(team));
        }
    }

    private void initMajorPieces(Team team, Rank rank, List<File> files) {
        List<Piece> majorPiecesLineup = majorPiecesLineup(team);
        for (int i = 0; i < majorPiecesLineup.size(); i++) {
            board.put(Square.of(files.get(i), rank), majorPiecesLineup.get(i));
        }
    }

    private List<Piece> majorPiecesLineup(final Team team) {
        return List.of(
                new Rook(team),
                new Knight(team),
                new Bishop(team),
                new Queen(team),
                new King(team),
                new Bishop(team),
                new Knight(team),
                new Rook(team)
        );
    }

    public Piece get(Square square) {
        return board.get(square);
    }

    public boolean isEnd() {
        return status.isEnd();
    }

    public void startGame() {
        status = new Running();
    }

    public void finishGame() {
        status = new End();
    }
}