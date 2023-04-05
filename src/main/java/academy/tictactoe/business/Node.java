package academy.tictactoe.business;

import academy.tictactoe.data.GameSetUp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Node {

    private final GameSetUp setUp;

    private final char[][] state;

    private final Move originMove;

    private boolean isEvaluated = false;

    private Integer score = null;

    private List<Node> children = null;

    // TODO Validate input state against dimensions and possible symbols
    public Node(GameSetUp setUp, char[][] state, Move originMove) {
        this.setUp = setUp;

        this.state = copyState(state);
        this.originMove = originMove;
    }

    public Node(Node parentNode, Move move) {
        this.setUp = parentNode.setUp;

        this.state = copyState(parentNode.state);
        char symbol = move.isPlayerX() ? setUp.getXPlayerSymbol() : setUp.getOpponentSymbol();
        this.state[move.getRow()][move.getColumn()] = symbol;

        this.originMove = move;
    }

    public static Node empty(GameSetUp gameSetUp) {
        int n = gameSetUp.getDimension();
        char[][] emptyState = new char[n][n];

        for (char[] row : emptyState) {
            Arrays.fill(row, gameSetUp.getEmptySymbol());
        }

        return new Node(gameSetUp, emptyState, null);
    }

    public Node move(Move move) {
        return new Node(this, move);
    }

    private static char[][] copyState(char[][] source) {
        int numberOfColumns = source.length;
        int numberOfRows = source[0].length;

        char[][] destination = new char[numberOfColumns][numberOfRows];
        for (int column = 0; column < numberOfColumns; column++) {
            System.arraycopy(source[column], 0, destination[column], 0, numberOfRows);
        }
        return destination;
    }

    public GameSetUp getSetUp() {
        return setUp;
    }

    public boolean isPlayerX() {
        if (originMove != null) {
            return !originMove.isPlayerX();
        }
        return true; // X's are first
    }

    public Move getOriginMove() {
        return originMove;
    }

    public List<Node> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<>();
            List<Move> possibleMoves = possibleMoves();
            for (Move move : possibleMoves) {
                this.children.add(this.move(move));
            }
        }
        return this.children;
    }

    protected List<Move> possibleMoves() {
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < setUp.getDimension(); i++) {
            for (int j = 0; j < setUp.getDimension(); j++) {
                if (state[i][j] == setUp.getEmptySymbol()) {
                    moves.add(new Move(i, j, isPlayerX()));
                }
            }
        }
        return moves;
    }

    // Calling this method would evaluate the node, if it was not done already
    public boolean isFinal() {
        return (getScore() != null);
    }

    // Calling this method would evaluate the node, if it was not done already
    public Integer getScore() {
        if (!isEvaluated) {
            Character winSymbol = findWin();
            if (winSymbol != null) {
                this.score = (winSymbol == setUp.getXPlayerSymbol()) ? 1 : -1;
            } else if (possibleMoves().isEmpty()) {
                this.score = 0;
            } else {
                this.score = null;
            }

            this.isEvaluated = true;
        }
        return this.score;
    }

    protected Character findWin() {
        char winSymbol;
        int countSame;
        int N = setUp.getDimension();

        WinnableSequence[] ws = {
                (int i, int j) -> { // Row sequence
                    return this.state[i][j];
                },
                (int i, int j) -> { // Column sequence
                    return this.state[j][i];
                },
                (int i, int j) -> { // Main diagonal - up
                    return this.state[i + j][j];
                },
                (int i, int j) -> { // Opposing diagonal - down
                    return this.state[j][i + j];
                },
                (int i, int j) -> { // Main diagonal - up
                    return this.state[i - j][j];
                },
                (int i, int j) -> { // Opposing diagonal - down
                    return this.state[N - j - 1][i + j];
                }
        };

        for (WinnableSequence seq : ws) {
            for (int i = 0; i < N; i++) {
                winSymbol = seq.charAt(i, 0);
                countSame = 0;
                for (int j = 0; j < N; j++) {
                    try {
                        if (seq.charAt(i, j) != setUp.getEmptySymbol() &&
                                seq.charAt(i, j) == winSymbol) {
                            countSame = countSame + 1;
                        } else {
                            winSymbol = seq.charAt(i, j);
                            countSame = 1;
                        }
                        if (countSame == setUp.getCountToWin()) {
                            return winSymbol;
                        }
                    } catch (java.lang.ArrayIndexOutOfBoundsException exc) {
                        break;
                    }
                }
            }
        }

        return null;
    }

    /**
     * A functional interface to define possible traversal sequence
     * for the elements of the state matrix. The state would indicate
     * a winning state when there is a sequence of same symbols,
     * longer that the {@link GameSetUp#getCountToWin}
     */
    @FunctionalInterface
    interface WinnableSequence {
        char charAt(int start, int index);
    }

    // Readable, but code repetition
//    protected Character findWinner() {
//
//        char winSymbol;
//        int countSame;
//
//
//        for (int row = 0; row < setUp.getDimension(); row++) {
//            winSymbol = this.state[row][0];
//            countSame = 0;
//            for (int column = 0; column < setUp.getDimension(); column++) {
//                if (state[row][column] != setUp.getEmptySymbol() &&
//                        state[row][column] == winSymbol) {
//                    countSame = countSame + 1;
//                } else {
//                    winSymbol = state[row][column];
//                    countSame = 1;
//                }
//                if (countSame == setUp.getCountToWin()) {
//                    return winSymbol;
//                }
//            }
//        }
//
//        //check for column win
//        for (int column = 0; column < setUp.getDimension(); column++) {
//            winSymbol = this.state[0][column];
//            countSame = 0;
//            for (int row = 0; row < setUp.getDimension(); row++) {
//                if (state[row][column] != setUp.getEmptySymbol() &&
//                        state[row][column] == winSymbol) {
//                    countSame = countSame + 1;
//                } else {
//                    winSymbol = state[row][column];
//                    countSame = 1;
//                }
//                if (countSame == setUp.getCountToWin()) {
//                    return winSymbol;
//                }
//            }
//        }
//
//        //check for win above main diagonal
//        for (int row = 0; row < setUp.getDimension(); row++) {
//            winSymbol = this.state[row][0];
//            countSame = 0;
//            for (int i = 0; i < setUp.getDimension(); i++) {
//                int ipp = (row + i) % setUp.getDimension();
//                if (state[ipp][ipp] != setUp.getEmptySymbol() &&
//                        state[ipp][ipp] == winSymbol) {
//                    countSame = countSame + 1;
//                } else {
//                    winSymbol = state[ipp][ipp];
//                    countSame = 1;
//                }
//                if (countSame == setUp.getCountToWin()) {
//                    return winSymbol;
//                }
//            }
//        }
//
//        for (int row = 0; row < setUp.getDimension(); row++) {
//            winSymbol = this.state[row][0];
//            countSame = 0;
//            for (int i = 0; i < setUp.getDimension(); i++) {
//                int ipp = (setUp.getDimension() + row - i) % setUp.getDimension();
//                if (state[ipp][ipp] != setUp.getEmptySymbol() &&
//                        state[ipp][ipp] == winSymbol) {
//                    countSame = countSame + 1;
//                } else {
//                    winSymbol = state[ipp][ipp];
//                    countSame = 1;
//                }
//                if (countSame == setUp.getCountToWin()) {
//                    return winSymbol;
//                }
//            }
//        }
//
//        for (int row = setUp.getDimension() - 1; row <= 0; row--) {
//            winSymbol = this.state[row][0];
//            countSame = 0;
//            for (int i = 0; i < setUp.getDimension(); i++) {
//                if (state[row + i][row + i] != setUp.getEmptySymbol() &&
//                        state[row + i][row + i] == winSymbol) {
//                    countSame = countSame + 1;
//                } else {
//                    winSymbol = state[row + i][row + i];
//                    countSame = 1;
//                }
//                if (countSame == setUp.getCountToWin()) {
//                    return winSymbol;
//                }
//            }
//        }
//
//        for (int row = setUp.getDimension() - 1; row <= 0; row--) {
//            winSymbol = this.state[row][0];
//            countSame = 0;
//            for (int i = 0; i < setUp.getDimension(); i++) {
//                if (state[row - i][row - i] != setUp.getEmptySymbol() &&
//                        state[row - i][row - i] == winSymbol) {
//                    countSame = countSame + 1;
//                } else {
//                    winSymbol = state[row - i][row - i];
//                    countSame = 1;
//                }
//                if (countSame == setUp.getCountToWin()) {
//                    return winSymbol;
//                }
//            }
//        }
//
//        return null;
//    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < setUp.getDimension(); row++) {
            for (int column = 0; column < setUp.getDimension(); column++) {

                builder.append(this.state[row][column]);
            }
            if (row < setUp.getDimension() - 1) builder.append('\n');
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Node otherNode = (Node) other;

        return (this.isPlayerX() == otherNode.isPlayerX()) && Arrays.deepEquals(this.state, otherNode.state);
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(isPlayerX())
                + Arrays.deepHashCode(state);
    }
}
