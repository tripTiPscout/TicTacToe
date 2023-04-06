package academy.tictactoe.business;

import academy.tictactoe.data.GameSetUp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class GameTest {

    @Test
    public void testEasyBotFindsWin() {
        char[][] state = {
                {'_', '_', 'X'},
                {'O', 'X', 'O'},
                {'X', '_', '_'}
        };

        Move originMove = new Move(0, 2, true);

        Bot bot = new Bot(BotDifficulty.EASY);
        Move move = bot.getNextMove(new Node(new GameSetUp(), state, originMove));

        // There is no next move
        assertSame(originMove, move);
    }

    @Test
    public void findBestMoveNormal() {
        char[][] state = {
                {'_', '_', '_'},
                {'O', 'X', '_'},
                {'X', '_', '_'}
        };

        Move playerMove = new Move(1, 1, true);
        Node newState = new Node(new GameSetUp(), state, playerMove);

        Bot bot = new Bot(BotDifficulty.NORMAL);

        Move originMove = new Move(0, 2, false);
        Move botMove = bot.getNextMove(newState);

        assertEquals(originMove, botMove);
    }

    @Test
    public void testEasyVsHardPlay() {
        Game game = new Game();
        game.setPlayer1(new Bot(BotDifficulty.EASY));
        game.setPlayer2(new Bot(BotDifficulty.HARD));

        game.play(-1);

        assertEquals(-1.0f, game.getFinalScore().get().floatValue());
    }

    @Test
    public void testHardVsHardPlay() {
        Game game = new Game();
        Bot hard = new Bot(BotDifficulty.HARD);
        game.setPlayer1(hard);
        game.setPlayer2(hard);

        game.play(-1);

        assertEquals(0.0f, game.getFinalScore().get().floatValue());
    }

    //TODO : Split in multiple tests
    @Test
    public void testFindBestMove() {
        Game game = new Game();
        Bot expert = new Bot(BotDifficulty.EXPERT);
        game.setPlayer1(expert);
        game.setPlayer2(expert);

        game.play(1);
        Move move = game.getState().getOriginMove();
        assertEquals(new Move(0, 0, true), move);
        assertSame('X', game.getState().toString().charAt(0));

        game.play(1);
        move = game.getState().getOriginMove();
        assertEquals(new Move(1, 1, false), move);

        game.play(1);
        move = game.getState().getOriginMove();
        assertEquals(new Move(0, 1, true), move);

        game.play(1);
        move = game.getState().getOriginMove();
        assertEquals(new Move(0, 2, false), move);

        game.play(1);
        move = game.getState().getOriginMove();
        assertEquals(new Move(2, 0, true), move);

        game.play(1);
        move = game.getState().getOriginMove();
        assertEquals(new Move(1, 0, false), move);

        game.play(1);
        move = game.getState().getOriginMove();
        assertEquals(new Move(1, 2, true), move);

        game.play(1);
        move = game.getState().getOriginMove();
        assertEquals(new Move(2, 1, false), move);

        game.play(1);
        move = game.getState().getOriginMove();
        assertEquals(new Move(2, 2, true), move);
    }

}
