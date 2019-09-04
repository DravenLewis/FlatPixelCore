package io.infinitestrike.ui;

public interface TextBoxListener {
	public abstract void onTextChanged(Object sender, String text);
	public abstract void onFocus(Object sender, boolean focused);
	public abstract void onFire(Object sender);
}
