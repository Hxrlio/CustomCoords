package mc.hxrl.customcoords.event;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mc.hxrl.customcoords.CustomCoords;
import mc.hxrl.customcoords.commands.CustomCoordCommand;
import mc.hxrl.customcoords.config.Config;
import mc.hxrl.customcoords.util.CoordTranslate;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class ClientEvents {
	
	private static Pattern pattern = Pattern.compile("-?[0-9]+");
	
	@EventBusSubscriber(modid = CustomCoords.MODID, value = Dist.CLIENT)
	public static class ClientForgeEvents {
		
		@SubscribeEvent
		public static void renderHudOverlays(RenderGameOverlayEvent e) {
			
			CustomCoords.OVERLAY.renderOverlay(e.getMatrixStack());
			
		}
		
		@SubscribeEvent
		public static void catchChatCoords(ClientChatReceivedEvent e) {
			
			if (!Config.READ_CHAT.get()) {
				return;
			}
			
			if (e.getSenderUUID() == Util.NIL_UUID) {
				return;
			}
			
			String chat = e.getMessage().getString().split(">", 2)[1];
			Matcher matcher = pattern.matcher(chat);
			List<String> matches = new ArrayList<String>();
			
			for (int i = 0; i < 3; i++) {
				
				if (!matcher.find()) {
					
					break;
					
				}
				
				matches.add(matcher.group());
				
			}
			
			int size = matches.size();
			
			if (size == 2) {
				
				String[] finalMatches = {matches.get(0), matches.get(1)};
				Component message = new TextComponent(CoordTranslate.translateTo2OW(finalMatches));
				Minecraft client = Minecraft.getInstance();
				client.gui.getChat().addMessage(message);
				
			} else if (size == 3) {
				
				String[] finalMatches = {matches.get(0), matches.get(1), matches.get(2)};
				Component message = new TextComponent(CoordTranslate.translateTo3OW(finalMatches));
				Minecraft client = Minecraft.getInstance();
				client.gui.getChat().addMessage(message);
				
			}
		}
		
		@SubscribeEvent
		public static void registerCommands(RegisterClientCommandsEvent e) {
			CustomCoordCommand.register(e.getDispatcher());
			
		}
	}
}
