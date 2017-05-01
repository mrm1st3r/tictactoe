package com.github.mrm1st3r.ttt.logic.strategy;

import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Optimal computer player that uses a miniMax algorithm and can't be beaten.
 */
public class AlphaBetaStrategy implements PlayerStrategy {

    private static final int INITIAL_ALPHA = Integer.MIN_VALUE;
    private static final int INITIAL_BETA = Integer.MAX_VALUE;

    private int simulatedMoveCount;
    private char symbol;
    private int bestKnownRating;
    private ArrayList<Coordinates> bestMoves;

    @Override
    public String getName() {
        return "AI";
    }

    @Override
    public Coordinates calculateMove(PlayingField playingField, char playerSymbol) {
        simulatedMoveCount = 0;
        symbol = playerSymbol;
        bestKnownRating = Integer.MIN_VALUE;
        bestMoves = new ArrayList<>();
        for (HashMap.Entry<Coordinates, Character> field : playingField) {
            simulateMove(playingField, field.getKey());
        }
        return randomBestMove();
    }

    private void simulateMove(PlayingField playingField, Coordinates coordinates) {
        if (playingField.isFree(coordinates)) {
            PlayingField copy = copyAndMove(playingField, coordinates);
            int moveRating = minValue(copy, INITIAL_ALPHA, INITIAL_BETA);
            if (bestKnownRating < moveRating) {
                bestKnownRating = moveRating;
                bestMoves.clear();
                bestMoves.add(coordinates);
            } else if (bestKnownRating == moveRating) {
                bestMoves.add(coordinates);
            }
        }
    }

    private Coordinates randomBestMove() {
        return bestMoves.get(ThreadLocalRandom.current().nextInt(bestMoves.size()));
    }

    private int nextValue(PlayingField playingField, int alpha, int beta) {
        if (playingField.getNextSymbol() == symbol) {
            return maxValue(playingField, alpha, beta);
        } else {
            return minValue(playingField, alpha, beta);
        }
    }

    private int maxValue(PlayingField playingField, int alpha, int beta) {
        if (playingField.isFinal()) {
            simulatedMoveCount++;
            return translateRating(playingField);
        }
        int val = alpha;
        for (HashMap.Entry<Coordinates, Character> field : playingField) {
            Coordinates coordinates = field.getKey();
            if (playingField.isFree(coordinates)) {
                PlayingField copy = copyAndMove(playingField, coordinates);
                val = Math.max(val, nextValue(copy, val, beta));
                if (val >= beta) {
                    return val;
                }
            }
        }
        return val;
    }

    private int minValue(PlayingField playingField, int alpha, int beta) {
        if (playingField.isFinal()) {
            simulatedMoveCount++;
            return translateRating(playingField);
        }
        int val = beta;
        for (HashMap.Entry<Coordinates, Character> field : playingField) {
            Coordinates coordinates = field.getKey();
            if (playingField.isFree(coordinates)) {
                PlayingField copy = copyAndMove(playingField, coordinates);
                val = Math.min(val, nextValue(copy, alpha, val));
                if (val <= alpha) {
                    return val;
                }
            }
        }
        return val;
    }

    /**
     * Translate the rating provided by the playing field (rating represents the winners player ID)
     * into a quasi analogue rating, relative to the current player (-1 for losing, 0 for tie and +1 for winning)
     */
    private int translateRating(PlayingField field) {
        int originRating = field.getRating();
        int playerID = field.getValidSymbols().indexOf(symbol);
        if (originRating == PlayingField.UNRESOLVED) {
            return 0;
        } else if (originRating == playerID) {
            return 1;
        } else {
            return -1;
        }
    }

    private PlayingField copyAndMove(PlayingField field, Coordinates coordinates) {
        PlayingField copy = new PlayingField(field);
        copy.setField(coordinates, copy.getNextSymbol());
        return copy;
    }

    public int getSimulatedMoveCount() {
        return simulatedMoveCount;
    }
}
