package sk.engine.io.input;

import sk.engine.graphics.Window;

public final class Mouse {
	
	public static final void create() {
		Mouse.create();
	}
	
	public static final boolean next() {
		return org.lwjgl.input.Mouse.next();
	}
	
	public static final int getEventButton() {
		return org.lwjgl.input.Mouse.getEventButton();
	}
	
	public static final int getX() {
		return org.lwjgl.input.Mouse.getX();
	}
	
	public static final int getY() {
		return Window.getHeight() - org.lwjgl.input.Mouse.getY();
	}
	
	public static final int getDX() {
		return org.lwjgl.input.Mouse.getDX();
	}
	
	public static final int getDY() {
		return org.lwjgl.input.Mouse.getDY();
	}
	
	public static final String getButtonName(int button) {
		return org.lwjgl.input.Mouse.getButtonName(button);
	}
	
	public static final boolean getEventButtonState() {
		return org.lwjgl.input.Mouse.getEventButtonState();
	}
	
	public static final boolean isButtonDown(int button) {
		return org.lwjgl.input.Mouse.isButtonDown(button);
	}
	
	public static final boolean isGrabbed() {
		return org.lwjgl.input.Mouse.isGrabbed();
	}
	
	public static final boolean isInsideWindow() {
		return org.lwjgl.input.Mouse.isInsideWindow();
	}
	
	public static final int getDWheel() {
		return org.lwjgl.input.Mouse.getDWheel();
	}
	
	public static final void setGrabbed(boolean grabbed) {
		org.lwjgl.input.Mouse.setGrabbed(grabbed);
	}
	
	public static final void setCursorPosition(int x, int y) {
		org.lwjgl.input.Mouse.setCursorPosition(x, y);
	}
	
	public static final boolean isCreated() {
		return org.lwjgl.input.Mouse.isCreated();
	}
}