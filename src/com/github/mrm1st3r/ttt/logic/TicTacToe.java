package com.github.mrm1st3r.ttt.logic;

import com.github.mrm1st3r.ttt.model.PlayingField;
import com.github.mrm1st3r.ttt.ui.UserInterface;

/**
 * TicTacToe main class.
 * 
 * @author Lukas Taake<lukas.taake@gmail.com>
 */

public final class TicTacToe {

	public static final String NAME = "TicTacToe";
	public static final String VERSION = "1.5.1";

	private static final char[] SIGNS = { 'X', 'O' };

	private static final int MAX_ROUNDS = 9;
	private static final int PLAYER_COUNT = 2;

	private static TicTacToe singleton = null;

	private UserInterface ui;
	private PlayingField f;

	private Player[] players;
	private int playerCount = 0;

	/**
	 * Create a new game.
	 * 
	 * @param uInterface
	 *            user interface to use
	 */
	private TicTacToe(UserInterface uInterface) {
		this.ui = uInterface;
		this.f = new PlayingField();
		this.players = new Player[PLAYER_COUNT];
		Player.loadStrategies();
	}

	/**
	 * Create the singleton.
	 * 
	 * @param uInterface
	 *            see {@link #TicTacToe(UserInterface)}
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
	 * Reset the game.
	 */
	public void reset() {
		players = new Player[PLAYER_COUNT];
		f = new PlayingField();
		playerCount = 0;
	}

	/**
	 * Start the user interface.
	 */
	public void startUi() {
		ui.init();
	}

	/**
	 * Start the game.
	 */
	public void startGame() {
		int active = 0;

		if (players[0] == null || players[1] == null) {
			throw new PlayerException("Players not set.");
		}

		for (int i = 0; (i < MAX_ROUNDS) && (getWinner() == null); i++) {

			// let the active player make moves until one is valid.
			ui.viewError(players[active].getName() + " ist am Zug.");

			while (true) {
				try {
					f.setField(players[active].play(), players[active]);
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
	 * @param pName
	 *            player name
	 * @param pStrat
	 *            player strategy
	 */
	public void addPlayer(String pName, String pStrat) {
		if (playerCount == PLAYER_COUNT) {
			throw new PlayerException("All players are set");
		}

		players[playerCount] = new Player(pName, SIGNS[playerCount++], pStrat);
	}

	/**
	 * Get a player.
	 * 
	 * @param i
	 *            player number
	 * @return the player
	 */
	public Player getPlayer(int i) {
		if (i < 0 || i >= playerCount) {
			throw new PlayerException("Couldn't access player no " + i);
		}

		return players[i];
	}

	/**
	 * Check if there is already a winner for this game.
	 * 
	 * @return winner
	 */
	public Player getWinner() {
		if (this.f.getRating() == 1) {
			return this.players[0];
		} else if (this.f.getRating() == -1) {
			return this.players[1];
		}

		return null;
	}
}
