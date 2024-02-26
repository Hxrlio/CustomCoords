package mc.hxrl.customcoords.hud;

import com.mojang.blaze3d.vertex.PoseStack;
import mc.hxrl.customcoords.CustomCoords;
import mc.hxrl.customcoords.config.Config;
import mc.hxrl.customcoords.logic.CoordLogic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.entity.Entity;

public class CoordHudOverlay extends GuiComponent {
	
	private static boolean found;
	private static int w = 0;
	private static int h = 0;
	private static int posX = 0;
	private static int posY = 0;
	private static String shownY;
	private static String[]shownXZ = {"", ""};
	
	public void renderOverlay(PoseStack ps) {
		
		Minecraft client = Minecraft.getInstance();
		
		if (client.options.hideGui || client.options.renderDebug) {
			return;
		}
		
		Entity player = client.getCameraEntity();
		
		if (player == null) {
			return;
		}
		
		if (CustomCoords.CHECK_ITEM) {
			
			
			
			found = false;
			
			for (int i = 0; i < 9; i++) {
				
				if (client.player.getInventory().getItem(i).getItem().getRegistryName().toString().equals(Config.REQ_ITEM.get())) {
					
					found = true;
					break;
				
				}
				
			}
			
			if (!found) {
				return;
			}
		}
		
		if (client.screen != null) {
			if (client.screen.height != h) {
				
				h = client.screen.height;
				posY = (h*Config.POS_Y.get())/100;
				
				if (Config.POS_VERTICAL.get().equals("BOTTOM")) {
					
					posY = h - posY;
					
				}
				
			}
			
			if (client.screen.width != w) {
				
				w = client.screen.width;
				posX = (h*Config.POS_X.get())/100;

				if (Config.POS_HORIZONTAL.get().equals("RIGHT")) {
					
					posX = h - posX;
					
				}
				
			}
			
		}
		
		shownY = CoordLogic.coordCalcY(player.getBlockY());
		
		shownXZ = CoordLogic.coordCalcXZ(player.getBlockX(), player.getBlockZ());
		
		String[] text = {CustomCoords.PRE_X, shownXZ[0], CustomCoords.POST_X, CustomCoords.PRE_Y, shownY, CustomCoords.POST_Y, CustomCoords.PRE_Z, shownXZ[1], CustomCoords.POST_Z};
		int rollX = posX;
		
		for (int i = 0; i < 9; i++) {
			
			if (!text[i].equals("")) {
				
				client.font.drawShadow(ps, text[i], rollX, posY, CustomCoords.COLORS[i % 3]);
				rollX = rollX + client.font.width(text[i]);
				
			}
		}
	}
}