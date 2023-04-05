package academy.tictactoe.business;

public interface Player {

    public String getName();

    public Move getNextMove(Node state);

}
