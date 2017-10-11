package com.github.mrm1st3r.ttt.logic;

public interface TicTacToeObserver {
    void initialize(TicTacToe game);

    void viewError(String e);

    void drawPlayingField();

    void announceActivePlayer(Player player);

    void announceWinner(Player winner);
}
