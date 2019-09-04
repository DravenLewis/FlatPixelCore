package io.infinitestrike.ui;

import io.infinitestrike.entity.InputEvent;

public interface ButtonListener{
	public void buttonClicked(InputEvent e, Button b);
	public void buttonHover(InputEvent e, Button b);
}