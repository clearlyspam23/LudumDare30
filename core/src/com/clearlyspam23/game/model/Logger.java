package com.clearlyspam23.game.model;

public interface Logger {
	
	public enum MessageType{
		info, important, critical
	}
	
	public void log(int tick, String message, MessageType type);

}
