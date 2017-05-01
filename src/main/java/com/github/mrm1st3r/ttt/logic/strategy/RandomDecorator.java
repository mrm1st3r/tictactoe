package com.github.mrm1st3r.ttt.logic.strategy;

import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;

import java.util.Random;

/**
 * Random strategy decorator.
 *
 * @author Lukas 'mrm1st3r' Taake
 */
public class RandomDecorator implements PlayerStrategy {

    private static final double DEFAULT_CHANCE = 0.5;

    private PlayerStrategy strategy;
    private double chance = DEFAULT_CHANCE;
    private Random random = new Random();

    public RandomDecorator() throws InstantiationException {
        throw new InstantiationException("You shall not construct!");
    }

    /**
     * Create a new instance.
     *
     * @param strategy strategy to decorate
     * @param chance   random chance
     */
    public RandomDecorator(PlayerStrategy strategy, double chance) {
        this.strategy = strategy;
        this.chance = chance;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Coordinates calculateMove(PlayingField field, char symbol) {
        if (Math.random() > this.chance) {
            return strategy.calculateMove(field, symbol);
        }
        return generateRandomCoordinates(field);
    }

    private Coordinates generateRandomCoordinates(PlayingField field) {
        Coordinates c;
        do {
            c = new Coordinates(
                    (int) Math.ceil(random.nextDouble() * field.getWidth()),
                    (int) Math.ceil(random.nextDouble() * field.getHeight()));
        } while (!field.isFree(c));
        return c;
    }
}
