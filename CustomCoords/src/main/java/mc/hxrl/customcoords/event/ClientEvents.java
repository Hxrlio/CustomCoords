package mc.hxrl.customcoords.event;

import mc.hxrl.customcoords.CustomCoords;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class ClientEvents {
	
	@EventBusSubscriber(modid = CustomCoords.MODID, value = Dist.CLIENT)
	public static class ClientForgeEvents {
		
		@SubscribeEvent
		public static void renderHudOverlays(RenderGameOverlayEvent e) {
			
			CustomCoords.OVERLAY.renderOverlay(e.getMatrixStack());
			
		}
	}
}
