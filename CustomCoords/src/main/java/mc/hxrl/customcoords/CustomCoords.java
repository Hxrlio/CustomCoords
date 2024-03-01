package mc.hxrl.customcoords;

import com.mojang.logging.LogUtils;
import mc.hxrl.customcoords.config.Config;
import mc.hxrl.customcoords.hud.CoordHudOverlay;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import org.slf4j.Logger;

@Mod("customcoords")
public class CustomCoords {
	
	public static final String MODID = "customcoords";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static CoordHudOverlay OVERLAY; // instance of the coord overlay so it can be used anywhere
    
    //see setupClient
    public static boolean CHECK_ITEM = false;
    public static boolean XAERO = false;
    public static int[] COLORS = {0, 0, 0};
	public static String PRE_X;
	public static String PRE_Y;
	public static String PRE_Z;
	public static String POST_X;
	public static String POST_Y;
	public static String POST_Z;

    public CustomCoords() {
    	//"hello, we exist and want to do things" type beat
        IEventBus mbus = FMLJavaModLoadingContext.get().getModEventBus();
        
        mbus.addListener(this::setupClient);
        
        MinecraftForge.EVENT_BUS.register(this);
        
        ModLoadingContext.get().registerConfig(Type.CLIENT, Config.CONFIG, "customcoords.toml");
        
    }
    
    private void setupClient(final FMLClientSetupEvent event) {
    	
    	OVERLAY = new CoordHudOverlay();
    	//stuff we need to do only once.
    	//it's neat to have these in a list for CoordHudOverlay.render (line 97)
    	COLORS[0] = Config.COLOR_PRE.get();
    	COLORS[1] = Config.COLOR_COORD.get();
    	COLORS[2] = Config.COLOR_POST.get();
    	
    	//need to decide if these things need to be shown at all, and if not we can just set it to blank and not do loads of pointless checks later.
    	PRE_X = (Config.SHOW_XZ.get()) ? Config.X_PRE_TEXT.get() : "";
    	POST_X = (Config.SHOW_XZ.get()) ? Config.X_POST_TEXT.get() : "";
    	PRE_Z = (Config.SHOW_XZ.get()) ? Config.Z_PRE_TEXT.get() : "";
    	POST_Z = (Config.SHOW_XZ.get()) ? Config.Z_POST_TEXT.get() : "";
    	PRE_Y = (Config.SHOW_Y.get()) ? Config.Y_PRE_TEXT.get() : "";
    	POST_Y = (Config.SHOW_XZ.get() && Config.SHOW_Y.get()) ? Config.Y_POST_TEXT.get() : "";
    	
    	ModList ml = ModList.get();
    	
    	//is some version of Xaero's minimap present?
		if (ml.isLoaded("xaerominimap") || ml.isLoaded("xaerominimapfair")) {
			XAERO = true;
		}
    	
		//if the required item in the config actually exists, then do see if we have it. When the resource location is empty, the air item gets returned instead.
    	if (!ForgeRegistries.ITEMS.getValue(new ResourceLocation(Config.REQ_ITEM.get())).toString().equals("air")) {
    		CHECK_ITEM = true;
    	}
    }
}
