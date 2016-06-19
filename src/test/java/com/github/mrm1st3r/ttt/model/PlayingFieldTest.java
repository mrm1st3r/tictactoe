package com.github.mrm1st3r.ttt.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.mrm1st3r.ttt.logic.Player;
import com.github.mrm1st3r.ttt.logic.TicTacToe;
import com.github.mrm1st3r.ttt.logic.strategy.LinearStrategy;
import com.github.mrm1st3r.ttt.model.Coordinates;
import com.github.mrm1st3r.ttt.model.PlayingField;
import com.github.mrm1st3r.ttt.ui.TextUI;

public class PlayingFieldTest {

	private PlayingField f;
	private static TicTacToe t;

	@BeforeClass
	public static void classInit() {
		t = TicTacToe.create(new TextUI());
		// Spieler mit Strategie erzeugen
		t.addPlayer("Test 1", "Dumm");
		t.addPlayer("Test 2", "Dumm");
	}

	@Before
	public void init() {
		f = new PlayingField();
	}

	@Test
	public void testSetField() {
		f.setField(new Coordinates(1, 1), t.getPlayer(0));
		assertTrue("Feld setzen", f.getField(new Coordinates(1, 1)) == t.getPlayer(0).getSign());
	}

	@Test(expected = com.github.mrm1st3r.ttt.model.FieldSetException.class)
	public void testSetFieldIllegalCoordinates() {
		f.setField(new Coordinates(1, 4), t.getPlayer(0));
	}

	@Test(expected = com.github.mrm1st3r.ttt.model.FieldSetException.class)
	public void testSetFieldDouble() {
		f.setField(new Coordinates(1, 1), t.getPlayer(0));
		f.setField(new Coordinates(1, 1), t.getPlayer(1));
	}

	@Test(expected = com.github.mrm1st3r.ttt.model.FieldSetException.class)
	public void testSetFieldUnknownSign() {
		f.setField(new Coordinates(1, 1), new Player("Test 1", 'T', new LinearStrategy()));
	}

	@Test(expected = com.github.mrm1st3r.ttt.model.FieldSetException.class)
	public void testSetFieldAfterWinning() {
		f.setField(new Coordinates(1, 1), t.getPlayer(0));
		f.setField(new Coordinates(3, 3), t.getPlayer(1));
		f.setField(new Coordinates(1, 2), t.getPlayer(0));
		f.setField(new Coordinates(3, 2), t.getPlayer(1));
		f.setField(new Coordinates(1, 3), t.getPlayer(0));
		f.setField(new Coordinates(3, 1), t.getPlayer(1));
	}

	@Test(expected = com.github.mrm1st3r.ttt.model.FieldSetException.class)
	public void testSetFieldDoubleMove() {
		f.setField(new Coordinates(1, 1), t.getPlayer(0));
		f.setField(new Coordinates(1, 2), t.getPlayer(0));
	}

	@Test
	public void testResetField() {
		f.resetField(new Coordinates(1, 2));
	}

	@Test(expected = com.github.mrm1st3r.ttt.model.FieldSetException.class)
	public void testResetFieldFail() {
		f.resetField(new Coordinates(1, 4));
	}

	@Test
	public void testRating() {
		f.rate();
		assertEquals(f.getRating(), 0);
	}

	@Test
	public void testRating2() {
		f.setField(new Coordinates(1, 1), t.getPlayer(0));
		f.setField(new Coordinates(3, 3), t.getPlayer(1));
		f.setField(new Coordinates(1, 2), t.getPlayer(0));
		f.setField(new Coordinates(3, 2), t.getPlayer(1));
		f.setField(new Coordinates(1, 3), t.getPlayer(0));

		assertEquals(f.getRating(), 1);
	}

/*
 * PlayingField.checkLine is no longer visible.
 * 
	@Test(expected = IllegalArgumentException.class)
	public void testCheckLine1() {
		f.checkLine(2, 1, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckLine2() {
		f.checkLine(0, 2, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCheckLine3() {
		f.checkLine(3, 4, 1, 0);
	}

*/
	@Test
	public void testValidateCoordinate() {
		assertTrue(f.validateCoordinates(new Coordinates(1,1)));
		assertTrue(f.validateCoordinates(new Coordinates(2,2)));
		assertFalse(f.validateCoordinates(new Coordinates(3,0)));
		assertFalse(f.validateCoordinates(new Coordinates(0,3)));
	}

	@Test
	public void testRemainingMoves() {
		assertEquals(9, f.countFreeFields());

		f.setField(new Coordinates(1, 1), t.getPlayer(0));
		assertEquals(8, f.countFreeFields());
	}
}
