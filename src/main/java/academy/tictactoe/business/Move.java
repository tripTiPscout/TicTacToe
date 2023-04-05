package academy.tictactoe.business;

import java.util.Objects;

public class Move {

    private final boolean isPlayerX;

    private final int row;

    private final int column;

    public Move(int row, int column, boolean isPlayerX) {
        this.row = row;
        this.column = column;
        this.isPlayerX = isPlayerX;
    }

    public boolean isPlayerX() {
        return isPlayerX;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return String.format("%s moves to [%d, %d]", isPlayerX ? "Player X" : "Opponent", row, column);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof Move))
            return false;
        Move otherMove = (Move) other;

        return (this.row == otherMove.row)
                && (this.column == otherMove.column)
                && (this.isPlayerX == otherMove.isPlayerX);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.column, this.row, this.isPlayerX);
    }
}
