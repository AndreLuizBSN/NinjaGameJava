package com.andreluizbsn.entities;

import com.andreluizbsn.main.Game;

public class TreeGenerator  {
	
	public void genTree( int xu, int yu, int depth ) {
		
		Tree tree = new Tree(xu * Game.basex, yu * Game.basey,Game.basex,Game.basey,6,Tree.SPRITE_TREE_ROOT_1);
		tree.depth = depth;
		Game.entities.add(tree);
		xu++;
		tree = new Tree(xu * Game.basex, yu * Game.basey,Game.basex,Game.basey,6,Tree.SPRITE_TREE_ROOT_2);
		tree.depth = depth;
		Game.entities.add(tree);
		
		xu-=2;
		yu-=1;
		tree = new Tree(xu * Game.basex + (Game.basex / 2) + 10, yu * Game.basey,Game.basex,Game.basey,6,Tree.SPRITE_TREE_4);
		tree.depth = depth;
		Game.entities.add(tree);
		xu++;
		tree = new Tree(xu * Game.basex + (Game.basex / 2) + 10, yu * Game.basey,Game.basex,Game.basey,6,Tree.SPRITE_TREE_5);
		tree.depth = depth;
		Game.entities.add(tree);
		xu++;
		tree = new Tree(xu * Game.basex + (Game.basex / 2) + 10, yu * Game.basey,Game.basex,Game.basey,6,Tree.SPRITE_TREE_6);
		tree.depth = depth;
		Game.entities.add(tree);
		
		xu-=2;
		yu-=1;
		tree = new Tree(xu * Game.basex + (Game.basex / 2) + 10, yu * Game.basey,Game.basex,Game.basey,6,Tree.SPRITE_TREE_1);
		tree.depth = depth;
		Game.entities.add(tree);
		xu++;
		tree = new Tree(xu * Game.basex + (Game.basex / 2) + 10, yu * Game.basey,Game.basex,Game.basey,6,Tree.SPRITE_TREE_2);
		tree.depth = depth;
		Game.entities.add(tree);
		xu++;
		tree = new Tree(xu * Game.basex + (Game.basex / 2) + 10, yu * Game.basey,Game.basex,Game.basey,6,Tree.SPRITE_TREE_3);
		tree.depth = depth;
		Game.entities.add(tree);
	}
	
}
