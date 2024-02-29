package mc.hxrl.customcoords.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;

public class CustomCoordCommand {
	
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		//register the main command /cc and register the arguments (share) elsewhere
		dispatcher.register(LiteralArgumentBuilder.<CommandSourceStack>literal("cc").then(ShareCommand.register()));
		
	}
}
