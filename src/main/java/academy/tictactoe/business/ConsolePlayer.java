package academy.tictactoe.business;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("consolePlayer")
public class ConsolePlayer implements Player {

    private final String name;

    public ConsolePlayer(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public Move getNextMove(Node state) {
        System.out.println("Current state is:");
        System.out.println(state.toString());

        System.out.println("What is your next move?");
        Scanner input = new Scanner(System.in);
        int row = input.nextInt();
        int column = input.nextInt();

        return new Move(row, column, state.isPlayerX());
    }

}
