package com.andreluizbsn.entities;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.andreluizbsn.graficos.Spritesheet;
import com.andreluizbsn.main.Game;
import com.andreluizbsn.world.Camera;
import com.andreluizbsn.world.World;


public class Player extends Entity{
	
	public static BufferedImage PLAYER_SPRITE_R = Game.spritesheet.getSprite(0, 0, Game.basex, Game.basey);
	
	public static BufferedImage[] PLAYER_SPRITE_R_MOVES = new BufferedImage[6];
	public static BufferedImage[] PLAYER_SPRITE_JUMP = new BufferedImage[9];
		
	public boolean right = true, left;
	//public double gravity = 2;
	public double gravity = 0.4, vspd = 0;
	public boolean isRight = true, jump = false, isJumping = false;
	public double jumpHeight = 200, jumpFrames = 0, jumpSpeed = 4;
	
	public double life = 100;
	public int coins = 0;
	public boolean isGetLadder = false;
	public boolean isLadder = false;
	public boolean isBarLadder = false;
	public boolean isJumpAnnimation = false;
	
	public boolean isInAnnimation = false;
	public double upLadderY = 0;
	public PlayerAnnimation playerAnnimation;
	public Entity ladderIgnore;
	
	public boolean up, down;

	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
		PLAYER_SPRITE_R_MOVES[0] = PLAYER_SPRITE_R;
		PLAYER_SPRITE_R_MOVES[1] = Game.spritesheet.getSprite(Game.basex*1, 0, Game.basex, Game.basey);
		PLAYER_SPRITE_R_MOVES[2] = Game.spritesheet.getSprite(Game.basex*2, 0, Game.basex, Game.basey);
		PLAYER_SPRITE_R_MOVES[3] = Game.spritesheet.getSprite(Game.basex*3, 0, Game.basex, Game.basey);
		PLAYER_SPRITE_R_MOVES[4] = Game.spritesheet.getSprite(Game.basex*4, 0, Game.basex, Game.basey);
		PLAYER_SPRITE_R_MOVES[5] = Game.spritesheet.getSprite(Game.basex*5, 0, Game.basex, Game.basey);
		
		PLAYER_SPRITE_JUMP[0] = Game.spritesheet.getSprite(Game.basex*6, 0, Game.basex, Game.basey);
		PLAYER_SPRITE_JUMP[1] = Game.spritesheet.getSprite(Game.basex*7, 0, Game.basex, Game.basey);
		PLAYER_SPRITE_JUMP[2] = Game.spritesheet.getSprite(Game.basex*8, 0, Game.basex, Game.basey);
		PLAYER_SPRITE_JUMP[3] = Game.spritesheet.getSprite(Game.basex*9, 0, Game.basex, Game.basey);
		PLAYER_SPRITE_JUMP[4] = Game.spritesheet.getSprite(Game.basex*10, 0, Game.basex, Game.basey);
		PLAYER_SPRITE_JUMP[5] = Game.spritesheet.getSprite(Game.basex*11, 0, Game.basex, Game.basey);
		PLAYER_SPRITE_JUMP[6] = Game.spritesheet.getSprite(Game.basex*12, 0, Game.basex, Game.basey);
		PLAYER_SPRITE_JUMP[7] = Game.spritesheet.getSprite(Game.basex*13, 0, Game.basex, Game.basey);
		PLAYER_SPRITE_JUMP[8] = Game.spritesheet.getSprite(Game.basex*14, 0, Game.basex, Game.basey);
	}
	
	
	
	public void tick(){
		if ( !isInAnnimation ) {
			depth = 2;
			
			vspd+=gravity;
			
			
			if(!World.isFree((int)x,(int)(y+1)) && jump ) {
				vspd = -11;
				jump = false;
				isJumpAnnimation = true;
				this.setMaxFrames(9);
			}
			
			try {
			
				if(!World.isFree((int)x,(int)(y+vspd))) {
					
					int signVsp = 0;
					if(vspd >= 0) {
						signVsp = 1;
					} else {
						signVsp = -1;
					}
					while(World.isFree((int)x,(int)(y+signVsp))) {
						y = y+signVsp;
					}
					vspd = 0;
				}
			
			} catch (Exception e) {
				System.out.println("Game Over " + e.getStackTrace());
				Game.state = "GAME_OVER";
				return;
			}
			
			if ( Game.state.equals("NORMAL") ) {
										
				y = y + vspd;
				
				if ( vspd == 0 && isJumpAnnimation ) {
					this.setMaxFrames(6);
				}
				
				if (World.isFree((int)(x), (int)y + 1)) {
					for ( int i = 0; i < Game.entities.size(); i++ ) {
						if ( Game.entities.get(i) instanceof Enemy ) {
							if ( Entity.isColidding(this, Game.entities.get(i)) ) {
								isJumping = true;
								jump = true;
								vspd = -4;
								((Enemy) Game.entities.get(i)).life--;
								if ( ((Enemy) Game.entities.get(i)).life <= 0 ) {
									Game.entities.remove(i);
									break;
								}
							}
						} else {
							jump = false;
						}
					}
				} else {
					isJumping = false;
					isJumpAnnimation = false;
				}
	
				
				if(right && World.isFree((int)(x+speed), (int)y)) {
					x+=speed;
					isRight = true;
					if ( ! isJumping && ! isJumpAnnimation ) {
						animation(6);
					} else {
						animation(9);
					}
				}
				
				for ( int i = 0; i < Game.entities.size(); i++ ) {
					if ( Game.entities.get(i) instanceof Enemy ) {
						if ( Entity.isColidding(this, Game.entities.get(i)) ) {
							//if ( Entity.rand.nextInt(100) < 30 )
								life-=0.5;
						}
					} else if ( Game.entities.get(i) instanceof Coin ) {
						if ( Entity.isColidding(this, Game.entities.get(i)) ) {
							Game.entities.remove(i);
							coins+=1;
							break;
						}
					}
				}
				
				if ( life <= 0 ) {
					System.out.println("Game Over");
					Game.state = "GAME_OVER";
				}
			}
			
			Camera.x = Camera.clamp( (int) x - Game.WIDTH / 2, 0, World.WIDTH * Game.basex - Game.WIDTH);
			Camera.y = Camera.clamp( (int) y - Game.HEIGHT / 2, 0, World.HEIGHT * Game.basey - Game.HEIGHT);
		} else {
			if ( playerAnnimation.equals(PlayerAnnimation.LADDER_UP) && this.y > this.upLadderY ) {
				this.y-=1;
			} else {
				this.y = this.upLadderY;
				this.isInAnnimation = false;
				this.isLadder = false;
			}
		}
	}

	public void render(Graphics g) {
		if ( isRight && right && ! isJumpAnnimation  ) {
			//sprite = this.PLAYER_SPRITE_R;
			sprite = PLAYER_SPRITE_R_MOVES[curAnimation];
		} else  {
			sprite = PLAYER_SPRITE_JUMP[curAnimation];			
		}
		
		super.render(g);
	}
}
