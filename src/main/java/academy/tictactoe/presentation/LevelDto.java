package academy.tictactoe.presentation;


import academy.tictactoe.data.GameSetUp;
import academy.tictactoe.data.Level;

public class LevelDto {

    private final char[][] state;
    private final GameSetUp setUp;
    private final Long id;

    LevelDto(Long id, char[][] state, GameSetUp setUp) {
        this.id = id;
        this.setUp = setUp;
        this.state = state;
    }

    // JSON converter uses the get methods, not the private fields up
    public Long getId() {
        return this.id;
    }

    public char[][] getState() {
        return this.state;
    }

    public GameSetUp getSetUp() {
        return this.setUp;
    }

    public static LevelDto fromEntity(Level entity) {
        return new LevelDto(entity.getId(), entity.getState(), entity.getSetUp());
    }

    public static Level updateEntity(Level level, LevelDto dto) {
        // don't change the id
        level.setState(dto.getState());
        level.setSetUp(dto.getSetUp());

        return level;
    }
}
