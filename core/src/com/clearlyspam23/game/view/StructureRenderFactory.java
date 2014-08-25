package com.clearlyspam23.game.view;

import com.clearlyspam23.game.model.Structure;

public interface StructureRenderFactory {
	
	public StructureRender getRenderer(Structure structure, float width, PurchaseMaker purchase);

}
