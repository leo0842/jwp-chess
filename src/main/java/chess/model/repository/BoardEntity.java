package chess.model.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("BOARD")
public class BoardEntity {

    @Id
    private Integer id;
    @Column("BOARDSQUARE_NM")
    private String squareName;
    @Column("PIECE_NM")
    private String pieceName;
    @Column("CASTLING_ELEMENT_YN")
    private String castlingElementYN;
    @Column("EN_PASSANT_NM")
    private String enPassantName;

    public BoardEntity(String squareName, String pieceName, String castlingElementYN,
        String enPassantName) {
        this.squareName = squareName;
        this.pieceName = pieceName;
        this.castlingElementYN = castlingElementYN;
        this.enPassantName = enPassantName;
    }

    public String getSquareName() {
        return squareName;
    }

    public String getPieceName() {
        return pieceName;
    }

    public String getCastlingYN() {
        return castlingElementYN;
    }

    public String getEnPassantName() {
        return enPassantName;
    }

    @Override
    public String toString() {
        return "BoardEntity{" +
            "squareName='" + squareName + '\'' +
            ", PieceName='" + pieceName + '\'' +
            ", castlingElementYN='" + castlingElementYN + '\'' +
            ", enPassantName='" + enPassantName + '\'' +
            '}';
    }
}
