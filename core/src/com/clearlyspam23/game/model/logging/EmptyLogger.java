package com.clearlyspam23.game.model.logging;

import com.clearlyspam23.game.model.Logger;

public class EmptyLogger implements Logger{

	@Override
	public void log(int tick, String message, MessageType type) {
		//dont do anything
	}

}
