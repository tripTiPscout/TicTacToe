package academy.tictactoe.data;

import javax.persistence.*;

@Entity
@Table(name = "levels")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private GameSetUp setUp;

    @Convert(converter = StateJSONConverter.class)
    private char[][] state;

    // All the games played starting at this state
//    @OneToMany(mappedBy = "games")
//    @Fetch(FetchMode.SELECT)
//    private List<Game> games;

    public Level(GameSetUp setUp, char[][] state) {
        this.setUp = setUp;
        this.state = state;
    }

    public Level() {}

    public void setSetUp(GameSetUp setUp) {
        this.setUp = setUp;
    }

    public void setState(char[][] state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public char[][] getState() {
        return state;
    }

    public GameSetUp getSetUp() {
        return this.setUp;
    }
}
