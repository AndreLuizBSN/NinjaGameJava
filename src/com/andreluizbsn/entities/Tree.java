package com.andreluizbsn.entities;

import java.awt.image.BufferedImage;

import com.andreluizbsn.main.Game;

public class Tree extends Entity {
	
	public static BufferedImage SPRITE_TREE_ROOT_1 = Game.spritesheet.getSprite(Game.basex*3,Game.basex*2, Game.basex, Game.basey);
	public static BufferedImage SPRITE_TREE_ROOT_2 = Game.spritesheet.getSprite(Game.basex*4,Game.basex*2, Game.basex, Game.basey);
	
	public static BufferedImage SPRITE_TREE_1 = Game.spritesheet.getSprite(Game.basex*5,Game.basex*2, Game.basex, Game.basey);
	public static BufferedImage SPRITE_TREE_2 = Game.spritesheet.getSprite(Game.basex*6,Game.basex*2, Game.basex, Game.basey);
	public static BufferedImage SPRITE_TREE_3 = Game.spritesheet.getSprite(Game.basex*7,Game.basex*2, Game.basex, Game.basey);
	
	public static BufferedImage SPRITE_TREE_4 = Game.spritesheet.getSprite(Game.basex*5,Game.basex*3, Game.basex, Game.basey);
	public static BufferedImage SPRITE_TREE_5 = Game.spritesheet.getSprite(Game.basex*6,Game.basex*3, Game.basex, Game.basey);
	public static BufferedImage SPRITE_TREE_6 = Game.spritesheet.getSprite(Game.basex*7,Game.basex*3, Game.basex, Game.basey);
	
	public boolean right = false, left = false;
	
	public int life = 1;

	public Tree ( double x, double y, int width, int height, double speed, BufferedImage sprite ) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick () {
		
	}
	
}
