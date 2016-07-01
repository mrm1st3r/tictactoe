package com.github.mrm1st3r.ttt.logic.strategy;

import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Set;

/**
 * @author Lukas Taake
 */
public final class StrategyLoader {

	private HashMap<String, Strategy> strategies;

	/**
	 * Load strategies from package logic.strategy by reflection.
	 */
	public void loadStrategies() {
		if (strategies != null) {
			return;
		}

		strategies = new HashMap<>();

		try {
			Reflections strategyPackage = new Reflections(Strategy.class.getPackage().getName());

			Set<Class<? extends Strategy>> classes = strategyPackage.getSubTypesOf(Strategy.class);

			for (Class<? extends Strategy> strategyClass : classes) {
				try {
					Strategy st = strategyClass.newInstance();
					strategies.put(st.getName(), st);
				} catch (Exception e) {
					System.err.println("Could not load class as strategy: " + strategyClass.getSimpleName());
				}
			}
		} catch (Exception e) {
			throw new StrategyException("Couldn't load strategies: " + e.getMessage());
		}
	}

	/**
	 * Load a strategy discovered by {@link #loadStrategies()}.
	 * @param strategyName strategy name (see @link Strategy#getName()}
	 * @return matching strategy
	 */
	public Strategy getStrategy(String strategyName) {
		if (strategies == null) {
			throw new IllegalStateException("Strategies are not loaded yet");
		}

		Strategy strategy = strategies.get(strategyName);

		if (strategy == null) {
			throw new StrategyException("No strategy found for name:" + strategyName);
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
