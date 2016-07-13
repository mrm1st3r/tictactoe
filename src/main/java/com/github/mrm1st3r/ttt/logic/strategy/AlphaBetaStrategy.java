package com.github.mrm1st3r.ttt.logic.strategy;

import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Optimal computer player that uses a miniMax algorithm and can't be beaten.
 *
 * @author Lukas 'mrm1st3r' Taake
 */
public class AlphaBetaStrategy extends Strategy {

    private List<Character> symbols;

    private int simulatedMoveCount;
    private char symbol;

    @Override
    public String getName() {
        return "AI";
    }

    @Override
    public Coordinates calculateMove(PlayingField playingField, char symbol) {

        simulatedMoveCount = 0;
        // list for all moves with best rating
        ArrayList<Coordinates> bestMoves = new ArrayList<>();

        symbols = playingField.getValidSymbols();
        this.symbol = symbol;

        int bestRating = Integer.MIN_VALUE,
                alpha = Integer.MIN_VALUE,
                beta = Integer.MAX_VALUE;

        // test all free fields
        for (HashMap.Entry<Coordinates, Character> field : playingField) {

            Coordinates coordinates = field.getKey();

            if (playingField.isFree(coordinates)) {

                PlayingField copy = copyAndMove(playingField, coordinates);

                int v;
                v = minValue(copy, alpha, beta);
                if (bestRating < v) {
                    bestRating = v;
                    bestMoves.clear();
                    bestMoves.add(coordinates);
                } else if (bestRating == v) {
                    bestMoves.add(coordinates);
                }
            }
        }

        System.out.println("\nChecked " + simulatedMoveCount + " nodes ("
                + bestMoves.size() + " best moves) " + bestRating);
        // randomly return one of the equally best moves
        int i = (int) (bestMoves.size() * Math.random());
        return bestMoves.get(i);
    }

    private int nextValue(PlayingField playingField, int alpha, int beta) {
        if (playingField.getNextSymbolIndex() == symbols.indexOf(symbol)) {
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
        int playerID = symbols.indexOf(symbol);
        int relativeRating;

        if (originRating == PlayingField.UNRESOLVED) {
            relativeRating = 0;
        } else if (originRating == playerID) {
            relativeRating = 1;
        } else {
            relativeRating = -1;
        }

        return relativeRating;
    }

    private PlayingField copyAndMove(PlayingField field, Coordinates coordinates) {
        PlayingField copy = new PlayingField(field);
        copy.setField(coordinates, symbols.get(copy.getNextSymbolIndex()));

        return copy;
    }
}
