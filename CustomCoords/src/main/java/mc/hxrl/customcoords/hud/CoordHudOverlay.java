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
		//if we shouldn't be showing right now, don't
		if (client.options.hideGui || client.options.renderDebug) {
			return;
		}
		
		Entity player = client.getCameraEntity();
		//if something gone wrong, abort
		if (player == null) {
			return;
		}
		
		if (CustomCoords.CHECK_ITEM) {
			//if we need to look for the item, we haven't found it yet
			found = false;
			
			for (int i = 0; i < 9; i++) {
				//but lets search the hotbar for it, 1 slot at a time
				if (client.player.getInventory().getItem(i).getItem().getRegistryName().toString().equals(Config.REQ_ITEM.get())) {
					//if we found it we don't need to keep looking
					found = true;
					break;
				
				}
				
			}
			//if it's not there we don't need to render
			if (!found) {
				return;
			}
		}
		//if the screens changed size since we last figured out where we belong
		if (client.getWindow().getHeight() != h) {
			//then lets figure it out again
			h = client.getWindow().getHeight();
			posY = (h*Config.POS_Y.get())/100;
				
			if (Config.POS_VERTICAL.get().equals("BOTTOM")) {
					
				posY = h - posY;
					
			}
				
		}
			
		if (client.getWindow().getWidth() != w) {
				
			w = client.getWindow().getWidth();
			posX = (w*Config.POS_X.get())/100;

			if (Config.POS_HORIZONTAL.get().equals("RIGHT")) {
					
				posX = w - posX;
					
			}
				
		}
		
		
		shownY = CoordLogic.coordCalcY(player.getBlockY());
		
		shownXZ = CoordLogic.coordCalcXZ(player.getBlockX(), player.getBlockZ());
		
		String[] text = {CustomCoords.PRE_X, shownXZ[0], CustomCoords.POST_X, CustomCoords.PRE_Y, shownY, CustomCoords.POST_Y, CustomCoords.PRE_Z, shownXZ[1], CustomCoords.POST_Z};
		int rollX = posX;
		//go through all the pieces of the coords and add them to the render
		for (int i = 0; i < 9; i++) {
			//but don't bother if it's an empty one
			if (!text[i].equals("")) {
				
				client.font.drawShadow(ps, text[i], rollX, posY, CustomCoords.COLORS[i % 3]);
				//rolling the x (screen coordinate) allows us to render different pieces separately
				//(so they can have different colours) without having them improperly placed.
				rollX = rollX + client.font.width(text[i]);
				
			}
		}
	}
}