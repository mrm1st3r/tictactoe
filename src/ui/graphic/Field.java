package ui.graphic;

import javax.swing.JButton;

public class Field extends JButton {

	static final long serialVersionUID = 0x1;

	protected int x;
	protected int y;

	public Field(int x, int y)
	{
		super();
		this.x = x;
		this.y = y;
	}

	public void update()
	{
		setText("");
	}
}
