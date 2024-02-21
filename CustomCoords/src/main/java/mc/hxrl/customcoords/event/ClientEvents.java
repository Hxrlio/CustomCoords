package mc.hxrl.customcoords.event;

import java.util.regex.Pattern;

import mc.hxrl.customcoords.CustomCoords;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
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
		
		@SubscribeEvent
		public static void catchChatCoords(ClientChatReceivedEvent e) {
			String chat = e.getMessage().getString().split(">", 2)[1];
			CustomCoords.LOGGER.info(chat);
			Pattern pattern = Pattern.compile("0");
		}
	}
}
