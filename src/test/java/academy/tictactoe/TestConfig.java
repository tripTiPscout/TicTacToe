package academy.tictactoe;

import academy.tictactoe.business.Player;
import academy.tictactoe.data.GameSetUp;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Bean("consolePlayer")
    @Primary
    public Player getConsolePlayer() {
        return Mockito.mock(Player.class);
    }

    @Bean("game_config")
    @Primary
    public GameSetUp getGameConfig() {
        return new GameSetUp(3, 3);
    }

    @Primary
    @Bean
    public Application getApplication() {
        return new Application();
    }

}

