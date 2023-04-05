package academy.tictactoe.business;

import academy.tictactoe.data.GameSetUp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class NodeTest {

    public char[][] startState;

    public Node startNode;

    @BeforeEach
    public void setUp() {
        startState = new char[][]{
                {'X', '_', 'X', 'O'},
                {'O', 'O', 'X', 'X'},
                {'X', 'O', '_', 'O'},
                {'O', 'X', '_', '_'}};
        startNode = new Node(new GameSetUp(4, 3), this.startState, null);
    }


    @Test
    public void testWinRowColumn() {
        //assertSame('O', startNode.findWin());

        assertSame(null, startNode.findWin());

        Node node = startNode.move(new Move(3, 2, true));

        assertSame(null, node.findWin());

        node = node.move(new Move(1, 0, true));
        assertSame('X', node.findWin());

        node = node.move(new Move(0, 0, false));
        assertSame(null, node.findWin());

        node = node.move(new Move(0, 1, false));
        System.out.println(node);
        assertSame('O', node.findWin());

        node = node.move(new Move(1, 1, true));
        assertSame('X', node.findWin());
    }

    @Test
    public void testPositions() {
        List<Move> moves = startNode.possibleMoves();
        assertSame(4, moves.size());

        assertThat(moves, contains(new Move(0, 1, true),
                new Move(2, 2, true),
                new Move(3, 2, true),
                new Move(3, 3, true)));
    }

    @Test
    public void testToString() {
        assertEquals(getStateString(startState), startNode.toString());
    }

    private static String getStateString(char[][] state) {
        return Arrays.stream(state).map(String::new).collect(Collectors.joining("\n"));
    }


    // Tests immutability
    @Test
    public void testMoveImmutability() {
        Move move = new Move(0, 1, true);

        String origin = startNode.toString();
        Node result = startNode.move(move);

        assertEquals(origin, startNode.toString(), "Check that the original state has not changed");

        char symbol = result.toString().charAt(
                (startNode.getSetUp().getDimension() * move.getRow()) + move.getColumn());

        assertSame(move.isPlayerX() ? startNode.getSetUp().getXPlayerSymbol() : startNode.getSetUp().getOpponentSymbol(), symbol);
    }

    @Test
    public void testChildren() {

        Node node = startNode.move(new Move(0, 1, true));

        List<Move> moves = node.possibleMoves();
        assertEquals(3, moves.size());

        assertThat(moves, contains(
                new Move(2, 2, false),
                new Move(3, 2, false),
                new Move(3, 3, false)));
    }

}
