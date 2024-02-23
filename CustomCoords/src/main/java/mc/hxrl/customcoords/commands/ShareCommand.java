package mc.hxrl.customcoords.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import mc.hxrl.customcoords.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;

public class ShareCommand {
	
	static ArgumentBuilder<CommandSourceStack, ?> register() {
		
		return Commands.literal("share")
				.then(Commands.argument("x", IntegerArgumentType.integer())
				.then(Commands.argument("z", IntegerArgumentType.integer())
				.executes(ctx -> {
					int[] i = translateXZ(IntegerArgumentType.getInteger(ctx, "x"), IntegerArgumentType.getInteger(ctx, "z"));
					Minecraft client = Minecraft.getInstance();
					client.player.chat("(" + String.valueOf(i[0]) + ", " + String.valueOf(i[1]) + ")");
					return 0;
				})))
				.then(Commands.argument("x", IntegerArgumentType.integer())
				.then(Commands.argument("y", IntegerArgumentType.integer())
				.then(Commands.argument("z", IntegerArgumentType.integer())
				.executes(ctx -> {
					int[] i = translateXZ(IntegerArgumentType.getInteger(ctx, "x"), IntegerArgumentType.getInteger(ctx, "z"));
					Minecraft client = Minecraft.getInstance();
					client.player.chat("(" + String.valueOf(i[0]) + ", " + translateY(IntegerArgumentType.getInteger(ctx, "y")) + ", " + String.valueOf(i[1]) + ")");
					return 0;
				}))))
				.executes(ctx -> {
			Minecraft client = Minecraft.getInstance();
			Component message = new TextComponent("yahoo");
			client.gui.getChat().addMessage(message);
			return shareSelf();
		});
	}
	
	static int translateY(int y) {
		
		return y - Config.Y_OFFSET.get();
		
	}
	
	static int[] translateXZ(int x, int z) {
		
		x = x - Config.X_OFFSET.get();
		z = z - Config.Z_OFFSET.get();
		
		if (Config.XZ_CHUNK.get()) {
			
			x = x*16;
			z = z*16;
			
		}
		
		int[] r = {x, z};
		return r;
		
	}
	
	static int shareSelf() {
		
		Minecraft client = Minecraft.getInstance();
		Entity player = client.getCameraEntity();
		
		if (player == null) {
			return 0;
		}
		
		client.player.chat("(" + String.valueOf(player.getBlockX()) + ", " + String.valueOf(player.getBlockZ()) + ")");
		
		return 0;
		
	}
}
