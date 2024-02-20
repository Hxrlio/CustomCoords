package mc.hxrl.customcoords.hud;

import java.text.DecimalFormat;
import com.mojang.blaze3d.vertex.PoseStack;

import mc.hxrl.customcoords.CustomCoords;
import mc.hxrl.customcoords.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.entity.Entity;

public class CoordHudOverlay extends GuiComponent {
	
	private static boolean found;
	private static boolean first = true;
	private static String preX = "";
	private static String preY = "";
	private static String preZ = "";
	private static String postX = "";
	private static String postY = "";
	private static String postZ = "";
	private static String shownY = "";
	private static String shownX = "";
	private static String shownZ = "";
	private static String reqItem = Config.REQ_ITEM.get();
	private static boolean showY = Config.SHOW_Y.get();
	private static boolean showXZ = Config.SHOW_XZ.get();
	private static DecimalFormat df = new DecimalFormat(Config.COORD_PREC.get());
	
	public void renderOverlay(PoseStack ps) {
		
		if (first) {
			if (showXZ) {
				preX = Config.X_PRE_TEXT.get();
				preZ = Config.Z_PRE_TEXT.get();
				postX = Config.X_POST_TEXT.get();
				postZ = Config.Z_POST_TEXT.get();
			}
			if (showY) {
				preY = Config.Y_PRE_TEXT.get();
			}
			if (showXZ && showY) {
				postY = Config.Y_POST_TEXT.get();
			}
			first = false;
		}
		
		Minecraft client = Minecraft.getInstance();
		
		if (client.options.hideGui || client.options.renderDebug) {
			return;
		}
		
		if (CustomCoords.CHECK_ITEM) {
			
			
			
			found = false;
			
			for (int i = 0; i < 9; i++) {
				
				if (client.player.getInventory().getItem(i).getItem().getRegistryName().toString().equals(reqItem)) {
					
					found = true;
					break;
				
				}
				
			}
			
			if (!found) {
				return;
			}
		}
		
		Entity player = client.getCameraEntity();
		
		if (player == null) {
			return;
		}
		
		if (showY) {
			double y = player.getY();
			shownY = df.format(y - 63);
		}
		
		if (showXZ) {
			double x = player.getX();
			double z = player.getZ();
			shownX = df.format(x);
			shownZ = df.format(z);
		}
		
		String text = preX + shownX + postX + preY + shownY + postY + preZ + shownZ + postZ;
		
		client.font.drawShadow(ps, text, 5, 5, 16777215);
		
	}
}