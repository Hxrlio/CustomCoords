package mc.hxrl.customcoords.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
	
	public static final ForgeConfigSpec CONFIG;
	
	public static ForgeConfigSpec.BooleanValue SHOW_XZ; // implemented
	public static ForgeConfigSpec.BooleanValue SHOW_Y; // implemented
	public static ForgeConfigSpec.BooleanValue XZ_CHUNK; // added
	public static ForgeConfigSpec.BooleanValue XZ_NETHER; // added
	public static ForgeConfigSpec.IntValue X_OFFSET; // added
	public static ForgeConfigSpec.IntValue Y_OFFSET; // added
	public static ForgeConfigSpec.IntValue Z_OFFSET; // added
	public static ForgeConfigSpec.IntValue POS_X; // added
	public static ForgeConfigSpec.IntValue POS_Y; // added
	public static ForgeConfigSpec.IntValue COLOR_PRE; // added
	public static ForgeConfigSpec.IntValue COLOR_COORD; // added
	public static ForgeConfigSpec.IntValue COLOR_POST; // added
	public static ForgeConfigSpec.ConfigValue<String> REQ_ITEM; // implemented
	public static ForgeConfigSpec.ConfigValue<String> X_PRE_TEXT; // implemented
	public static ForgeConfigSpec.ConfigValue<String> Y_PRE_TEXT; // implemented
	public static ForgeConfigSpec.ConfigValue<String> Z_PRE_TEXT; // implemented
	public static ForgeConfigSpec.ConfigValue<String> X_POST_TEXT; // implemented
	public static ForgeConfigSpec.ConfigValue<String> Y_POST_TEXT; // implemented
	public static ForgeConfigSpec.ConfigValue<String> Z_POST_TEXT; // implemented
	public static ForgeConfigSpec.ConfigValue<String> COORD_PREC; // implemented
	public static ForgeConfigSpec.ConfigValue<String> POS_HORIZONTAL; // added
	public static ForgeConfigSpec.ConfigValue<String> POS_VERTICAL; // added
	
	static {
		
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
		setup(builder);
		CONFIG = builder.build();
		
	}
	
	private static void setup(ForgeConfigSpec.Builder builder) {
		
		builder.push("Required item for coords to show (blank = none)");
		REQ_ITEM = builder.define("Item", "minecraft:compass");
		builder.pop();
		
		builder.push("Which coords should be displayed?");
		SHOW_XZ = builder.define("X & Z", true);
		SHOW_Y = builder.define("Y", true);
		builder.pop();
		
		builder.push("Where should they be on the screen?");
		builder.comment("Relative to left or right of screen?");
		POS_HORIZONTAL = builder.define("Valid Inputs: LEFT / RIGHT", "LEFT");
		builder.comment("Relative to top or bottom of screen?");
		POS_VERTICAL = builder.define("Valid Inputs: TOP / BOTTOM", "TOP");
		POS_X = builder.defineInRange("% screen width", 1, 0, 100);
		POS_Y = builder.defineInRange("% screen height", 1, 0, 100);
		builder.pop();
		
		builder.push("Customise coord values");
		XZ_CHUNK = builder.define("XZ by Chunk? (opposed to by Block)", false);
		XZ_NETHER = builder.define("XZ in Nether refers to Overworld?", false);
		X_OFFSET = builder.defineInRange("X offset", 0, -30000000, 30000000);
		Z_OFFSET = builder.defineInRange("Z offset", 0, -30000000, 30000000);
		Y_OFFSET = builder.defineInRange("Y offset", -63, -256, 64);
		builder.pop();
		
		builder.push("Customise coord visuals");
		COORD_PREC = builder.define("Digit display of coords", "0");
		COLOR_PRE = builder.defineInRange("Color of text before each coordinate", 16777215, 0, 16777215);
		COLOR_COORD = builder.defineInRange("Color of coordinates", 16777215, 0, 16777215);
		COLOR_POST = builder.defineInRange("Color of text after each coordinate", 16777215, 0, 16777215);
		X_PRE_TEXT = builder.define("Text before X", "");
		Y_PRE_TEXT = builder.define("Text before Y", "");
		Z_PRE_TEXT = builder.define("Text before Z", "");
		X_POST_TEXT = builder.define("Text after X", ", ");
		Y_POST_TEXT = builder.define("Text after Y", ", ");
		Z_POST_TEXT = builder.define("Text after Z", "");
		builder.pop();
	}
}
