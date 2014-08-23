package com.clearlyspam23.game.model.logging;

import com.clearlyspam23.game.model.Logger;

public class ConsoleLogger implements Logger {

	@Override
	public void log(String message) {
		System.out.println(message);
	}

}
