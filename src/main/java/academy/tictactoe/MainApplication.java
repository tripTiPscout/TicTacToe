package academy.tictactoe;

import academy.tictactoe.business.Bot;
import academy.tictactoe.business.BotDifficulty;
import academy.tictactoe.business.ConsolePlayer;
import academy.tictactoe.business.Game;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MainApplication.class, args);

        // context. methods

        // Play game:
//        char[][] state = {
//                {'_', '_', '_'},
//                {'_', '_', '_'},
//                {'_', '_', 'X'}
//        };
//
//        Game game = new Game();
//        //Game game = new Game(state);
//        //Game game = new Game(new GameSetUp(5, 5));
//
//        //game.setPlayer1(new ConsolePlayer("Kati"));
//        game.setPlayer1(new ConsolePlayer("Peter"));
//        game.setPlayer2(new Bot(BotDifficulty.HARD));
//
//        game.play(-1);
    }

}
