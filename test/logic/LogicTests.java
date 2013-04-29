package logic;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	PlayerTest.class,
	PlayingFieldTest.class,
	TicTacToeTest.class
})
public class LogicTests {

}
