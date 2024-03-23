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
	//this is a regular expression pattern. 1 or 0 "-" followed by 1 or more digits.
	private static Pattern pattern = Pattern.compile("-?[0-9]+");
	
	//client only stuff
	@EventBusSubscriber(modid = CustomCoords.MODID, value = Dist.CLIENT)
	public static class ClientForgeEvents {
		
		@SubscribeEvent
		public static void renderHudOverlays(RenderGameOverlayEvent e) {
			//if we aren't rendering through Xaero's do it ourselves
			if (!CustomCoords.XAERO || !Config.XAERO_INT.get()) {
				CustomCoords.OVERLAY.renderOverlay(e.getMatrixStack());
			}
		}
		
		@SubscribeEvent
		public static void catchChatCoords(ClientChatReceivedEvent e) {
			//if we aren't meant to be reading chat, don't
			if (!Config.READ_CHAT.get()) {
				return;
			}
			//if the server or a mod or a command sent the message then don't bother
			if (e.getSenderUUID().equals(Util.NIL_UUID)) {
				return;
			}
			//e.getMessage().getString() gives "<Player> Message".
			//we do not care about the player, and don't want to accidentally grab "<liltimmy11> I am 12 years old" as coords (11,12).
			//so we cut off the part of the message before the first ">", just leaving the actual message.
			String test = e.getMessage().getContents();
			CustomCoords.LOGGER.info("0: " + test);
			String chat = e.getMessage().getString();
			CustomCoords.LOGGER.info("1: " + chat);
			chat = chat.split(">", 2)[1];
			Matcher matcher = pattern.matcher(chat);
			//matches is all the numbers in the message
			List<String> matches = new ArrayList<String>();
			//we look for up to 3 matches
			for (int i = 0; i < 3; i++) {
				
				if (!matcher.find()) {
					//if we're out of matches, stop wasting time
					break;
					
				}
				//put the newly acquired number into matches
				matches.add(matcher.group());
				
			}
			
			int size = matches.size();
			
			if (size == 2) {
				//2 numbers could be coords (x, z)
				String[] finalMatches = {matches.get(0), matches.get(1)};
				Component message = new TextComponent(CoordTranslate.translateTo2OW(finalMatches));
				Minecraft client = Minecraft.getInstance();
				client.gui.getChat().addMessage(message);
				
			} else if (size == 3) {
				//3 numbers could be coords (x, y, z)
				String[] finalMatches = {matches.get(0), matches.get(1), matches.get(2)};
				Component message = new TextComponent(CoordTranslate.translateTo3OW(finalMatches));
				Minecraft client = Minecraft.getInstance();
				client.gui.getChat().addMessage(message);
			}
		}
		
		@SubscribeEvent
		public static void registerCommands(RegisterClientCommandsEvent e) {
			//register the /cc command
			CustomCoordCommand.register(e.getDispatcher());
			
		}
	}
}
