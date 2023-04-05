package academy.tictactoe;

import academy.tictactoe.business.Bot;
import academy.tictactoe.business.BotDifficulty;
import academy.tictactoe.business.Player;
import academy.tictactoe.data.GameSetUp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean("difficulty")
    public BotDifficulty getDifficulty() {
        return BotDifficulty.HARD;
    }

    @Bean("name")
    public String getPlayerName() {
        return "Peter";
    }

    @Bean("game_config")
    public GameSetUp getGameConfig() {
        return new GameSetUp(4, 3);
    }

    @Bean("bot")
    public Player getBot(BotDifficulty difficulty) {
        return new Bot(difficulty);
    }

    @Bean
    public char[][] getStartingState() {
        char[][] state = {
                {'O', '_', '_'},
                {'_', 'X', 'O'},
                {'_', 'O', '_'},
        };
        return state;
    }

}
