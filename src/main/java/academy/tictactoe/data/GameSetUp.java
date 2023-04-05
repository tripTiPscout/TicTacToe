package academy.tictactoe.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GameSetUp {

    private final String FIRST_PLAYER_SYMBOL_JSON_FIELD = "firstPlayerSymbol";

    private char xPlayerSymbol;

    private final char opponentSymbol;

    private final char emptySymbol;

    private final int dimension;

    private final int countToWin;

    @Id
    @GeneratedValue
    private Long id;

    // Be careful using this as DTO - you will always have the default values, since they are set up
    public GameSetUp() {
        this(3, 3);
    }

    public GameSetUp(int dimension, int countToWin) {
        this('X', 'O', '_', dimension, countToWin);
    }

    public GameSetUp(char xPlayerSymbol, char opponentSymbol, char emptySymbol, int dimension, int countToWin) {
        this.xPlayerSymbol = xPlayerSymbol;
        this.opponentSymbol = opponentSymbol;
        this.emptySymbol = emptySymbol;
        this.dimension = dimension;
        this.countToWin = countToWin;
    }

    @JsonProperty(FIRST_PLAYER_SYMBOL_JSON_FIELD)
    public char getXPlayerSymbol() {
        return xPlayerSymbol;
    }

    @JsonProperty(FIRST_PLAYER_SYMBOL_JSON_FIELD)
    public void setXPlayerSymbol(char xPlayerSymbol) {
        this.xPlayerSymbol = xPlayerSymbol;
    }

    public char getOpponentSymbol() {
        return opponentSymbol;
    }

    public char getEmptySymbol() {
        return emptySymbol;
    }

    public int getDimension() {
        return dimension;
    }

    public int getCountToWin() {
        return countToWin;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
