package com.github.mrm1st3r.ttt.logic.strategy;

import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Set;

/**
 * Load all strategy classes via reflection.
 */
public final class StrategyLoader {

	private HashMap<String, PlayerStrategy> strategies;

	/**
	 * Load strategies from package logic.strategy by reflection.
	 */
	public void loadStrategies() {
		if (strategies != null) {
			return;
		}
		strategies = new HashMap<>();
		try {
			loadSubClasses();
		} catch (Exception e) {
			throw new StrategyException("Could not load strategies", e);
		}
	}

	private void loadSubClasses() {
		Reflections strategyPackage = new Reflections(PlayerStrategy.class.getPackage().getName());
		Set<Class<? extends PlayerStrategy>> classes = strategyPackage.getSubTypesOf(PlayerStrategy.class);
		classes.forEach(this::tryClassAsStrategy);
	}

	private void tryClassAsStrategy(Class<? extends PlayerStrategy> strategyClass) {
		try {
            PlayerStrategy st = strategyClass.newInstance();
            strategies.put(st.getName(), st);
        } catch (Exception e) {
            System.err.println("Could not load class as strategy: " + strategyClass.getSimpleName());
        }
	}

	/**
	 * Load a strategy discovered by {@link #loadStrategies()}.
	 * @param strategyName strategy name (see @link Strategy#getName()}
	 * @return matching strategy
	 */
	public PlayerStrategy getStrategy(String strategyName) {
		if (strategies == null) {
			throw new IllegalStateException("Strategies are not loaded yet");
		}
		PlayerStrategy strategy = strategies.get(strategyName);
		if (strategy == null) {
			throw new StrategyException("No strategy found for name: " + strategyName);
		}
		return strategy;
	}

	public int countStrategies() {
		if (strategies == null) {
			return 0;
		}
		return strategies.size();
	}
}
