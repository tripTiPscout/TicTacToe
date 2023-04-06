package academy.tictactoe.business;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Usually you would not make a test using dependency injection,
 * but sometimes you will need it. So let's see an example
 * configuration and test with DI - even if it's just for
 * training purposes.
 */
@ActiveProfiles("test")
@SpringBootTest
public class DIMockTest {

    @Autowired
    @Qualifier("consolePlayer")
    Player mockPlayer;

    @Autowired
    Game game;

    @Test
    public void testGame() {
        Mockito.when(mockPlayer.getName()).thenReturn("Mock name");

        Move mockMove = new Move(3, 3, true);
        Mockito.when(mockPlayer.getNextMove(Mockito.any())).thenReturn(mockMove);

        game.play(1);
        Move move = game.getState().getOriginMove();
        assertEquals(mockMove, move);

        game.play(1);
        assertTrue(game.getFinalScore().isPresent());
    }

}
