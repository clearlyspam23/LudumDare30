package com.clearlyspam23.game.model.logging;

import com.clearlyspam23.game.model.Logger;

public class ConsoleLogger implements Logger {

	@Override
	public void log(int tick, String message, MessageType type) {
		System.out.println(type.name() + " - " + tick + ": " + message);
	}

}
