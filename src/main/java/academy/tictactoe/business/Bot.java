package academy.tictactoe.business;

import java.util.List;

public class Bot implements Player {

    private final BotDifficulty difficulty;

    public Bot(BotDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getName() {
        return "Bot " + difficulty.toString();
    }

    public Move getNextMove(Node state) {
        var bestMove = findBestMove(state, difficulty.getMovesAhead(), state.isPlayerX());
        System.out.printf("%s, to get %f score.\n", bestMove.getKey(), bestMove.getValue().floatValue());

        return bestMove.getKey();
    }

    // TODO :
    //  Our Bot considers that the opponent will play optimally and if they see "inevitable"
    //  defeat - the bot just chooses the first child, basically surrendering.
    //  Fix the Bot to try to prolong the game in hope the opponent will make a mistake
    private static Pair<Move, Number> findBestMove(Node currentState, int depth, boolean isMaxPlayer) {
        if (currentState.isFinal()) {
            return new Pair<>(currentState.getOriginMove(), currentState.getScore());
        }
        if (depth == 0) {
            return new Pair<>(currentState.getOriginMove(), getEvaluation(currentState));
        }

        List<Node> children = currentState.getChildren();
        Node firstChild = children.get(0);
        Pair<Move, Number> bestMove = new Pair<>(firstChild.getOriginMove(), findBestMove(firstChild, depth - 1, !isMaxPlayer).getValue());

        for (int i = 1; i < children.size(); i++) {
            Node child = children.get(i);

            // TODO : For a bigger board this gets too slow, fix heuristic and implement alpha-beta pruning
            Pair<Move, Number> bestMoveFromChild = new Pair<>(
                    child.getOriginMove(), findBestMove(child, depth - 1, !isMaxPlayer).getValue());
            bestMove = greaterThan(bestMoveFromChild.getValue(), bestMove.getValue(), isMaxPlayer) ?
                    bestMoveFromChild : bestMove;
        }
        return bestMove;
    }

    // TODO : Write a better heuristic
    public static Float getEvaluation(Node currentState) {
        return (currentState.isPlayerX() ? -0.5f : 0.5f);
    }

    private static boolean greaterThan(Number firstScore, Number secondScore, boolean isMaxPlayer) {
        double diff = firstScore.floatValue() - secondScore.floatValue();
        if (isMaxPlayer) {
            return diff > 0;
        }
        return diff < 0;
    }

}
