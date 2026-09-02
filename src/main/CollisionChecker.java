package main;

import entity.Entity;
import tile.SpecialTile;

public class CollisionChecker {
	GamePanel gp;
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}
	public void checkTile(Entity entity) {
		int entityLeftWorldX = (int)(entity.worldX) + entity.solidArea.x;
		int entityRightWorldX = (int)(entity.worldX) + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = (int)(entity.worldY) + entity.solidArea.y;
		int entityBottomWorldY = (int)(entity.worldY) + entity.solidArea.y + entity.solidArea.height;
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		int tileNum1, tileNum2;

		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		}
		try {

        }catch(Exception e) {

		}
	}
	public int checkObject(Entity entity, boolean player) {
		int index = 999;
		for(int i = 0; i < gp.obj.length; i++) {
			if(gp.obj[i] != null) {
				entity.solidArea.x = (int)(entity.worldX) + entity.solidArea.x;
				entity.solidArea.y = (int)(entity.worldY) + entity.solidArea.y;
				gp.obj[i].solidArea.x = (int) (gp.obj[i].worldX + gp.obj[i].solidArea.x);
				gp.obj[i].solidArea.y = (int) (gp.obj[i].worldY + gp.obj[i].solidArea.y);
				switch(entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if(player == true) {
							index = i;
						}
					}
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if(player == true) {
							index = i;
						}
					}
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if(player == true) {
							index = i;
						}
					}
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) {
							entity.collisionOn = true;
						}
						if(player == true) {
							index = i;
						}
					}
					break;
					}
					entity.solidArea.x = entity.solidAreaDefaultX;
					entity.solidArea.y = entity.solidAreaDefaultY;
					gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
					gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
				}
			}
		return index;
	}
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;
        for(int i = 0; i < target.length; i++) {
			if(target[i] != null && target[i] != entity) {
                entity.solidArea.x = (int)(entity.worldX) + entity.solidArea.x;
                entity.solidArea.y = (int)(entity.worldY) + entity.solidArea.y;
                target[i].solidArea.x = (int)(target[i].worldX + target[i].solidArea.x);
                target[i].solidArea.y = (int)(target[i].worldY + target[i].solidArea.y);
                switch(entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                                index = i;
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                                index = i;
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(target[i].solidArea)) {
                                entity.collisionOn = true;
                                index = i;
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(target[i].solidArea)) {
                                entity.collisionOn = true;
                                index = i;
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }
	public int checkEntityInteraction(Entity entity, Entity[] target, int range) {
		int index = 999;
		for(int i = 0; i < target.length; i++) {
			if(target[i] != null) {
				entity.solidArea.x = (int)(entity.worldX) + entity.solidArea.x;
				entity.solidArea.y = (int)(entity.worldY) + entity.solidArea.y;
				target[i].solidArea.x = (int)(target[i].worldX + target[i].solidArea.x);
				target[i].solidArea.y = (int)(target[i].worldY + target[i].solidArea.y);

				switch(entity.direction) {
					case "up":    entity.solidArea.y -= range; break;
					case "down":  entity.solidArea.y += range; break;
					case "left":  entity.solidArea.x -= range; break;
					case "right": entity.solidArea.x += range; break;
				}

				if(entity.solidArea.intersects(target[i].solidArea)) {
					index = i;
				}

				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;
			}
		}
		return index;
	}

    // FIX: la collisione NPC→Player avviene solo se si muovono in direzioni opposte (si "scontrano")
	public void checkPlayer(Entity entity) {
		entity.solidArea.x = (int)(entity.worldX) + entity.solidArea.x;
		entity.solidArea.y = (int)(entity.worldY) + entity.solidArea.y;
		gp.player.solidArea.x = (int)(gp.player.worldX + gp.player.solidArea.x);
		gp.player.solidArea.y = (int)(gp.player.worldY + gp.player.solidArea.y);

		switch(entity.direction) {
			case "up":
				entity.solidArea.y -= entity.speed;
				if(entity.solidArea.intersects(gp.player.solidArea)) {
					entity.collisionOn = true;
				}
				break;
			case "down":
				entity.solidArea.y += entity.speed;
				if(entity.solidArea.intersects(gp.player.solidArea)) {
					entity.collisionOn = true;
				}
				break;
			case "left":
				entity.solidArea.x -= entity.speed;
				if(entity.solidArea.intersects(gp.player.solidArea)) {
					entity.collisionOn = true;
				}
				break;
			case "right":
				entity.solidArea.x += entity.speed;
				if(entity.solidArea.intersects(gp.player.solidArea)) {
					entity.collisionOn = true;
				}
				break;
		}
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
	}

	// ─────────────────────────────────────────────
	//  RAMPINO / SALTO — usati da Player.tryJump()/tryHook()/tryCornice()
	// ─────────────────────────────────────────────

	/** Colonna/riga a 'tilesAhead' tile di distanza da 'entity', nella direzione in cui sta
	 *  guardando ORA (gestisce sia "up"/"down"/"left"/"right" che "idle_up" ecc.). Calcolata dal
	 *  centro del solidArea, non dall'angolo dello sprite. */
	public int[] tileAhead(Entity entity, int tilesAhead) {
		int centerX = entity.worldX + entity.solidArea.x + entity.solidArea.width  / 2;
		int centerY = entity.worldY + entity.solidArea.y + entity.solidArea.height / 2;
		int col = centerX / gp.tileSize;
		int row = centerY / gp.tileSize;

		String dir = entity.direction != null ? entity.direction : "down";
		if (dir.startsWith("idle_")) dir = dir.substring(5); // "idle_up" -> "up"

		switch (dir) {
			case "up":    row -= tilesAhead; break;
			case "down":  row += tilesAhead; break;
			case "left":  col -= tilesAhead; break;
			case "right": col += tilesAhead; break;
			default: break; // direzione sconosciuta: nessun offset, torna la cella corrente
		}
		return new int[]{col, row};
	}

	/** La SpecialTile a (col,row), o null se è terreno normale o fuori mappa. */
	public SpecialTile specialTileAt(int col, int row) {
		return gp.tileM.getSpecialTileAt(col, row);
	}

	/** true se (col,row) blocca il movimento — fuori mappa conta come bloccato. Non controlla
	 *  entità (npc/mostri) sopra: solo il terreno/le tile — vedi TODO in STATUS.md. */
	public boolean isTileCollisionAt(int col, int row) {
		if (col < 0 || row < 0 || col >= gp.maxWorldCol || row >= gp.maxWorldRow) return true;
		int id = gp.tileM.mapTileNum[col][row];
		return gp.tileM.tile[id].collision;
	}
}
