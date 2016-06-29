package com.github.mrm1st3r.ttt.logic.strategy;

import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Set;

/**
 * @author Lukas Taake
 */
public final class StrategyLoader {
	private static HashMap<String, AbstractStrategy> strategies;

	/**
	 * Load strategies from package logic.strategy by reflection.
	 */
	public static void loadStrategies() {
		strategies = new HashMap<>();

		try {
			Reflections strategyPackage = new Reflections(
					AbstractStrategy.class.getPackage().getName());

			Set<Class<? extends AbstractStrategy>> strategies =
					strategyPackage.getSubTypesOf(AbstractStrategy.class);

			for (Class<? extends AbstractStrategy> strategyClass : strategies) {
				try {
					AbstractStrategy st = strategyClass.newInstance();
					StrategyLoader.strategies.put(st.getName(), st);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			throw new StrategyException("Couldn't load strategies: "
					+ e.getMessage());
		}
	}

	/**
	 * Load a strategy discovered by {@link #loadStrategies()}.
	 * @param strategyName strategy name (see @link Strategy#getName()}
	 * @return matching strategy
	 */
	public static AbstractStrategy getStrategy(String strategyName) {
		if (strategies == null) {
			throw new IllegalStateException("Strategies are not loaded yet");
		}

		AbstractStrategy strategy = strategies.get(strategyName);

		if (strategy == null) {
			throw new StrategyException("No strategy found for name:" + strategyName);
		}

		return strategy;
	}
}
