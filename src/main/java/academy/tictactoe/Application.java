package academy.tictactoe;

import academy.tictactoe.business.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Lazy
public class Application {

    private final Game game;

    @Autowired
    protected Application(Game game) {
        this.game = game;
    }

    protected Application() {
        this.game = null;
    }

    @PostConstruct
    public void run() {
        if (this.game != null) {
            game.play(-1);
        }
    }

}
