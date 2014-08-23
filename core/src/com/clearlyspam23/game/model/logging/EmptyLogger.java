package com.clearlyspam23.game.model.logging;

import com.clearlyspam23.game.model.Logger;

public class EmptyLogger implements Logger{

	@Override
	public void log(String message) {
		//dont do anything
	}

}
