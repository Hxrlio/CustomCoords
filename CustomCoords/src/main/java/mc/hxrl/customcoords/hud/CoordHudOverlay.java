package mc.hxrl.customcoords.hud;

import java.text.DecimalFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.entity.Entity;

public class CoordHudOverlay extends GuiComponent {
	
	public void renderOverlay(PoseStack ps) {
		
		Minecraft client = Minecraft.getInstance();
		
		if (client.options.hideGui || client.options.renderDebug) {
			return;
		}
		
		Entity player = client.getCameraEntity();
		
		if (player == null) {
			return;
		}
		
		DecimalFormat df = new DecimalFormat("0");
		
		double y = player.getY();
		String shownY = df.format(y - 63);
		
		client.font.drawShadow(ps, shownY, 5, 5, 16777215);
		
	}
}