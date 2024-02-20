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
	private static boolean bottom = false;
	private static boolean right = false;
	private static int w = 0;
	private static int h = 0;
	private static int posX = 0;
	private static int posY = 0;
	private static int percX;;
	private static int percY;
	private static int[] colors = {0, 0, 0};
	private static String preX = "";
	private static String preY = "";
	private static String preZ = "";
	private static String postX = "";
	private static String postY = "";
	private static String postZ = "";
	private static String shownY = "";
	private static String shownX = "";
	private static String shownZ = "";
	private static String reqItem;
	private static boolean showY;
	private static boolean showXZ;
	private static DecimalFormat df;
	
	public void renderOverlay(PoseStack ps) {
		
		if (first) {
			
			percX = Config.POS_X.get();
			percY = Config.POS_Y.get();
			colors[0] = Config.COLOR_PRE.get();
			colors[1] = Config.COLOR_COORD.get();
			colors[2] = Config.COLOR_POST.get();
			reqItem = Config.REQ_ITEM.get();
			showY = Config.SHOW_Y.get();
			showXZ = Config.SHOW_XZ.get();
			df = new DecimalFormat(Config.COORD_PREC.get());
			
			if (Config.POS_VERTICAL.get().equals("BOTTOM")) {
				
				bottom = true;
				
			}
			
			if (Config.POS_HORIZONTAL.get().equals("RIGHT")) {
				
				right = true;
				
			}
			
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
		
		Entity player = client.getCameraEntity();
		
		if (player == null) {
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
		
		if (client.screen != null) {
			if (client.screen.height != h) {
				
				h = client.screen.height;
				posY = (h*percY)/100;
				
				if (bottom) {
					
					posY = h - posY;
					
				}
				
			}
			
			if (client.screen.width != w) {
				
				w = client.screen.width;
				posX = (h*percX)/100;

				if (right) {
					
					posX = h - posX;
					
				}
				
			}
			
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
		
		String[] text = {preX, shownX, postX, preY, shownY, postY, preZ, shownZ, postZ};
		int rollX = posX;
		
		for (int i = 0; i < 9; i++) {
			
			if (!text[i].equals("")) {
				
				client.font.drawShadow(ps, text[i], rollX, posY, colors[i % 3]);
				rollX = rollX + client.font.width(text[i]);
				
			}
			
		}
		
	}
	
}