package main;

import entity.Npc_HumanRedWorker;
import monster.*;
import object.*;

public class AssetSetter {
	GamePanel gp;
	public AssetSetter (GamePanel gp) {
		this.gp = gp;
	}
	public void setObject() {
//		gp.obj[0] = new OBJ_Door(gp);
//		gp.obj[0].worldX = gp.tileSize * 23;
//		gp.obj[0].worldY = gp.tileSize * 10;

		gp.obj[1] = new OBJ_Key(gp);
		gp.obj[1].worldX = gp.tileSize * 28;
		gp.obj[1].worldY = gp.tileSize * 22;
	}
	public void setNpc() {
		gp.npc[0] = new Npc_HumanRedWorker(gp);
		gp.npc[0].worldX = gp.tileSize*21;
		gp.npc[0].worldY = gp.tileSize*21;
	}
	public void setMonster() {
		gp.monster[0] = new MON_Goblin(gp);
		gp.monster[0].worldX = gp.tileSize*22;
		gp.monster[0].worldY = gp.tileSize*22;

		gp.monster[1] = new MON_Goblin(gp);
		gp.monster[1].worldX = gp.tileSize*23;
		gp.monster[1].worldY = gp.tileSize*22;

		gp.monster[2] = new MON_Goblin(gp);
		gp.monster[2].worldX = gp.tileSize*24;
		gp.monster[2].worldY = gp.tileSize*22;
	}
}
