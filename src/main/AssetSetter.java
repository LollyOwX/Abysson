package main;

import entity.Entity;
import entity.Npc_HumanRedWorker;
import monster.*;
import object.*;

import java.util.function.Supplier;

public class AssetSetter {
	GamePanel gp;
	public AssetSetter (GamePanel gp) {
		this.gp = gp;
	}

	void place(Entity[] array, int index, Supplier<Entity> factory, int col, int row, String paletteDef) {
		place(array, index, factory, col, row, paletteDef, null);
	}

	// Overload con questId: se null, non assegna nessuna quest — obj/monster continuano a usare
	// la versione a 6 argomenti sopra senza doverla toccare.
	void place(Entity[] array, int index, Supplier<Entity> factory, int col, int row, String paletteDef, String questId) {
		Entity e = factory.get();
		e.worldX  = gp.tileSize * col;
		e.worldY  = gp.tileSize * row;
		e.palette = PaletteSwap.parsePalette(paletteDef);
		if (questId != null) e.givesQuestId = questId;
		array[index] = e;
	}

	public void setObject() {
		place(gp.obj, 0, () -> new OBJ_Door(gp), 23, 10, null);
		place(gp.obj, 1, () -> new OBJ_Key(gp), 28, 22, null);
	}

	public void setNpc() {
		place(gp.npc, 0, () -> new Npc_HumanRedWorker(gp), 21, 21, null, "goblin_bounty");
	}

	public void setMonster() {
		place(gp.monster, 0, () -> new MON_Goblin(gp), 22, 22, null);
		place(gp.monster, 1, () -> new MON_Goblin(gp), 23, 22, null);
		place(gp.monster, 2, () -> new MON_Goblin(gp), 24, 22, "5BA60B>4A0F0F,A7CB35>7A1F1F,347322>2E0A0A,214614>1A0505");
	}
}