package com.github.mrm1st3r.ttt.logic;

import com.github.mrm1st3r.ttt.logic.strategy.AbstractStrategy;
import com.github.mrm1st3r.ttt.logic.strategy.StrategyLoader;
import com.github.mrm1st3r.ttt.model.PlayingField;
import com.github.mrm1st3r.ttt.ui.UserInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TicTacToe main class.
 *
 * @author Lukas Taake<lukas.taake@gmail.com>
 */

public final class TicTacToe {

	public static final String NAME = "TicTacToe";
	public static final String VERSION = TicTacToe.class.getPackage().getImplementationVersion();

	private static final char[] SIGNS = {'X', 'O'};

	private static final int MAX_ROUNDS = 9;
	private static final int PLAYER_COUNT = 2;

	private static TicTacToe singleton = null;

	private UserInterface ui;
	private PlayingField f;

	private List<Player> players;

	/**
	 * Create a new game.
	 *
	 * @param uInterface user interface to use
	 */
	private TicTacToe(UserInterface uInterface) {
		StrategyLoader.loadStrategies();
		this.ui = uInterface;

		reset();
	}

	/**
	 * Reset the game.
	 */
	void reset() {
		players = new ArrayList<>();
	}

	/**
	 * Create the singleton.
	 *
	 * @param uInterface see {@link #TicTacToe(UserInterface)}
	 * @return the game instance
	 */
	public static TicTacToe create(UserInterface uInterface) {
		if (singleton == null) {
			singleton = new TicTacToe(uInterface);
		}

		return singleton;
	}

	/**
	 * @return the game instance.
	 */
	public static TicTacToe getInstance() {
		return singleton;
	}

	/**
	 * Start the user interface.
	 */
	public void start() {
		ui.init();
	}

	/**
	 * Start the game.
	 */
	public void startGame() {
		int active = 0;

		if (players.size() < PLAYER_COUNT) {
			throw new PlayerException("Players not set.");
		}

		List<Character> symbols = players.stream().map(Player::getSymbol).collect(Collectors.toList());
		f = new PlayingField(PlayingField.DEFAULT_WIDTH, PlayingField.DEFAULT_HEIGHT, symbols);

		for (int i = 0; (i < MAX_ROUNDS) && (getWinner() == null); i++) {

			// let the active player make moves until one is valid.
			ui.viewError(players.get(active).getName() + " ist am Zug.");

			while (true) {
				try {
					f.setField(players.get(active).play(), players.get(active));
					break;
				} catch (Exception e) {
					ui.viewError(e.getMessage());
					e.printStackTrace();
				}
			}
			ui.updateField();

			// switch active player
			active = 1 - active;
		}

		ui.printResult(getWinner());
	}

	/**
	 * @return the used playing field
	 */
	public PlayingField getPlayingField() {
		return this.f;
	}

	/**
	 * @return the active user interface
	 */
	public UserInterface getUI() {
		return this.ui;
	}

	/**
	 * Add a new player to the game.
	 *
	 * @param name  player name
	 * @param strategyName player strategy
	 */
	public void addPlayer(String name, char symbol, String strategyName) {
		if (players.size() == PLAYER_COUNT) {
			throw new PlayerException("All players are set");
		}

		AbstractStrategy strategy = StrategyLoader.getStrategy(strategyName);
		players.add(new Player(name, symbol, strategy));
	}

	/**
	 * Get a player.
	 *
	 * @param i player number
	 * @return the player
	 */
	public Player getPlayer(int i) {
		return players.get(i);
	}

	/**
	 * Check if there is already a winner for this game.
	 *
	 * @return winner
	 */
	private Player getWinner() {
		if (this.f.getRating() == 1) {
			return this.players.get(0);
		} else if (this.f.getRating() == -1) {
			return this.players.get(1);
		}

		return null;
	}
}
