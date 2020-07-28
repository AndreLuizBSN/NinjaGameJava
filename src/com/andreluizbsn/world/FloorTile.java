package com.andreluizbsn.world;

import java.awt.image.BufferedImage;

public class FloorTile extends Tile{

	public boolean isMoveY = false;
	public boolean isClimb = false;
	
	public FloorTile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
	}

}
