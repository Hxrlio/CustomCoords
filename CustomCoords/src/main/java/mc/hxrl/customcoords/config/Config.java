package mc.hxrl.customcoords.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
	
	public static final ForgeConfigSpec CONFIG;
	
	public static ForgeConfigSpec.BooleanValue showXZ;
	public static ForgeConfigSpec.BooleanValue showY;
	public static ForgeConfigSpec.BooleanValue xzChunk;
	public static ForgeConfigSpec.IntValue yOffset;
	public static ForgeConfigSpec.ConfigValue<String> reqItem;
	public static ForgeConfigSpec.ConfigValue<String> xPreText;
	public static ForgeConfigSpec.ConfigValue<String> yPreText;
	public static ForgeConfigSpec.ConfigValue<String> zPreText;
	public static ForgeConfigSpec.ConfigValue<String> xPostText;
	public static ForgeConfigSpec.ConfigValue<String> yPostText;
	public static ForgeConfigSpec.ConfigValue<String> zPostText;
	
	static {
		
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
		setup(builder);
		CONFIG = builder.build();
		
	}
	
	private static void setup(ForgeConfigSpec.Builder builder) {
		
		
		
	}
}
