package com.andreluizbsn.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.andreluizbsn.entities.Player;
import com.andreluizbsn.entities.Tree;
import com.andreluizbsn.entities.TreeGenerator;
import com.andreluizbsn.graficos.Spritesheet;
import com.andreluizbsn.main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = Game.basex;
	
	private Spritesheet background_frente;;
	private BufferedImage BACKGROUND_FRENTE;
	private Spritesheet background_tras;
	private BufferedImage BACKGROUND_TRAS;

	public World ( String path ) {
		background_frente = new Spritesheet("/fundo_frente.png");
		background_tras = new Spritesheet("/fundo_tras.png");
		BACKGROUND_FRENTE = background_frente.getSprite(0, 0, (int) background_frente.getRealWidth(), (int) background_frente.getRealHeight());
		BACKGROUND_TRAS = background_tras.getSprite(0, 0, (int) background_tras.getRealWidth(), (int) background_tras.getRealHeight());
						
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			
			FloorTile ft;
			
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for ( int xx = 0; xx < map.getWidth(); xx++ ) {
				for ( int yy = 0; yy < map.getHeight(); yy++ ) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx * Game.basex, yy * Game.basey, Tile.TILE_FLOOR);
					if ( pixelAtual == 0xFF000000 ) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * Game.basex, yy * Game.basey, Tile.TILE_FLOOR);
					} else if ( pixelAtual == 0xFFffffff ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.TILE_WALL_1);
					} else if ( pixelAtual == 0xFF808080 ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.TILE_WALL_2);
					} else if ( pixelAtual == 0xFFA0A0A0 ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.TILE_GROUND_1);
					} else if ( pixelAtual == 0xFF303030 ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.TILE_GROUNG_2);
					} else if ( pixelAtual == 0xFFFF0000 ) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * Game.basex, yy * Game.basey, Tile.TILE_FLOOR_PLUS_1);
					} else if ( pixelAtual == 0xFF7F0000 ) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * Game.basex, yy * Game.basey, Tile.TILE_FLOOR_PLUS_2);
					}
					//climb
					
					else if ( pixelAtual == 0xFFE5BF00 ) {
						ft = new FloorTile(xx * Game.basex, yy * Game.basey, Tile.TILE_CLIMB_1);
						ft.isMoveY = true;
						ft.isClimb = true;
						tiles[xx + (yy * WIDTH)] = ft; 
					} else if ( pixelAtual == 0xFFFFD800 ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.TILE_CLIMB_2); 
					} else if ( pixelAtual == 0xFFC4A300 ) {
						ft = new FloorTile(xx * Game.basex, yy * Game.basey, Tile.TILE_CLIMB_3);
						ft.isMoveY = true;
						ft.isClimb = true;
						tiles[xx + (yy * WIDTH)] = ft; 
					} else if ( pixelAtual == 0xFFD8B400 ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.TILE_CLIMB_4);
					} else if ( pixelAtual == 0xFFA88C00 ) {
						ft = new FloorTile(xx * Game.basex, yy * Game.basey, Tile.TILE_CLIMB_5);
						ft.isMoveY = true;
						ft.isClimb = true;
						tiles[xx + (yy * WIDTH)] = ft; 
					} else if ( pixelAtual == 0xFFAF9200 ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.TILE_CLIMB_6);
					} else if ( pixelAtual == 0xFFB79900 ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.TILE_CLIMB_7);
					} else if ( pixelAtual == 0xFF826C00 ) {
						ft = new FloorTile(xx * Game.basex, yy * Game.basey, Tile.TILE_CLIMB_8);
						ft.isMoveY = true;
						ft.isClimb = true;
						tiles[xx + (yy * WIDTH)] = ft; 
					} else if ( pixelAtual == 0xFF897200 ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.TILE_CLIMB_9);
					} else if ( pixelAtual == 0xFF917900 ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.TILE_CLIMB_10);
					} else if ( pixelAtual == 0xFF9E8300 ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.TILE_CLIMB_11);
					}
					
					//Down
					
					else if ( pixelAtual == 0xFF007A0E ) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * Game.basex, yy * Game.basey, Tile.TILE_DOWN_1);
					} else if ( pixelAtual == 0xFF009611 ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.TILE_DOWN_2);
					} else if ( pixelAtual == 0xFF009911 ) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * Game.basex, yy * Game.basey, Tile.TILE_DOWN_3);
					} else if ( pixelAtual == 0xFF00A813 ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.TILE_DOWN_4);
					} else if ( pixelAtual == 0xFF00AD14 ) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * Game.basex, yy * Game.basey, Tile.TILE_DOWN_5);
					} else if ( pixelAtual == 0xFF00BC16 ) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * Game.basex, yy * Game.basey, Tile.TILE_DOWN_6);
					} else if ( pixelAtual == 0xFF00C617 ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.TILE_DOWN_7);
					} else if ( pixelAtual == 0xFF00DB19 ) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * Game.basex, yy * Game.basey, Tile.TILE_DOWN_8);
					} else if ( pixelAtual == 0xFF00E51A ) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * Game.basex, yy * Game.basey, Tile.TILE_DOWN_9);
					} else if ( pixelAtual == 0xFF00F71C ) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * Game.basex, yy * Game.basey, Tile.TILE_DOWN_10);
					} else if ( pixelAtual == 0xFF00FF21 ) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * Game.basex, yy * Game.basey, Tile.TILE_DOWN_11);
					}
					
					//tree front
					 else if ( pixelAtual == 0xFFB200FF ) {
						new TreeGenerator().genTree(xx, yy, 9);					
					}
					else if ( pixelAtual == 0xFF0026FF ) { // player
						/*Game.player.setX(xx * 16);
						Game.player.setY(yy * 16);*/
						Game.player = new Player( xx * Game.basex, yy * Game.basey,Game.basex,Game.basey,6,Player.PLAYER_SPRITE_R);
					}
				}
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public static boolean isFree ( int xnext, int ynext ) {

		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;

		int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;

		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

		int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
	}
	
	public static boolean[] isClimbDown ( int xnext, int ynext ) {
		
		boolean[] b = {false, false};
		boolean isClimb = false;
		boolean isMoveY = false;

		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		if ((tiles[x1 + (y1 * World.WIDTH)] instanceof FloorTile)) {
			isMoveY = ((FloorTile) tiles[x1 + (y1 * World.WIDTH)]).isMoveY;
			isClimb = ((FloorTile) tiles[x1 + (y1 * World.WIDTH)]).isClimb;
		}
		
		b[0] = isMoveY;
		b[1] = isClimb;
		return b;
	}

	public static void restartGame () {
		// TODO: Aplicar método para reiniciar o jogo corretamente.
		return;
	}

	public void render ( Graphics g ) {
		
		g.drawImage(BACKGROUND_TRAS, 0, 0, null);
		
		g.drawImage(BACKGROUND_FRENTE, 
				( (int) background_frente.getRealWidth()-(Game.WIDTH + ( (World.WIDTH * Game.basex) - (Camera.x + Game.WIDTH))))*-1,
				( (int) background_frente.getRealHeight()-(Game.HEIGHT + ( (World.HEIGHT * Game.basey) - (Camera.y + Game.HEIGHT))))*-1,
				null);
				
		int xstart = Camera.x >> 6;
		int ystart = Camera.y >> 6;
		
		Tile tile;

		int xfinal = xstart + (Game.WIDTH >> 6);
		int yfinal = ystart + (Game.HEIGHT >> 6);

		for ( int xx = xstart; xx <= xfinal; xx++ ) {
			for ( int yy = ystart; yy <= yfinal; yy++ ) {
 				if ( xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT )
					continue;
				tile = tiles[xx + (yy * WIDTH)];
				if ( tile != null ) {
					//if ( ! tile.isWater ) {
 						tile.render(g);
					/*} else {
						if ( tile.tileType.equals("CALM_WATER") ) {
							if ( tile.currentAnnimation < tile.maxAnnimation ) {
								tile = new FloorTile(xx, yy, Tile.CALM_WATER[tile.currentAnnimation]);
							} else {
								tile.currentAnnimation = 0;
								tile = new FloorTile(xx, yy, Tile.CALM_WATER[0]);
							}
						}
						tile.render(g);
					}*/
				}
			}
		}
	}
}
