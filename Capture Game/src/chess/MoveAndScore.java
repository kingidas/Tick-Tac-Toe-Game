package chess;
import chess.Move;

class MoveAndScore {
    int score;
    Move point;

    MoveAndScore(int score, Move point) {
        this.score = score;
        this.point = point;
}

    public Move getPoint() {
        return point;
    }

    public int getScore() {
        return score;
    }
    
}