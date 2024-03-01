package mc.hxrl.customcoords.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import mc.hxrl.customcoords.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.Entity;

public class ShareCommand {
	
	static ArgumentBuilder<CommandSourceStack, ?> register() {
		//register /cc share with all its possible arguments
		return Commands.literal("share")
				//option 1: 2 arguments -> arguments must be x & z
				.then(Commands.argument("x", IntegerArgumentType.integer())
				.then(Commands.argument("z", IntegerArgumentType.integer())
				//so translate those and drop them in the chat
				.executes(ctx -> {
					int[] i = translateXZ(IntegerArgumentType.getInteger(ctx, "x"), IntegerArgumentType.getInteger(ctx, "z"));
					Minecraft client = Minecraft.getInstance();
					client.player.chat("(" + String.valueOf(i[0]) + ", " + String.valueOf(i[1]) + ")");
					return 0;
				})))
				//option 2: 3 arguments -> arguments must be x, y, & z
				.then(Commands.argument("x", IntegerArgumentType.integer())
				.then(Commands.argument("y", IntegerArgumentType.integer())
				.then(Commands.argument("z", IntegerArgumentType.integer())
				//so translate x & z, as well as y, and drop that in the chat
				.executes(ctx -> {
					int[] i = translateXZ(IntegerArgumentType.getInteger(ctx, "x"), IntegerArgumentType.getInteger(ctx, "z"));
					Minecraft client = Minecraft.getInstance();
					client.player.chat("(" + String.valueOf(i[0]) + ", " + translateY(IntegerArgumentType.getInteger(ctx, "y")) + ", " + String.valueOf(i[1]) + ")");
					return 0;
				}))))
				//option 3: 0 arguments -> player wants to share their own coordinates
				.executes(ctx -> {
					return shareSelf();
		});
	}
	
	static int translateY(int y) {
		
		return y - Config.Y_OFFSET.get();
		
	}
	
	static int[] translateXZ(int x, int z) {
		
		x = x - Config.X_OFFSET.get();
		z = z - Config.Z_OFFSET.get();
		//if we're in chunk-mode we need to 16x the number to get back to normal coords
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
		//if something is messed up, run away
		if (player == null) {
			return 0;
		}
		//send true (X, Z)
		client.player.chat("(" + String.valueOf(player.getBlockX()) + ", " + String.valueOf(player.getBlockZ()) + ")");
		
		return 0;
		
	}
}
