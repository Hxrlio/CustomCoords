package mc.hxrl.customcoords.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ShareCommand {
	
	static ArgumentBuilder<CommandSourceStack, ?> register() {
		
		return Commands.literal("share").executes(null);
		
	}
}
